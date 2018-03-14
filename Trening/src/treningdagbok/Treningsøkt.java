package treningdagbok;
import java.sql.*;
import java.sql.Date;
import java.util.*;
//import sun.util.calendar.BaseCalendar.Date;
import treningdagbok.ActiveDomainObject;

public class Treningsøkt extends ActiveDomainObject {
    private int øktid;
    private Date dato;
    private Time tidspunkt;
    private int varighet;
    private int form;
    private int prestasjon;
    private int løpenr;

    public Treningsøkt(int øktid) {
        this.øktid = øktid;
    }

    public int getØktid() {
		return øktid;
	}

	public Date getDato() {
		return dato;
	}

	public void setDato(Date dato) {
		this.dato = dato;
	}

	public Time getTidspunkt() {
		return tidspunkt;
	}

	public void setTidspunkt(Time tidspunkt) {
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

	public int getLøpenr() {
		return løpenr;
	}

	public void setLøpenr(int løpenr) {
		this.løpenr = løpenr;
	}

	public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT dato, tidspunkt, varighet, form, prestasjon, løpenr " +
                 "FROM Treningsøkt WHERE øktid=" + øktid);
            while (rs.next()) {
                dato = rs.getDate("dato");
                tidspunkt = rs.getTime("tidspunkt");
                varighet = rs.getInt("varighet");
                form = rs.getInt("form");
                prestasjon = rs.getInt("prestasjon");
                løpenr = rs.getInt("løpenr");
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
			String datoString = StaticMethods.toQuote(dato.toString());
			String tidspunktString = StaticMethods.toQuote(tidspunkt.toString());
			try {
				stmt.executeUpdate("insert into Treningsøkt values("+øktid+","+datoString+","+varighet+","+tidspunktString+
				","+form+","+prestasjon+","+løpenr+")");
				return;
			} catch(Exception e) {
			System.out.println("Error inserting: "+e);
			}
			stmt.executeUpdate("update Treningsøkt set dato="+datoString+", tidspunkt="+tidspunktString+", varighet="
			+varighet+", form="+form+", prestasjon="+prestasjon+", løpenr="+løpenr+"where øktid=" + øktid);
		} catch(Exception e) {
			System.out.println("error updating Treningsøkt" + e);
		}
	}
}
