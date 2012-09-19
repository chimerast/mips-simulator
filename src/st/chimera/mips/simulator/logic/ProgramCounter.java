/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.logic;

import st.chimera.mips.simulator.gate.*;

/**
 * ProgramCounter。
 * 
 * @author chimera
 */
public class ProgramCounter extends Gate {
	private Register register;
	private DataPath upper;
	private DataPath output;

	public ProgramCounter() {
		super("ProgramCounter");
		register = new Register(true);
		upper = new DataPath(6);
		output = register.getOutput();
		output.addGate(this);
	}

	public void setInput(DataPath path) {
		register.setInput(path);
	}

	public void setWrite(DataPath path) {
		register.setWrite(path);
	}

	public int getCount() {
		return register.getValue();
	}

	public DataPath getOutput() {
		return register.getOutput();
	}

	public DataPath getUpper() {
		return upper;
	}

	public void update() {
		upper.set(output.get() >>> 26);
	}
}
