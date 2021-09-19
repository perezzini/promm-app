package ar.com.promm;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

	   private static App instance;
	   public static App get() { return instance; }

	   @Override
	   public void onCreate() {
            super.onCreate();
            instance = this;
            Fabric.with(this, new Answers(), new Crashlytics());
	   }
	}