
import java.sql.*;
import java.util.*;


public class ØvelsesGruppe extends ActiveDomainObject {
	private int gruppeid;
<<<<<<< HEAD
	private int String beskrivelse;

	public ØvelsesGruppe(int gruppeid) {
		this.gruppeid = gruppeid
=======
	private String beskrivelse;

	public ØvelsesGruppe(int gruppeid) {
		this.gruppeid = gruppeid;
>>>>>>> 7ee6afb9e47e77079fc42b4a6d3eaa994df2cbac
	}

	public void initialize(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
<<<<<<< HEAD
			ResultSet rs = stmt.executeQuery("select beskrivelse from ØvelsesGruppe where gruppeid="+gruppeid);
=======
			ResultSet rs = stmt.executeQuery("select gruppebeskrivelse from ØvelsesGruppe where gruppeid="+gruppeid);
>>>>>>> 7ee6afb9e47e77079fc42b4a6d3eaa994df2cbac
			while(rs.next()) {
				beskrivelse = rs.getString("gruppebeskrivelse");
			}
		} catch ( Exception e) {
			System.out.println("db error during select of bruker= "+e);
			return;
		}

	}

	public void refresh(Connection conn) {
		initialize(conn);
	}

	public void save(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
<<<<<<< HEAD
			ResultSet rs = stmt.executeQuery("update ØvelsesGruppe set beskrivelse="+beskrivelse+"where gruppeid="+gruppeid);
=======
			ResultSet rs = stmt.executeQuery("update ØvelsesGruppe set gruppebeskrivelse="+beskrivelse+"where gruppeid="+gruppeid);
>>>>>>> 7ee6afb9e47e77079fc42b4a6d3eaa994df2cbac
			} catch(Exception e) {
			System.out.println("db error during update of øvelsesgruppe="+e);
			return;
			}
	}

<<<<<<< HEAD
=======
}

>>>>>>> 7ee6afb9e47e77079fc42b4a6d3eaa994df2cbac



