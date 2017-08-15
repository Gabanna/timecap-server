package de.rgse.timecap.config;

import java.io.IOException;

import org.wildfly.swarm.datasources.DatasourcesFraction;

public class PersistenceConfig {

	private PersistenceConfig() {
	}

	public static DatasourcesFraction instance() throws IOException {
		DatasourcesFraction fraction = new DatasourcesFraction();

		String driverName;
		String url = System.getenv("JDBC_DATABASE_URL");
		String userName = System.getenv("JDBC_DATABASE_USERNAME");
		String password = System.getenv("JDBC_DATABASE_PASSWORD");

		if (url == null) {
			driverName = "h2";
			url = "jdbc:h2:./data/db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
			userName = "sa";
			password = "sa";
		
		} else {
			driverName = "postgresql";
		
		}

		final String driver = driverName;
		final String connectionUrl = url;
		final String connectionUserName= userName;
		final String connectionPassword= password;
		
		fraction.dataSource("timecap-server", source -> {
			source.driverName(driver);
			source.connectionUrl(connectionUrl);
			source.userName(connectionUserName);
			source.password(connectionPassword);
		});

		return fraction;
	}
}
