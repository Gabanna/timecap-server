package de.rgse.timecap;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class Launcher extends Swarm {

	public Launcher() throws Exception {
		super();
	}

	public static void main(String[] args) throws Exception {
		Swarm swarm = new Launcher();
		swarm.fraction(datasourceWithH2());
		
		swarm.start();
		JAXRSArchive appDeployment = ShrinkWrap.create(JAXRSArchive.class);
		appDeployment.addModule("com.h2database.h2");
		appDeployment.addAsWebInfResource("/src/main/webapp/WEB-INF");

		// Deploy your app
		swarm.deploy(appDeployment);

	}
	
	private static DatasourcesFraction datasourceWithH2() {
        return new DatasourcesFraction()
                .jdbcDriver("h2", (d) -> {
                    d.driverClassName("org.h2.Driver");
                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
                    d.driverModuleName("com.h2database.h2");
                })
                .dataSource("ExampleDS", (ds) -> {
                    ds.driverName("h2");
                    ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                    ds.userName("sa");
                    ds.password("sa");
                });
    }	
}
