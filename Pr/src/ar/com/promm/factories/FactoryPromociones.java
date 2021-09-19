package ar.com.promm.factories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.promm.ImageSetter;
import ar.com.promm.R;
import ar.com.promm.StoresDB;
import ar.com.promm.datastructures.Command;
import ar.com.promm.datastructures.Local;
import ar.com.promm.datastructures.Promocion;
import ar.com.promm.datastructures.Tarjeta_promocion;
import ar.com.promm.enums.Enums.COMMANDTYPE;
import ar.com.promm.interfaces.AdapterFactory;
import ar.com.promm.interfaces.AdapterFun;
import ar.com.promm.interfaces.ArrayParser;
import ar.com.promm.parsers.DateParser;
import ar.com.promm.parsers.JSonParser;

public class FactoryPromociones implements AdapterFactory<Tarjeta_promocion> {
	

	private static final COMMANDTYPE cmdTypeNext = COMMANDTYPE.MOREPROMMS;
	
	private static final COMMANDTYPE cmdTypeAll = COMMANDTYPE.ALLPROMMS;
	
	private static FactoryPromociones instance=new FactoryPromociones();
	
	private FactoryPromociones() {}
	
	public static FactoryPromociones getInstance() {
		if (instance==null) {
			instance= new FactoryPromociones();
		}
		return instance;
	}

	@Override
	public int getlayoutId() {
		return R.layout.listaitem_promocion;
	}

	@Override
	public ArrayParser<Tarjeta_promocion> getParser() {
		return new ArrayParser<Tarjeta_promocion>() {
			@Override
			public ArrayList<Tarjeta_promocion> parse(String s)
					throws JSONException {
				ArrayList<Tarjeta_promocion> promos = JSonParser.parsePromociones(s);
				StoresDB storesDB = StoresDB.getStoresDB();
				for (Tarjeta_promocion p : promos) {
					Local l = p.getProm().getlocal();
					storesDB.addLocal(l.getid(), l);
				}
				return promos;
			}
		};
	}
	
	
	private static final OnClickListener prommClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			if (view.getTag() instanceof Promocion) {
				Promocion p = (Promocion) view.getTag();
				p.dive(view.getContext());
			}
		}
	};
	
	public static void commonSetup (View v, final Promocion p) {
		ImageSetter idwn = ImageSetter.getInstance();
		TextView ttName = (TextView) v.findViewById(R.id.ttName);
		TextView ttAddr = (TextView) v.findViewById(R.id.ttAddr);
		TextView ttDesc = (TextView) v.findViewById(R.id.ttDesc);
		TextView ttSchedule = (TextView) v.findViewById(R.id.ttSchedule);
		ImageView avatariv = (ImageView) v.findViewById(R.id.avatarIV);
		TextView inmapiv = (TextView) v.findViewById(R.id.inMapIV);
		TextView shareiv = (TextView) v.findViewById(R.id.share);

		final Local l = p.getlocal();
		
		inmapiv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				l.showInMap(v.getContext());
			}
		});
		shareiv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				p.share(v.getContext());
			}
		});
		avatariv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				l.dive(v.getContext());
				
			}
		});
		ttDesc.setText(p.getdesc());
		ttName.setText(l.getname());
		ttAddr.setText(l.getaddr());
		Date startTime = DateParser.str2Date(p.getStartTime());
		Date stopTime = DateParser.str2Date(p.getStopTime());
		if (isAllDay(startTime, stopTime))
			ttSchedule.setText(R.string.todoeldia);
		else
			ttSchedule.setText(DateParser.printSchedule(startTime, stopTime));
		avatariv.setAdjustViewBounds(true);
		idwn.setimg(l.getsmallurl(), avatariv);
	}
	
	private static boolean isAllDay(Date startTime, Date stopTime) {
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(startTime);   // assigns calendar to given date 
		if (calendar.get(Calendar.HOUR_OF_DAY)==0 && calendar.get(Calendar.MINUTE)==0)
		{
			calendar.setTime(stopTime);
			return (calendar.get(Calendar.HOUR_OF_DAY)==23 && calendar.get(Calendar.MINUTE) == 59);
		}
		return false;
	}

	@Override
	public void customizeView(View v, ImageSetter idwn, Tarjeta_promocion t,
			AdapterFun af) {
		Promocion p = t.getProm();
		commonSetup(v,p);
		v.setTag(p);
		v.setOnClickListener(prommClickListener);
	}

	@Override
	public Command getCommandForNext() {
		Command cmd = new Command(cmdTypeNext);
		return cmd;
	}

	@Override
	public Command getCommandForAll() {
		return new Command(cmdTypeAll);
	}

}
