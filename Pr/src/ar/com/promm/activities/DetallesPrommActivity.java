package ar.com.promm.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ar.com.promm.R;
import ar.com.promm.datastructures.Promocion;
import ar.com.promm.factories.FactoryPromociones;

public class DetallesPrommActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_promm);
		Bundle b = getIntent().getExtras();
		final Promocion p=(Promocion) b.getParcelable("Promocion");
		FactoryPromociones.commonSetup(getWindow().getDecorView(),p);
		TextView goBack = (TextView) findViewById(R.id.goBack);
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DetallesPrommActivity.this.finish();
				
			}
		});
		
	}


}
