package ar.com.promm.datastructures;


import org.json.JSONException;
import org.json.JSONStringer;

import ar.com.promm.enums.Enums.COMMANDTYPE;
import ar.com.promm.enums.Enums.LANGUAGE;

public class Command {
	
	private JSONStringer stringer;
	
	/*public Command() {
		
	}*/
	
	public Command(COMMANDTYPE cmd) {
		try {
			this.stringer= new JSONStringer().object();
			writeValueIfNotNull(stringer, "cmd",cmd);
		} catch (JSONException e) {
			// da, no podes fallar aca, seria cualquiera
			e.printStackTrace();
		}
	}
	
	public void setLang (LANGUAGE lang) {
		writeValueIfNotNull(stringer, "lang", lang);
	}
	
	public void setCursor (String cursor) {
		writeValueIfNotNull(stringer, "cursor", cursor);
	}
	
	public void setId(String id) {
		writeValueIfNotNull(stringer, "android", id);
	}
	
	public void setGeo(String lat, String lng) {
		writeValueIfNotNull(stringer, "lat", lat);
		writeValueIfNotNull(stringer, "lng", lng);
	}
	
	private JSONStringer writeValueIfNotNull(JSONStringer stringer, String key, Object value) {
		if (value!=null) {
			try {
				stringer=stringer.key(key).value(value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stringer;
	}
	
	public String serialize() {
		String res="{}";
		try {
			res = stringer.endObject().toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}

	public void setLocal(String localid) {
		writeValueIfNotNull(stringer, "StoreID", localid);
	}


}
