import java.sql.*;
import java.util.*;

import treningdagbok.ActiveDomainObject;

public class FriØvelse extends ActiveDomainObject {
    private int øvelsesid;
    private int øktid;
    private String resultat;

    public FriØvelse (int øvelsesid, int øktid) {
        this.øktid = øktid;
        this.øvelsesid = øvelsesid;
    }
    

    public int getØvelsesid() {
		return øvelsesid;
	}


	public void setØvelsesid(int øvelsesid) {
		this.øvelsesid = øvelsesid;
	}


	public int getØktid() {
		return øktid;
	}


	public void setØktid(int øktid) {
		this.øktid = øktid;
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
            ResultSet rs = stmt.executeQuery("SELECT øvelsesid, øktid, resultat "+
                "FROM FriØvelse WHERE øktid="+øktid+" AND øvelsesid="+øvelsesid);
            while (rs.next()) {
                øktid = rs.getInt("øktid");
                øvelsesid = rs.getInt("øvelsesid");
            }
        } catch (Exception e) {
            System.out.println("DB-feil ved select av FriØvelse = "+e);
            return;
        }
    }

    public void refresh (Connection conn) {
        initialize(conn);
    }

    public void save (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("UPDATE FriØvelse SET"+
                "resultat="+resultat+" WHERE øvelsesid="+øvelsesid+" AND øktid="+øktid);
        } catch (Exception e) {
            System.out.println("DB-feil ved opdatering av FriØvelse = "+e);
            return;
        }
    }


}