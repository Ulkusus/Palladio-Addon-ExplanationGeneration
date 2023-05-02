package org.palladiosimulator.addons.explanationgeneration.analyzer;

import java.util.HashMap;

import org.palladiosimulator.addons.explanationgeneration.data.CaseDefinition;

/**
 * Just a wrapper for Snakeyaml to use in importing case definitions.
 * Should remain in this package/bundle to avoid potential class loader problems with OSGi.
 * */
public class CaseDefinitionWrapper {
	private HashMap<String, CaseDefinition> cases;

	public HashMap<String, CaseDefinition> getCases() {
		return cases;
	}

	public void setCases(HashMap<String, CaseDefinition> cases) {
		this.cases = cases;
	}
}
