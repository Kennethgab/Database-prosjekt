package treningdagbok;

import java.sql.DriverManager;
import java.util.Properties;

public class SQLConn extends DBConn{
	
	public SQLConn() {
	}
	
		
		public void connect(String username, String password, String url) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				//Properties p = new Properties();
				String databaseURL = "jdbc:mysql://" + url + "?autoReconnect=true&useSSL=true";
				conn = DriverManager.getConnection(databaseURL, username, password);
				//conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test?autoReconnect=true&useSSL=false",p);
			} catch (Exception e) {
				throw new IllegalStateException("Error connecting to db:\n\t\t " + e);
			}
		}
		
	

}
