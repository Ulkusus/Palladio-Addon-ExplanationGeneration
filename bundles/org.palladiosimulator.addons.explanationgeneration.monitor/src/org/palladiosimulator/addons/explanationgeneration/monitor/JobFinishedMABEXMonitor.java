package org.palladiosimulator.addons.explanationgeneration.monitor;

import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.behavior.resourcesimulation.events.JobFinished;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;

@OnEvent(when = JobFinished.class)
public class JobFinishedMABEXMonitor extends AbstractMABEXMonitor {
	
	public JobFinishedMABEXMonitor() {
		super(new String[]{
				MABEXStrings.TIME,
				MABEXStrings.JOBID,
				MABEXStrings.PROCESSINGRESOURCETYPE
		});
	}

	@Subscribe
	public void onJobFinished(final JobFinished jobFinished) {
		getAnalyzer().passEvent(newEvent()
				.put(MABEXStrings.TIME, jobFinished.time())
				.put(MABEXStrings.JOBID, jobFinished.getEntity().getId())
				.put(MABEXStrings.PROCESSINGRESOURCETYPE, jobFinished.getEntity().getProcessingResourceType())
				);
	}
}
