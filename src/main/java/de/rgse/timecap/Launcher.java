package de.rgse.timecap;

import org.wildfly.swarm.Swarm;

import de.rgse.timecap.config.PersistenceConfig;

public class Launcher {

	public static void main(String[] args) throws Exception {
		Swarm swarm = new Swarm();
		
		swarm.fraction(PersistenceConfig.instance());
		
		swarm.start();
		swarm.deploy();
	}
}
