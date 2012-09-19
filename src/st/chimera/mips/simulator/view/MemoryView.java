/*
 * 作成日: 2003/08/09
 */
package st.chimera.mips.simulator.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import st.chimera.mips.simulator.*;
import st.chimera.mips.simulator.gate.*;
import st.chimera.mips.simulator.logic.*;
import st.chimera.util.*;

public class MemoryView extends JComponent {
	JTable table;
	public MemoryView() {
		setPreferredSize(new Dimension(356, 172));
		setBorder(new TitledBorder("Memory View"));
		setLayout(new BorderLayout());

		table = new JTable(new AbstractTableModel() {
			public int getColumnCount() {
				return 5;
			}
			public int getRowCount() {
				return Memory.MEMORY_SIZE / 16;
			}
			public Object getValueAt(int row, int col) {
				if (col == 0) {
					return "[" + Code.to16bitHexString(row * 16) + "]";
				} else {
					return Code.toSpaced32bitHexString(
						Architecture.getInstance().getMemory(row * 4 + (col - 1)));
				}
			}
			public String getColumnName(int column) {
				if (column == 0) {
					return "offset";
				} else { 
					return "[" + Code.to8bitHexString((column - 1) * 4) + "]";
				}
			}
		});
		add(new JScrollPane(table), BorderLayout.CENTER);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);
		table.setShowGrid(false);
		table.setEnabled(false);
		table.setRowHeight(12);
		table.setBackground(Color.WHITE);

		DefaultTableCellRenderer colorRenderer = new DefaultTableCellRenderer() {
			public void setValue(Object value) {
				if (value instanceof String) {
					String c = (String)value;
					setText(c);
				} else {
					super.setValue(value);
				}
			}
		};
		colorRenderer.setHorizontalAlignment(JLabel.CENTER);

		TableColumnModel tcm = table.getColumnModel();
		tcm.setColumnSelectionAllowed(false);
		for (int i = 0; i < tcm.getColumnCount(); ++i) {
			TableColumn tc = tcm.getColumn(i);
			tc.setResizable(false);
			tc.setPreferredWidth(72);
			tc.setCellRenderer(colorRenderer);
		}
		tcm.getColumn(0).setPreferredWidth(40);

		Architecture.getInstance().addCallBeforeClockEdge(new Clock.Callback() {
			public void call() {
				table.repaint();
			}
		});
	}
}
