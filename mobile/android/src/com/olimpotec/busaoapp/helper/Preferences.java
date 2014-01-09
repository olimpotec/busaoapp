package com.olimpotec.busaoapp.helper;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

import com.olimpotec.busaoapp.BusaoApp;

public class Preferences
{
	private static SharedPreferences preferences;
	
	private Preferences ()
	{}
	
	public static SharedPreferences singleton (BusaoApp app)
	{
		if (preferences == null)
			preferences = app.getSharedPreferences ("com.olimpotec.busaoapp", Context.MODE_PRIVATE);
		
		return preferences;
	}
	
	public static SharedPreferences singleton ()
	{
		return preferences;
	}
	
	public static void clear ()
	{
		Map<String, ?> keys = preferences.getAll ();
		
		SharedPreferences.Editor editor = singleton ().edit ();

		for(Map.Entry<String, ?> entry : keys.entrySet ())
		{
			if (entry.getValue ().getClass ().equals (String.class))
				editor.putString (entry.getKey (), "");
			else if (entry.getValue ().getClass ().equals (Integer.class))
				editor.putInt (entry.getKey (), 0);
			else if (entry.getValue ().getClass ().equals (Long.class))
				editor.putLong (entry.getKey (), 0);
			else if (entry.getValue ().getClass ().equals (Boolean.class))
				editor.putBoolean (entry.getKey (), false);
			else if (entry.getValue ().getClass ().equals (Float.class))
				editor.putFloat (entry.getKey (), 0);
		}
		
		editor.commit ();
	}
}