package ar.com.promm;

public final class IdRetriever {
	
	public synchronized static String getId() {
		return android.provider.Settings.Secure.getString(App.get().getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
	}

}
