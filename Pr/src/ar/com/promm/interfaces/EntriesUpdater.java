package ar.com.promm.interfaces;

import java.util.ArrayList;


public interface EntriesUpdater<T> {
	public void update(ArrayList<T> entries, String cursor);

}
