/*
 * 作成日: 2003/08/05
 */
package st.chimera.mips.simulator.gate;

/**
 * 定数データパス。
 * 
 * @author chimera
 */
public class ConstantDataPath extends DataPath {
	public ConstantDataPath(int size, int value) {
		super(size);
		super.set(value);
	}
	
	public void set(boolean data, int index) {
	}

	public void set(int data) {
	}	
}
