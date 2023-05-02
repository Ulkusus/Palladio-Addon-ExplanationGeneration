package org.palladiosimulator.addons.explanationgeneration.monitor;

import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.behavior.usagemodel.events.UserStarted;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;

@OnEvent(when = UserStarted.class)
public class UserStartedMABEXMonitor extends AbstractMABEXMonitor {

	public UserStartedMABEXMonitor() {
		super(new String[]{
				MABEXStrings.TIME,
				MABEXStrings.USER
		});
	}

	@Subscribe
	public void onUserStarted(final UserStarted userStarted) {
		getAnalyzer().passEvent(newEvent()
				.put(MABEXStrings.TIME, userStarted.time())
				.put(MABEXStrings.USER, userStarted.getEntity().getUser().getId())
				);
	}
}
