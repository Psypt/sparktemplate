package pt.ubiquity.sparktemplate.util;

import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION_MODE;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_URL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_SESSION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_THREAD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_TIMESTAMP;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TRANSACTION_TYPE;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;

import pt.ubiquity.sparktemplate.ServerStart;

public class ConfigPersistenceUnit {

	private EntityManager em;
	
	public ConfigPersistenceUnit(){
		Map<String,String> properties = new HashMap<String,String>();

	    // Ensure RESOURCE_LOCAL transactions is used.
	    properties.put(TRANSACTION_TYPE,
	        PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

	    // Configure the internal EclipseLink connection pool
	    properties.put(JDBC_DRIVER, ServerStart.appProperties.getDbDriver());
	    properties.put(JDBC_URL, ServerStart.appProperties.getDbUrl());
	    properties.put(JDBC_USER, ServerStart.appProperties.getDbUser());
	    properties.put(JDBC_PASSWORD, ServerStart.appProperties.getDbPassword());

	    // Configure logging. FINE ensures all SQL is shown
	    properties.put(LOGGING_LEVEL, "FINE");
	    properties.put(LOGGING_TIMESTAMP, "true");
	    properties.put(LOGGING_THREAD, "false");
	    properties.put(LOGGING_SESSION, "false");

	    // Ensure that no server-platform is configured
	    properties.put(TARGET_SERVER, TargetServer.None);
	    
	    //ddl create database schema
	    properties.put(DDL_GENERATION,"create-tables");
	    properties.put(DDL_GENERATION_MODE,"database");

	    // Now the EntityManagerFactory can be instantiated for testing using: 
	     em = Persistence.createEntityManagerFactory("persistence", properties).createEntityManager();
	}
	
	public EntityManager getEntityManager(){
		return em;
	}
	
}
