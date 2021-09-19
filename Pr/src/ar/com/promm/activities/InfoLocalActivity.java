package ar.com.promm.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ar.com.promm.R;
import ar.com.promm.datastructures.Local;

public class InfoLocalActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
		Local local = b.getParcelable("Local");
		setContentView(R.layout.activity_info_local);
		TextView goBack = (TextView) findViewById(R.id.goBack);
		TextView ttDesc = (TextView) findViewById(R.id.ttDesc);
		
		ttDesc.setText(local.getdesc());
		
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InfoLocalActivity.this.finish();
				
			}
		});
	}

}
