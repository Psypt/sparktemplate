package pt.ubiquity.sparktemplate.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

	private String frontEndPath;
	private String dbDriver;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	public AppProperties(){
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("conf/app.properties");
			prop.load(input);
			frontEndPath = prop.getProperty("front-end.path");
			dbDriver = prop.getProperty("db.driver");
			dbUrl = prop.getProperty("db.url");
			dbUser = prop.getProperty("db.user");
			dbPassword = prop.getProperty("db.password");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getFrontEndPath() {
		return frontEndPath;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

}
