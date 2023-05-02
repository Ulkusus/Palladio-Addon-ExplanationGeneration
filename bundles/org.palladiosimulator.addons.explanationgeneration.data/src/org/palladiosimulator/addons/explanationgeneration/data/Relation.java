package org.palladiosimulator.addons.explanationgeneration.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.palladiosimulator.addons.explanationgeneration.data.conditions.AbstractMABEXCondition;

/**
 * NOT IN USE! - Left here for documentation.
 * 
 * An attempt at adding optional relations to {@link CaseDefinition}s.
 * While it would have worked, the design was unsatisfactory during initial tests.
 * */
public class Relation {
	private static final Logger LOGGER = LogManager.getLogger(Relation.class);
	/** for snakeyaml */
	private String events, select, stop, where;
	
	/** maintains the order */
	private ArrayList<String> aliases;
	/** maintains the mapping */
	private HashMap<String,String> aliasedEvents;
	
	private ArrayList<String> selected;
	private ArrayList<AbstractMABEXCondition> stopConditions;

	public String getWhere() {
		return where;
	}

	public void setWhere(String condition) {
		this.where = condition;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
		String[] typeAndAlias;
		String[] eventSplit = MABEXStrings.splitOnComma(events);
		aliases = new ArrayList<>(eventSplit.length);
		aliasedEvents = new HashMap<>();
		for (String event : eventSplit) {
			typeAndAlias = event.split(MABEXStrings.alias);
			if (typeAndAlias.length == 2) {
				aliasedEvents.put(typeAndAlias[1], typeAndAlias[0]);
				aliases.add(typeAndAlias[1]);
			} else {
				aliasedEvents.put(typeAndAlias[0], typeAndAlias[0]);
				aliases.add(typeAndAlias[0]);
			}
		}
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
		selected = new ArrayList<String>(Arrays.asList(MABEXStrings.splitOnComma(select)));	
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stopString) {
		this.stop = stopString;
		for (String stp : stop.split(MABEXStrings.and)) {
			AbstractMABEXCondition condition = AbstractMABEXCondition.from(stp);
			if (condition == null) 
				LOGGER.warn("Found unknown stop condition: " + stp + " in a relation.");
			 else
				 stopConditions.add(condition);
		}
	}

	public ArrayList<String> getSelected() {
		return selected;
	}
}
