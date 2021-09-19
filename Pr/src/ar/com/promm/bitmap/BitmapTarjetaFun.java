package ar.com.promm.bitmap;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import ar.com.promm.interfaces.AdapterFun;
import ar.com.promm.interfaces.BitmapContainer;

public class BitmapTarjetaFun implements BitmapFun {
	private final WeakReference<BitmapContainer> tReference;
	private AdapterFun af;
	
	public BitmapTarjetaFun(BitmapContainer bc, AdapterFun af){
		this.af=af;
		this.tReference = new WeakReference<BitmapContainer>(bc);
	}

	@Override
	public void execute(Bitmap b) {
        if (tReference != null) {
            BitmapContainer t = tReference.get();
            if (t != null) {
            	t.setBitmap(b);
                if (b!=null)
                	af.finished();
            }
        }
	}

}
