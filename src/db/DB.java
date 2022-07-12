package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	// 1 - Declare a type connection and starts with null
	private static Connection conn = null;

	// 2 - You must load file db.properties using method Properties
	private static Properties LoadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	// 3 - Get connection with your db.properties data url with method Connection

	public static Connection getConnection() {
		try {
			if (conn == null) {
				Properties props = LoadProperties();
				String url = props.getProperty("dburl"); // "dburl" was get from file db.properties
				conn = DriverManager.getConnection(url, props);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		return conn;
	}

	// 4 - Close connection
	public static void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	public static void closeStatement(Statement st) {

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}

	}

	public static void closeResultSet(ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}

	}

}
