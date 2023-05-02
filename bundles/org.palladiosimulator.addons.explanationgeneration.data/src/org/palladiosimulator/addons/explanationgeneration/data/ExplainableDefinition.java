package org.palladiosimulator.addons.explanationgeneration.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

public class ExplainableDefinition {
	private List<String> events;
	private LinkedHashMap<Integer,ArrayList<String>> data;
	private LinkedHashMap<String,ConcurrentSkipListSet<Integer>> matches;

	public List<String> getEvents() {
		return events;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public HashMap<Integer,ArrayList<String>> getData() {
		return data;
	}

	public void setData(LinkedHashMap<Integer,ArrayList<String>> data) {
		this.data = data;
	}

	public LinkedHashMap<String,ConcurrentSkipListSet<Integer>> getMatches() {
		return matches;
	}

	public void setMatches(LinkedHashMap<String,ConcurrentSkipListSet<Integer>> matches) {
		this.matches = matches;
	}
}
