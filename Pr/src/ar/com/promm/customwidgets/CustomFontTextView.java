package ar.com.promm.customwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import ar.com.promm.R;

public class CustomFontTextView extends TextView {

	public CustomFontTextView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		System.out.println("FIRST CONSTRUCTOR");
	}

	public CustomFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CustomFontTextView, 0, 0);
		String typeFaceName = a
				.getString(R.styleable.CustomFontTextView_typeFace);
		a.recycle();
		if (typeFaceName == null)
			typeFaceName = "Dosis-Regular";
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/"
				+ typeFaceName + ".ttf");
		setTypeface(tf);
	}

	public CustomFontTextView(Context context) {
		super(context);
		System.out.println("THIRD CONSTRUCTOR");
		// TODO Auto-generated constructor stub
	}

}
