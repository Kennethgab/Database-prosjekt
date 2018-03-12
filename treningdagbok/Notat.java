import java.sql.*;

import treningdagbok.ActiveDomainObject;

public class Notat extends ActiveDomainObject {
    private int løpenr;
    private String treningsformål;
    private String øktbeskrivelse;
    private String resultat;


    public Notat (int løpenr) {
        this.løpenr = løpenr;
    }

    public int getLøpenr () {
        return løpenr;
    }

    public String getTreningsformål() {
		return treningsformål;
	}

	public void setTreningsformål(String treningsformål) {
		this.treningsformål = treningsformål;
	}

	public String getØktbeskrivelse() {
		return øktbeskrivelse;
	}

	public void setØktbeskrivelse(String øktbeskrivelse) {
		this.øktbeskrivelse = øktbeskrivelse;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}


	public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT "+
                "løpenr, treningsformål, øktbeskrivelse, resultat"+
                " WHERE løpenr="+løpenr);
            while (rs.next()) {
                løpenr = rs.getInt("løpenr");
                treningsformål = rs.getString("treningsformål");
                øktbeskrivelse = rs.getString("øktbeskrivelse");
                resultat = rs.getString("resultat");
            }
        } catch (Exception e) {
            System.out.println("DB-feil ved select av notat = "+e);
            return;
        }
    }
	
	public void refresh (Connection conn) {
		initialize(conn);
	}
	
    public void save (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("UPDATE Notat SET "+
                "treningsformål="+treningsformål+", øktbeskrivelse="+øktbeskrivelse+", resultat="+resultat+
                "WHERE løpenr="+løpenr);
        } catch (Exception e) {
            System.out.println("DB-feil ved oppdatering av notat = "+e);
            return;
        }
    }

}