package org.palladiosimulator.addons.explanationgeneration.data;

import java.util.HashMap;

/**
 * Represents a machine-understandable explanation.
 * 
 * Created by a subclass of AbstractMABEXBuilder.
 * Sent to implementers of IMABEXInterpreter to be explained to the user.
 * */
public class Explanation {
	/** Matches the type of the case definition that lead to this instance. */
	private String type;
	/** Contains the data selected in the case definition that lead to this instance. */
	private HashMap<String, Object> data;
	
	public HashMap<String, Object> getData() {
		return data;
	}

	public Explanation(String type) {
		this.type = type;
		data = new HashMap<>();
	}
	
	public String getType() {
		return type;
	}
	
	public Explanation put(String detail, Object object) {
		data.put(detail, object);
		return this;
	}
	
	public Object get(String details) {
		return data.get(details);
	}
}
