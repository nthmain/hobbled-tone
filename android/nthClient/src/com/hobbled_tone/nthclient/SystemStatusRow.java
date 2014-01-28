package com.hobbled_tone.nthclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SystemStatusRow implements Row {

	private final Element element;
	private final LayoutInflater inflater;
	
	public SystemStatusRow(LayoutInflater mInflater, Element mElement) {
		element = mElement;
		inflater = mInflater;
	}
	
	@Override
	public View getView(View convertView) {
		ViewHolder holder;
		View view;
		
		if (convertView == null) {
			ViewGroup vGroup = (ViewGroup)inflater.inflate(R.layout.sys_status_row, null);
			
			holder = new ViewHolder((ImageView)vGroup.findViewById(R.id.image),
					(TextView)vGroup.findViewById(R.id.title),
					(TextView)vGroup.findViewById(R.id.description));
			
			vGroup.setTag(holder);
			
			view = vGroup;
		} else {
			holder = (ViewHolder)convertView.getTag();
			view = convertView;
		}
		
		//Set the view.
		holder.imageView.setImageResource(element.getImageId());
		holder.title.setText(element.getStatusTitle());
		holder.description.setText(element.getDescription());
		
		return view;
	}

	@Override
	public int getViewType() {
		return RowType.SYSTEM_STATUS_ROW.ordinal();
	}
	
	private static class ViewHolder {
		final ImageView imageView;
		final TextView title;
		final TextView description;
		
		private ViewHolder(ImageView mImageView, TextView mTitle, TextView mDescription) {
			imageView = mImageView;
			title = mTitle;
			description = mDescription;
		}
	}
}
