package org.palladiosimulator.addons.explanationgeneration.data;

import java.util.HashMap;
import java.util.Set;

/**
 * An identified case that needs explaining.
 * Created by the analyzer to be passed on to the builder.
 * Contains the relevant events as well as the name of the case.
 * */
public class OccuredCase {
	private String name;
	private HashMap<String,EventWithData> events;
	
	public OccuredCase(String name) {
		this.name = name;
		events = new HashMap<>();
	}
	
	public OccuredCase putEvent(String alias, EventWithData event) {
		events.put(alias, event);
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public EventWithData getEvent(String alias) {
		return events.get(alias);
	}
	
	public int lastEventNo () {
		return events.size()-1;
	}
	
	public HashMap<String,EventWithData> getEvents() {
		return events;
	}
	
	public Set<String> getContainedAliases() {
		return events.keySet();
	}
}
