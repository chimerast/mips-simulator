/*
 * 作成日: 2003/08/01
 */
package st.chimera.mips.simulator.logic;

import st.chimera.mips.simulator.gate.*;

/**
 * ALU。
 * 
 * @author chimera
 */
public class ALU extends Gate {
	public static final int AND = 0;
	public static final int OR  = 1;
	public static final int ADD = 2;
	public static final int SUB = 6;
	public static final int SLT = 7;

	private DataPath input1;
	private DataPath input2;
	private DataPath operation;
	private DataPath output;
	private DataPath zero;

	public ALU() {
		super("ArithmeticLogicUnit");
		output = new DataPath(32);
		zero = new DataPath(1);
	}

	public DataPath getOutput() {
		return output;
	}

	public DataPath getZero() {
		return zero;
	}

	public void setInput1(DataPath path) {
		input1 = path;
		path.addGate(this);
	}

	public void setInput2(DataPath path) {
		input2 = path;
		path.addGate(this);
	}

	public void setOperation(DataPath path) {
		operation = path;
		path.addGate(this);
	}

	public void update() {
		int src1 = input1.get();
		int src2 = input2.get();
		int result = 0;

		switch (operation.get()) {
			case AND :
				result = src1 & src2;
				break;
			case OR :
				result = src1 | src2;
				break;
			case ADD :
				result = src1 + src2;
				break;
			case SUB :
				result = src1 - src2;
				break;
			case SLT :
				result = src1 < src2 ? 1 : 0;
				break;
			default :
				System.err.println("unsupported operation in ALU");
				break;
		}

		output.set(result);
		zero.set(result == 0, 0);
	}
}
