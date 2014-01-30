package com.newtonehome.hobbled_tone.service;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.newtonehome.hobbled_tone.db.Db;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

/*
 * This service performs all the server I/O (data polling, etc.) for this application.
 * The service is the only component that will add to the database.
 * 
 * Any of the activities can then trigger updates from listening with ContentOverservables.
 */
public class HobbledUpdater extends Service {

	private static final String TAG = "HobbledUpdater";
	
	private Timer timer;
	private TimerTask timerTask;
	private boolean debugTaskRunning = false;
	
	private static final String TAG_TIMESTAMP = "timestamp";
	private static final String TAG_SYSTEMSTATUS = "sysStat";
	private static final String TAG_SYSTEMID = "sysId";
	
	@Override
	public void onCreate() {
	
		//Run the asynctask to poll for server updates.
		Log.d(TAG,"starting asynctask to poll for server data");
		
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				new DataPoller().execute();
			}
		}, 0, 15000);	//Run the task every 15 seconds.
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		
		//There is nothing useful that the service will provide to an activity binding to it. Do nothing.
		
		return null;
	}
	
	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
		  
		
		  
	    return Service.START_NOT_STICKY;
	  }
	  
	  @Override
	  public void onDestroy() {
		  //Clean up anything?
		  
		  Log.d(TAG,"cleaning up");
		  
		  if (timer != null) {
			  Log.d(TAG,"stopping timer");
			  timer.cancel();
		  }
		  
		  super.onDestroy();
	  }
	  
	  /*
	   * The service runs in the background, but still needs to run an asynchronous
	   * 	task to run the asynchronous network i/o and database access.
	   * 
	   * 	TODO: this asynctask should periodically check DB for size, etc. (generally maintain data)
	   * 
	   */
	  private class DataPoller extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Log.d(TAG,"DataPoller running");

			HttpContext localContext = new BasicHttpContext();
			HttpClient client = new DefaultHttpClient();  
			HttpGet get = new HttpGet("http://192.168.1.100:3000/sys_stat/1"); 
			
			try {
				HttpResponse response = client.execute(get, localContext);
				HttpEntity entity = response.getEntity();
				
				//Get response string
				String response_string = EntityUtils.toString(entity);
								
				//Parse into JSON and then add to database.
				JSONArray resultsArray = new JSONArray(new JSONTokener(response_string));
				
				//This response should be an array with length = 1, so just handle the first response.
					//If length is more, log it as warning.
				if (resultsArray.length() > 1) {
					Log.w(TAG,"sys results array is greater than 1, expecting length=1");
				}
				
				//Make sure at least 1 exists before continuing.
				if (resultsArray.length() >= 1) {
					
					JSONObject obj = resultsArray.getJSONObject(0);
					
					String timestamp = obj.getString(TAG_TIMESTAMP);
					String sys_stat = obj.getString(TAG_SYSTEMSTATUS);
					String sys_id = obj.getString(TAG_SYSTEMID);
					
					//Not doing any string checks before adding to DB.
					//ContentProvider can handle any anomolies.
					
					ContentValues contentValues = new ContentValues();
					contentValues.put(Db.TIMESTAMP, timestamp);
					contentValues.put(Db.SYS_ID, sys_id);
					contentValues.put(Db.SYS_STATUS, sys_stat);
					
					getContentResolver().insert(Db.DATA_URI, contentValues);
					
				}
				
 			} catch (Exception e) {
				Log.d(TAG,"Error getting data: " + e.toString());
			}
			
			return null;
		}
		  
	  }
}
