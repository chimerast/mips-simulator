package st.chimera.mips.assembler;

import st.chimera.util.*;

public class Assembler {
	static {
		registers = createRegisterMap();
		instructions = createInstructionMap();
	}

	private static final IntMap instructions;
	private static final IntMap registers;

	private static final int STAGE_PARSE_LABEL = 0;
	private static final int STAGE_PARSE_INSTRUCTION = 1;

	public static final int createInstruction(int op, int address) {
		return (op << 26) | (address << 0);
	}

	public static final int createInstruction(int op, int rs, int rt, int imm) {
		return (op << 26) | (rs << 21) | (rt << 16) | (imm << 0);
	}

	public static final int createInstruction(
		int op, int rs, int rt, int rd, int shamt, int funct) {
		return (op << 26) | (rs << 21) | (rt << 16) | (rd << 11) | (shamt << 6) | (funct << 0);
	}

	private static final IntMap createInstructionMap() {
		IntMap map = new IntMap();

		map.put("sll", createInstruction(0, 0, 0, 0, 0, 0));
		map.put("srl", createInstruction(0, 0, 0, 0, 0, 2));
		map.put("sra", createInstruction(0, 0, 0, 0, 0, 3));

		map.put("sllv", createInstruction(0, 0, 0, 0, 0, 4));
		map.put("srlv", createInstruction(0, 0, 0, 0, 0, 6));
		map.put("srav", createInstruction(0, 0, 0, 0, 0, 7));

		map.put("jr", createInstruction(0, 0, 0, 0, 0, 8));
		map.put("jalr", createInstruction(0, 0, 0, 0, 0, 9));

		map.put("syscall", createInstruction(0, 0, 0, 0, 0, 12));
		map.put("break", createInstruction(0, 0, 0, 0, 0, 13));

		map.put("mfhi", createInstruction(0, 0, 0, 0, 0, 16));
		map.put("mthi", createInstruction(0, 0, 0, 0, 0, 17));
		map.put("mflo", createInstruction(0, 0, 0, 0, 0, 18));
		map.put("mtlo", createInstruction(0, 0, 0, 0, 0, 19));

		map.put("mult", createInstruction(0, 0, 0, 0, 0, 24));
		map.put("multu", createInstruction(0, 0, 0, 0, 0, 25));
		map.put("div", createInstruction(0, 0, 0, 0, 0, 26));
		map.put("divu", createInstruction(0, 0, 0, 0, 0, 27));

		map.put("add", createInstruction(0, 0, 0, 0, 0, 32));
		map.put("addu", createInstruction(0, 0, 0, 0, 0, 33));
		map.put("sub", createInstruction(0, 0, 0, 0, 0, 34));
		map.put("subu", createInstruction(0, 0, 0, 0, 0, 35));
		map.put("and", createInstruction(0, 0, 0, 0, 0, 36));
		map.put("or", createInstruction(0, 0, 0, 0, 0, 37));
		map.put("xor", createInstruction(0, 0, 0, 0, 0, 38));
		map.put("nor", createInstruction(0, 0, 0, 0, 0, 39));

		map.put("slt", createInstruction(0, 0, 0, 0, 0, 42));
		map.put("sltu", createInstruction(0, 0, 0, 0, 0, 43));

		// I Instruction
		map.put("bltz", createInstruction(1, 0, 0, 0));
		map.put("bgez", createInstruction(1, 0, 1, 0));
		map.put("bltzal", createInstruction(1, 0, 16, 0));
		map.put("bgezal", createInstruction(1, 0, 17, 0));

		// J Instruction
		map.put("j", createInstruction(2, 0));
		map.put("jal", createInstruction(3, 0));

		// I Instruction
		map.put("beq", createInstruction(4, 0, 0, 0));
		map.put("bne", createInstruction(5, 0, 0, 0));

		map.put("blez", createInstruction(6, 0, 0, 0));
		map.put("bgtz", createInstruction(7, 0, 0, 0));

		map.put("addi", createInstruction(8, 0, 0, 0));
		map.put("addiu", createInstruction(9, 0, 0, 0));
		map.put("slti", createInstruction(10, 0, 0, 0));
		map.put("sltiu", createInstruction(11, 0, 0, 0));
		map.put("andi", createInstruction(12, 0, 0, 0));
		map.put("ori", createInstruction(13, 0, 0, 0));
		map.put("xori", createInstruction(14, 0, 0, 0));
		map.put("lui", createInstruction(15, 0, 0, 0));

		map.put("lb", createInstruction(32, 0, 0, 0));
		map.put("lh", createInstruction(33, 0, 0, 0));
		map.put("lwl", createInstruction(34, 0, 0, 0));
		map.put("lw", createInstruction(35, 0, 0, 0));
		map.put("lbu", createInstruction(36, 0, 0, 0));
		map.put("lhu", createInstruction(37, 0, 0, 0));
		map.put("lwr", createInstruction(38, 0, 0, 0));

		map.put("sb", createInstruction(40, 0, 0, 0));
		map.put("sh", createInstruction(41, 0, 0, 0));
		map.put("swl", createInstruction(42, 0, 0, 0));
		map.put("sw", createInstruction(43, 0, 0, 0));
		map.put("swr", createInstruction(46, 0, 0, 0));

		return map.unmodifiableIntMap();
	}

