package ar.com.promm.datastructures;

import org.json.JSONException;
import org.json.JSONStringer;

import ar.com.promm.enums.Enums.COMMANDTYPE;
import ar.com.promm.enums.Enums.LANGUAGE;

public class Command_simple {
	
	private COMMANDTYPE type;
	private LANGUAGE lang;
	private String id;
	private String lat;
	private String lng;

	
	public Command_simple(COMMANDTYPE type) {
		this.type=type;
	}
	
	public void setLang (LANGUAGE lang) {
		this.lang=lang;
	}
	
	public void setUniqueId(String id) {
		this.id=id;
	}
	
	public void setGeo(String lat, String lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	private JSONStringer writeValueIfNotNull(JSONStringer stringer, String key, Object value) throws JSONException {
		if (value!=null) {
			stringer=stringer.key(key).value(value);
		}
		return stringer;
	}
	
	public String serialize() {
		String myString=null;
		try {
			JSONStringer stringer = new JSONStringer().object();
			stringer = writeValueIfNotNull(stringer, "lat",lat);
			stringer = writeValueIfNotNull(stringer, "lng",lng);
			stringer = writeValueIfNotNull(stringer, "cmd",type);
			stringer = writeValueIfNotNull(stringer, "lang",lang);
			stringer = writeValueIfNotNull(stringer, "id",id);
			myString = stringer.endObject().toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myString;
	}

	public void setId(String id) {
		this.id=id;
	}


}
