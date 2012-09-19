/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.logic.controler;

import st.chimera.mips.simulator.gate.*;

/**
 * ControlUnit用DispatchROM2。
 * 
 * @author chimera
 */
public class DispatchROM2 extends Gate {
	private DataPath opcode;
	private DataPath funct;
	private DataPath output;

	public DispatchROM2() {
		super("DispatchROM2");
		output = new DataPath(4);
	}

	public void setFunct(DataPath path) {
		funct = path;
		path.addGate(this);
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
				switch (funct.get()) {
					case ControlUnit.JR :
						output.set(8);
						break;
					default :
						output.set(7);
				}
				break;
			case ControlUnit.LW :
				output.set(3);
				break;
			case ControlUnit.SW :
				output.set(5);
				break;
			case ControlUnit.JMP :
				output.set(0);
				break;
			case ControlUnit.JAL :
				output.set(13);
				break;
			default :
				output.set(0);
				break;
		}
	}
}
