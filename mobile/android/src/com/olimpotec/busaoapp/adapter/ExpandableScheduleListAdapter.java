package com.olimpotec.busaoapp.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.olimpotec.busaoapp.R;


public class ExpandableScheduleListAdapter extends ExpandableListAdapter 
{
	Long qtdMarkers;
	public static String [] colorMarkers = {"#33B5E5", "#AA66CC", "#99CC00", "#FFBB33", "#FF4444", "#FF7979", "#CB97E5", "#B6DB49"};
	public ExpandableScheduleListAdapter(Context context,
			List<String> listDataHeader,
			HashMap<String, List<String>> listChildData, Long qtdMarkers) 
	{
		super(context, listDataHeader, listChildData);
		this.qtdMarkers = qtdMarkers;
	}
	
	@Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.schedule_item, null);
        }
 
        GridView gridView = (GridView) convertView.findViewById(R.id.grid_view);
        
        gridView.setNumColumns(qtdMarkers.intValue() + 1);
        
        gridView.setAdapter(new ScheduleItemAdapter(this._context, childText.split("-")));
        
        return convertView;
    }
}
