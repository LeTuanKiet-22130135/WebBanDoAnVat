package db;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/webanvat"; // Change database name accordingly
    private static final String USER = "root";  // Replace with your DB username
    private static final String PASSWORD = "12345";  // Replace with your DB password
    private static Connection connection = null;
    
    public static Connection getConnection() throws Exception {
    	if (connection == null) {
    		try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
		return connection;
    }
}
