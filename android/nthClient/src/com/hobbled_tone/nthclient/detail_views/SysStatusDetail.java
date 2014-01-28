package com.hobbled_tone.nthclient.detail_views;

import android.app.Activity;
import android.os.Bundle;

/*
 * This activity will display a graph showing the system status over time.
 * Also display last updated timestamp, and display warning if timestamp older than X minutes.
 */
public class SysStatusDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sysstatusdetail);
	}
}
