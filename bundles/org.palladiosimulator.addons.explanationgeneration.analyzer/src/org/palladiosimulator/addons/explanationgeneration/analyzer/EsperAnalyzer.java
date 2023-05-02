package org.palladiosimulator.addons.explanationgeneration.analyzer;

import org.palladiosimulator.addons.explanationgeneration.data.EventWithData;
import org.palladiosimulator.addons.explanationgeneration.data.OccuredCase;

//import com.espertech.esper.common.client.EPCompiled;
//import com.espertech.esper.compiler.client.EPCompileException;
//import com.espertech.esper.compiler.client.EPCompiler;
//import com.espertech.esper.compiler.client.EPCompilerProvider;

/**
 * FIXME did not work due to differences between Java 8 Apache Xerces (internal
 * classes referenced in the Manifest of Esper) and Java 11 Apache Xerces (moved
 * from the internal package to an extra bundle)
 */
@Deprecated
public class EsperAnalyzer extends AbstractMABEXAnalyzer {
	// private EPCompiler compiler;

	public EsperAnalyzer() {
		super(null);
		// compiler = EPCompilerProvider.getCompiler();
//		try {
//			EPCompiled test = compiler.compile("create map schema Test as (prop1 string)", null);
//			System.out.println(test);
//		} catch (EPCompileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException();
//		}
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
