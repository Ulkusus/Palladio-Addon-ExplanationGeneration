package org.palladiosimulator.addons.explanationgeneration.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.palladiosimulator.addons.explanationgeneration.data.conditions.AbstractMABEXCondition;

public class CaseDefinition {
	private static final Logger LOGGER = LogManager.getLogger(CaseDefinition.class);
	
	private String select;
	private ArrayList<String> selected;
	private String trigger;
	private ArrayList<String> triggers;
	/** This could be simplified into a boolean, but then snakeyaml won't accept the 'where:' tag anymore...*/
	private String where;
	private String stop;
	private HashSet<AbstractMABEXCondition> stopConditions;
	private HashSet<AbstractMABEXCondition> conditions;
	/* Not in use. See the Relation class for details */
	//private LinkedHashMap<String,Relation> relations;
	private LinkedHashMap<String,String> aliasedEvents;
	
	public CaseDefinition() {
		aliasedEvents = new LinkedHashMap<>();
		selected = new ArrayList<>();
		conditions = new HashSet<>();
		stopConditions = new HashSet<>();
	}
	
	/** @return the type of the first event in this cases chain of events */
	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String triggerText) {
		String[] triggerSplit = triggerText.split(MABEXStrings.comma);
		triggers = new ArrayList<>(triggerSplit.length);
		for (String triggerEvent : triggerSplit) {
			String typeAndAlias[] = triggerEvent.split(MABEXStrings.alias);
			
			// trigger needs to be there for snakeyaml, so might as well use it as convenience access
			if (trigger == null)
				trigger = typeAndAlias[0];
			
			//record the event chain
			// if there is an alias, put it as the key, else just put the event type as both key and value
			// also record the chain of events/aliases to follow
			if (typeAndAlias.length == 2) {
				aliasedEvents.put(typeAndAlias[1], typeAndAlias[0]);
				triggers.add(typeAndAlias[1]);
			} else {
				aliasedEvents.put(typeAndAlias[0], typeAndAlias[0]);
				triggers.add(typeAndAlias[0]);
			}
		}
	}
	
	public String getEvent(int Number) {
		return aliasedEvents.get(triggers.get(Number));
	}
	
	public String getAlias(int Number) {
		return triggers.get(Number);
	}
	
	public boolean isDone(int Number) {
		return triggers.size() == Number;
	}
	
	public String getWhere() {
		return where;
	}
	
	/**
	 * @param stop whether to return the stop conditions instead
	 * */
	public HashSet<AbstractMABEXCondition> getConditions(boolean stop) {
		if (stop)
			return stopConditions;
		return conditions;
	}
	
	public void setWhere(String whereString) {
		this.where = whereString;
		for (String wher : where.split(MABEXStrings.and)) {
			AbstractMABEXCondition condition = AbstractMABEXCondition.from(wher);
			if (condition == null) 
				LOGGER.warn("Found unknown condition: " + wher);
			 else
				 conditions.add(condition);
		}
	}

	// Not in use. See the Relation class for details. 
	//public LinkedHashMap<String, Relation> getRelations() {return relations;}
	//public void setRelations(LinkedHashMap<String, Relation> relations) {this.relations = relations;}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
		selected = new ArrayList<String>(Arrays.asList(MABEXStrings.splitOnComma(select)));
	}
	
	public ArrayList<String> getSelected() {
		return selected;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stopString) {
		this.stop = stopString;
		for (String stp : stop.split(MABEXStrings.and)) {
			AbstractMABEXCondition condition = AbstractMABEXCondition.from(stp);
			if (condition == null) 
				LOGGER.warn("Found unknown stop condition: " + stp);
			 else
				 stopConditions.add(condition);
		}
	}
}
