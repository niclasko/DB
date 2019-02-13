package org.nicsoft.DB.Data.JDBC;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverPropertyInfo;
import java.util.Properties;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;

import java.util.logging.Logger;
import java.io.File;

public class JDBCDriver implements Driver {
	
	private Driver driver;
	
	JDBCDriver(Driver d) {
		this.driver = d;
	}
	
	public boolean acceptsURL(String u) throws SQLException {
		return this.driver.acceptsURL(u);
	}
	
	public Connection connect(String u, Properties p) throws SQLException {
		return this.driver.connect(u, p);
	}
	
	public int getMajorVersion() {
		return this.driver.getMajorVersion();
	}
	
	public int getMinorVersion() {
		return this.driver.getMinorVersion();
	}
	
	public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
		return this.driver.getPropertyInfo(u, p);
	}
	
	public boolean jdbcCompliant() {
		return this.driver.jdbcCompliant();
	}
	
	public Logger getParentLogger() {
		return null;
	}
	
	/*
	** Add related libraries needed by JDBC driver
	** All jars in the same directory as JDBC driver path
	*/
	private static void updateClassPath(String jdbcDriverPath) throws SQLException, Exception {
		
		String jdbcDriverFolderPath = 
			jdbcDriverPath.substring(0, jdbcDriverPath.lastIndexOf("/") + 1);

		File[] files = new File(jdbcDriverFolderPath).listFiles();
		
		URLClassLoader classLoader =
			(URLClassLoader)ClassLoader.getSystemClassLoader();
	
		for(int i=0; i<files.length; i++) {
			if(files[i].toString().lastIndexOf(".jar") > 0) {
				
				URL url = files[i].toURI().toURL();
				
				Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
				method.setAccessible(true);
				method.invoke(classLoader, url);
				
			}
		}
		
	}
	
	public static Connection getConnection(String jdbcDriverPath, String className, String connectionString, String user, String passWord) throws SQLException, Exception {
		
		JDBCDriver.updateClassPath(jdbcDriverPath);
	
		URL u = new URL("jar:file:" + jdbcDriverPath + "!/");
		String classname = className;
		URLClassLoader ucl = new URLClassLoader(new URL[] { u });
		Driver d = (Driver)Class.forName(classname, true, ucl).newInstance();
		DriverManager.registerDriver(new JDBCDriver(d));
		
		return DriverManager.getConnection(connectionString, user, passWord);
		
	}
}