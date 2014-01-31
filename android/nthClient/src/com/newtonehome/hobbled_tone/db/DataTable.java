package com.newtonehome.hobbled_tone.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataTable {

	private static final String TAG = "DataTable";
	
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + Db.DATA_TABLE + "(" +
			Db._ID + " integer primary key autoincrement, " +
			Db.TIMESTAMP + " text not null, " +
			Db.SYS_ID + " text not null," +
			Db.SYS_STATUS + " text, " +
			Db.RPI_TEMP + " text, " +
			Db.RPI_VOLT + " text" +
			");";
	
	  public static void onCreate(SQLiteDatabase database) {
		  Log.d(TAG,"creating database");
		  database.execSQL(CREATE_TABLE);
	  }
	
	  public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
	    Log.w(TAG, "Upgrading (dropping existing table), from " + oldVersion + " to " + newVersion);
	    database.execSQL("DROP TABLE IF EXISTS " + Db.DATA_TABLE);
	    onCreate(database);
	  }
}
