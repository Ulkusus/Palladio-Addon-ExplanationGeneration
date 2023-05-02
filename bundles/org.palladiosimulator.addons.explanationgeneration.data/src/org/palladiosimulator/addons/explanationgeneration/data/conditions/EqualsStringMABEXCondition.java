package org.palladiosimulator.addons.explanationgeneration.data.conditions;

import java.util.HashMap;
import java.util.Set;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;

public class EqualsStringMABEXCondition extends AbstractMABEXCondition {
	
	private String alias,detail,toMatch;
	
	public EqualsStringMABEXCondition(String part1, String toMatch) {
		String[] helper = MABEXStrings.splitOnDot(part1);
		alias = helper[0];
		detail = helper[1];
		this.toMatch = toMatch; 
	}

	@Override
	public boolean isTrueFor(HashMap<String, EventWithData> events, String newAlias, EventWithData newEvent) {
		return events.getOrDefault(alias, newEvent).get(detail).equals(toMatch);
	}

	@Override
	public boolean canEvaluateWith(Set<String> aliases, String newAlias) {
		return aliases.contains(alias) || newAlias.equals(alias);
	}

}
