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

public class ControlOutputView extends JComponent {
	private static final String[] bitname = { 
		"pcw", "pcwc", "iord", "memr", "memw", "irw", "mtor", 
		"pcsrc1", "pcsrc0", "aluop1", "aluop0", "alusb2", "alusb1", "alusb0", 
		"alusa", "regw", "regdst1", "regdst0" };
	private static final Color ON_COLOR = Color.BLACK;

	public ControlOutputView() {
		setPreferredSize(new Dimension(120, 172));
		setBorder(new TitledBorder("Control Output"));
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
		
		int output = Architecture.getInstance().getControlOutput();
		for (int i = 0; i < bitname.length; ++i) {
			if (((output >> (17 - i)) & 1) == 1)
				gx.setPaint(ON_COLOR);
			else
				gx.setPaint(Color.WHITE);
				
			gx.fillRect(0 + (i / 9) * 56, (i % 9) * 16, 7, 15);
			gx.setPaint(Color.BLACK);
			gx.drawRect(0 + (i / 9) * 56, (i % 9) * 16, 7, 15);
			gx.drawString(bitname[i], 10 + (i / 9) * 56, (i % 9) * 16 + 12);
		}
				
		gx.setTransform(save);
	}
}
