package com.olimpotec.busaoapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.olimpotec.busaoapp.custom.CustomTextView;

public class ScheduleItemAdapter extends BaseAdapter 
{
    private Context mContext;
 
    // Keep all Images in array
    public String[] items;
    
 
    // Constructor
    public ScheduleItemAdapter(Context c, String[] items)
    {
        mContext = c;
        this.items = items;
    }
 
    @Override
    public int getCount() 
    {
        return items.length;
    }
 
    @Override
    public Object getItem(int position) 
    {
        return items[position];
    }
 
    @Override
    public long getItemId(int position) 
    {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        CustomTextView textView = new CustomTextView(mContext);
        
        String [] data = items[position].split("#");
        
        textView.setText(data[0]);
        
        if(data.length > 1)
        {
        	//parent.setBackgroundColor(Color.parseColor(ExpandableScheduleListAdapter.colorMarkers[Integer.valueOf(data[1].trim())]));
        	textView.setTextColor(Color.parseColor(ExpandableScheduleListAdapter.colorMarkers[Integer.valueOf(data[1].trim())]));
        }
        textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL);
        
        
        return textView;
    }
 
}