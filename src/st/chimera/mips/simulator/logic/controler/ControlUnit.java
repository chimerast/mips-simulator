/*
 * 作成日: 2003/08/02
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package st.chimera.mips.simulator.logic.controler;

import st.chimera.mips.simulator.*;
import st.chimera.mips.simulator.gate.*;

/**
 * @author chimera
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class ControlUnit extends Gate {
	public static final int RTYPE = 0;
	public static final int JMP = 2;
	public static final int JAL = 3;
	public static final int BEQ = 4;
	public static final int ADDI = 8;
	public static final int LW = 35;
	public static final int SW = 43;

	public static final int JR = 8;
	public static final int ADD = 32;
	public static final int SUB = 34;
	public static final int AND = 36;
	public static final int OR = 37;
	public static final int SLT = 42;

	public static final String[] microcode = {
		/*
		 ppimmimppaaaaaarrraa
		 ccoeertcclllllleeedd
		 wwrmmwossuuuuuugggdd
		  cdrw rrroosssswddrr 
		  n    eccppbbba 1010
		  d    g1010210
		*/
		"1X0101X000000100XX11", // Fetch
		"00X000XXX0001100XX01", 
		"00X000XXX0001010XX10", // Mem1
		"001100XXX0001010XX11", // LW2
		"00X0001XX00010110000", 
		"001010XXX0001010XX00", // SW2
		"00X000XXX1000010XX10", // RType
		"00X0000XX10000110100", // RType1
		"1XX000X01XXXXXX0XX00", // RType2
		"00X000XXX0001010XX11", // IType1
		"00X0000XX00000110000", 
		"01X000X010100010XX00", // BEQ1
		"1XX000X10XX10000XX10", // JMP1
		"00X0000XXXXXXXX11000"  // JAL1
	};
	
	public static final int code[];
	public static final int control[];

	static {
		code = new int[microcode.length];
		control = new int[microcode.length];
		for (int i = 0; i < microcode.length; ++i) {
			String codeStr = microcode[i].substring(0, 18).replace('X', '0');
			String controlStr = microcode[i].substring(18);
			code[i] = Integer.parseInt(codeStr, 2);
			control[i] = Integer.parseInt(controlStr, 2);
		}
	}

	DataPath opcode;
	Register state;
	Adder adder;
	DispatchROM1 rom1;
	DispatchROM2 rom2;
	ConstantDataPath const0;
	ConstantDataPath const1;
	MultiPlexor multiPlexor;
	DataPath selection;
	int output;
	int realPC;
	DataPath regoutput;

	DataPath pcWrite;
	DataPath pcWriteCond;
	DataPath iord;
	DataPath memRead;
	DataPath memWrite;
	DataPath irWrite;
	DataPath memtoReg;
	DataPath pcSource;
	DataPath aluOp;
	DataPath aluSrcB;
	DataPath aluSrcA;
	DataPath regWrite;
	DataPath regDst;

	public ControlUnit() {
		super("ControlUnit");

		state = new Register(false);
		adder = new Adder();
		rom1 = new DispatchROM1();
		rom2 = new DispatchROM2();
		const0 = new ConstantDataPath(4, 0);
		const1 = new ConstantDataPath(4, 1);
		multiPlexor = new MultiPlexor(4, 4);
		selection = new DataPath(2);

		multiPlexor.setInput(const0, 0);
		multiPlexor.setInput(rom1.getOutput(), 1);
		multiPlexor.setInput(rom2.getOutput(), 2);
		multiPlexor.setInput(adder.getOutput(), 3);
		state.setInput(multiPlexor.getOutput());
		adder.setInput1(state.getOutput());
		adder.setInput2(const1);
		multiPlexor.setSelection(selection);
		regoutput = state.getOutput();
		regoutput.addGate(this);

		pcWrite = new DataPath(1);
		pcWriteCond = new DataPath(1);
		iord = new DataPath(1);
		memRead = new DataPath(1);
		memWrite = new DataPath(1);
		irWrite = new DataPath(1);
		memtoReg = new DataPath(1);
		pcSource = new DataPath(2);
		aluOp = new DataPath(2);
		aluSrcB = new DataPath(3);
		aluSrcA = new DataPath(1);
		regWrite = new DataPath(1);
		regDst = new DataPath(2);
	}

	public void setFunct(DataPath path) {
		rom2.setFunct(path);
	}

	public void setOpcode(DataPath path) {
		rom1.setOpcode(path);
		rom2.setOpcode(path);
	}

	public DataPath getPCWrite() {
		return pcWrite;
	}

	public DataPath getPCWriteCond() {
		return pcWriteCond;
	}

	public DataPath getIorD() {
		return iord;
	}

	public DataPath getMemRead() {
		return memRead;
	}

	public DataPath getMemWrite() {
		return memWrite;
	}

	public DataPath getIRWrite() {
		return irWrite;
	}

	public DataPath getMemtoReg() {
		return memtoReg;
	}

	public DataPath getPCSource() {
		return pcSource;
	}

	public DataPath getALUOp() {
		return aluOp;
	}

	public DataPath getALUSrcB() {
		return aluSrcB;
	}

	public DataPath getALUSrcA() {
		return aluSrcA;
	}

	public DataPath getRegWrite() {
		return regWrite;
	}

	public DataPath getRegDst() {
		return regDst;
	}

	public int getOutput() {
		return output;
	}
	
	public int getRealPC() {
		return realPC;
	}

	public void update() {
		int address = regoutput.get();
		output = code[address];
		selection.set(control[address]);
		
		if (address == 0) 
			realPC = Architecture.getInstance().getProgramCount();

		pcWrite.set((output >> 17) & 0x1);
		pcWriteCond.set((output >> 16) & 0x1);
		iord.set((output >> 15) & 0x1);
		memRead.set((output >> 14) & 0x1);
		memWrite.set((output >> 13) & 0x1);
		irWrite.set((output >> 12) & 0x1);
		memtoReg.set((output >> 11) & 0x1);
		pcSource.set((output >> 9) & 0x3);
		aluOp.set((output >> 7) & 0x3);
		aluSrcB.set((output >> 4) & 0x7);
		aluSrcA.set((output >> 3) & 0x1);
		regWrite.set((output >> 2) & 0x1);
		regDst.set((output >> 0) & 0x3);
	}
}
