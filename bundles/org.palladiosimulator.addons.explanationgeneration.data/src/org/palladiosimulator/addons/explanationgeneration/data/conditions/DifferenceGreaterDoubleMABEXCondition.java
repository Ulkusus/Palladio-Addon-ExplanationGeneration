package org.palladiosimulator.addons.explanationgeneration.data.conditions;

import java.util.HashMap;
import java.util.Set;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;

public class DifferenceGreaterDoubleMABEXCondition extends AbstractMABEXCondition {
	
	private String alias1,alias2,detail1,detail2;
	private Double doubl;
	
	public DifferenceGreaterDoubleMABEXCondition(String part1, String part2, Double d) {
		doubl = d;
		String[] helper = MABEXStrings.splitOnDot(part1);
		alias1 = helper[0];
		detail1 = helper[1];
		helper = MABEXStrings.splitOnDot(part2);
		alias2 = helper[0];
		detail2 = helper[1];
	}

	@Override
	public boolean isTrueFor(HashMap<String, EventWithData> events, String newAlias, EventWithData newEvent) {
		Double d1 = (Double) events.getOrDefault(alias1, newEvent).get(detail1);
		Double d2 = (Double) events.getOrDefault(alias2, newEvent).get(detail2);
		return Math.abs(d1-d2) > doubl;
	}

	@Override
	public boolean canEvaluateWith(Set<String> aliases, String newAlias) {
		if (aliases.contains(alias1) || alias1.equals(newAlias))
			if (aliases.contains(alias2) || alias2.equals(newAlias))
				return true;
		return false;
	}

}
