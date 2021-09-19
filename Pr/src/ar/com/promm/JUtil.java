package ar.com.promm;

import org.json.JSONException;
import org.json.JSONObject;

public class JUtil
{
	/* Ugh..... */

	public static String maybeGetString(JSONObject jobj, String key,
					    String def)
	{
		try {
			return jobj.getString(key);
		} catch (JSONException e) {
			return def;
		}
	}

	public static int maybeGetInt(JSONObject jobj, String key, int def)
	{
		try {
			return jobj.getInt(key);
		} catch (JSONException e) {
			return def;
		}
	}

	public static boolean maybeGetBoolean(JSONObject jobj, String key,
					      boolean def)
	{
		try {
			return jobj.getBoolean(key);
		} catch (JSONException e) {
			return def;
		}
	}
}
