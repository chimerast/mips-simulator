/*
 * 作成日: 2003/08/01
 */
package st.chimera.mips.simulator.gate;

/**
 * Nto1デコーダ。
 * 
 * @author chimera
 */
public class Nto1Decorder extends Gate {
	private DataPath input;
	private DataPath[] output;
	private DataPath selection;

	/**
	 * コンストラクタ。
	 * 
	 * @param n 入力ビット数
	 */
	public Nto1Decorder(int n) {
		super("Nto1Decorder");
		output = new DataPath[n];
		for (int i = 0; i < output.length; ++i)
			output[i] = new DataPath(1);
	}

	/**
	 * 出力データパス。
	 * 
	 * @param index インデックス
	 * @return 出力
	 */
	public DataPath getOutput(int index) {
		return output[index];
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

	protected void update() {
		for (int i = 0; i < output.length; ++i)
			output[i].set(input.get() == i, 0);
	}
}
