package com.olimpotec.busaoapp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.olimpotec.busaoapp.R;
import com.olimpotec.busaoapp.helper.LogHelper;
import com.olimpotec.busaoapp.model.entity.Bus;

public class BusListAdapter extends ArrayAdapter<Bus> {
	private int				resource;
	private LayoutInflater	inflater;
	private Context 		context;
	public BusListAdapter ( Context ctx, int resourceId, List<Bus> objects) {
		
		super( ctx, resourceId, objects );
		
		resource = resourceId;
		inflater = LayoutInflater.from( ctx );
		context=ctx;
	}
	@Override
	public View getView ( int position, View convertView, ViewGroup parent ) { 
		LogHelper.debug(this, "getView");
		convertView = ( RelativeLayout ) inflater.inflate( resource, null ); 
		
		Bus bus = getItem( position );
		LogHelper.debug(this, bus.getBusName());
				TextView legendName = (TextView) convertView.findViewById(R.id.legendName);
		legendName.setText(bus.getBusName());

		
		TextView busNumber = (TextView) convertView.findViewById(R.id.busNumber);
		busNumber.setText(bus.getBusNumber());
		
		if(bus.getColor().equalsIgnoreCase("Vermelho"))
			busNumber.setTextColor(Color.rgb(255,0,0));
		else if(bus.getColor().equalsIgnoreCase("Azul"))
			busNumber.setTextColor(Color.rgb(0,0,255));
		else if(bus.getColor().equalsIgnoreCase("Amarelo"))
			busNumber.setTextColor(Color.rgb(202,202,0));
		
		return convertView;
	}
}

