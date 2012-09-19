package st.chimera.theme;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;

public class ChimeraMetalTheme extends DefaultMetalTheme {
	private final FontUIResource controlTextFont =
		new FontUIResource("Monospaced", FontUIResource.PLAIN, 12);
	private final FontUIResource menuTextFont =
		new FontUIResource("Monospaced", FontUIResource.PLAIN, 12);
	private final FontUIResource subTextFont =
		new FontUIResource("Monospaced", FontUIResource.PLAIN, 12);
	private final FontUIResource systemTextFont =
		new FontUIResource("Monospaced", FontUIResource.PLAIN, 12);
	private final FontUIResource userTextFont =
		new FontUIResource("Monospaced", FontUIResource.PLAIN, 12);
	private final FontUIResource windowTitleFont =
		new FontUIResource("Monospaced", FontUIResource.PLAIN, 12);
	private final ColorUIResource white = new ColorUIResource(Color.WHITE);
	private final ColorUIResource black = new ColorUIResource(Color.BLACK);

	public FontUIResource getControlTextFont() {
		return controlTextFont;
	}
	public FontUIResource getMenuTextFont() {
		return menuTextFont;
	}
	public FontUIResource getSubTextFont() {
		return subTextFont;
	}
	public FontUIResource getSystemTextFont() {
		return systemTextFont;
	}
	public FontUIResource getUserTextFont() {
		return userTextFont;
	}
	private final ColorUIResource primary1 = new ColorUIResource(0, 0, 0);
	private final ColorUIResource primary2 = new ColorUIResource(204, 204, 204);
	private final ColorUIResource primary3 = new ColorUIResource(255, 255, 255);
	private final ColorUIResource primaryHighlight = new ColorUIResource(102, 102, 102);

	private final ColorUIResource secondary2 = new ColorUIResource(204, 204, 204);
	private final ColorUIResource secondary3 = new ColorUIResource(255, 255, 255);
	private final ColorUIResource controlHighlight = new ColorUIResource(102, 102, 102);

	protected ColorUIResource getPrimary1() {
		return primary1;
	}
	protected ColorUIResource getPrimary2() {
		return primary2;
	}
	protected ColorUIResource getPrimary3() {
		return primary3;
	}
	public ColorUIResource getPrimaryControlHighlight() {
		return primaryHighlight;
	}
	protected ColorUIResource getSecondary2() {
		return secondary2;
	}
	protected ColorUIResource getSecondary3() {
		return secondary3;
	}
	public ColorUIResource getControlHighlight() {
		return super.getSecondary3();
	}
	public ColorUIResource getFocusColor() {
		return getBlack();
	}
	public ColorUIResource getTextHighlightColor() {
		return getBlack();
	}
	public ColorUIResource getHighlightedTextColor() {
		return getWhite();
	}
	public ColorUIResource getMenuSelectedBackground() {
		return getBlack();
	}
	public ColorUIResource getMenuSelectedForeground() {
		return getWhite();
	}
	public ColorUIResource getAcceleratorForeground() {
		return getBlack();
	}
	public ColorUIResource getAcceleratorSelectedForeground() {
		return getWhite();
	}

	public void addCustomEntriesToTable(UIDefaults table) {
		Border blackLineBorder = new BorderUIResource(new LineBorder(getBlack()));

		Object textBorder =
			new BorderUIResource(
				new CompoundBorder(blackLineBorder, new BasicBorders.MarginBorder()));

		table.put("ToolTip.border", blackLineBorder);
		table.put("TitledBorder.border", blackLineBorder);

		table.put("TextField.border", textBorder);
		table.put("PasswordField.border", textBorder);
		table.put("TextArea.border", textBorder);
		table.put("TextPane.border", textBorder);
		table.put("EditorPane.border", textBorder);
	}
}
