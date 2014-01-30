/*
Copyright (c) 2014 Douglas Long

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.newtonehome.hobbled_tone.db;

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
