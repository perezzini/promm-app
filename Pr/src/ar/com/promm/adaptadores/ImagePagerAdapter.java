package ar.com.promm.adaptadores;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import ar.com.promm.ImageSetter;
import ar.com.promm.R;
import ar.com.promm.datastructures.Local;
import ar.com.promm.interfaces.EntriesUpdater;
import ar.com.promm.runnables.URLsRetriever;

public class ImagePagerAdapter extends PagerAdapter {
	private ArrayList<String> mImages = new ArrayList<String>();
	private Activity parentAct;

	private ImageSetter is = ImageSetter.getInstance();

	private class Queue implements Runnable {
		private ArrayList<String> entries;

		public Queue(ArrayList<String> entries) {
			this.entries = entries;
		}

		@Override
		public void run() {
			if (entries.size() == 0) {
				Toast.makeText(parentAct, "No hay imágenes por el momento",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (entries == null) {
				Toast.makeText(parentAct, "Hubo un error durante la descarga",
						Toast.LENGTH_SHORT).show();
				return;
			}
			mImages.addAll(entries);
			notifyDataSetChanged();
		}

	}

	public ImagePagerAdapter(Activity parentAct) {
		this.parentAct = parentAct;

	}

	public void retrieveList(Local local) {
		new Thread(new URLsRetriever(updateEntries, local.getid())).start();
	}

	@Override
	public int getCount() {
		return mImages.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (position > mImages.size()) {
			return null;
		}
		Context context = container.getContext();
		ImageView imageView = new ImageView(context);
		int padding = context.getResources().getDimensionPixelSize(
				R.dimen.padding_large);
		imageView.setPadding(padding, padding, padding, padding);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		is.setimg(mImages.get(position), imageView);
		((ViewPager) container).addView(imageView, 0);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}

	private EntriesUpdater<String> updateEntries = new EntriesUpdater<String>() {
		@Override
		public void update(ArrayList<String> entries, String _) {
			parentAct.runOnUiThread(new Queue(entries));
		}
	};
}