package com.hobbled_tone.nthclient;

public class Element {

	private final Integer imageId;
	private final String statusTitle;
	private final String detailTitle;
	private final String description;
	
	public Element(Integer mImageId, String mStatusTitle, String mDetailTitle, String mDescription) {
		imageId = mImageId;
		statusTitle = mStatusTitle;
		detailTitle = mDetailTitle;
		description = mDescription;
	}
	
	public Integer getImageId() {
		return imageId;
	}
	
	public String getStatusTitle() {
		return statusTitle;
	}
	
	public String getDetailTitle() {
		return detailTitle;
	}
	
	public String getDescription() {
		return description;
	}
}
