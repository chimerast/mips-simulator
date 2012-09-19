/*
 * 作成日: 2003/08/01
 */
package st.chimera.mips.simulator.gate;

/**
 * n入力マルチプレクサ。
 * 
 * @author chimera
 */
public class MultiPlexor extends Gate {
	private DataPath[] input;
	private DataPath output;
	private DataPath selection;

	/**
	 * コンストラクタ。
	 * 
	 * @param n 入力数
	 * @param bit ビット数
	 */
	public MultiPlexor(int n, int bit) {
		super("MultiPlexor");
		input = new DataPath[n];
		output = new DataPath(bit);
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
	 * @param index インデックス
	 */
	public void setInput(DataPath path, int index) {
		input[index] = path;
		path.addGate(this);
	}
	
	/**
	 * 選択データパス。
	 * 
	 * @param path 選択
	 */
	public void setSelection(DataPath path) {
		selection = path;
		path.addGate(this);
	}
		
	protected void update() {
		output.set(input[selection.get()].get());
	}
}
