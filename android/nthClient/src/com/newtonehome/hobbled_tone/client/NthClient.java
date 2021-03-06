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
import com.newtonehome.hobbled_tone.db.DbHelper;
import com.newtonehome.hobbled_tone.service.HobbledUpdater;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.app.LoaderManager;

/*
 * NthClient is the top level app for this package. It consists of a top 'action' bar that displays any pertinent system info,
 * 	followed by a listactivity that shows what is being monitored.
 */
public class NthClient extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

	public static final String TAG = "NthClient";
			
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
	private LoaderManager lm;
	private NthClientCursorAdapter adapter;
	private static final int LOADER_ID = 0;
	
	Intent serviceIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_main);
        
        //For now, just set system status OK
        	//In future, this will be set on Loader update or content change.
        ImageView systemStatusIcon = (ImageView)findViewById(R.id.imageViewSystemStatus);
        systemStatusIcon.setImageResource(android.R.drawable.button_onoff_indicator_on);        
        
        //Make a call to the database helper here to create the database if necessary.
        DbHelper helper = new DbHelper(getApplicationContext());
        helper.getWritableDatabase();

        Log.d(TAG,"onCreate");
        mCallbacks = this;
        
        //Setup CursorAdapter and Loader.
        lm = getLoaderManager();
        lm.initLoader(LOADER_ID, null, mCallbacks);
		
        String[] from = new String[] { Db.TIMESTAMP };
		int[] to = new int[] {R.id.labelTitle};
		
		adapter = new NthClientCursorAdapter(this, R.layout.sys_status_row, null, from, to, 0);
		
		final ListView listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Log.d(TAG,"list item clicked, opening detail activity");
			}
			
		});
		
		//Initialize intent.
		serviceIntent = new Intent(this, HobbledUpdater.class);
    }

    @Override
    public void onStart() {
    	super.onStart();
    	
    	Log.d(TAG,"onStart, starting service...");
    	
    	//Start service to update database.
    	if (serviceIntent != null) {
    		startService(serviceIntent);
    	}
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	Log.d(TAG,"onResume");
    	
    }
    
    @Override
    public void onPause() {
    	
    	Log.d(TAG,"onPause");
    	
    	super.onPause();
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	
    	Log.d(TAG,"onStop, stopping service");
    	
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
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { Db._ID, Db.TIMESTAMP };
		
		CursorLoader cursorLoader = new CursorLoader(this, Db.CLIENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (loader.getId() == LOADER_ID && adapter != null) {
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
