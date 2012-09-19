package st.chimera.mips.simulator;

import st.chimera.mips.simulator.gate.*;
import st.chimera.mips.simulator.logic.*;
import st.chimera.mips.simulator.logic.controler.*;

public class Architecture {
	private static final Architecture instance = new Architecture();

	public static Architecture getInstance() {
		return instance;
	}

	private ControlUnit control;
	private ALUControlUnit aluControl;

	private ProgramCounter programCounter;
	private MultiPlexor iordMux;
	private Memory memory;
	private InstructionRegister instructionRegister;
	private Register memoryDataRegister;
	private MultiPlexor regDstMux;
	private ConstantDataPath const31;
	private MultiPlexor memToRegMux;
	private Registers registers;
	private SignExtender signExtender;
	private Shifter shifter;
	private Register tempRegA;
	private Register tempRegB;
	private MultiPlexor aluSrcAMux;
	private MultiPlexor aluSrcBMux;
	private ConstantDataPath const4;
	private ConstantDataPath const0;
	private ALU alu;
	private Shifter addrShifter;
	private AddressMerger addrMerger;
	private Register aluOutReg;
	private MultiPlexor pcSrcMux;
	private And pcWriteCond;
	private Or pcWrite;

	private boolean realMode;

	private String[] sourceCode;

	private Architecture() {
		sourceCode = new String[0];
		
		control = new ControlUnit();
		aluControl = new ALUControlUnit();

		programCounter = new ProgramCounter();
		iordMux = new MultiPlexor(2, 32);
		memory = new Memory();
		instructionRegister = new InstructionRegister();
		memoryDataRegister = new Register(false);
		regDstMux = new MultiPlexor(3, 5);
		const31 = new ConstantDataPath(5, 31);
		memToRegMux = new MultiPlexor(2, 32);
		registers = new Registers();
		signExtender = new SignExtender();
		shifter = new Shifter();
		tempRegA = new Register(false);
		tempRegB = new Register(false);
		aluSrcAMux = new MultiPlexor(2, 32);
		aluSrcBMux = new MultiPlexor(5, 32);
		const4 = new ConstantDataPath(32, 4);
		const0 = new ConstantDataPath(32, 0);
		alu = new ALU();
		addrShifter = new Shifter();
		addrMerger = new AddressMerger();
		aluOutReg = new Register(false);
		pcSrcMux = new MultiPlexor(3, 32);
		pcWriteCond = new And();
		pcWrite = new Or();

		programCounter.setInput(pcSrcMux.getOutput());

		iordMux.setInput(programCounter.getOutput(), 0);
		iordMux.setInput(aluOutReg.getOutput(), 1);

		memory.setAddress(iordMux.getOutput());
		memory.setInput(tempRegB.getOutput());

		instructionRegister.setInput(memory.getOutput());
		memoryDataRegister.setInput(memory.getOutput());

		regDstMux.setInput(instructionRegister.getRT(), 0);
		regDstMux.setInput(instructionRegister.getRD(), 1);
		regDstMux.setInput(const31, 2);

		memToRegMux.setInput(aluOutReg.getOutput(), 0);
		memToRegMux.setInput(memoryDataRegister.getOutput(), 1);

		registers.setReadNumber1(instructionRegister.getRS());
		registers.setReadNumber2(instructionRegister.getRT());
		registers.setWriteNumber(regDstMux.getOutput());
		registers.setInput(memToRegMux.getOutput());

		signExtender.setInput(instructionRegister.getImm());
		shifter.setInput(signExtender.getOutput());

		tempRegA.setInput(registers.getOutput1());
		tempRegB.setInput(registers.getOutput2());

		aluSrcAMux.setInput(programCounter.getOutput(), 0);
		aluSrcAMux.setInput(tempRegA.getOutput(), 1);

		aluSrcBMux.setInput(tempRegB.getOutput(), 0);
		aluSrcBMux.setInput(const4, 1);
		aluSrcBMux.setInput(signExtender.getOutput(), 2);
		aluSrcBMux.setInput(shifter.getOutput(), 3);
		aluSrcBMux.setInput(const0, 4);

		addrShifter.setInput(instructionRegister.getAddress());
		addrMerger.setUpper(programCounter.getUpper());
		addrMerger.setLower(addrShifter.getOutput());

		alu.setInput1(aluSrcAMux.getOutput());
		alu.setInput2(aluSrcBMux.getOutput());

		aluOutReg.setInput(alu.getOutput());

		pcSrcMux.setInput(alu.getOutput(), 0);
		pcSrcMux.setInput(aluOutReg.getOutput(), 1);
		pcSrcMux.setInput(addrMerger.getOutput(), 2);

		pcWriteCond.setInput1(alu.getZero());
		pcWriteCond.setInput2(control.getPCWriteCond());
		pcWrite.setInput1(pcWriteCond.getOutput());
		pcWrite.setInput2(control.getPCWrite());

		control.setOpcode(instructionRegister.getOpcode());
		control.setFunct(instructionRegister.getFunct());
		aluControl.setAluOp(control.getALUOp());
		aluControl.setFunct(instructionRegister.getFunct());

		programCounter.setWrite(pcWrite.getOutput());
		iordMux.setSelection(control.getIorD());
		memory.setWrite(control.getMemWrite());
		memory.setRead(control.getMemRead());
		memToRegMux.setSelection(control.getMemtoReg());
		instructionRegister.setWirte(control.getIRWrite());
		regDstMux.setSelection(control.getRegDst());
		registers.setWrite(control.getRegWrite());
		aluSrcAMux.setSelection(control.getALUSrcA());
		aluSrcBMux.setSelection(control.getALUSrcB());
		alu.setOperation(aluControl.getAluOperation());
		pcSrcMux.setSelection(control.getPCSource());
	}

