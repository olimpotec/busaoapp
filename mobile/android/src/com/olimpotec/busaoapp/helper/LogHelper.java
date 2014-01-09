package com.olimpotec.busaoapp.helper;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import android.util.Log;

public class LogHelper
{
	private LogHelper ()
	{}
	
	public static void error (@SuppressWarnings ("rawtypes") Class c, String message, Throwable throwable)
	{
		Log.e (c.getName (), message, throwable);
	}
	
	public static void error (Object o, String message, Throwable throwable)
	{
		error (o.getClass (), message, throwable);
	}

	public static void error (@SuppressWarnings ("rawtypes") Class c, String message)
	{
		Log.e (c.getName (), message);
	}
	
	public static void error (Object o, String message)
	{
		error (o.getClass (), message);
	}

	public static void debug (@SuppressWarnings ("rawtypes") Class c, HttpGet request)
	{
		String tag = c.getName ();
		
		Log.d (tag, "HTTP GET");
		
		Log.d (tag, request.getURI ().toString ());

		for (Header header : request.getAllHeaders ())
			Log.d (tag, header.getName () + ": " + header.getValue ());
	}
	
	public static void debug (Object o, HttpGet request)
	{
		debug (o.getClass (), request);
	}

	public static void debug (@SuppressWarnings ("rawtypes") Class c, HttpResponse response)
	{
		String tag = c.getName ();
		
		Log.d (tag, "HTTP RESPONSE");
		
		Log.d (tag, "HTTP/" + response.getStatusLine ().getProtocolVersion () + " " + response.getStatusLine ().getStatusCode () + " " + response.getStatusLine ().getReasonPhrase ());

		for (Header header : response.getAllHeaders ())
			Log.d (tag, header.getName () + ": " + header.getValue ());
	}
	
	public static void debug (Object o, HttpResponse response)
	{
		debug (o.getClass (), response);
	}

	public static void debug (@SuppressWarnings ("rawtypes") Class c, String message)
	{
		Log.d (c.getName (), message);
	}
	
	public static void debug (Object o, String message)
	{
		debug (o.getClass (), message);
	}

	public static void debug (@SuppressWarnings ("rawtypes") Class c, HttpPost request)
	{
		String tag = c.getName ();
		
		Log.d (tag, "HTTP POST");
		
		Log.d (tag, request.getURI ().toString ());

		for (Header header : request.getAllHeaders ())
			Log.d (tag, header.getName () + ": " + header.getValue ());
	}
	
	public static void debug (Object o, HttpPost request)
	{
		debug (o.getClass (), request);
	}
}