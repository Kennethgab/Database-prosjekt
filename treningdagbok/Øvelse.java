
import java.util.*;
import java.sql.*;


public class Øvelse extends ActiveDomainObject {
	private String navn;
	private String beskrivelse;
	private int øvelsesid;
	private int apparatid;



	public Øvelse(int øvelsesid){
	this.øvelsesid = øvelsesid;
	}

	public void refresh(Connection conn) {
		initialize(conn);
	}
	public void save(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			Resultset rs = stmt.executeQuery("update Øvelse set øvelsenavn="+navn+", øvelsebeskrivelse="+beskrivelse+", apparatid="+apparatid+"where øvelsesid="+øvelsesid);
		} catch(Exception e) {
			System.out.println("db error during update of øvelse="+e);
			return;
		}
	}
	public int getØvelsesid() {
		return øvelsesid;
		}

	public void initialize(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select øvelsenavn, øvelsebeskrivelse, apparatid from Øvelse where øvelsesid="+øvelsesid);
			while(rs.next()) {
				navn = rs.getString("øvelsenavn");
				beskrivelse = rs.getString("øvelsebeskrivelse");
				apparatid = rs.getInt("apparatid");
			}
		} catch( Exception e) {
			System.out.println("db error during select of øvelse= "+e);
		}
	}
}




