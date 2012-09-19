/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.logic;

import st.chimera.mips.simulator.gate.*;

/**
 * 16bit -> 32bit拡張。
 * 
 * @author chimera
 */
public class SignExtender extends Gate {
	private DataPath input;
	private DataPath output;

	/**
	 * コンストラクタ
	 */
	public SignExtender() {
		super("SignExtender");
		output = new DataPath(32);
	}

	/**
	 * 出力データパス。
	 * 
	 * @return 出力
	 */	
	public DataPath getOutput() {
		return output;
	}

	/**
	 * 入力データパス。
	 * 
	 * @param path 入力
	 */
	public void setInput(DataPath path) {
		input = path;
		path.addGate(this);
	}
	
	public void update() {
		output.set((input.get() << 16) >> 16);
	}
}
