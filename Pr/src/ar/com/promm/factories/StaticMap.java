package ar.com.promm.factories;

import ar.com.promm.interfaces.AdapterFactory;

public class StaticMap {
	public static final int prommsCode = 1;
	public static final int storesCode = 0;
	
	public static AdapterFactory<?> getFactoryFromNumber(int magicN) {
		AdapterFactory<?> ret = null;
		switch (magicN) {
			case prommsCode:
				ret = FactoryPromociones.getInstance();
				break;
			case storesCode:
				ret = FactoryLocales.getInstance();
				break;
		}
		return ret;
	}
}
