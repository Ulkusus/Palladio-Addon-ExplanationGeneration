package org.palladiosimulator.addons.explanationgeneration.monitor;

import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.behavior.resourcesimulation.events.JobInitiated;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;

@OnEvent(when = JobInitiated.class)
public class JobInitiatedMABEXMonitor extends AbstractMABEXMonitor {
	
	public JobInitiatedMABEXMonitor() {
		super(new String[]{
				MABEXStrings.TIME,
				MABEXStrings.JOBID,
				MABEXStrings.PROCESSINGRESOURCETYPE
		});
	}

	@Subscribe
	public void onJobInitiated(final JobInitiated jobInitiated) {
		getAnalyzer().passEvent(newEvent()
				.put(MABEXStrings.TIME, jobInitiated.time())
				.put(MABEXStrings.JOBID, jobInitiated.getEntity().getId())
				.put(MABEXStrings.PROCESSINGRESOURCETYPE, jobInitiated.getEntity().getProcessingResourceType())
				);
	}
}
