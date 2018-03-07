import java.sql.*;
import java.sql.Date;
import java.util.*;

//import sun.util.calendar.BaseCalendar.Date;
import treningsdagbok.ActiveDomainObject;

public class Trenings�kt extends ActiveDomainObject {
    private int �ktid;
    private Date dato;
    private Time tidspunkt;
    private int varighet;
    private int form;
    private int prestasjon;
    private int l�penr;

    public Trenings�kt (int �ktid) {
        this.�ktid = �ktid;
    }
   
    public int get�ktid() {
		return �ktid;
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

	public int getL�penr() {
		return l�penr;
	}

	public void setL�penr(int l�penr) {
		this.l�penr = l�penr;
	}

	public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT dato, tidspunkt, varighet, form, prestasjon, løpenr " +
                 "FROM Treningsøkt WHERE øktid=" + �ktid);
            while (rs.next()) {
                dato = rs.getDate("dato");
                tidspunkt = rs.getTime("tidspunkt");
                varighet = rs.getInt("varighet");
                form = rs.getInt("form");
                prestasjon = rs.getInt("prestasjon");
                l�penr = rs.getInt("løpenr");
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
            ResultSet rs = stmt.executeQuery("UPDATE Trenings�kt set" +
            "dato="+dato+", tidspunkt="+tidspunkt+", varighet="+varighet+", form="+form+", prestasjon="+prestasjon+", l�penr=" +l�penr+
            " WHERE �ktid="+�ktid);
        } catch (Exception e) {
            System.out.println("DB-feil ved oppdatering av �kt = "+e);
            return;
        }
    }

	

}