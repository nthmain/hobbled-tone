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

import java.util.ArrayList;
import java.util.List;

public class ElementDataSource {

	private final List<Element> elements = new ArrayList<Element>();
	
	public ElementDataSource() {
		//Add system status
		elements.add(new Element(android.R.drawable.ic_menu_mylocation, "System Status", null, "NTH System Status"));
		
		//Add Rpi temp status
		elements.add(new Element(android.R.drawable.ic_menu_info_details, null, "Pi SoC Temp", "Raspberry Pi Core Temp"));
		elements.add(new Element(android.R.drawable.ic_menu_info_details, null, "Pu SoC Votlage", "Raspberry Pi Core Voltage"));
	}
	
	public List<Element> getElements() {
		return elements;
	}
}
