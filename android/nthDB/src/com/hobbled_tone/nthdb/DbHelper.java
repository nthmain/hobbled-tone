package com.hobbled_tone.nthdb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String TAG = "DbHelper";
	private Context context = null;
	
	public DbHelper(Context context) {
		super(context, Db.DB_NAME, null, Db.DB_VER);
		
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//Create database from assets db create schema.
		createDb(db,"db_create_schema.sql");
		
		//Any initial data / setup data to throw in there?
			//Not right now.
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	private void createDb(SQLiteDatabase db, String dbName)  {
		boolean inBlock = false;
		StringBuilder buffer = new StringBuilder();
		
		AssetManager assetManager = context.getAssets();
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(dbName)));
			
			String line = null;
			
			int i = 1;
			while ((line = reader.readLine()) != null)
			{	
				// Skip comments.
				if (line.startsWith("--"))
				{
					continue;
				}
				
				buffer.append(line + "\n");
				
				// Execute a complete statement that is not part of a block.
				if (line.endsWith(";") && inBlock == false)
				{
					db.execSQL(buffer.toString());
					buffer.setLength(0);
					continue;
				}
				
				// This is a multi-line sql trigger definition.
				if (line.toLowerCase(Locale.US).contains("trigger"))
				{
					inBlock = true;
					continue;
				}
				
				// The end of a block. execute the trigger creation.
				if (line.toLowerCase(Locale.US).contains("end"))
				{
					inBlock = false;
					db.execSQL(buffer.toString());
					buffer.setLength(0);
				}			
			}
			
			reader.close();
			
		} catch (Exception e) {
			Log.e(TAG,"couldn't create db from schema");
			//e.printStackTrace();
		}
	}
}
