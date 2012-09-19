package st.chimera.util;

public class Code {
	public static String to8bitHexString(int n) {
		String str = Integer.toHexString(n);
		while (str.length() != 2)
			str = "0" + str;
		return str;
	}

	public static String to16bitHexString(int n) {
		String str = Integer.toHexString(n);
		while (str.length() != 4)
			str = "0" + str;
		return str;
	}

	public static String to32bitHexString(int n) {
		String str = Integer.toHexString(n);
		while (str.length() != 8)
			str = "0" + str;
		return str;
	}

	public static String toSpaced32bitHexString(int n) {
		String str = to32bitHexString(n);
		return str.substring(0, 2)
			+ " "
			+ str.substring(2, 4)
			+ " "
			+ str.substring(4, 6)
			+ " "
			+ str.substring(6, 8);
	}
}
