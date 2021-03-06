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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DbContentProvider extends ContentProvider {

	private static final String TAG = "DbContentProvider";
	
	//DbHelper is the only way to get to the database.
	private DbHelper helper;
		
	@Override
	public boolean onCreate()
	{
		Log.d(TAG,"onCreate, initializing DbHelper");
		helper = new DbHelper(getContext());
		return true;
	}
	
	//Uri matching (usefulness more evident when multiple tables exist)
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(Db.DATA_URI.getAuthority(), "data", Db.DATA_URI_MATCH);
		uriMatcher.addURI(Db.DATA_URI.getAuthority(), "data/#", Db.DATA_ID_URI_MATCH);
		uriMatcher.addURI(Db.DATA_URI.getAuthority(), "data/client", Db.CLIENT_URI_MATCH);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int rval = 0;
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		//Match Uri in order to perform the correct database operation.
		int matchNum = uriMatcher.match(uri);
		switch (matchNum) {
		case Db.DATA_URI_MATCH:
			rval = db.delete(Db.DATA_TABLE, selection, selectionArgs);
			break;
		case Db.DATA_ID_URI_MATCH:
			rval = db.delete(Db.DATA_TABLE, Db._ID + " = ?", new String[] {uri.getLastPathSegment()});
			break;
		default:
			rval = -1;
			break;
		}
		
		if ( rval >= 0) {
			//Notify db change (for all the listeners out there).
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		return rval;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		long inId = 0;
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		//Match Uri in order to perform the correct database operation.
		int matchNum = uriMatcher.match(uri);
		
		//Kept the switch here to match other similar methods (only single case).
		switch (matchNum) {
		case Db.DATA_URI_MATCH:
			inId = db.insert(Db.DATA_TABLE, null, values);
		}
		
		//If insert occurred, notify the change.
		if (inId >= 0) {
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.withAppendedPath(uri, String.valueOf(inId));
		}
		//else
		
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor cursor;
		
		Log.d(TAG,"query");
		
		int matchNum = uriMatcher.match(uri);
		switch (matchNum) {
		case Db.DATA_URI_MATCH:
			
			cursor = db.query(Db.DATA_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
			
		case Db.DATA_ID_URI_MATCH:
			
			cursor = db.query(Db.DATA_TABLE, projection, Db._ID + " = ?", new String[] {uri.getLastPathSegment()}, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
			
		case Db.CLIENT_URI_MATCH:

			//Return first set of each 'system' type.
				//TODO implement this (for now just return most recent system status)
			
			cursor = db.rawQuery("SELECT _id, max(time_stamp) time_stamp, sys_id, sys_status, rpi_temp, rpi_volt from data", null);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
			
		default:
			break;
		}
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int rval = 0;
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		int matchNum = uriMatcher.match(uri);
		switch (matchNum) {
		case Db.DATA_URI_MATCH:
			rval = db.update(Db.DATA_TABLE, values, selection, selectionArgs);
			break;
		case Db.DATA_ID_URI_MATCH:
			rval = db.update(Db.DATA_TABLE, values, Db._ID + " = ?", new String[] {uri.getLastPathSegment()});
			break;
		default:
			rval = -1;
			break;		
		}
		
		if (rval >= 0) {
			//Notify change.
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		return rval;
	}

}
