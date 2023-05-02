package org.palladiosimulator.addons.explanationgeneration.data.conditions;

import java.util.HashMap;
import java.util.Set;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;

public abstract class AbstractMABEXCondition {

	/** Generates a new condition from a string if there is a valid match */
	public static AbstractMABEXCondition from(String conditionString) {
		/* Note that else is mostly not needed here as any branch should end in a return statement */
		
		if (conditionString.startsWith(MABEXStrings.not))
			return new NegatedMABEXCondition(conditionString.replaceFirst(MABEXStrings.not + MABEXStrings.whitespace, ""));
		
		String[] parts = MABEXStrings.splitOnWhitespace(conditionString);
		
		if (parts[0].equals(MABEXStrings.difference))
			if (parts[3].equals(MABEXStrings.greater))
				if (MABEXStrings.isDouble(parts[4]))
					return new DifferenceGreaterDoubleMABEXCondition(parts[1], parts[2], MABEXStrings.getDouble(parts[4]));
		
		if (parts[1].equals(MABEXStrings.equals)) {
			if (MABEXStrings.isString(parts[2]))
				return new EqualsStringMABEXCondition(parts[0], MABEXStrings.getString(parts[2]));
			return new EqualsMABEXCondition(parts[0], parts[2]);
		}
			
		
		if (parts[1].equals(MABEXStrings.less)) {
			if (MABEXStrings.isDouble(parts[2]))
				return new LessThanDoubleMABEXCondition(parts[0], MABEXStrings.getDouble(parts[2]));
			return new LessThanMABEXCondition(parts[0], parts[2]);
		}
		
		if (parts[1].equals(MABEXStrings.greater)) {
			if (MABEXStrings.isDouble(parts[2]))
				return new GreaterThanDoubleMABEXCondition(parts[0], MABEXStrings.getDouble(parts[2]));
			return new GreaterThanMABEXCondition(parts[0], parts[2]);
		}
		
		return null;
		
	}
	
	public abstract boolean isTrueFor(HashMap<String,EventWithData> events, String newAlias, EventWithData newEvent);
	public abstract boolean canEvaluateWith(Set<String> aliases, String newAlias);

}