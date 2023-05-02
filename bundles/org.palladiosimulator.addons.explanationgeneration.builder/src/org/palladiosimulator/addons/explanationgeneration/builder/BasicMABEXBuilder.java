package org.palladiosimulator.addons.explanationgeneration.builder;

import org.palladiosimulator.addons.explanationgeneration.data.Explanation;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.addons.explanationgeneration.data.OccuredCase;
import org.palladiosimulator.addons.explanationgeneration.data.computed.AbstractMABEXComputed;
import org.palladiosimulator.addons.explanationgeneration.interpreter.IMABEXInterpreter;

public class BasicMABEXBuilder extends AbstractMABEXBuilder {

	private String[] helper;

	public BasicMABEXBuilder(IMABEXInterpreter[] interpreters) {
		super(interpreters);
	}

	@Override
	public boolean buildExplanation(OccuredCase caze) {
		String name = caze.getName();
		Explanation explanation = new Explanation(name);
		caseDefinitions.get(name).getSelected().forEach(detailToPut -> {
			if (MABEXStrings.isComputed(detailToPut)) {
				AbstractMABEXComputed computed = AbstractMABEXComputed.from(detailToPut);
				explanation.put(computed.getAlias(), computed.computeFor(caze.getEvents()));
			} else {
				helper = detailToPut.split(MABEXStrings.dot);
				explanation.put(detailToPut, caze.getEvent(helper[0]).get(helper[1]));
			}
		});

		//FIXME not done yet
//		caseDefinitions.get(type).getRelations().forEach((relationName, relation) -> {});

		for (IMABEXInterpreter interpreter : interpreters) {
			interpreter.interpret(explanation);
		}
		return true;
	}

}
