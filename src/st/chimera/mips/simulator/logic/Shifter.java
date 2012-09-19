/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.logic;

import st.chimera.mips.simulator.gate.*;

/**
 * ジャンプ命令用2bitシフト器。
 * 
 * @author chimera
 */
public class Shifter extends Gate {
	private DataPath input;
	private DataPath output;

	/**
	 * コンストラクタ。
	 */
	public Shifter() {
		super("Shifter");
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
		output.set(input.get() << 2);
	}
}
