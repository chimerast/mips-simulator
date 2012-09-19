/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.logic;

import st.chimera.mips.simulator.gate.*;

/**
 * J形式命令用にProgramCounterの上位4ビットをアドレスに
 * 付け加える。
 * 
 * @author chimera
 */
public class AddressMerger extends Gate {
	DataPath output;
	DataPath lower;
	DataPath upper;
	
	/** 
	 * コンストラクタ。
	 */
	public AddressMerger() {
		super("AddressMerger");
		output = new DataPath(32);
	}
	
	/**
	 * 出力データパス。
	 * 
	 * @return データパス
	 */
	public DataPath getOutput() {
		return output;
	}

	/**
	 * 入力下位28ビットデータパス。
	 * 
	 * @param path 
	 */
	public void setLower(DataPath path) {
		lower = path;
		path.addGate(this);
	}
	

	/**
	 * 入力上位4ビットデータパス。
	 * 
	 * @param path 
	 */
	public void setUpper(DataPath path) {
		upper = path;
		path.addGate(this);
	}
	
	protected void update() {
		output.set((upper.get() << 28) | lower.get());
	}
}
