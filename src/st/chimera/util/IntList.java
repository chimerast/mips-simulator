package st.chimera.util;

import java.util.*;

public class IntList {

	private List list;

	public IntList() {
		list = new ArrayList();
	}

	private IntList(List list) {
		this.list = list;
	}

	public boolean add(int o) {
		return list.add(new Integer(o));
	}

	public void clear() {
		list.clear();
	}

	public int get(int index) {
		return ((Integer)list.get(index)).intValue();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public int set(int index, int value) {
		Integer oldValue = (Integer) (list.set(index, new Integer(value)));
		return oldValue.intValue();
	}

	public int size() {
		return list.size();
	}

	public int[] toArray() {
		Integer[] array = (Integer[]) (list.toArray(new Integer[size()]));
		int[] retArray = new int[size()];
		for (int i = 0; i < array.length; i++) {
			retArray[i] = array[i].intValue();
		}
		return retArray;
	}

	public IntList unmodifiableIntList() {
		return new IntList(Collections.unmodifiableList(this.list));
	}

}
