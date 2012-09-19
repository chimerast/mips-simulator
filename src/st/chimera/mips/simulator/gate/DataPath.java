/*
 * 作成日: 2003/08/01
 */
package st.chimera.mips.simulator.gate;

import java.util.*;

/**
 * データパス。
 * 
 * @author chimera
 */
public class DataPath {
	private static List datapathes = Collections.synchronizedList(new LinkedList());

	public static void resetAllDataPath() {
		Iterator itr = datapathes.iterator();
		while (itr.hasNext()) {
			DataPath datapath = (DataPath)itr.next();
			if (!(datapath instanceof ConstantDataPath))
				datapath.data = 0;
			datapath.updateGate();
		}
	}

	private int data;
	private final int size;
	
	private List relatedGate = new ArrayList();

	public DataPath(int size) {
		datapathes.add(this);
		this.data = 0;
		this.size = size;
	}

	public int get() {
		return data;
	}
	
	public void addGate(Gate gate) {
		relatedGate.add(gate);
	}

	public boolean get(int index) {
		return ((data >> index) & 1) == 1;
	}

	public void set(boolean data, int index) {
		int olddata = this.data;		
		if (!(index < size))
			System.err.println("datapath overflow");

		this.data &= ~(1 << index);
		this.data |= (data ? 1 : 0) << index;

		if (this.data != olddata)
			updateGate();
	}

	public void set(int data) {
		int olddata = this.data;
		int mask = size == 32 ? ~0 : (1 << size) - 1;
		if ((data & ~mask) != 0)
			System.err.println("datapath overflow");

		this.data = data & mask;
		
		if (this.data != olddata)
			updateGate();
	}
	
	private void updateGate() {
		Iterator itr = relatedGate.iterator();
		while (itr.hasNext()) {
			((Gate)itr.next()).setUpdate();
		}
	}
}
