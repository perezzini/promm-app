package ar.com.promm.interfaces;

import java.util.ArrayList;

import org.json.JSONException;

public interface ArrayParser<A> {
	
	public ArrayList<A> parse(String s) throws JSONException;

}
