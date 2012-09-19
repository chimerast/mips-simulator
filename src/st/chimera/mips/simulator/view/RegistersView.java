/*
 * 作成日: 2003/08/03
 */
package st.chimera.mips.simulator.view;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.border.*;

import st.chimera.mips.simulator.*;
import st.chimera.mips.simulator.gate.*;
import st.chimera.util.*;

public class RegistersView extends JComponent {
	private static final String[] regname = {
		"r0", "at", "v0", "v1", "a0", "a1", "a2", "a3", 
		"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7",
		"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
		"t8", "t9", "k0", "k1", "gp", "sp", "fp", "ra"  };

	public RegistersView() {
		setPreferredSize(new Dimension(284, 172));
		setBorder(new TitledBorder("Register"));
		
		Architecture.getInstance().addCallBeforeClockEdge(new Clock.Callback() {
			public void call() {
				repaint();
			}
		});
	}

	protected void paintComponent(Graphics g) {
		Insets insets = getInsets();

		Graphics2D gx = (Graphics2D)g;
		gx.setFont(new Font("Monospaced", Font.PLAIN, 12));
		AffineTransform save = gx.getTransform();
		gx.translate(insets.left, insets.top);

		gx.drawString("PC", 0, 12);
		gx.drawString(Code.to32bitHexString(Architecture.getInstance().getProgramCount()), 16, 12);

		int[] values = Architecture.getInstance().getRegisterValues();
		boolean[] updated = Architecture.getInstance().getRegisterUpdated();
		for (int i = 0; i < 32; ++i) {
			if (updated[i])
				gx.setPaint(Color.BLACK);
			else
				gx.setPaint(Color.DARK_GRAY);

			gx.drawString(regname[i], (i / 8) * 70 + 0, (i % 8 + 1) * 16 + 12);
			gx.drawString(
				Code.to32bitHexString(values[i]),
				(i / 8) * 70 + 16,
				(i % 8 + 1) * 16 + 12);
		}

		gx.setTransform(save);
	}
}
