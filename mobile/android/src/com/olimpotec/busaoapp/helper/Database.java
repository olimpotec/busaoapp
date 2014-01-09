package com.olimpotec.busaoapp.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class Database extends OrmLiteSqliteOpenHelper
{
	//The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.olimpotec.busaoapp/databases/";
    private static String ASSETS_DB_PATH = "db/";
    
    private static String DB_NAME = "busaoapp.db";
 
    private SQLiteDatabase db; 
 
    private final Context context;
    
	private static Database database;
	
	public static final int VERSION = 2;
	
	private Database (Context context)
	{
		super (context, DB_NAME, null, VERSION);
		LogHelper.debug(this, "Data Base Constructor");
		
		this.context = context;
	}
	
	public static Database singleton (Context context)
	{
		if (database == null)
			database = new Database (context); 
		try 
		{
			database.createDataBase();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		database.openDataBase();
		
		return database;
	}
	
	public static Database singleton ()
	{
		if (database == null)
			return null; 
		
		return database;
	}

	
	
	/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException
    {
 
    	boolean dbExist = checkDataBase();
 
    	if(!dbExist)
    	{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
    		db = this.getReadableDatabase();
 
        	try{
    			copyDataBase();
    		} 
        	catch (IOException e) 
        	{
        		throw new Error(e.getCause());
        	}
    	}
    }
    
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try
    	{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    		LogHelper.debug(this, "Database Exist!");
    	}
    	catch(SQLiteException e)
    	{
    		LogHelper.debug(this, "Database doesnt exist yet");
    		//database does't exist yet.
    	}
 
    	if(checkDB != null)
    	{
    		checkDB.close();
    	}

    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException
    {
    	LogHelper.debug(this, "Copying database from: "+ ASSETS_DB_PATH + DB_NAME );
    	//Open your local db as the input stream
    	InputStream myInput = context.getAssets().open(ASSETS_DB_PATH + DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	
    	while ((length = myInput.read(buffer))>0)
    	{
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    	
    	LogHelper.debug(this, "Database Copied!" );
 
    }
 
    public void openDataBase() throws SQLException
    {
    	LogHelper.debug(this, "Openning Database on !" + DB_PATH + DB_NAME );
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    	
    }
    
	@Override
	protected void finalize () throws Throwable
	{
		Database.singleton ().close ();
		
		super.finalize ();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
}