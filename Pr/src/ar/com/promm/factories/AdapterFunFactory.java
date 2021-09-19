package ar.com.promm.factories;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import ar.com.promm.VarAccess;
import ar.com.promm.interfaces.AdapterFun;

public class AdapterFunFactory {
	private static AdapterFunFactory instance=null;
	
	private AdapterFunFactory() {}
	
	public static AdapterFunFactory getInstance() {
		if (instance==null)
			instance = new AdapterFunFactory();
		return instance;
	}
	
	public AdapterFun getPTRLVHandler(final VarAccess<PullToRefreshListView> vaptrlv) {
		return new AdapterFun() {
			@Override
			public void finished() {
				vaptrlv.get().onRefreshComplete();
			}
		};
	}
}
