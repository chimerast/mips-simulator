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
import st.chimera.util.*;

public class SourceView extends JComponent {
	public JTable table;
	JScrollPane pane;
	public SourceView() {
		setPreferredSize(new Dimension(404, 320));
		setBorder(new TitledBorder("Source View"));
		setLayout(new BorderLayout());

		table = new JTable();
		pane = new JScrollPane(table);
		add(pane, BorderLayout.CENTER);
		updateTable();

		Architecture.getInstance().addCallBeforeClockEdge(new Clock.Callback() {
			public void call() {
				int pc = Architecture.getInstance().getRealProgramCount();
				if (pc / 4 < table.getRowCount())
					table.setRowSelectionInterval(pc / 4, pc / 4);
			}
		});
	}

	public void updateTable() {
		table.setModel(new AbstractTableModel() {
			public int getColumnCount() {
				return 3;
			}
			public int getRowCount() {
				return Architecture.getInstance().getSourceCount();
			}
			public Object getValueAt(int row, int col) {
				switch (col) {
					case 0 :
						return "[" + Code.to16bitHexString(row * 4) + "]";
					case 1 :
						return Code.to32bitHexString(Architecture.getInstance().getMemory(row));
					case 2 :
						return Architecture.getInstance().getSourceCode(row);
				}
				return null;
			}
			public String getColumnName(int column) {
				switch (column) {
					case 0 :
						return "offset";
					case 1 :
						return "code";
					case 2 :
						return "source";
				}
				return null;
			}
		});
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);
		table.setShowGrid(false);
		table.setEnabled(false);
		table.setRowHeight(12);
		table.setBackground(Color.WHITE);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		tcm.getColumn(0).setResizable(false);
		tcm.getColumn(0).setCellRenderer(colorRenderer);
		tcm.getColumn(0).setPreferredWidth(40);
		tcm.getColumn(1).setResizable(false);
		tcm.getColumn(1).setCellRenderer(colorRenderer);
		tcm.getColumn(1).setPreferredWidth(56);
		tcm.getColumn(2).setPreferredWidth(280);
	}
}
