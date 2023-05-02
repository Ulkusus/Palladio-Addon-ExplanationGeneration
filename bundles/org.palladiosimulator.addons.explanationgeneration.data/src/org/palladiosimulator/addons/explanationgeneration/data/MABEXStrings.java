package org.palladiosimulator.addons.explanationgeneration.data;

/**
 * Contains the strings used in the ExplanationGeneration component and convenience methods that deal with them.
 * */
public final class MABEXStrings {
	
	/* Keys used by monitors when adding details to an EventWithData instance */
	/** ALWAYS AUTOMATICALLY PRESENT in an {@link EventWithData} instance.
	 *  Carries the type of the event according to the monitors name that created it.
	 *  See AbstractMABEXMonitor for details. */
	public static final String TYPE = "type";
	/** Carries the simulation time() an event happened at. */
	public static final String TIME = "time";
	/** Used in e.g. UserStartedMABEXMonitor for the users id. */
	public static final String USER = "user";
	/** Used in e.g. JobInitiatedMABEXMonitor for the used resource type. */
	public static final String PROCESSINGRESOURCETYPE = "processingresourcetype";
	/** Used in e.g. SEFFExternalActionCalledMABEXMonitor for the entry request contained in the event. */
	public static final String ENTRYREQUEST = "entryrequest";
	/** Used in e.g. JobInitiatedMABEXMonitor for the jobs id. */
	public static final String JOBID = "id";
	
	/** Used by AbstractMABEXMonitor to automatically extract the event type from the monitor class' name. */
	public static final String monitorSuffix = "MABEXMonitor";
	
	public static final String consoleName = "Explanations";
	
	/* keywords etc. used in case definitions */
	public static final String specialCharacter = "%";
	public static final String whitespace = "(?U)\\s";
	public static final String dot = "[.]";
	public static final String comma = whitespace + "?," + whitespace + "?";
	public static final String doubleSuffix = specialCharacter + "d";
	public static final String stringSuffix = specialCharacter + "s";
	public static final String equals = "EQUALS";
	public static final String less = "<";
	public static final String greater = ">";
	public static final String alias = whitespace + "AS" + whitespace;
	public static final String and = whitespace + "AND" + whitespace;
	public static final String any = "ANY";
	public static final String not = "NOT";
	public static final String difference = "DIFFERENCE";
	public static final String differenceComputed = specialCharacter + difference;
	public static final String count = specialCharacter + "COUNT";
	
	/* utility methods for working with the strings defined above */
	public static String[] splitOnWhitespace(String toSplit) {
		return toSplit.split(whitespace);
	}
	
	public static String[] splitOnDot(String toSplit) {
		return toSplit.split(dot);
	}
	
	public static String[] splitOnComma(String toSplit) {
		return toSplit.split(comma);
	}
	
	public static String[] splitOnAnd(String toSplit) {
		return toSplit.split(and);
	}
	
	/** Checks if the given String ends with the suffix that is defined as signaling a double value */
	public static boolean isDouble(String maybeDouble) {
		return maybeDouble.endsWith(doubleSuffix);
	}
	
	/** Removes the defined double suffix from a string and returns the rest as a double */
	public static Double getDouble(String doubl) {
		return Double.valueOf(doubl.substring(0, doubl.length() - doubleSuffix.length()));
	}

	/** Checks if the given String ends with the suffix that is defined as signaling a string value */
	public static boolean isString(String maybeString) {
		return maybeString.endsWith(stringSuffix);
	}

	/** Removes the defined string suffix from a string and returns the rest */
	public static String getString(String string) {
		return string.substring(0, string.length() - stringSuffix.length());
	}
	
	/** Used by the BasicMABEXBuilder to check whether a detail to be added to an explanation needs to be computed first */
	public static boolean isComputed(String string) {
		return string.startsWith(specialCharacter);
	}
	}
