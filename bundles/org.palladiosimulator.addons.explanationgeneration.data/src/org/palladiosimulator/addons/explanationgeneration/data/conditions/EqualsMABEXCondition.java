package org.palladiosimulator.addons.explanationgeneration.data.conditions;

import java.util.HashMap;
import java.util.Set;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;

public class EqualsMABEXCondition extends AbstractMABEXCondition {
	private final String alias1,alias2,detail1,detail2;
	
	public EqualsMABEXCondition(String part1, String part2) {
		String[] helper = part1.split(MABEXStrings.dot);
		alias1 = helper[0];
		detail1 = helper[1];
		helper = part2.split(MABEXStrings.dot);
		alias2 = helper[0];
		detail2 = helper[1];
	}
	@Override
	public boolean isTrueFor(HashMap<String, EventWithData> events, String newAlias, EventWithData newEvent) {
		return events.getOrDefault(alias1, newEvent).get(detail1).equals(events.getOrDefault(alias2, newEvent).get(detail2));
	}
	@Override
	public boolean canEvaluateWith(Set<String> aliases, String newAlias) {
		if (aliases.contains(alias1) || newAlias.equals(alias1))
			if (aliases.contains(alias2) || newAlias.equals(alias2))
				return true;
		return false;
	}
}
