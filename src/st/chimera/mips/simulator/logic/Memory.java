/*
 * 作成日: 2003/08/02
 */
package st.chimera.mips.simulator.logic;

import st.chimera.mips.simulator.*;
import st.chimera.mips.simulator.gate.*;

/**
 * Memory。
 * 
 * @author chimera
 */
public class Memory extends Gate {
	public static int MEMORY_SIZE = 65536;

	private DataPath input;
	private DataPath output;
	private DataPath address;
	private DataPath write;
	private DataPath read;
	private int[] data;

	public Memory() {
		super("Memory");
		output = new DataPath(32);
		data = new int[MEMORY_SIZE >> 2];
	}

	public int getMemory(int index) {
		return data[index];
	}

	public void setData(int[] data) {
		this.data = new int[MEMORY_SIZE >> 2];
		System.arraycopy(data, 0, this.data, 0, data.length);
	}

	public DataPath getOutput() {
		return output;
	}

	public void setInput(DataPath path) {
		input = path;
		path.addGate(this);
	}

	public void setAddress(DataPath path) {
		address = path;
		path.addGate(this);
	}

	public void setWrite(DataPath path) {
		write = path;
		path.addGate(this);
	}

	public void setRead(DataPath path) {
		read = path;
		path.addGate(this);
	}

	protected void update() {
		int addr = address.get() >> 2;
		if (read.get(0)) {
			if (addr < data.length && addr >= 0) {
				if (data[addr] != 0x0c) {
					output.set(data[addr]);
				} else {
					Architecture.getInstance().stopClock();
				}
			} else {
				System.out.println("Segmentation fault");
				Architecture.getInstance().stopClock();
			}
		} else if (write.get(0)) {
			if (addr < data.length && addr >= data.length / 2) {
				data[addr] = input.get();
			} else {
				System.out.println("Segmentation fault");
				Architecture.getInstance().stopClock();
				
			}
		}
	}
}
