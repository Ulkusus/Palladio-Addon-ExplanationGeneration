package org.palladiosimulator.addons.explanationgeneration.monitor;

import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.behavior.systemsimulation.events.SEFFExternalActionCalled;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;

@OnEvent(when = SEFFExternalActionCalled.class)
public class SEFFExternalActionCalledMABEXMonitor extends AbstractMABEXMonitor {
	
	public SEFFExternalActionCalledMABEXMonitor() {
		super(new String[]{
				MABEXStrings.TIME,
				MABEXStrings.ENTRYREQUEST
		});
	}

	@Subscribe
	public void onSEFFExternalActionCalled(final SEFFExternalActionCalled seffExternalActionCalled) {
		getAnalyzer().passEvent(newEvent()
				.put(MABEXStrings.TIME, seffExternalActionCalled.time())
				.put(MABEXStrings.ENTRYREQUEST, seffExternalActionCalled.getEntity())
				);
	}
}
