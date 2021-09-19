package ar.com.promm.datastructures;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import ar.com.promm.StoresDB;
import ar.com.promm.activities.DetallesLocalActivity;
import ar.com.promm.activities.GalleryActivity;
import ar.com.promm.activities.InfoLocalActivity;

public class Local implements Parcelable {
	
	private String nombre;
	private String direccion;
	private String telefono;
	private String descripcion;
	private String smallurl;
	private String largeurl;
	private String lat;
	private String lng;
	private String id;
	private Boolean voted;
	private Integer votes;
	
	
	
	public Local(String nombre, String direccion, String telefono, String descripcion, String smallurl, String largeurl, String lat, String lng, String id, Integer votes, Boolean voted) {
		this.nombre=nombre;
		this.direccion=direccion;
		this.telefono=telefono;
		this.descripcion=descripcion;
		this.smallurl=smallurl;
		this.largeurl = largeurl;
		this.lat=lat;
		this.lng=lng;
		this.id=id;
		this.voted=voted;
		this.votes=votes;
	}
	
	public String getLat() {
		return this.lat;
	}
	
	public String getLng() {
		return this.lng;
	}
	
	public String getname() {
		return this.nombre;
	}
	
	public String getsmallurl() {
		return this.smallurl;
	}
	
	public String getlargeurl() {
		return this.largeurl;
	}
	
	public String getaddr() {
		return this.direccion;
	}
	
	public String getphone() {
		return this.telefono;
	}
	
	public String getdesc() {
		return this.descripcion;
	}
	
	public String getid() {
		return this.id;
	}
	
	public static final Parcelable.Creator<Local> CREATOR = new Parcelable.Creator<Local>() {
		public Local createFromParcel(Parcel in) {
			return new Local(in.readString(),in.readString(), in.readString(), in.readString(), in.readString(), in.readString(), in.readString(), in.readString(), in.readString(), in.readInt(), (in.readByte() != 0));
		}

		@Override
		public Local[] newArray(int arg0) {
			return new Local[arg0];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(nombre);
		dest.writeString(direccion);
		dest.writeString(telefono);
		dest.writeString(descripcion);
		dest.writeString(smallurl);
		dest.writeString(largeurl);
		dest.writeString(lat);
		dest.writeString(lng);
		dest.writeString(id);
		dest.writeInt(votes);
		dest.writeByte((byte) (voted ? 1 : 0));
	}
	
	public void dial(Context context) {
		Intent dialIntent = new Intent(Intent.ACTION_DIAL);          
        dialIntent.setData(Uri.parse("tel:"+getphone()));          
        context.startActivity(dialIntent);  
	}
	
	public void showInMap(Context context) {
		StringBuilder sb =  new StringBuilder(); // demasiadas concatenaciones..
		Intent showMapIntent = new Intent(Intent.ACTION_VIEW);          
		sb.append("geo:");
		sb.append(getLat());
		sb.append(",");
		sb.append(getLng());
		sb.append("?q=");
		sb.append(getLat());
		sb.append(",");
		sb.append(getLng());
		showMapIntent.setData(Uri.parse(sb.toString()));          
        context.startActivity(showMapIntent);
	}

	public void dive(Context context) {
		Intent intent = new Intent(context,DetallesLocalActivity.class);
    	Bundle b = new Bundle();
    	Local otherlocal;
    	Local parcellocal = this;
    	if ((otherlocal = StoresDB.getStoresDB().getLocal(id))!=null) {
    		parcellocal = otherlocal;
    	}
        b.putParcelable("Local", parcellocal);
        intent.putExtras(b);
        context.startActivity(intent);
		
	}

	public void showGallery(Context context) {
		Intent intent = new Intent(context,GalleryActivity.class);
		Bundle b = new Bundle();
        b.putParcelable("Local", this);
        intent.putExtras(b);
        context.startActivity(intent);
		
		
	}
	
	public void showInfo(Context context) {
		Intent intent = new Intent(context,InfoLocalActivity.class);
		Bundle b = new Bundle();
        b.putParcelable("Local", this);
        intent.putExtras(b);
        context.startActivity(intent);
	}

	public Integer getVotes() {
		return this.votes;
	}

	public boolean isVoted() {
		return this.voted;
	}

	public void upVote() {
		if (isVoted()) {
			return;
		}
		this.voted=true;
		this.votes++;
		
	}

	public void downVote() {
		if (!isVoted()) {
			return;
		}
		this.voted=false;
		this.votes--;
		
	}

}
