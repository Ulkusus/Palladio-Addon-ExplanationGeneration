package org.palladiosimulator.addons.explanationgeneration.monitor;

import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.addons.explanationgeneration.analyzer.AbstractMABEXAnalyzer;
import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.core.extension.SimulationBehaviorExtension;

/**
 * All MABEXMonitors should extend this class.
 * See {@link TemplateMABEXMonitor} for details.<br>
 * <br>
 * Sets the event type according to the subclass' name in the constructor.<br>
 * Stores the analyzer they should pass an {@link EventWithData} to (see getAnalyzer()).<br>
 * Provides the correct {@link EventWithData} via newEvent().
 * */
public abstract class AbstractMABEXMonitor implements SimulationBehaviorExtension {
	
	private static AbstractMABEXAnalyzer analyzer;
	
	/** The event this monitor listens to */
	protected final String type;
	/** The details of the event this monitor will pass on */
	protected final String[] details;

	public static void setAnalyzer(AbstractMABEXAnalyzer analyser) {
		analyzer = analyser;
	}

	public static AbstractMABEXAnalyzer getAnalyzer() {
		return analyzer;
	}

	public String getType() {
		return type;
	};

	public List<String> getEventDetails() {
		return Arrays.asList(details);
	}

	protected AbstractMABEXMonitor(String[] details) {
		String className = this.getClass().getSimpleName();
		if (className.endsWith(MABEXStrings.monitorSuffix))
			this.type = className.substring(0, className.lastIndexOf(MABEXStrings.monitorSuffix));
		else this.type = className;
		this.details = details;
	}
	
	/** Returns a new {@link EventWithData} instance of this class' type. */
	protected EventWithData newEvent() {
		return new EventWithData(type);
	}

}
