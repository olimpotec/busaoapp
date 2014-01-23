package com.olimpotec.busaoapp.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.olimpotec.busaoapp.R;
import com.olimpotec.busaoapp.helper.LogHelper;

public class CustomTextView extends TextView
{
	private String font = "HelveticaExComp";
	
    public CustomTextView(Context context)
    {
        super(context);
       
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        
        String fontInfo = a.getString(R.styleable.CustomTextView_setFont);
        
		if(fontInfo != null) 
			font = fontInfo;
		
		a.recycle();
		
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle)
    {
		super(context, attrs, defStyle);
		
		TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyle, 0);
		String fontInfo = a.getString(R.styleable.CustomTextView_setFont);
		
		
		if(fontInfo != null) 
			font = fontInfo;
		
		a.recycle();
		
		init();
    }

    public void init()
    {	
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+font+".ttf");
        
        setTypeface(tf);
    }
}