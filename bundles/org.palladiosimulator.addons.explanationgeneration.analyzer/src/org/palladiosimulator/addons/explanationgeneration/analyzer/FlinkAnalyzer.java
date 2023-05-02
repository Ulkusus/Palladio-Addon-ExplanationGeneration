package org.palladiosimulator.addons.explanationgeneration.analyzer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.palladiosimulator.addons.explanationgeneration.builder.AbstractMABEXBuilder;
import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.OccuredCase;

/**
 * FIXME
 * Did not work due to Flink trying to get its configuration class via reflection, which fails in an OSGi environment.
 * */
@Deprecated
public class FlinkAnalyzer extends AbstractMABEXAnalyzer {
	private static final Logger LOGGER = LogManager.getLogger(FlinkAnalyzer.class);
	
	//private final StreamExecutionEnvironment executionEnvironment;
	
	//private DataStream<EnrichedEvent> input;

	public FlinkAnalyzer(AbstractMABEXBuilder builder) {
		super(null);
		LOGGER.warn("test1");
		// throws a ClassNotFoundException when trying to get its config class via reflection
		//executionEnvironment = StreamExecutionEnvironment.createLocalEnvironment();
		LOGGER.warn("test2");
	}

	@Override
	public void passEvent(EventWithData event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void queueOccurredCase(OccuredCase caze) {
		// TODO Auto-generated method stub
		
	}

}
