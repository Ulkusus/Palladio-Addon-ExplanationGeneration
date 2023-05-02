package org.palladiosimulator.addons.explanationgeneration.analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.FrameworkUtil;
import org.palladiosimulator.addons.explanationgeneration.builder.AbstractMABEXBuilder;
import org.palladiosimulator.addons.explanationgeneration.data.CaseDefinition;
import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.OccuredCase;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/** 
 * Subclasses of this class take care of finding cases in the
 * incoming stream of events the monitors pass from Slingshot.
 * 
 * They then pass an {@link OccuredCase} to the linked {@link AbstractMABEXBuilder}.
 */
public abstract class AbstractMABEXAnalyzer {
	private static final Logger LOGGER = LogManager.getLogger(AbstractMABEXAnalyzer.class);

	/** The path this component tries to get case definitions from */
	private String caseDefinitionFile;
	
	/** The relative path this component defaults to for getting case definitions. */
	private static final String casesDefaultLocation = "src/DefaultCaseDefinitions.yml";
	 
	protected LinkedList<EventWithData> events;
	protected AbstractMABEXBuilder builder;
	protected Map<String,CaseDefinition> caseDefinitions;
	
	/**
	 * Used by monitors to pass on the events they detect.
	 * */
	public abstract void passEvent(EventWithData event);
	
	/**
	 * Used by potential cases to pass a detected occurrence to the {@link MABEXAnalyzer}.
	 * Could be reused to let a CEP framework pass their detected cases back as well.
	 * */
	public abstract void queueOccurredCase(OccuredCase caze);
	
	public AbstractMABEXAnalyzer(AbstractMABEXBuilder builder) {
		this.builder = builder;
		events = new LinkedList<>();
		this.builder.linkEvents(events);
	}

	/**
	 * TODO set this up with SimulationStartedMABEXMonitor
	 * */
	public void setCaseDefinitionFile(String path) {
		caseDefinitionFile = path;
	}
	
	/**
	 * Loads the {@link CaseDefinition}s given in a file at the path caseDefinitionFile points to.
	 * 
	 * FIXME falls back to the relative path defined in caseDetailLocation for now
	 * */
	public void loadCaseDefinitions() {			
		
		Yaml yaml = new Yaml(new Constructor(CaseDefinitionWrapper.class, new LoaderOptions()));
		InputStream inputStream;
		CaseDefinitionWrapper wrapper;
		
		if (caseDefinitionFile == null) {
			try {
				inputStream = FileLocator.openStream(FrameworkUtil.getBundle(getClass()), new Path(casesDefaultLocation), false);
				wrapper = yaml.load(inputStream);
				caseDefinitions = wrapper.getCases() != null ? wrapper.getCases() : new LinkedHashMap<String,CaseDefinition>();
				builder.setCaseDefinitions(caseDefinitions);
				LOGGER.warn(new Yaml().dump(caseDefinitions));
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.warn("Unable to load the default case definition file at " + casesDefaultLocation + "!");
			}
		} else
			
		//FIXME this part below currently does not matter as the link to the launch config tab is missing in the main class

		try {
			inputStream = new FileInputStream(caseDefinitionFile);
			wrapper = yaml.load(inputStream);
			caseDefinitions = wrapper.getCases();
			builder.setCaseDefinitions(wrapper.getCases());
			LOGGER.warn(new Yaml().dump(caseDefinitions));
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.warn("Cleared the stored case definition file path after failing to load the file. Will load empty case definition map now.");
			caseDefinitionFile = null;
			loadCaseDefinitions();
		}
	}
}
