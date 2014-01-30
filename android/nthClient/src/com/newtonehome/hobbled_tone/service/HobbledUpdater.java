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

import android.app.Service;
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
				
				Log.d(TAG,"response is " + entity.toString());
				
			} catch (Exception e) {
				Log.d(TAG,"Error getting data: " + e.toString());
			}
			
			return null;
		}
		  
	  }
}
