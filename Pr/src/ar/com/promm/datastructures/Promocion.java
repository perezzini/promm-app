package ar.com.promm.datastructures;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import ar.com.promm.activities.DetallesPrommActivity;


public class Promocion implements Parcelable
{
	private String descripcion;
	private String startTimeStr, stopTimeStr;
	private Local l;
	private String prommShareId;

	public Promocion(Local l, String descripcion,
			 String startTimeStr, String stopTimeStr,
			 String prommShareId)
	{
		this.l = l;
		this.descripcion = descripcion;
		this.startTimeStr = startTimeStr;
		this.stopTimeStr = stopTimeStr;
		this.prommShareId = prommShareId;
	}

	public String getPrommShareId() {
		return this.prommShareId;
	}

	public String getdesc(){
		return this.descripcion;
	}

	public String getStartTime(){
		return this.startTimeStr;
	}

	public String getStopTime(){
		return this.stopTimeStr;
	}

	public Local getlocal() {
		return this.l;
	}

	public static final Parcelable.Creator<Promocion> CREATOR = new Parcelable.Creator<Promocion>() {
		public Promocion createFromParcel(Parcel in) {
			Local l = in.readParcelable(Local.class.getClassLoader());
			String descripcion = in.readString();
			String startTimeStr = in.readString();
			String stopTimeStr = in.readString();
			String prommShareId = in.readString();
			
			return new Promocion(l, descripcion, startTimeStr, stopTimeStr, prommShareId);
		}

		@Override
		public Promocion[] newArray(int arg0) {
			return new Promocion[arg0];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(l, 0);
		dest.writeString(descripcion);
		dest.writeString(startTimeStr);
		dest.writeString(stopTimeStr);
		dest.writeString(prommShareId);
	}

	public void share(Context context) {
		String shareText="¡Echa un vistazo a este Promm de " + getlocal().getname() + "! https://prommapp.com/p/" + getPrommShareId();
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		sendIntent.setType("text/plain");
		context.startActivity(sendIntent);
	}

	public void dive(Context context) {
        Intent intent = new Intent(context,DetallesPrommActivity.class);
        Bundle b = new Bundle();
        b.putParcelable("Promocion", this);
        intent.putExtras(b);
        context.startActivity(intent);				
		
	}


}
