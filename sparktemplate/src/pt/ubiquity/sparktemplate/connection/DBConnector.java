package pt.ubiquity.sparktemplate.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pt.ubiquity.sparktemplate.ServerStart;

public class DBConnector {
	
	 private static Connection connection;
	
	 public DBConnector(){
		 try {
			Class.forName(ServerStart.appProperties.getDbDriver());
			connection = DriverManager
				.getConnection(ServerStart.appProperties.getDbUrl(),ServerStart.appProperties.getDbUser(), ServerStart.appProperties.getDbPassword());

			} catch (SQLException e) {
				System.out.println("Connection Failed! Check output console.");
				e.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}

			if (connection != null) {
				System.out.println("Connection to database ");
			} else {
				System.out.println("Failed to make connection!");
			}
	 }
	 
	 
	 
}
