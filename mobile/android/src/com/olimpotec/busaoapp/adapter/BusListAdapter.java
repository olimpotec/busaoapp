package com.olimpotec.busaoapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.olimpotec.busaoapp.R;
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
		
		convertView = ( RelativeLayout ) inflater.inflate( resource, null ); 
		
		Bus bus = getItem( position );
		
		TextView busName = (TextView) convertView.findViewById(R.id.busName);
		busName.setText(bus.getBusName());
		
		TextView company = (TextView) convertView.findViewById(R.id.company);
		company.setText(bus.getCompany());
		
		TextView busNumber = (TextView) convertView.findViewById(R.id.busNumber);
		busNumber.setText(bus.getBusNumber());
		
		
		ImageView busColor = (ImageView) convertView.findViewById(R.id.busColor);
		
		
		if(bus.getColor().equalsIgnoreCase("Vermelho"))
			busColor.setImageResource(R.drawable.icon_bus_red);
		else if(bus.getColor().equalsIgnoreCase("Azul"))
			busColor.setImageResource(R.drawable.icon_bus_blue);
		else if(bus.getColor().equalsIgnoreCase("Amarelo"))
			busColor.setImageResource(R.drawable.icon_bus_yellow);
		
		return convertView;
	}
}

