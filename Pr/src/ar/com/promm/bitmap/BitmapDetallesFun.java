package ar.com.promm.bitmap;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.widget.ImageView;
import ar.com.promm.R;

public class BitmapDetallesFun implements BitmapFun {
    private final WeakReference<ImageView> imageViewReference;
	
	public BitmapDetallesFun(ImageView imageView) {
		this.imageViewReference= new WeakReference<ImageView>(imageView);
	}

	@Override
	public void execute(Bitmap b) {
		if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
            	if (b!=null)
            		imageView.setImageBitmap(b);
            	else
            		imageView.setImageResource(R.drawable.null_background);
            }
        }
	}

}
