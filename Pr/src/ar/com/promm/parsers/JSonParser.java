package ar.com.promm.parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.com.promm.JUtil;
import ar.com.promm.datastructures.Local;
import ar.com.promm.datastructures.Promocion;
import ar.com.promm.datastructures.Tarjeta_local;
import ar.com.promm.datastructures.Tarjeta_promocion;

public class JSonParser
{
	private class Str {
		private static final String Stores	= "Stores";
		private static final String Promms	= "Promms";

		private static final String StoreName	= "StoreName";
		private static final String StoreAddr	= "StoreAddr";
		private static final String SmallPic	= "SmallPic";
		private static final String BigPic	= "BigPic";
		private static final String GeoLat	= "GeoLat";
		private static final String GeoLng	= "GeoLng";
		private static final String Phone	= "Phone";
		private static final String StoreDesc	= "StoreDesc";
		private static final String StoreID	= "StoreID";
		private static final String Votes	= "Votes";
		private static final String Voted	= "Voted";

		/* For now, we'll not use short description. PHP must control it */
		//private static final String Title	= "Title";
		private static final String PrommDesc	= "PrommDesc";
		private static final String Start	= "Start";
		private static final String Finish	= "Finish";
		private static final String PrommShareID	= "ShareID";

		private static final String urls	= "urls";
		private static final String url		= "url";
		
		private static final String cursor	= "cursor";
	}

	private static Promocion parsePromocion(JSONObject jobj)
	throws JSONException
	{
		/*
		 * All of the store info should be included in the Promm,
		 * so call the store parsing routine on it to get the store
		 * object
		 */
		Local l = parseLocal(jobj);

		/* Parse Promm-specific fields */
		String desc		= jobj.getString(Str.PrommDesc);
		String startTimeStr	= jobj.getString(Str.Start);
		String stopTimeStr	= jobj.getString(Str.Finish);
		String prommShareId		= jobj.getString(Str.PrommShareID);

		return new Promocion(l, desc, startTimeStr,
				     stopTimeStr, prommShareId);
	}

	private static Local parseLocal(JSONObject jobj)
	throws JSONException
	{
		String name	= jobj.getString(Str.StoreName);
		String addr	= jobj.getString(Str.StoreAddr);
		String tel	= jobj.getString(Str.Phone);
		String desc	= jobj.getString(Str.StoreDesc);
		String smallurl	= jobj.getString(Str.SmallPic);
		String largeurl	= jobj.getString(Str.BigPic);
		String lat	= jobj.getString(Str.GeoLat);
		String lng	= jobj.getString(Str.GeoLng);
		String id	= jobj.getString(Str.StoreID);
		Integer votes	= jobj.getInt(Str.Votes);

		Boolean is_voted = JUtil.maybeGetBoolean(jobj, Str.Voted, false);

		return new Local(name, addr, tel, desc, smallurl, largeurl, lat,
				 lng, id, votes, is_voted);
	}

	public static ArrayList<Tarjeta_local> parseLocales(String input)
	throws JSONException
	{
		ArrayList<Tarjeta_local> tarjetas = new ArrayList<Tarjeta_local>();
		JSONObject jobj = new JSONObject(input);
		JSONArray jArray = jobj.getJSONArray(Str.Stores);

		for (int i = 0; i < jArray.length(); i++) {
			JSONObject elem = jArray.getJSONObject(i);
			Local l = parseLocal(elem);
			Tarjeta_local t = new Tarjeta_local(l);

			tarjetas.add(t);
		}

		return tarjetas;
	}

	public static ArrayList<Tarjeta_promocion> parsePromociones(String input)
	throws JSONException
	{
		ArrayList<Tarjeta_promocion> tarjetas = new ArrayList<Tarjeta_promocion>();
		JSONObject jobj = new JSONObject(input);
		JSONArray jArray = jobj.getJSONArray(Str.Promms);

		for (int i = 0; i < jArray.length(); i++) {
			JSONObject elem = jArray.getJSONObject(i);
			Promocion p = parsePromocion(elem);
			Tarjeta_promocion t = new Tarjeta_promocion(p);

			tarjetas.add(t);
		}

		return tarjetas;
	}

	public static ArrayList<String> parseURLs(String input)
	throws JSONException
	{
		ArrayList<String> urls = new ArrayList<String>();
		JSONObject jobj = new JSONObject(input);
		JSONArray jArray = jobj.getJSONArray(Str.urls);

		for (int i = 0; i < jArray.length(); i++) {
			JSONObject elem = jArray.getJSONObject(i);
			String url = parseSingleURL(elem);
			urls.add(url);
		}

		return urls;
	}
	

	public static String parseCursor(String input) throws JSONException {
		JSONObject jobj = new JSONObject(input);
		return JUtil.maybeGetString(jobj, Str.cursor, null);
	}

	private static String parseSingleURL(JSONObject jobj)
	throws JSONException
	{
		String singleurl = jobj.getString(Str.url);
		return singleurl;
	}

}
