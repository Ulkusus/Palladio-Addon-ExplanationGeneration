package org.palladiosimulator.addons.explanationgeneration.data.computed;

import java.util.HashMap;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;

public abstract class AbstractMABEXComputed {
	
	protected final String myAlias;
	
	public static AbstractMABEXComputed from(String computedString) {
		
		String[] parts = MABEXStrings.splitOnWhitespace(computedString);
		
		if(parts[0].equals(MABEXStrings.differenceComputed))
			return new DifferenceMABEXComputed(parts[1], parts[2], parts[4]);
		
		if (parts[0].equals(MABEXStrings.count))
			return new CountMABEXComputed(MABEXStrings.splitOnComma(parts[1]), parts[3]);
		
		return null;
	}
	
	public AbstractMABEXComputed(String myAlias) {
		this.myAlias = myAlias;
	}
	
	public String getAlias() {
		return myAlias;
	}

	/*FIXME
	 * a method similar to this may be required if optional relations with multiple events of the same alias are added
	 * (e.g. find all UserStarted within 5 seconds before this event)
	 * Unless the aliases get numbered automatically? Might be not very performant though.
	 * */ 
	//public abstract Object computeFor(ArrayList<String> aliases, ArrayList<EventWithData> events);

	/**
	 * Computes the value for this instance depending on the input events.
	 * */
	public abstract Object computeFor(HashMap<String, EventWithData> events);
}
