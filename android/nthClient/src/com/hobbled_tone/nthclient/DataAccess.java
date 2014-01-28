package com.hobbled_tone.nthclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.util.Log;

public class DataAccess extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "DataAccess";
	
	private static String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		
		Log.d(TAG,"starting DataAccess");
		
//		ServiceResponse response = Resting.get("http://192.168.1.100/sys_stat",3000);
//		
//		Log.d(TAG,"response is " + response);
		
		//HttpContext localContext = new BasicHttpContext();
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://192.168.1.100:3000/sys_stat/0");
		
		try {
			HttpResponse response = client.execute(get);
			Log.d(TAG,"response is " + response.getStatusLine().toString());
			
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				instream.close();
				Log.d(TAG,"result is " + result);
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		Log.d(TAG,"done");
		
		return null;
	}

}
