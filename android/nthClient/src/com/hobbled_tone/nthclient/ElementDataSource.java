package com.hobbled_tone.nthclient;

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
