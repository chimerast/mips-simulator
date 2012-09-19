/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.logic;

import st.chimera.mips.simulator.gate.*;

/**
 * InstructionRegister。
 * 
 * @author chimera
 */
public class InstructionRegister extends Gate {

	private Register register;
	private DataPath opcode;
	private DataPath rs;
	private DataPath rt;
	private DataPath rd;
	private DataPath funct;
	private DataPath imm;
	private DataPath address;
	private DataPath output;

	public InstructionRegister() {
		super("InstructionRegister");
		register = new Register(true);
		opcode = new DataPath(6);
		rs = new DataPath(5);
		rt = new DataPath(5);
		rd = new DataPath(5);
		funct = new DataPath(6);
		imm = new DataPath(16);
		address = new DataPath(26);
		output = register.getOutput();
		output.addGate(this);
	}
	
	public DataPath getOpcode() {
		return opcode;
	}

	public DataPath getImm() {
		return imm;
	}

	public DataPath getRS() {
		return rs;
	}

	public DataPath getRT() {
		return rt;
	}

	public DataPath getRD() {
		return rd;
	}

	public DataPath getFunct() {
		return funct;
	}
	
	public DataPath getAddress() {
		return address;
	}

	public void setInput(DataPath path) {
		register.setInput(path);
	}
	
	public void setWirte(DataPath path) {
		register.setWrite(path);
	}

	protected void update() {
		int instruction = output.get();
		opcode.set((instruction >> 26) & 0x3f);
		rs.set((instruction >> 21) & 0x1f);
		rt.set((instruction >> 16) & 0x1f);
		rd.set((instruction >> 11) & 0x1f);
		funct.set((instruction >> 0) & 0x3f);
		imm.set((instruction >> 0) & 0xffff);
		address.set(instruction & 0x3ffffff);
	}
}
