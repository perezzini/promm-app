package ar.com.promm.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.SharedPreferences;

import ar.com.promm.R;

public class SplashScreenActivity extends Activity {
	
	private static final String welcomeScreenAlreadyShownKey = "WelcomeScreenAlreadyShown";

	private long splashDelay = 2*1000; //2 segundos

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		Boolean welcomeScreenAlreadyShown = mPrefs.getBoolean(welcomeScreenAlreadyShownKey, false);
		
		final Class<?> nextActivity;
		
		if (welcomeScreenAlreadyShown) {
			nextActivity=OtraTabsActivity.class;
		} else {
			mPrefs.edit().putBoolean(welcomeScreenAlreadyShownKey,true).commit();
			nextActivity=FirstTutorialActivity.class;
		}
		
		

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Intent mainIntent = new Intent(SplashScreenActivity.this,nextActivity);//.setClass(SplashScreenActivity.this, nextActivity);
				startActivity(mainIntent);
				finish();//Destruimos esta activity para prevenir que el usuario retorne aqui presionando el boton Atras.
			}
		};

		new Timer().schedule(task, splashDelay);
	}

}