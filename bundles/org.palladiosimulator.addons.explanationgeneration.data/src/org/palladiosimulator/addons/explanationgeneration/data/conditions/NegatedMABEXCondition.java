package org.palladiosimulator.addons.explanationgeneration.data.conditions;

import java.util.HashMap;
import java.util.Set;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;

public class NegatedMABEXCondition extends AbstractMABEXCondition {
	
	private AbstractMABEXCondition myCondition;
	
	public NegatedMABEXCondition(String conditionString) {
		myCondition = AbstractMABEXCondition.from(conditionString);
	}

	@Override
	public boolean isTrueFor(HashMap<String, EventWithData> events, String newAlias, EventWithData newEvent) {
		return !myCondition.isTrueFor(events, newAlias, newEvent);
	}

	@Override
	public boolean canEvaluateWith(Set<String> aliases, String newAlias) {
		return myCondition.canEvaluateWith(aliases, newAlias);
	}

}
