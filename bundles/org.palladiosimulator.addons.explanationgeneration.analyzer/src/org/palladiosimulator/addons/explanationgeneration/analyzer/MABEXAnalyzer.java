package org.palladiosimulator.addons.explanationgeneration.analyzer;

import java.util.ArrayDeque;
import java.util.Iterator;

import org.palladiosimulator.addons.explanationgeneration.builder.AbstractMABEXBuilder;
import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.OccuredCase;

public class MABEXAnalyzer extends AbstractMABEXAnalyzer {
	// private static final Logger LOGGER =
	// LogManager.getLogger(MABEXAnalyzer.class);
	private ArrayDeque<PotentialCase> potentialCases;
	private ArrayDeque<OccuredCase> occurredCases;

	public MABEXAnalyzer(AbstractMABEXBuilder builder) {
		super(builder);

		potentialCases = new ArrayDeque<>();
		occurredCases = new ArrayDeque<>();
	}

	@Override
	public void passEvent(EventWithData event) {
		events.add(event);
		analyze(event);
	}

	private void analyze(EventWithData lastEvent) {

		caseDefinitions.forEach((id, definition) -> {
			// check whether the event could be the trigger for any case
			if (lastEvent.getType().equals(definition.getTrigger())) {
				potentialCases.add(new PotentialCase(id, definition, this));
			}
		});

		// Make each potential case check if an event matches the one it needs next.
		// If the event matches and was the last one it needed, the case queues an OccurredCase with this analyzer.
		// If it did or is otherwise not valid anymore, it gets removed.
		Iterator<PotentialCase> it = potentialCases.iterator();
		while (it.hasNext()) {
			if (it.next().checkEvent(lastEvent))
				it.remove();
		}

		// send all occurred cases off to the builder
		occurredCases.forEach(builder::buildExplanation);
		occurredCases.clear();
	}

	@Override
	public void queueOccurredCase(OccuredCase caze) {
		occurredCases.add(caze);
	}
}
