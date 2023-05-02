package org.palladiosimulator.addons.explanationgeneration.data;

import java.util.HashMap;

/**
 * These are created by monitors to be sent to the analyzer.<br>
 * They are then passed on to the builder grouped together in an {@link OccuredCase}.
 * <br><br>
 * Note that put() uses the builder pattern for ease of use.
 * */
public class EventWithData {
	/** Contains the data extracted from the event*/
	private HashMap<String,Object> data;
	
	public EventWithData(String type) {
		this.data = new HashMap<>();
		data.put(MABEXStrings.TYPE, type);
	}
	
	public HashMap<String, Object> getData() {
		return data;
	}
	
	public Object get(String details) {
		return data.get(details);
	}
	
	public EventWithData put(String details, Object object) {
		data.put(details, object);
		return this;
	}
	
	public String getType() {
		return (String) data.get(MABEXStrings.TYPE);
	}
}
