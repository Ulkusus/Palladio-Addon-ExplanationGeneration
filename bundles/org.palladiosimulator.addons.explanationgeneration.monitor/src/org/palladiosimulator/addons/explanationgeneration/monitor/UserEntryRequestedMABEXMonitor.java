package org.palladiosimulator.addons.explanationgeneration.monitor;

import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.behavior.usagemodel.events.UserEntryRequested;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;

@OnEvent(when = UserEntryRequested.class)
public class UserEntryRequestedMABEXMonitor extends AbstractMABEXMonitor {
	
	public UserEntryRequestedMABEXMonitor() {
		super(new String[] {
				MABEXStrings.TIME,
				MABEXStrings.USER
		});
	}

	@Subscribe
	public void onUserEntryRequested(final UserEntryRequested userEntryRequested) {
		getAnalyzer().passEvent(newEvent()
				.put(MABEXStrings.TIME, userEntryRequested.time())
				.put(MABEXStrings.USER, userEntryRequested.getUserInterpretationContext().getUser().getId())
				);
	}
}
