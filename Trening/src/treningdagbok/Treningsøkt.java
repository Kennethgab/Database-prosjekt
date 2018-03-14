package treningdagbok;
import java.sql.*;
import java.sql.Date;
import java.util.*;
//import sun.util.calendar.BaseCalendar.Date;
import treningdagbok.ActiveDomainObject;

public class Treningsøkt extends ActiveDomainObject {
    private int øktid;
   // private Date dato;
    private Timestamp tidspunkt;
    private int varighet;
    private int form;
    private int prestasjon;
	private String notat;

    public Treningsøkt(int øktid) {
        this.øktid = øktid;
    }

    public int getØktid() {
		return øktid;
	}

	public Timestamp getTidspunkt() {
		return tidspunkt;
	}

	public void setTidspunkt(Timestamp tidspunkt) {
		this.tidspunkt = tidspunkt;
	}

	public int getVarighet() {
		return varighet;
	}

	public void setVarighet(int varighet) {
		this.varighet = varighet;
	}

	public int getForm() {
		return form;
	}

	public void setForm(int form) {
		this.form = form;
	}

	public int getPrestasjon() {
		return prestasjon;
	}

	public void setPrestasjon(int prestasjon) {
		this.prestasjon = prestasjon;
	}

	public void setNotat(String notat) {
		this.notat = notat;
	}

	public String getNotat () {
		return this.notat;
	}

	public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT dato, tidspunkt, varighet, form, prestasjon, løpenr,notat " +
                 "FROM Treningsøkt WHERE øktid=" + øktid);
            while (rs.next()) {
                tidspunkt = rs.getTimestamp("tidspunkt");
                varighet = rs.getInt("varighet");
                form = rs.getInt("form");
                prestasjon = rs.getInt("prestasjon");
				notat = rs.getString("notat");
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
			String notatString = StaticMethods.toQuote(notat);
			//String datoString = StaticMethods.toQuote(dato.toString());
			String tidspunktString = StaticMethods.toQuote(tidspunkt.toString());
			try {
				stmt.executeUpdate("insert into Treningsøkt values("+øktid+","+varighet+","+tidspunktString+
				","+form+","+prestasjon+","+notatString+")");
				return;
			} catch(Exception e) {
			System.out.println("Error inserting: "+e);
			}
			stmt.executeUpdate("update Treningsøkt set tidspunkt="+tidspunktString+", varighet="
			+varighet+", form="+form+", prestasjon="+prestasjon+", notat="+notatString+ " where øktid=" + øktid);
		} catch(Exception e) {
			System.out.println("error updating Treningsøkt" + e);
		}
	}
    
    @Override
	public String toString() {
		return "ØktID: "+øktid + "\nVarighet: "+varighet+"\nTidspunkt: " + tidspunkt.toString() +"\nForm: "+form+"\nPrestasjon: "+prestasjon+"\nNotat: "+notat;
	}
}
