package ar.com.promm.runnables;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;

import android.net.http.AndroidHttpClient;
import ar.com.promm.PrommCfg;
import ar.com.promm.interfaces.EntriesUpdater;
import ar.com.promm.parsers.JSonParser;

public class URLsRetriever implements Runnable {

	private final static String url = PrommCfg.remotePageURL+"/php/store_gallery.php?store_id=";

	private EntriesUpdater<String> updateEntries;

	private String localid;

	public URLsRetriever(EntriesUpdater<String> updateEntries, String localid) {
		this.updateEntries = updateEntries;
		this.localid = localid;

	}

	@Override
	public void run() {
		String json = getJSON();
		ArrayList<String> list=null;
		try {
			list = JSonParser.parseURLs(json);
		} catch (JSONException e1) {
		}		
		updateEntries.update(list,null);

	}

	private String getJSON() {
		final AndroidHttpClient client = AndroidHttpClient
				.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url+localid);
		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {

				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					BufferedReader bfr = new BufferedReader(
							new InputStreamReader(inputStream));
					return bfr.readLine();
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// Could provide a more explicit error message for IOException or
			// IllegalStateException
			getRequest.abort();
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}

}
