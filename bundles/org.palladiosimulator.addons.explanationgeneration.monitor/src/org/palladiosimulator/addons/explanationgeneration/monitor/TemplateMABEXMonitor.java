package org.palladiosimulator.addons.explanationgeneration.monitor;

import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.palladiosimulator.analyzer.slingshot.common.events.AbstractSimulationEvent;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;

/**
 * This is a template for new monitor classes.
 * This is not a functional Monitor class!
 * 
 * To create a new monitor:
 * 1. Make a renamed copy of this class (named [event name]MABEXMonitor.class).
 * 	-> if the class name ends with "MABEXMonitor", it will set its type to [event name]
 * 	-> if it does not, it will set its type to the full simple name of the class instead
 * 2. Change the OnEvent annotation to the event class you want to listen to
 * 3. Change the parameter of the onEventName method to the event class
 * 4. in onEventName, .put() all important information this event contains
 * 		-> please use or add fields in the data class {@link MABEXStrings} for consistency
 * 5. Add all keys to the String array passed to the super() constructor
 * 		-> This allows the system to print them all together for convenience
 * 6. In the ExplanationGeneration class, add this class to the "monitorClasses" HashSet
 * 		-> This makes the extension register this monitor class
 * Optional:
 * 7. Rename the onEvent method to fit the event you want to listen to
 * 		-> the method should still start with "on" to be correctly detected
 * 			(Though you can also use @EventHandler to mark the method)
 * 8. Remove this Javadoc
 * */
@OnEvent(when = TemplateMABEXMonitor.class)
public class TemplateMABEXMonitor extends AbstractMABEXMonitor {

	public TemplateMABEXMonitor() {
		/* The passed array declares what details this monitor extracts from the event.
		 * It is used when outputting these to the user/expert looking to write case definitions
		 * and should thus be kept up to date. */
		super(new String[]{
				MABEXStrings.TIME
				});
	}

	@Subscribe
	public void onEvent(final AbstractSimulationEvent event) {
		getAnalyzer().passEvent(newEvent()
				.put(MABEXStrings.TIME, event.time())
				/* add more/other details here */
				);
	}
}
