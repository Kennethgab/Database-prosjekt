

package treningdagbok;

import java.sql.*;
import java.util.Properties;

public abstract class DBConn {
	protected Connection conn;
	public DBConn () {
	}
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Properties p = new Properties();
			p.put("user", "root");
			p.put("password", "root");
			String databaseURL = "jdbc:mysql://localhost:3306/data?autoReconnect=true&useSSL=true";
			String user = "root";
			String password = "root";
			conn = DriverManager.getConnection(databaseURL, user, password);
			//conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test?autoReconnect=true&useSSL=false",p);
		} catch (Exception e) {
			System.out.println("Error connecting to db: " + e);
		}
	}
}


