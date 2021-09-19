package ar.com.promm.adaptadores;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ar.com.promm.ImageSetter;
import ar.com.promm.Logger;
import ar.com.promm.R;
import ar.com.promm.VarAccess;
import ar.com.promm.datastructures.Command;
import ar.com.promm.enums.Enums.STATE;
import ar.com.promm.factories.AdapterFunFactory;
import ar.com.promm.interfaces.AdapterFactory;
import ar.com.promm.interfaces.AdapterFun;
import ar.com.promm.interfaces.EntriesUpdater;
import ar.com.promm.runnables.EntriesFetcher;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class GenericArrayAdapter<A> extends ArrayAdapter<A> implements
		OnRefreshListener<ListView> {

	private static final int FOOTERID = 0xf007; // Me lo saque del orto mal

	private static final int SENTINELN = 5; // Configurable

	private ArrayList<A> values;
	private static final ImageSetter idwn = ImageSetter.getInstance();
	private Boolean nomore = false;
	private AdapterFun oncomplete;
	private Lock lock = new Lock();
	private AdapterFactory<A> af;
	private VarAccess<PullToRefreshListView> vaptrlv = new VarAccess<PullToRefreshListView>();
	private Activity parentAct;

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		if (refreshView != vaptrlv.get()) {
			System.out.println("YDALEBOCAAAAAAAAAAAAAAAAAA");
		}
		forcerefresh();
	}

	private class Queue implements Runnable {
		private ArrayList<A> entries;

		public Queue(ArrayList<A> entries) {
			this.entries = entries;
		}

		@Override
		public void run() {
			oncomplete.finished();
			lock.setState(STATE.IDLE);
			if (entries == null) {
				endoflist();
				Toast.makeText(getContext(),
						"Hubo un error durante la descarga", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (entries.size() == 0) {
				endoflist();
				return;
			}
			values.remove(values.size() - 1);
			values.addAll(entries);
			values.add(null);
			Logger.log("ACTUALLY UPDATING. SIZE: " + entries.size());
			notifyDataSetChanged();
		}

	}

	public GenericArrayAdapter(Context context, AdapterFactory<A> af,
			ArrayList<A> objects, Activity parent) {
		super(context, af.getlayoutId(), objects);

		this.af = af;
		this.oncomplete = AdapterFunFactory.getInstance().getPTRLVHandler(
				vaptrlv);
		this.values = objects;
		this.parentAct = parent;
	}

	private EntriesUpdater<A> updateEntries = new EntriesUpdater<A>() {
		@Override
		public void update(ArrayList<A> entries, String cursor) {
			if (cursor!=null)
				GenericArrayAdapter.this.cursor=cursor;
			parentAct.runOnUiThread(new Queue(entries));
		}
	};

	private String cursor = null;

	public void forcerefresh() {
		switch (lock.getState()) {
		case IDLE: {
			lock.setState(STATE.FORCINGREFRESH);
			Logger.log("FORCING REFRESH");
			vaptrlv.get().setRefreshing(true);
			nomore = false;
			values.clear();
			values.add(null);
			notifyDataSetChanged();
			final Command command = af.getCommandForAll();
			postProcess(command);
			new Thread(new EntriesFetcher<A>(updateEntries,
					command , af.getParser())).start();

			break;
		}
		case GETTINGMORE: {
		}
		case FORCINGREFRESH:
			Logger.log("ALREADY FORCING REFRESH");
			break;
		}

	}

	private void getmore() {
		switch (lock.getState()) {
		case IDLE: {
			Logger.log("GETTING MORE");
			lock.setState(STATE.GETTINGMORE);
			if (cursor == null)
				break;
			Command command = af.getCommandForNext();
			postProcess(command);
			new Thread(new EntriesFetcher<A>(updateEntries,
					command , af.getParser())).start();
			break;
		}
		case GETTINGMORE:
			break;
		case FORCINGREFRESH:
			break;
		}
	}

	private void postProcess(Command command) {
		if (cursor!=null)
			command.setCursor(cursor);

	}

	private final AdapterFun adapterFun = new AdapterFun() {
		public void finished() {
			notifyDataSetChanged();
		}
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		A a = values.get(position);
		if (a == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.footer, parent, false);
			v.setId(FOOTERID);
			return v;
		}

		if (v == null || v.getId() == FOOTERID) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(af.getlayoutId(), parent, false);
		}

		if (position > (getCount() - SENTINELN) && !nomore) {
			getmore();
		}
		af.customizeView(v, idwn, a, adapterFun);

		return v;
	}

	private void endoflist() {
		nomore = true;
		Logger.log("Llega con values size: " + values.size());
		if (values.size() > 0 && values.get(values.size() - 1) == null)
			values.remove(values.size() - 1);
		notifyDataSetChanged();
	}

	public void injectPTRLV(PullToRefreshListView ptrlv) {
		this.vaptrlv.set(ptrlv);

	}
}
