package org.palladiosimulator.addons.explanationgeneration.monitor;

import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.core.events.SimulationStarted;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;



/** This monitor fulfills the special extra function of making the analyzer reload its case definitions */
@OnEvent(when = SimulationStarted.class)
public class SimulationStartedMABEXMonitor extends AbstractMABEXMonitor {

	public SimulationStartedMABEXMonitor() {
		/* The passed array declares what details this monitor extracts from the event.
		 * It is used when outputting these to the user/expert looking to write case definitions
		 * and should thus be kept up to date. */
		super(new String[]{
				MABEXStrings.TIME
				});
	}

	@Subscribe
	public void onSimulationStarted(final SimulationStarted event) {
		// only call this in this monitor so we get fresh case definitions at simulation start
		getAnalyzer().loadCaseDefinitions();
		
		getAnalyzer().passEvent(newEvent()
				.put(MABEXStrings.TIME, event.time())
				/* add more/other details here */
				);
	}
}
