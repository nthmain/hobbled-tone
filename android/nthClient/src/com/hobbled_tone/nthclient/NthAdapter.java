package com.hobbled_tone.nthclient;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NthAdapter extends BaseAdapter {

	private static final String TAG = "NthAdapter";
	
	private final List<Row> rows;
	
	public NthAdapter(Context context, List<Element> elems) {
		
		rows = new ArrayList<Row>();
		
		for (Element elem : elems) {
			if (elem.getStatusTitle() != null) {
				rows.add(new SystemStatusRow(LayoutInflater.from(context), elem));
			} else {
				rows.add(new DetailStatusRow(LayoutInflater.from(context), elem));
			}
		}
	}
	
	@Override
	public int getViewTypeCount() {
		return RowType.values().length;
	}
	
	@Override
	public int getCount() {
		return rows.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return rows.get(position).getView(convertView);
	}

}
