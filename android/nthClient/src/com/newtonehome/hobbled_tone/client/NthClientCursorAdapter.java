package com.newtonehome.hobbled_tone.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newtonehome.hobbled_tone.db.Db;
import com.newtonehome.hobbled_tone.nthclient.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class NthClientCursorAdapter extends SimpleCursorAdapter {

	private int layout;
	private final LayoutInflater inflater;
	
	public NthClientCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);

		this.layout = layout;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(layout, null);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		super.bindView(view, context, cursor);
	
		//Set Label strings
		String titleString = "View System Detail";
		String lastUpdateString = "Last Updated: " + timeToString(cursor.getString(cursor.getColumnIndex(Db.TIMESTAMP)));
		
		TextView titleTextView = (TextView)view.findViewById(R.id.labelTitle);
		TextView lastUpdateTextView = (TextView)view.findViewById(R.id.labelTimeStamp);
		
		titleTextView.setText(titleString);
		lastUpdateTextView.setText(lastUpdateString);		
	}

	public static String timeToString(String timeInMillis) {
		
		Date date = new Date(Long.parseLong(timeInMillis));
		//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
}
