package org.palladiosimulator.addons.explanationgeneration.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchListener;
import org.palladiosimulator.addons.explanationgeneration.analyzer.AbstractMABEXAnalyzer;
import org.palladiosimulator.addons.explanationgeneration.analyzer.MABEXAnalyzer;
import org.palladiosimulator.addons.explanationgeneration.builder.AbstractMABEXBuilder;
import org.palladiosimulator.addons.explanationgeneration.builder.BasicMABEXBuilder;
import org.palladiosimulator.addons.explanationgeneration.interpreter.ConsoleBasedMABEXInterpreter;
import org.palladiosimulator.addons.explanationgeneration.interpreter.IMABEXInterpreter;
import org.palladiosimulator.addons.explanationgeneration.monitor.AbstractMABEXMonitor;
import org.palladiosimulator.addons.explanationgeneration.monitor.JobFinishedMABEXMonitor;
import org.palladiosimulator.addons.explanationgeneration.monitor.JobInitiatedMABEXMonitor;
import org.palladiosimulator.addons.explanationgeneration.monitor.SEFFExternalActionCalledMABEXMonitor;
import org.palladiosimulator.addons.explanationgeneration.monitor.SimulationStartedMABEXMonitor;
import org.palladiosimulator.addons.explanationgeneration.monitor.UserEntryRequestedMABEXMonitor;
import org.palladiosimulator.addons.explanationgeneration.monitor.UserFinishedMABEXMonitor;
import org.palladiosimulator.addons.explanationgeneration.monitor.UserStartedMABEXMonitor;
import org.palladiosimulator.analyzer.slingshot.core.extension.AbstractSlingshotExtension;
import org.yaml.snakeyaml.Yaml;

/**
 * The main class for the explanation generation Slingshot extension.
 * Configures the component and registers monitors.
 * 
 * @author Jan Haas
 * */
public class ExplanationGeneration extends AbstractSlingshotExtension {
	private static final Logger LOGGER = LogManager.getLogger(ExplanationGeneration.class);

	private IMABEXInterpreter[] interpreters;
	private AbstractMABEXBuilder builder;
	private AbstractMABEXAnalyzer analyzer;
	
	private final ILaunchListener myLaunchListener =  new ILaunchListener() {
		@Override
		public void launchAdded(ILaunch launch) {
			//FIXME that's not it, the launch object does not contain that attribute (currently?)
			analyzer.setCaseDefinitionFile(launch.getAttribute("caseDefinitions"));
		}
		@Override
		public void launchRemoved(ILaunch launch) {}
		@Override
		public void launchChanged(ILaunch launch) {}
	};
	
	public ExplanationGeneration () {
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(myLaunchListener);
	}
	
	/**
	 * FIXME
	 * Unless a better method to detect them (that works in an OSGi environment) is found,
	 * new monitor classes need to be added to this set.
	 * */
	private static final HashSet<Class<? extends AbstractMABEXMonitor>> monitorClasses = new HashSet<>(Arrays.asList(
		SimulationStartedMABEXMonitor.class,
		JobFinishedMABEXMonitor.class,
		JobInitiatedMABEXMonitor.class,
		SEFFExternalActionCalledMABEXMonitor.class,
		UserStartedMABEXMonitor.class,
		UserFinishedMABEXMonitor.class,
		UserEntryRequestedMABEXMonitor.class
		));

	@Override
	protected void configure() {
		// allow for multiple outputs
		interpreters = new IMABEXInterpreter[]{
				new ConsoleBasedMABEXInterpreter()
				/* Change or add more interpreters here if needed */
				};
		
		builder = new BasicMABEXBuilder(interpreters);
		
		analyzer = new MABEXAnalyzer(builder);
		
		// for all monitors, set the analyzer they should report to
		AbstractMABEXMonitor.setAnalyzer(analyzer);
		
		// for printing the list of available monitors and their details to the console
		HashMap<String,List<String>> eventDetails = new HashMap<>();
		
		//let Slingshot know about all listed monitor classes (in the first line)
		//and have some fun with java and no abstract static methods (all other lines)
		monitorClasses.forEach(clazz -> {
			install(clazz);
			try {

				AbstractMABEXMonitor monitor = clazz.getDeclaredConstructor().newInstance();
				eventDetails.put(monitor.getType(), monitor.getEventDetails());
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		});
		
		//FIXME output this to a file for convenience (but where to place that file considering this is OSGi?)
		//Uses Snakeyaml for prettier printing
		LOGGER.warn("\n\nAvailable Events and their Details:\n"+new Yaml().dump(eventDetails));
	}
	
	

}
