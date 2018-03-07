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