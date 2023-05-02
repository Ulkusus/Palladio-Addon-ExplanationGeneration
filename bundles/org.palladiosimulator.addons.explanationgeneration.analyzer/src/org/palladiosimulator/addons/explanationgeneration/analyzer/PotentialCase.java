package org.palladiosimulator.addons.explanationgeneration.analyzer;

import org.palladiosimulator.addons.explanationgeneration.data.CaseDefinition;
import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.addons.explanationgeneration.data.OccuredCase;

/**
 * A potential case.
 * The {@link MABEXAnalyzer} creates and calls these to check if a case occurs.
 * 
 * This has a reference to {@link AbstractMABEXAnalyzer}, so it is placed in
 * this bundle despite being more of a utility class.
 */
public class PotentialCase {
	// private static final Logger LOGGER = LogManager.getLogger(PotentialCase.class);

	private CaseDefinition definition;
	private int nextEventPosition;
	private AbstractMABEXAnalyzer analyzer;
	private OccuredCase myCase;

	private boolean yes;

	public PotentialCase(String id, CaseDefinition definition, AbstractMABEXAnalyzer analyzer) {
		this.definition = definition;
		this.analyzer = analyzer;
		nextEventPosition = 0;
		myCase = new OccuredCase(id);
	}

	/**
	 * check which conditions can currently be evaluated and if they match.
	 * */
	private boolean doConditionsMatch(EventWithData event, boolean stopConditions, String itsAlias) {
		yes = true;
		definition.getConditions(stopConditions).forEach(condition -> {
			if (condition.canEvaluateWith(myCase.getContainedAliases(), itsAlias)) {
				yes &= condition.isTrueFor(myCase.getEvents(), itsAlias, event);
				// fail-fast return from forEach
				if (!yes)
					return;
			} else	// assume that skipped conditions are true and skipped stop conditions are false 
				yes &= !stopConditions;
		});
		return yes;
	}

	/**
	 * Checks whether an event fits this potential case.
	 * If it was the last required event, queues an {@link OccuredCase} with the analyzer.
	 * 
	 * @return whether this potential case can be removed from the list.
	 */
	public boolean checkEvent(EventWithData event) {
		
		//check for stop condition first(!)
		// -> this means stop conditions are excluding an event that triggers 
		// 	  them even though it might also be the event this potential case needs next
		if (definition.getStop() != null) {
			// if you want to stop when seeing a specific event, you can match ANY.type to the event name in question
			if (doConditionsMatch(event, true, MABEXStrings.any))
				return true;
		}

		// first event type is definitely the one we are looking for, others we need to
		// check if they match
		if (nextEventPosition == 0) {
			if (doConditionsMatch(event, false, definition.getAlias(nextEventPosition))) {
				myCase.putEvent(definition.getAlias(nextEventPosition), event);
			}
			// if the first event does not match already, directly discard this potential case
			else
				return true;
		} else if (event.getType().equals(definition.getEvent(nextEventPosition))) {
			//if we found an event that might match the next one in the chain, check if it matches all conditions, then add it
			if (doConditionsMatch(event, false, definition.getAlias(nextEventPosition))) {
				myCase.putEvent(definition.getAlias(nextEventPosition), event);
			} 
			// if an event is the right type, but does not match, keep looking
			else
				return false;
		}
		// if the event does not match at all, keep looking
		else
			return false;
		
		// at this point we have added a new event
		nextEventPosition +=1;
		
		if (definition.isDone(nextEventPosition)) {
			analyzer.queueOccurredCase(myCase);
			// the job is done, delete this potential case
			return true;
		}
		
		// the job is not done yet, keep this potential case
		return false;
	}
}
