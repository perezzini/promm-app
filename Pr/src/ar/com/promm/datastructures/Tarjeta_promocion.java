package ar.com.promm.datastructures;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import ar.com.promm.interfaces.BitmapContainer;

public class Tarjeta_promocion implements BitmapContainer {
	
	private Bitmap b;
	private Promocion p;
	public Tarjeta_promocion(Promocion p) {
		this.p=p;
	}
	
	public Promocion getProm() {
		return this.p;
	}
	
	public Bitmap getBitmap(){
		return this.b;
	}
	
	public void setBitmap(Bitmap b){
		this.b=b;
	}
	
	public static final Parcelable.Creator<Tarjeta_promocion> CREATOR = new Parcelable.Creator<Tarjeta_promocion>() {
		public Tarjeta_promocion createFromParcel(Parcel in) {
			return new Tarjeta_promocion((Promocion) in.readParcelable(Promocion.class.getClassLoader()));
		}

		@Override
		public Tarjeta_promocion[] newArray(int arg0) {
			return new Tarjeta_promocion[arg0];
		}
	};



}
