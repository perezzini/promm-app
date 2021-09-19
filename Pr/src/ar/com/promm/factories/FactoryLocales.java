package ar.com.promm.factories;

import java.util.ArrayList;

import org.json.JSONException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ar.com.promm.ImageSetter;
import ar.com.promm.R;
import ar.com.promm.StoresDB;
import ar.com.promm.datastructures.Command;
import ar.com.promm.datastructures.Local;
import ar.com.promm.datastructures.Tarjeta_local;
import ar.com.promm.datastructures.Tarjeta_promocion;
import ar.com.promm.enums.Enums.COMMANDTYPE;
import ar.com.promm.interfaces.AdapterFactory;
import ar.com.promm.interfaces.AdapterFun;
import ar.com.promm.interfaces.ArrayParser;
import ar.com.promm.parsers.JSonParser;

public class FactoryLocales implements AdapterFactory<Tarjeta_local> {
	
	static FactoryLocales instance=null;
	
	private static final COMMANDTYPE cmdTypeNext = COMMANDTYPE.MORESTORES;
	
	private static final COMMANDTYPE cmdTypeAll = COMMANDTYPE.ALLSTORES;
	
	private FactoryLocales() {}
	
	public static FactoryLocales getInstance() {
		if (instance==null) {
			instance=new FactoryLocales();
		}
		return instance;
	}

	@Override
	public int getlayoutId() {
		return R.layout.listaitem_local;
	}

	@Override
	public ArrayParser<Tarjeta_local> getParser() {
		return new ArrayParser<Tarjeta_local>() {
			@Override
			public ArrayList<Tarjeta_local> parse(String s)
					throws JSONException {
				ArrayList<Tarjeta_local> localest = JSonParser.parseLocales(s);
				StoresDB storesDB = StoresDB.getStoresDB();
				for (Tarjeta_local lt : localest) {
					Local l = lt.getlocal();
					storesDB.addLocal(l.getid(), l);
				}
				return localest;
			}
		};
	}
	
	private static final OnClickListener storeClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
	        if (view.getTag() instanceof Local) {
	        	Local l = (Local) view.getTag();
	        	l.dive(view.getContext());
	        }
	    }
	};
	
	@Override
	public void customizeView(View v, ImageSetter idwn, Tarjeta_local t, AdapterFun af) {
		TextView ttName = (TextView) v.findViewById(R.id.ttName);
		TextView ttAddr = (TextView) v.findViewById(R.id.ttAddr);
		Local l = t.getlocal();
		
		v.setTag(l);
		v.setOnClickListener(storeClickListener);
		
		ttName.setText(l.getname());
		ttAddr.setText(l.getaddr());
		
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
