import java.sql.*;

import treningdagbok.ActiveDomainObject;

public class Notat extends ActiveDomainObject {
    private int løpeNr;
    private String treningsformål;
    private String øktbeskrivelse;
    private String resultat;


    public Notat (int løpeNr) {
        this.løpeNr = løpeNr;
    }

    public int getLøpeNr () {
        return løpeNr;
    }

    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT "+
                "løpeNr, treningsformål, øktbeskrivelse, resultat"+
                " WHERE løpeNr="+løpeNr);
            while (rs.next()) {
                løpeNr = rs.getInt("løpeNr");
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
                "WHERE løpeNr="+løpeNr);
        } catch (Exception e) {
            System.out.println("DB-feil ved oppdatering av notat = "+e);
            return;
        }
    }

}