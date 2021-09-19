package ar.com.promm.activities;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ar.com.promm.R;
import ar.com.promm.adaptadores.ImagePagerAdapter;
import ar.com.promm.datastructures.Local;

public class GalleryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
		Local local = b.getParcelable("Local");
		setContentView(R.layout.images_gallery);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		TextView goBack = (TextView) findViewById(R.id.goBack);
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GalleryActivity.this.finish();
				
			}
		});
	    ImagePagerAdapter adapter = new ImagePagerAdapter(this);
	    viewPager.setAdapter(adapter);
	    adapter.retrieveList(local);
	}
}
