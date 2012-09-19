/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.logic.controler;

import st.chimera.mips.simulator.gate.*;

/**
 * ControlUnit用DispatchROM1。
 * 
 * @author chimera
 */
public class DispatchROM1 extends Gate {
	private DataPath opcode;
	private DataPath output;

	public DispatchROM1() {
		super("DispatchROM1");
		output = new DataPath(4);
	}

	public void setOpcode(DataPath path) {
		opcode = path;
		path.addGate(this);
	}

	public DataPath getOutput() {
		return output;
	}

	protected void update() {
		switch (opcode.get()) {
			case ControlUnit.RTYPE :
				output.set(6);
				break;
			case ControlUnit.JMP :
				output.set(12);
				break;
			case ControlUnit.JAL :
				output.set(12);
				break;
			case ControlUnit.BEQ :
				output.set(11);
				break;
			case ControlUnit.ADDI :
				output.set(9);
				break;
			case ControlUnit.LW :
				output.set(2);
				break;
			case ControlUnit.SW :
				output.set(2);
				break;
			default :
				output.set(0);
				break;
		}
	}
}
