package org.palladiosimulator.addons.explanationgeneration.data.conditions;

import java.util.HashMap;
import java.util.Set;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;

public class LessThanDoubleMABEXCondition extends AbstractMABEXCondition {
	
	private String alias;
	private String detail;
	private Double constant;
	
	public LessThanDoubleMABEXCondition (String part1, Double constant) {
		String[] helper = MABEXStrings.splitOnDot(part1);
		alias = helper[0];
		detail = helper[1];
		this.constant = constant; 
	}
	

	@Override
	public boolean isTrueFor(HashMap<String, EventWithData> events, String newAlias, EventWithData newEvent) {
		if (constant.compareTo((Double) events.getOrDefault(alias, newEvent).get(detail)) == 1)
			return true;
		return false;
	}

	@Override
	public boolean canEvaluateWith(Set<String> aliases, String newAlias) {
		if (aliases.contains(alias) || newAlias.equals(alias))
			return true;
		return false;
	}

}
