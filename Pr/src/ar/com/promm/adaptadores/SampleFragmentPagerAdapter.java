package ar.com.promm.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import ar.com.promm.factories.StaticMap;
import ar.com.promm.tabs.GenericFragment;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] {"Hoy", "Locales"};

    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
    	switch (position) {
    		case 0:
    			return GenericFragment.newInstance(StaticMap.prommsCode);//new GenericFragment<Tarjeta_promocion>(FactoryPromociones.getInstance());
    		default:
    			return GenericFragment.newInstance(StaticMap.storesCode);//new GenericFragment<Tarjeta_local>(FactoryLocales.getInstance());	
    	}
    	
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}