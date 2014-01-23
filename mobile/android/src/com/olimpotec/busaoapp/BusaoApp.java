package com.olimpotec.busaoapp;

import android.app.Application;

import com.olimpotec.busaoapp.helper.Database;
import com.olimpotec.busaoapp.helper.Preferences;

public class BusaoApp extends Application 
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		Preferences.singleton (this);
		
		Database.singleton (this);	
	}
}
