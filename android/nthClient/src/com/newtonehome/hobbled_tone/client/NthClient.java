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

package com.newtonehome.hobbled_tone.client;

import com.newtonehome.hobbled_tone.nthclient.R;
import com.newtonehome.hobbled_tone.db.Db;
import com.newtonehome.hobbled_tone.service.HobbledUpdater;

import android.os.Bundle;
import android.os.Handler;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.app.LoaderManager;

/*
 * 
 */
public class NthClient extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	public static final String TAG = "NthClient";
	
	public ContentObserver contentObserver;
	private ClientAdapter adapter;
	private static final int LOADER_ID = 1;
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
	private LoaderManager lm;
	
	Intent serviceIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //
        //String[] values = new String[] { "Item 1", "Item 2" };
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_element_adapter, R.id.listTitle, values);
        //setListAdapter(new NthAdapter(getApplicationContext(), new ElementDataSource().getElements()));
        
        Log.d(TAG,"onCreate");
        
        //Setup CursorAdapter and Loader.
        String[] from = new String[] { Db.TIMESTAMP };
		int[] to = new int[] {R.id.title};
		
		adapter = new ClientAdapter(this, R.layout.sys_status_row, null, from, to, 0);
		
		setListAdapter(adapter);
		
		mCallbacks = this;
		
		lm = getLoaderManager();
		lm.initLoader(LOADER_ID, null, mCallbacks);
		
		//Initialize intent.
		serviceIntent = new Intent(this, HobbledUpdater.class);
    }

    @Override
    public void onStart() {
    	super.onStart();
    	
    	//Start service to update database.
    	if (serviceIntent != null) {
    		startService(serviceIntent);
    	}
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	//Listen for updates.
    	lm.restartLoader(LOADER_ID, null, mCallbacks);
		
		contentObserver = new ContentObserver(new Handler()) {
			@Override
			public void onChange(boolean selfChange)
			{
				//Notify adapter that something has changed.
				if (adapter != null) {
					Log.d(TAG,"db change, notifying adapter");
					adapter.notifyDataSetChanged();
				}
			}
			
			@Override
			public boolean deliverSelfNotifications() {
				return true;
			}
		};
		
		getContentResolver().registerContentObserver(Db.DATA_URI, true, contentObserver);
    }
    
    @Override
    public void onPause() {
    	//Stop listening for updates.
    	getContentResolver().unregisterContentObserver(contentObserver);
    	
    	super.onPause();
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	
    	//Stop service is it's running.
    	if (serviceIntent != null) {
    		stopService(serviceIntent);
    	}
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nth_client, menu);
        return true;
    }
    
    @Override
    public void onListItemClick(ListView list, View view, int pos, long id) {
    	Log.d(TAG,"item clicked, running asynctask");
    	
    	DataAccess getData = new DataAccess();
    	getData.execute();
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { Db._ID, Db.TIMESTAMP };
		
		CursorLoader cursorLoader = new CursorLoader(this, Db.DATA_URI, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (loader.getId() == 1 && adapter != null) {
			adapter.swapCursor(cursor);
		}	
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		if (adapter != null) {
			adapter.swapCursor(null);
		}	
	}
}
