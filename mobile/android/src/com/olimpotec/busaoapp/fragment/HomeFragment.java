package com.olimpotec.busaoapp.fragment;

import java.sql.SQLException;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.olimpotec.busaoapp.BusActivity;
import com.olimpotec.busaoapp.R;
import com.olimpotec.busaoapp.adapter.BusListAdapter;
import com.olimpotec.busaoapp.model.dao.BusDao;
import com.olimpotec.busaoapp.model.entity.Bus;

public class HomeFragment extends ListFragment {
	
	private BusDao busDao;
	private ProgressDialog progress;
	private String query;
	public HomeFragment()
	{
		try {
			busDao = new BusDao ();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreate (Bundle bundle)
	{
		
		super.onCreate(bundle);
		
		
		refresh();
	}
	
	@Override
	public void onActivityCreated (Bundle bundle)
	{
		
		super.onActivityCreated (bundle);
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		return inflater.inflate(R.layout.fragment_home, container, false); 
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) 
	{
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	public void refresh ()
	{
		
		
		List<Bus> bus;
		
		if(query == null)
			bus =  busDao.getByPage(0, 20);
		else
			bus = busDao.getAllByQuery(query, 0, 20);
		
		if(bus == null || bus.size() == 0)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setMessage(R.string.bus_notfound_msg)
			       .setTitle(R.string.bus_notfound_title).setPositiveButton("OK", null);

			AlertDialog dialog = builder.create();
			dialog.show();
		}
		else
			setListAdapter( new BusListAdapter(getActivity(), R.layout.home_bus_row_item, bus));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) 
	{
		Bus bus = (Bus) this.getListAdapter().getItem(position);
		
		Intent i = new Intent (this.getActivity(), BusActivity.class);
		Bundle extras = new Bundle ();
		
		extras.putInt(BusActivity.BUS_ID, bus.getBusId());
		extras.putString(BusActivity.BUS_NAME, bus.getBusNumber() +" - "+ bus.getBusName());
		i.putExtras(extras);
		
		startActivity (i);
		
		super.onListItemClick(l, v, position, id);
	}
	
	public void setQuery (String query)
	{
		this.query = query;
	}
}
