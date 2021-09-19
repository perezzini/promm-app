package ar.com.promm.datastructures;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import ar.com.promm.interfaces.BitmapContainer;

public class Tarjeta_local implements  BitmapContainer {
	
	private Bitmap b;
	private Local l;
	
	public Tarjeta_local(Local l) {
		this.l=l;
	}
	
	public Local getlocal() { 
		return this.l; 
	}
	
	public Bitmap getBitmap(){
		return this.b;
	}
	
	public void setBitmap(Bitmap b){
		this.b=b;
	}

	public static final Parcelable.Creator<Tarjeta_local> CREATOR = new Parcelable.Creator<Tarjeta_local>() {
		public Tarjeta_local createFromParcel(Parcel in) {
			return new Tarjeta_local((Local) in.readParcelable(Local.class.getClassLoader()));
		}

		@Override
		public Tarjeta_local[] newArray(int arg0) {
			return new Tarjeta_local[arg0];
		}
	};



}
