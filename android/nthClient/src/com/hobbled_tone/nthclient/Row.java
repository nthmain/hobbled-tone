package com.hobbled_tone.nthclient;

import android.view.View;

public interface Row {
	public View getView(View convertView);
	public int getViewType();
}
