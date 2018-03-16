package treningdagbok;
import java.sql.*;
import java.util.*;

public class Apparat extends ActiveDomainObject {
    private int apparatid;
    private String apparatbeskrivelse;
    private String navn;


    public Apparat (int apparatid) {
        this.apparatid = apparatid;
    }

    public void setBeskrivelse(String apparatbeskrivelse) {
    	this.apparatbeskrivelse = apparatbeskrivelse;
    }
    public void setNavn(String navn) {
    	this.navn = navn;
    }
    public String getBeskrivelse() {
    	return apparatbeskrivelse;
    }
    public int getApparatID () {
        return apparatid;
    }
    public String getNavn() {
    	return this.navn;
    }
    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select apparatnavn, apparatbeskrivelse from Apparat where apparatid=" + apparatid);
            while (rs.next()) {
                navn =  rs.getString("apparatnavn");
                apparatbeskrivelse = rs.getString("apparatbeskrivelse");
            }

        } catch (Exception e) {
            throw new IllegalStateException("db error during select of bruker= "+e);
        }

    }

    public void refresh (Connection conn) {
        initialize (conn);
    }

    public void save (Connection conn) {
		String error = "";
        try {
        	Statement stmt = conn.createStatement();
        	String nameString = StaticMethods.toQuote(navn);
    		String beskrivelseString = StaticMethods.toQuote(apparatbeskrivelse);
        	try {
        		stmt.executeUpdate("insert into Apparat values ("+apparatid+","+ StaticMethods.toQuote(navn) +","+StaticMethods.toQuote(apparatbeskrivelse)+")");
        		return;
        	} catch(Exception e) {
        		error +=e;
        		stmt.executeUpdate("update Apparat set apparatnavn="+nameString+", apparatbeskrivelse="+beskrivelseString+"where apparatid="+apparatid);
        	}
        } catch (Exception e) {
            throw new IllegalStateException("db error during update of apparat\n\t\tinsert error: " + error + " \n\t\t\tupdate error: "+e);
        }
    }

	@Override
	public String toString() {
		return "ApparatID: " + this.apparatid + "\nNavn: "+ navn + "\nBeskrivelse: " + this.apparatbeskrivelse;
	}

}
