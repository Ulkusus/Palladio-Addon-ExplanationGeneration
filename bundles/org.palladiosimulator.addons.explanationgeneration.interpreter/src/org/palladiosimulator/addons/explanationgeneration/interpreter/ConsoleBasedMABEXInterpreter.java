package org.palladiosimulator.addons.explanationgeneration.interpreter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.osgi.framework.FrameworkUtil;
import org.palladiosimulator.addons.explanationgeneration.data.Explanation;
import org.palladiosimulator.addons.explanationgeneration.data.MABEXStrings;
import org.yaml.snakeyaml.Yaml;

public class ConsoleBasedMABEXInterpreter implements IMABEXInterpreter {
	private static final Logger LOGGER = LogManager.getLogger(ConsoleBasedMABEXInterpreter.class);
	
	/** the relative/total path this interpreter tries to load explanations from */
	private static final String explanationFileLocation = "src/Explanations.yml";
	
	private Map<String,String> explanations;
	private MessageConsoleStream out;
	
	public ConsoleBasedMABEXInterpreter() {
		out = findConsole(MABEXStrings.consoleName).newMessageStream();
	}
	
	@Override
	public boolean interpret(Explanation explanation) {
		String output = explanations.get(explanation.getType());
		if (output == null) {
			LOGGER.debug("Found no defined output for explanation " + explanation.getType());
			return false;
		} else {
			try {
				out.write(new StrSubstitutor(explanation.getData()).replace(output) + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	@Override
	public void updateDefs() {
		loadExplanations();
	}
	
	/**
	 * Load the .yml containing the explanation texts used by this interpreter.
	 * FIXME once the launch config tab is linked, add another file input section there if this interpreter is present
	 * */
	private void loadExplanations() {
		InputStream inputStream;
		try {
			inputStream = FileLocator.openStream(FrameworkUtil.getBundle(getClass()), new Path(explanationFileLocation), false);
			explanations = new Yaml().load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method was copied from https://wiki.eclipse.org/FAQ_How_do_I_write_to_the_console_from_a_plug-in%3F
	 * in March of 2023
	 * */
	private MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	   }
	
	@Override
	protected void finalize() {
		try {
			//close the stream when this is cleaned up
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This private class was copied and slightly adapted from
	 * https://stackoverflow.com/questions/14044715/strsubstitutor-replacement-with-jre-libraries
	 * in March of 2023
	 * */
	private class StrSubstitutor {
	    private Map<String, String> map;
	    private final Pattern p = Pattern.compile("\\$\\{(.+?)\\}");
	    
	    public StrSubstitutor(Map<String,Object> map) {
	    	this.map = new HashMap<>();
	    	map.forEach((key,value) -> this.map.put(key, value.toString()));
	    }

	    public String replace(String str) {
	        Matcher m = p.matcher(str);
	        StringBuilder sb = new StringBuilder();
	        while (m.find()) {
	            String var = m.group(1);
	            String replacement = map.get(var);
	            m.appendReplacement(sb, replacement);
	        }
	        m.appendTail(sb);
	        return sb.toString();
	    }
	}

}
