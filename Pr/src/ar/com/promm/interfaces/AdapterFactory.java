package ar.com.promm.interfaces;

import android.view.View;
import ar.com.promm.ImageSetter;
import ar.com.promm.datastructures.Command;

public interface AdapterFactory<A> {

	int getlayoutId();
	
	ArrayParser<A> getParser();

	void customizeView(View v, ImageSetter idwn, A a, AdapterFun af);

	Command getCommandForNext();
	
	Command getCommandForAll();


}
