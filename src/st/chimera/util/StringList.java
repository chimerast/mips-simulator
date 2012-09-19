package st.chimera.util;

import java.util.*;

public class StringList {

	private List list;

	public StringList() {
		list = new ArrayList();
	}

	private StringList(List list) {
		this.list = list;
	}

	public boolean add(String o) {
		return list.add(o);
	}

	public void clear() {
		list.clear();
	}

	public String get(int index) {
		return (String)list.get(index);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public String set(int index, String value) {
		String oldValue = (String) (list.set(index, value));
		return oldValue;
	}

	public int size() {
		return list.size();
	}

	public String[] toArray() {
		return (String[]) (list.toArray(new String[size()]));
	}

	public StringList unmodifiableIntList() {
		return new StringList(Collections.unmodifiableList(this.list));
	}

}
