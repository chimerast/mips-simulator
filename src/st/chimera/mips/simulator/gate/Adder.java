/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.gate;

/**
 * 加算器。
 * 
 * @author chimera
 */
public class Adder extends Gate {
	private DataPath input1;
	private DataPath input2;
	private DataPath output;

	/**
	 * コンストラクタ。
	 */
	public Adder() {
		super("Adder");
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
		output.set(input1.get() + input2.get());
	}
}
