package Banking_Management_System;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCutils {
	private JDBCutils() {

	}

	static {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws SQLException, IOException {

		FileInputStream fis = new FileInputStream(
				"D:\\Projects\\JDBC\\Banking system\\Banking_Management_System\\src\\Banking_Management_System\\proper.properties");
		Properties properties = new Properties();
		properties.load(fis);
		Connection connection = DriverManager.getConnection(properties.getProperty("url"),
				properties.getProperty("username"), properties.getProperty("password"));
		return connection;
	}
	
	public static void cleanUp(Connection connection, Statement statement, ResultSet resultset) throws SQLException {

		if (connection != null) {

			connection.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (resultset != null) {

			resultset.close();
		}

	}
	
}
