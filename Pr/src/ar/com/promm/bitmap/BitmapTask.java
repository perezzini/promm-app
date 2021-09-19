package ar.com.promm.bitmap;

import java.util.Dictionary;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class BitmapTask extends AsyncTask<String, Void, Bitmap> {
	private String url;
	Dictionary<String, Bitmap> ht;
	private BitmapFun f;

	public BitmapTask(BitmapFun f, Dictionary<String, Bitmap> ht) {
		this.ht = ht;
		this.f = f;
	}

	@Override
	// Actual download method, run in the task thread
	protected Bitmap doInBackground(String... params) {
		BitmapDownloader bd = new BitmapDownloader();
		// params comes from the execute() call: params[0] is the url.
		url = params[0];
		return bd.downloadBitmap(url);
	}

	@Override
	// Once the image is downloaded, associates it to the imageView
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			bitmap = null;
		}
		if (bitmap != null) {
			ht.put(url, bitmap);
		}
		f.execute(bitmap);
	}

}