	public void addCallBeforeClockEdge(Clock.Callback callback) {
		Clock.addCallBeforeClockEdge(callback);
	}

	public boolean changeMode() {
		if (!Clock.isRunning()) 
			realMode = !realMode;
		return realMode;
/*
		try {
			boolean running = false;
			if (Clock.isRunning()) {
				Clock.terminate();
				running = true;
			}

			if (realMode) {
				Gate.terminateAllUnit();
				realMode = false;
				Gate.updateAllUnit();
			} else {
				Gate.startAllUnit();
				realMode = true;
			}

			if (running) {
				Clock.start();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			return realMode;
		}
*/
	}

	public int getControlOutput() {
		return control.getOutput();
	}

	public int getMemory(int index) {
		return memory.getMemory(index);
	}

	public int getProgramCount() {
		return programCounter.getCount();
	}

	public int getRealProgramCount() {
		return control.getRealPC();
	}

	public boolean[] getRegisterUpdated() {
		return registers.getRegisterUpdated();
	}

	public int[] getRegisterValues() {
		return registers.getRegisterValues();
	}

	public String getSourceCode(int index) {
		return sourceCode[index];
	}

	public int getSourceCount() {
		return sourceCode.length;
	}

	public boolean isRealMode() {
		return realMode;
	}

	public boolean resetPC() {
		if (Clock.isRunning())
			return false;

		DataPath.resetAllDataPath();
		Register.resetAllRegister();
		registers.setSP(Memory.MEMORY_SIZE);
		Gate.updateAllUnit();
		Clock.callBackAll();
		return true;
	}

	public void setData(int[] data) {
		memory.setData(data);
		resetPC();
	}

	public void setSourceCode(String[] source) {
		sourceCode = new String[source.length];
		System.arraycopy(source, 0, sourceCode, 0, source.length);
	}

	public void startClock() {
		if (realMode)
			Gate.startAllUnit();
		Clock.start();
	}

	public void stopClock() {
		try {
			Clock.terminate();
			if (realMode)
				Gate.terminateAllUnit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setClockDelayTime(int time) {
		Clock.setClockDelayTime(time);
	}
}
