package org.palladiosimulator.addons.explanationgeneration.builder;

import java.util.LinkedList;
import java.util.Map;

import org.palladiosimulator.addons.explanationgeneration.data.CaseDefinition;
import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.OccuredCase;
import org.palladiosimulator.addons.explanationgeneration.interpreter.IMABEXInterpreter;

public abstract class AbstractMABEXBuilder {
	protected IMABEXInterpreter[] interpreters;
	protected Map<String, CaseDefinition> caseDefinitions;
	protected LinkedList<EventWithData> events;
	
	public AbstractMABEXBuilder(IMABEXInterpreter[] interpreters) {
		this.interpreters = interpreters;
	}
	
	public void linkEvents(LinkedList<EventWithData> events) {
		this.events = events;
	}

	public abstract boolean buildExplanation(OccuredCase caze);

	
	public void setCaseDefinitions(Map<String, CaseDefinition> caseDefinitions) {
		this.caseDefinitions = caseDefinitions;
		// assume that if the case definitions get updated, we likely also want to update the interpreters
		for (IMABEXInterpreter i : interpreters)
			i.updateDefs();
	}
}
