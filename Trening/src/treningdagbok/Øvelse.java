package treningdagbok;
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

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}


	public int getApparatid() {
		return apparatid;
	}


	public void setApparatid(int apparatid) {
		this.apparatid = apparatid;
	}

	public int getØvelsesid() {
		return øvelsesid;
	}

	public void refresh(Connection conn) {
		initialize(conn);
	}
	public void save(Connection conn) {
		String error = "";
		try {
			Statement stmt = conn.createStatement();
			String nameString = StaticMethods.toQuote(navn);
			String beskrivelseString = StaticMethods.toQuote(beskrivelse);
			try {
				if( apparatid == 0) {
					stmt.executeUpdate("insert into Øvelse values ("+øvelsesid+","+ nameString + ","+ beskrivelseString +","+null+")");
					return;
				}
				stmt.executeUpdate("insert into Øvelse values ("+øvelsesid+","+ nameString + ","+ beskrivelseString + "," + apparatid+")");
				return;

			}	catch(Exception e) {
				error +=e;
				if (apparatid == 0) {
					stmt.executeUpdate("update Øvelse set øvelsenavn="+nameString+", øvelsebeskrivelse="+beskrivelseString+", apparatid="+null+"where øvelsesid="+øvelsesid+")");
				}
				stmt.executeUpdate("update Øvelse set øvelsenavn="+nameString+", øvelsebeskrivelse="+beskrivelseString+", apparatid="+apparatid+"where øvelsesid="+øvelsesid+")");
	 	    }
	} catch(Exception e) {
		throw new IllegalStateException("db error during update of Øvelse\n\t\tinsert error: " + error + " \n\t\t\tupdate error: "+e);
}
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

	@Override
	public String toString() {
		return "ØvelsesID: "+this.øvelsesid+"\nNavn: "+this.navn+"\nBeskrivelse: "+this.beskrivelse+"\nApparatID: " + this.apparatid;
	}
}




