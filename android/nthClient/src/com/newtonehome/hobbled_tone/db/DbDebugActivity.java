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

import java.util.Timer;
import java.util.TimerTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/*
 * This activity is useful for debugging the database on the device,
 * as well as the components internal or external to this package that
 * trigger off of the database.
 * 
 * ie. One can test the data display graphs by adding test data here.
 */
public class DbDebugActivity extends Activity {

	private static final String TAG = "DbDebugActivity";
	
	Button buttonPumpDebug = null;
	
	private Timer timer;
	private TimerTask timerTask;
	private boolean debugTaskRunning = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db_debug);
	
		Log.d(TAG,"onCreate");
		
		//Setup buttonPumpDebug to activate 'pumping debug' values into the database for testing.
		buttonPumpDebug = (Button)findViewById(R.id.buttonPumpDebug);
		buttonPumpDebug.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (!debugTaskRunning) {
					//Start the task that adds debug data to the DB.
					timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							new AddDebugDataTask().execute();
						}
					}, 0, 15000);	//Run the task every 15 seconds.
					
					debugTaskRunning = true;
					
					//Update button text.
					buttonPumpDebug.setText(R.string.buttonPumpDebugStopTask);
					
				} else {
					//Task is currently running, stop it.
					timer.cancel();		//This can be called multiple times with no effect.
					debugTaskRunning = false;
					
					//Update button text.
					buttonPumpDebug.setText(R.string.buttonPumpDebugStartTask);
				}
			}
		});
		
		//Setup buttonClearDB button to drop all tables if clicked and Ok'd.
		Button buttonClearDB = (Button)findViewById(R.id.buttonClearDB);
		buttonClearDB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Show a dialog window to confirm before deleting the entire local (device) database.
				AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
				builder.setTitle("Clear database table");
				builder.setMessage("Remove all database tables?");

				builder.setNegativeButton("YES", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						DbHelper helper = new DbHelper(getApplicationContext());
						SQLiteDatabase db = helper.getWritableDatabase();

						db.delete(Db.DATA_TABLE, null, null);
						dialog.dismiss();
					}
				});

				builder.setPositiveButton("NO", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});

				AlertDialog alert = builder.create();
				alert.show();
				
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		
		//Set button text here.
		if (buttonPumpDebug != null) {
			if (debugTaskRunning) {
				buttonPumpDebug.setText(R.string.buttonPumpDebugStopTask);
			} else {
				buttonPumpDebug.setText(R.string.buttonPumpDebugStartTask);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.db_debug, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		
		Log.d(TAG,"onDestroy");
		//If debug is still running, stop it here.
		
	}
	
	private class AddDebugDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Log.d(TAG,"asynctask adding data now");
			return null;
		}
	}
}
