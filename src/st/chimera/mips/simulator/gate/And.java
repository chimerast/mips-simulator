/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.gate;

/**
 * Andゲート。
 * 
 * @author chimera
 */
public class And extends Gate {
	private DataPath input1;
	private DataPath input2;
	private DataPath output;
	
	/**
	 * コンストラクタ
	 */
	public And() {
		super("And");
		output = new DataPath(1);
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
	 * @param path 入力１
	 */
	public void setInput1(DataPath path) {
		input1 = path;
		path.addGate(this);
	}

	/**
	 * 入力データパス。
	 * 
	 * @param path 入力２
	 */
	public void setInput2(DataPath path) {
		input2 = path;
		path.addGate(this);
	}

	protected void update() {
		output.set(input1.get(0) & input2.get(0), 0);
	}
}
