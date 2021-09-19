package ar.com.promm;

import java.util.concurrent.ConcurrentHashMap;

import ar.com.promm.datastructures.Local;

public class StoresDB {
	private static StoresDB instance;
	private ConcurrentHashMap<String,Local> map;

	private StoresDB() {
		this.map = new ConcurrentHashMap<String, Local>();
	}
	
	public Local getLocal(String id) {
		return map.get(id);
	}
	
	public void addLocal(String id, Local l) {
		map.put(id, l);
	}
	
	public static StoresDB getStoresDB() {
		if (instance==null) {
			instance = new StoresDB();
		}
		return instance;
	}
	
}
