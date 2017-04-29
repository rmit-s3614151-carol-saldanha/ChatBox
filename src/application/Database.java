package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:8889/encryption";


	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	void setDatabase() {
 
		Connection conn = null;
		Statement stmt = null;

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			stmt = conn.createStatement();
			String sql;

			sql = "";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("add column name [int]");
				String first = rs.getString("add column name [string] ");
				String last = rs.getString("add column name [string] ");

				// Display values
			
			}
			// Avoid leaks
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
				System.out.println(se.getMessage());
			System.out.println("SQL Exception occured. Please contaact admin : nirajbohra@gmail.com");
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// close resources 
			try {
				if (stmt != null)
					stmt.close();
			} 
			catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		}
	}
	
}
