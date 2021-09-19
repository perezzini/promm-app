package ar.com.promm;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.widget.ImageView;
import ar.com.promm.bitmap.BitmapDetallesFun;
import ar.com.promm.bitmap.BitmapTarjetaFun;
import ar.com.promm.bitmap.BitmapTask;
import ar.com.promm.interfaces.AdapterFun;
import ar.com.promm.interfaces.BitmapContainer;

public class ImageSetter {
	
	private static final Hashtable<String, Bitmap> ht=new Hashtable<String, Bitmap>();
	private static ImageSetter instance;
	private ImageSetter() {
	}
	
	public static ImageSetter getInstance() {
		if (instance==null)
			instance=new ImageSetter();
		return instance;
	}

    public void setimg(String url, ImageView imageView) {
    	Bitmap b = ht.get(url);
    	if (b==null) {
    		BitmapTask task = new BitmapTask(new BitmapDetallesFun(imageView), ht);
    		task.execute(url);
    	} else {
    		imageView.setImageBitmap(b);
    	}
    }
    
    public void setcardimg(String url, BitmapContainer bc, AdapterFun af) {
    	Bitmap b = ht.get(url);
    	if (b==null) {
    		BitmapTask task = new BitmapTask(new BitmapTarjetaFun(bc, af), ht);
    		task.execute(url);
    	} else {
    		bc.setBitmap(b);
    	}
    }

}
