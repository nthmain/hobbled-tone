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

package com.newtonehome.hobbled_tone.client;

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
