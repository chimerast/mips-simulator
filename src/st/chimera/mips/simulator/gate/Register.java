/*
 * 作成日: 2003/08/01
 */
package st.chimera.mips.simulator.gate;

import java.util.*;

/**
 * レジスタ。
 * 
 * @author chimera
 */
public class Register {
	private static List registers = new LinkedList();

	/**
	 * クロックエッジ。全てのレジスタの内部状態を切り替える。
	 */
	public static void clockEdge() {
		// Register -> Register の直結があるため二段構成
		Iterator itr = registers.iterator();
		while (itr.hasNext())
			 ((Register)itr.next()).updateState();
		itr = registers.iterator();
		while (itr.hasNext())
			 ((Register)itr.next()).updateOutput();
	}

	/**
	 * 全てのレジスタを初期化する。
	 */
	public static void resetAllRegister() {
		Iterator itr = registers.iterator();
		while (itr.hasNext()) {
			Register register = (Register)itr.next();
			register.state = 0;
			register.output.set(0);
		}		
	}

	private DataPath input;
	private DataPath output;
	private int state;
	private DataPath write;
	private boolean useWrite;
	private int updated;

	/**
	 * コンストラクタ。
	 * 
	 * @param use 書き込みフラグの使用
	 */
	public Register(boolean use) {
		registers.add(this);
		
		output = new DataPath(32);
		useWrite = use;
		updated = 0;
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
	 * 保持している値の取得。
	 * 
	 * @return 現在の値
	 */
	public int getValue() {
		return state;
	}

	/**
	 * 更新フラグ。
	 * 
	 * @return 更新フラグ
	 */
	public boolean getUpdated() {
		return updated != 0;
	}

	/**
	 * 値の設定。主にスタックポインタ設定用。
	 * 
	 * @param value 値
	 */
	public void setValue(int value) {
		state = value;
		output.set(state);
	}

	/**
	 * 入力データパス。
	 * 
	 * @param path 入力
	 */
	public void setInput(DataPath path) {
		input = path;
	}

	/**
	 * 書き込みデータパス。
	 * 
	 * @param path 書き込みフラグ
	 */
	public void setWrite(DataPath path) {
		write = path;
	}

	protected void updateOutput() {
		output.set(state);
	}

	protected void updateState() {
		if (updated > 0)
			--updated;
		if (!(useWrite && !write.get(0))) {
			state = input.get();
			updated = 4;
		}
	}
}
