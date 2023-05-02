package org.palladiosimulator.addons.explanationgeneration.monitor;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.common.reflect.ClassPath;

/**
 * An attempt at getting the monitors automatically.
 * NOT functional!
 * Left here for documentation purposes.
 * @deprecated
 * */
public class MABEXMonitorFinder {
	
	private static final Logger LOGGER = LogManager.getLogger(MABEXMonitorFinder.class);
	
	public static Set<Class> findMonitors() {
		
		try {
			LOGGER.warn(ClassPath.from(ClassLoader.getPlatformClassLoader()).getAllClasses().size());
			return ClassPath.from(ClassLoader.getPlatformClassLoader())
					  .getAllClasses()
					  .stream()
					  .map(clazz -> {LOGGER.warn(clazz);return clazz;})
					  .filter(clazz -> clazz.getPackageName()
					  .contains("org.palladiosimulator.addons.explanationgeneration.monitor.monitors"))
					  .map(clazz -> clazz.load())
					  .collect(Collectors.toSet());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Collections.emptySet();
		}
	}
}
