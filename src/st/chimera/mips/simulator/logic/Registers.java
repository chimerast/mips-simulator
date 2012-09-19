/*
 * 作成日: 2003/08/01
 */
package st.chimera.mips.simulator.logic;

import st.chimera.mips.simulator.gate.*;

/**
 * Registers。
 * 
 * @author chimera
 */
public class Registers {
	private MultiPlexor mux1;
	private MultiPlexor mux2;
	private Nto1Decorder nto1;
	private Register[] register;
	private And[] and;

	public Registers() {

		register = new Register[32];
		for (int i = 0; i < register.length; ++i)
			register[i] = new Register(true);
		and = new And[32];
		for (int i = 0; i < and.length; ++i)
			and[i] = new And();

		nto1 = new Nto1Decorder(32);
		mux1 = new MultiPlexor(32, 32);
		mux2 = new MultiPlexor(32, 32);

		for (int i = 0; i < register.length; ++i) {
			and[i].setInput2(nto1.getOutput(i));
			register[i].setWrite(and[i].getOutput());
			mux1.setInput(register[i].getOutput(), i);
			mux2.setInput(register[i].getOutput(), i);
		}
	}

	public DataPath getOutput1() {
		return mux1.getOutput();
	}

	public DataPath getOutput2() {
		return mux2.getOutput();
	}
	
	public int[] getRegisterValues() {
		int[] ret = new int[32];
		for (int i = 0; i < register.length; ++i)
			ret[i] = register[i].getValue();
		return ret;
	}
	
	public boolean[] getRegisterUpdated() {
		boolean[] ret = new boolean[32];
		for (int i = 0; i < register.length; ++i)
			ret[i] = register[i].getUpdated();
		return ret;
	}

	public void setInput(DataPath path) {
		for (int i = 0; i < register.length; ++i)
			register[i].setInput(path);
	}
	
	public void setReadNumber1(DataPath path) {
		mux1.setSelection(path);
	}

	public void setReadNumber2(DataPath path) {
		mux2.setSelection(path);
	}
	
	public void setWriteNumber(DataPath path) {
		nto1.setInput(path);
	}
	
	public void setWrite(DataPath path) {
		for (int i = 0; i < and.length; ++i) 
			and[i].setInput1(path);
	}
	
	public void setSP(int value) {
		register[29].setValue(value);
	}
}
