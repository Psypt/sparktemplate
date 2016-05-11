package pt.ubiquity.sparktemplate;

import pt.ubiquity.sparktemplate.connection.DBConnector;
import pt.ubiquity.sparktemplate.dao.UserDAO;
import pt.ubiquity.sparktemplate.util.AppProperties;
import pt.ubiquity.sparktemplate.util.ConfigPersistenceUnit;

public class ServerStart {

	public static Routes routes = new Routes();
	public static AppProperties appProperties = new AppProperties();
	public static DBConnector dbConnector = new DBConnector();
	public static ConfigPersistenceUnit persistenceUnit = new ConfigPersistenceUnit();
	public static UserDAO userDAO = new UserDAO();
	
	public static void main(String[] args) {
		 routes.initRoutes();
	}

}
