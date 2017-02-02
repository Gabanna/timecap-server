package de.rgse.timecap.rest.util;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.reflections.Reflections;

@ApplicationPath("/")
public class TimecapApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new Reflections("de.rgse.timecap").getTypesAnnotatedWith(Path.class);
		classes.addAll(super.getClasses());
		return classes;
	}
}
