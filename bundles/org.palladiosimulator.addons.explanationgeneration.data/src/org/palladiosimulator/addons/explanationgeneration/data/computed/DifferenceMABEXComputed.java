package org.palladiosimulator.addons.explanationgeneration.data.computed;

import java.util.HashMap;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;

public class DifferenceMABEXComputed extends AbstractMABEXComputed {
	
	private String alias1,alias2,detail1,detail2;

	public DifferenceMABEXComputed(String part1, String part2, String myAlias) {
		super(myAlias);
		String[] helper = MABEXStrings.splitOnDot(part1);
		alias1 = helper[0];
		detail1 = helper[1];
		helper = MABEXStrings.splitOnDot(part2);
		alias2 = helper[0];
		detail2 = helper[1];
	}

	@Override
	public Object computeFor(HashMap<String, EventWithData> events) {
		Double d1 = (Double) events.get(alias1).get(detail1);
		Double d2 = (Double) events.get(alias2).get(detail2);
		return d1 - d2;
	}

}
