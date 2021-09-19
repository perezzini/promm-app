package ar.com.promm.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.promm.ImageSetter;
import ar.com.promm.R;
import ar.com.promm.StoresDB;
import ar.com.promm.datastructures.Command;
import ar.com.promm.datastructures.Local;
import ar.com.promm.enums.Enums.COMMANDTYPE;
import ar.com.promm.internetaccess.CommandsQueue;

public class DetallesLocalActivity extends Activity {

	private void updateSmile(TextView likestv, Local l, TextView smiletv) {
		likestv.setText(l.getVotes().toString());

		if (l.isVoted()) {
			likestv.setTextColor(getResources().getColor(R.color.greenPromm));
			smiletv.setTextColor(getResources().getColor(R.color.greenPromm));
		} else {
			likestv.setTextColor(Color.WHITE);
			smiletv.setTextColor(Color.WHITE);
		}
		StoresDB.getStoresDB().addLocal(l.getid(), l);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_local);

		final Bundle b = getIntent().getExtras();
		ImageSetter idwn = ImageSetter.getInstance();

		TextView openDialTel = (TextView) findViewById(R.id.btnDialTel);
		TextView addrtv = (TextView) findViewById(R.id.ttAddr);
		TextView localnametv = (TextView) findViewById(R.id.ttName1);
		final TextView likestv = (TextView) findViewById(R.id.likesText);
		final TextView smiletv = (TextView) findViewById(R.id.smileText);
		TextView opengallery = (TextView) findViewById(R.id.btnPhotoGallery);
		TextView infoLocal = (TextView) findViewById(R.id.infoTV);

		ImageView avatarIV = (ImageView) findViewById(R.id.avatarIV);
		final Local l = b.getParcelable("Local");
		localnametv.setText(l.getname());

		updateSmile(likestv,l, smiletv);
		
		TextView goBack = (TextView) findViewById(R.id.goBack);
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DetallesLocalActivity.this.finish();
				
			}
		});
		
		infoLocal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				l.showInfo(v.getContext());
			}
		});

		smiletv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Command c;
				if (!l.isVoted()) {
					c = new Command(COMMANDTYPE.UPVOTE);
					c.setLocal(l.getid());
					l.upVote();
				} else {
					c = new Command(COMMANDTYPE.DOWNVOTE);
					c.setLocal(l.getid());
					l.downVote();
				}
				updateSmile(likestv,l, smiletv);
				CommandsQueue.getInstance().queueresponselesscmd(c);
			}
		});

		opengallery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				l.showGallery(v.getContext());
			}
		});

		openDialTel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				l.dial(v.getContext());
			}
		});

		addrtv.setText(l.getaddr());
		addrtv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				l.showInMap(v.getContext());
			}
		});
		
		avatarIV.setAdjustViewBounds(true);
		idwn.setimg(l.getlargeurl(), avatarIV);
	}

}
