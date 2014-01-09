package com.olimpotec.busaoapp.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.olimpotec.busaoapp.R;

public class CustomTextView extends TextView
{
	private String font = "HelveticaNeue";
	
    public CustomTextView(Context context)
    {
        super(context);
       
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
		font = a.getString(R.styleable.CustomTextView_setFont);
		a.recycle();
		
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle)
    {
		super(context, attrs, defStyle);
		
		TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyle, 0);
		font = a.getString(R.styleable.CustomTextView_setFont);
		a.recycle();
		
		init();
    }

    public void init()
    {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+font+".ttf");
        
        setTypeface(tf);
    }
}