package ar.com.promm.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import ar.com.promm.R;

public class FirstTutorialActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_tutorial);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.first_tutorial, menu);
		return true;
	}

	public void toNextActivity(View view) {
		Intent intent = new Intent(view.getContext(), TemporaryLocationActivity.class);
		startActivity(intent);
		finish();
	}
}
