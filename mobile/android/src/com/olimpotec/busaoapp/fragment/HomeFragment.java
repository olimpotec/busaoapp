package com.olimpotec.busaoapp.fragment;

import java.sql.SQLException;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olimpotec.busaoapp.R;
import com.olimpotec.busaoapp.adapter.BusListAdapter;
import com.olimpotec.busaoapp.helper.LogHelper;
import com.olimpotec.busaoapp.model.dao.BusDao;

public class HomeFragment extends ListFragment {
	
	private BusDao busDao;
	
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
		LogHelper.debug(this, "onCreate()");
		setListAdapter( new BusListAdapter(getActivity(), R.layout.home_bus_row_item, busDao.getByPage(0, 20)));
	}
	
	@Override
	public void onActivityCreated (Bundle bundle)
	{
		super.onActivityCreated (bundle);
		LogHelper.debug(this, "onActivityCreated()");
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		LogHelper.debug(this, "onCreateView()");
		return inflater.inflate(R.layout.fragment_home, container, false); 
    }
}
