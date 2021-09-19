package ar.com.promm.tabs;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.promm.R;
import ar.com.promm.adaptadores.GenericArrayAdapter;
import ar.com.promm.factories.StaticMap;
import ar.com.promm.interfaces.AdapterFactory;
import ar.com.promm.interfaces.BitmapContainer;
import ar.com.promm.interfaces.TabFragment;

public class GenericFragment<A extends BitmapContainer> extends TabFragment {

	public static final String ARGE_MAGICN = "ARG_MAGICN";

	private ArrayList<A> alltarjetas = new ArrayList<A>();
	private PullToRefreshListView ptrlv_all;
	private GenericArrayAdapter<A> adapter;
	private AdapterFactory<A> adapterFactory;
	private Boolean autostart = false;
	
	public static GenericFragment<?> newInstance(int magicN) {
		   Bundle args = new Bundle();
		   args.putInt(ARGE_MAGICN, magicN);
		   GenericFragment<?> fragment = new GenericFragment();
		   fragment.setArguments(args);
		   return fragment;
		}

	private GenericArrayAdapter<A> getAdapter() {
		return this.adapter;
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int magicNumber = getArguments().getInt(ARGE_MAGICN);
		this.adapterFactory = (AdapterFactory<A>) StaticMap.getFactoryFromNumber(magicNumber);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		System.out.println(toString() + "VISIBLE TO USER: " + isVisibleToUser);
		this.autostart = isVisibleToUser;
		if (getView() != null)
			showTime();
	}

	private void initialize(Bundle b) {
		System.out.println("INITIALIZING");
		/*if (this.adapterFactory == null) {
			this.adapterFactory = (AdapterFactory<A>) StaticMap
					.getFactoryFromNumber(magicN);
		}*/
		if (this.alltarjetas == null) {
			this.alltarjetas = new ArrayList<A>();
		}
		if (this.adapter == null) {
			this.adapter = new GenericArrayAdapter<A>(getActivity(),
					this.adapterFactory, this.alltarjetas, getActivity());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.lista_todas, container, false);
		initialize(savedInstanceState);
		this.ptrlv_all = (PullToRefreshListView) mView
				.findViewById(R.id.ptrlv_all);

		getAdapter().injectPTRLV(this.ptrlv_all);

		this.ptrlv_all.setAdapter(getAdapter());

		this.ptrlv_all.setOnRefreshListener(getAdapter());

		if (this.autostart)
			this.showTime();

		return mView;
	}

	@Override
	public void clickWhileVisible() {
		this.ptrlv_all.getRefreshableView().smoothScrollToPosition(0);

	}

	@Override
	public void showTime() {
		if (getAdapter()==null) {
			System.out.println("NULL ADAPTER");
			return;
		}
		if (this.alltarjetas.isEmpty())
			getAdapter().forcerefresh();
	}
}
