package st.chimera.util;

import java.util.*;

public class IntMap {

	private Map map = new HashMap();

	public IntMap() {
		map = new HashMap();
	}

	private IntMap(Map map) {
		this.map = map;
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public int get(String key) {
		return ((Integer)map.get(key)).intValue();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public void put(String key, int value) {
		map.put(key, new Integer(value));
	}

	public int remove(String key) {
		return ((Integer)map.remove(key)).intValue();
	}

	public int size() {
		return map.size();
	}

	public IntMap unmodifiableIntMap() {
		return new IntMap(Collections.unmodifiableMap(this.map));
	}

}
