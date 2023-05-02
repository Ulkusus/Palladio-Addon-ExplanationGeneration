package org.palladiosimulator.addons.explanationgeneration.data.computed;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;

/**
 * Likely not very useful for now, but was meant for
 * relations, as in optional components of an occurred case,
 * where the number of events is not fixed.
 * */
public class CountMABEXComputed extends AbstractMABEXComputed {
	
	private Set<String> aliasesToCount;
	
	public CountMABEXComputed(String[] aliasesToCount, String myAlias) {
		super(myAlias);
		this.aliasesToCount = Set.of(aliasesToCount);
	}

	@Override
	public Object computeFor(HashMap<String, EventWithData> events) {
		return (long) Sets.intersection(events.keySet(), aliasesToCount).size();
	}

}
