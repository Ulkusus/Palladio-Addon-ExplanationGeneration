package org.palladiosimulator.addons.explanationgeneration.monitor;

import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.behavior.usagemodel.events.UserFinished;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;

@OnEvent(when = UserFinished.class)
public class UserFinishedMABEXMonitor extends AbstractMABEXMonitor {
	
	public UserFinishedMABEXMonitor() {
		super(new String[] {
				MABEXStrings.TIME,
				MABEXStrings.USER
		});
	}

	@Subscribe
	public void onUserFinished(final UserFinished userFinished) {
		getAnalyzer().passEvent(newEvent()
				.put(MABEXStrings.TIME, userFinished.time())
				.put(MABEXStrings.USER, userFinished.getEntity().getUser().getId())
				);
	}
}
