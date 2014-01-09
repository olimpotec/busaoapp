package com.olimpotec.busaoapp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Timer ().schedule (new TimerTask ()
		{
			@Override
			public void run ()
			{
				startActivity (new Intent (SplashActivity.this, MainActivity.class));
			}
		}, 2000);
    }


    @Override
	protected void onRestart ()
	{
		super.onRestart ();

		finish ();
	}
}
