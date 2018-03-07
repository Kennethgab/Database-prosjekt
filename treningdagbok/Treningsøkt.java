import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

import sun.util.calendar.BaseCalendar.Date;
import treningsdagbok.ActiveDomainObject;

public class Treningsøkt extends ActiveDomainObject {
    private int øktId;
    private Date dato;
    private Time tidspunkt;
    private int varighet;
    private int form;
    private int prestasjon;
    private int løpeNr;

    public Treningsøkt (int øktId) {
        this.øktId = øktId;
    }

    public int getØktId () {
        return øktId;
    }

    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT dato, tidspunkt, varighet, form, prestasjon, løpeNr " +
                 "FROM Treningsøkt WHERE øktId=" + øktId);
            while (rs.next()) {
                dato = rs.getDate("dato");
                tidspunkt = rs.getTime("tidspunkt");
                varighet = rs.getInt("varighet");
                form = rs.getInt("form");
                prestasjon = rs.getInt("prestasjon");
                løpeNr = rs.getInt("løpeNr");
            }
        } catch (Exception e) {
            System.out.println("DB-feil ved select av bruker = " + e);
            return;
        }
    }
    
    public void refresh (Connection conn) { 
        initialize(conn);
    }

    public void save (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("UPDATE Treningsøkt set" +
            "dato="+dato+", tidspunkt="+tidspunkt+", varighet="+varighet+", form="+form+", prestasjon="+prestasjon+", løpeNr=" +løpeNr+
            " WHERE øktId="+øktId);
        } catch (Exception e) {
            System.out.println("DB-feil ved oppdatering av økt = "+e);
            return;
        }
    }

}