package org.palladiosimulator.addons.explanationgeneration.interpreter;

import org.palladiosimulator.addons.explanationgeneration.data.Explanation;

/**
 * The last link in the MABEX-based explanation generation chain.<br>
 * The main ExplanationGeneration class hands an array of these to the explanation builder.<br>
 * The builder then sends an instance of {@link Explanation} to all contained interpreters whenever a case occurs.<br>
 * Classes implementing this interface should create a human-readable or otherwise usable output depending on the
 * Explanations type acquirable via getType().
 * <br><br>
 * Types are defined in the .yaml file loaded by the main ExplanationGeneration class' loadCaseDefinitions() 
 * */
public interface IMABEXInterpreter {
	
	/**
	 * Takes an {@link Explanation} and turns it into the desired output.
	 * 
	 * @return whether the interpreter succeeded in doing so<br>
	 * Note: The BasicMABEXBuilder simply ignores this information.
	 * */
	public abstract boolean interpret(Explanation explanation);
	
	/**
	 * Can be used by the builder to make this interpreter
	 * update its output definitions.
	 * */
	public abstract void updateDefs();
}