	private static final IntMap createRegisterMap() {
		IntMap map = new IntMap();

		for (int i = 0; i < 32; ++i)
			map.put(Integer.toString(i), i);

		map.put("zero", 0);
		map.put("at", 1);
		for (int i = 0; i < 2; ++i)
			map.put("v" + i, 2 + i);
		for (int i = 0; i < 4; ++i)
			map.put("a" + i, 4 + i);
		for (int i = 0; i < 8; ++i)
			map.put("t" + i, 8 + i);
		for (int i = 0; i < 8; ++i)
			map.put("s" + i, 16 + i);
		for (int i = 0; i < 2; ++i)
			map.put("t" + (i + 8), 24 + i);
		for (int i = 0; i < 2; ++i)
			map.put("k" + i, 26 + i);
		map.put("gp", 28);
		map.put("sp", 29);
		map.put("fp", 30);
		map.put("ra", 31);

		return map.unmodifiableIntMap();
	}

	public static final int getRegisterNumber(String reg) {
		return registers.get(reg);
	}

	private int address;
	private IntList code = new IntList();
	private StringList str = new StringList();
	private IntMap labelmap = new IntMap();
	private int stage;

	public Assembler() {
		this.address = 0;
		this.stage = STAGE_PARSE_LABEL;
	}

	public int getLabelAbsoluteAddress(String label) {
		if (stage == STAGE_PARSE_INSTRUCTION) {
			return labelmap.get(label);
		} else {
			return 0;
		}
	}

	public int getLabelRelativeAddress(String label) {
		if (stage == STAGE_PARSE_INSTRUCTION) {
			return labelmap.get(label) - (address + 1);
		} else {
			return 0;
		}
	}

	public void nextStage() {
		this.address = 0;
		this.stage = STAGE_PARSE_INSTRUCTION;
	}

	public void print() {
		int[] array = code.toArray();
		String[] strarray = str.toArray();
		int add = 0;
		for (int i = 0; i < array.length; ++i) {
			System.out.println(
				Integer.toHexString(add)
					+ ": "
					+ Integer.toHexString(array[i])
					+ " ; "
					+ strarray[i]);
			add += 4;
		}
	}
	
	public int[] getData() {
		return code.toArray();
	}
	
	public String[] getSourceCode() {
		return str.toArray(); 
	}

	public void putInstructionString(String string) {
		if (stage == STAGE_PARSE_INSTRUCTION) {
			str.add(string);
		}
	}

	public void putIInstruction(String op, int rs, int rt, int imm) {
		if (stage == STAGE_PARSE_INSTRUCTION) {
			int opcode = instructions.get(op);
			code.add(opcode | (rs << 21) | (rt << 16) | (imm & 0xffff << 0));
		}
		this.address += 4;
	}

	public void putJInstruction(String op, int address) {
		if (stage == STAGE_PARSE_INSTRUCTION) {
			int opcode = instructions.get(op);
			code.add(opcode | address);
		}
		this.address += 4;
	}

	public void putLabel(String label) {
		if (stage == STAGE_PARSE_LABEL) {
			labelmap.put(label, address);
		}
	}

	public void putRInstruction(String op, int rs, int rt, int rd, int shamt) {
		if (stage == STAGE_PARSE_INSTRUCTION) {
			int opcode = instructions.get(op);
			code.add(opcode | (rs << 21) | (rt << 16) | (rd << 11) | (shamt << 6));
		}
		this.address += 4;
	}
}
