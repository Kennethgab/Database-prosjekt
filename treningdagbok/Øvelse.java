public class Øvelse extends ActiveDomainObject {
	private String navn;
	private String beskrivelse;
	private int øvelsesid;
	private int apperatid;




	public void refresh(Connection conn) {
		initialize(conn);
	}
	public void save(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			Resultset rs = stmt.executeQuery("update Øvelse:");
		} catch(Exception e) {
			System.out.println("db error during update of øvelse="+e));
			return;
		}
	}
	public int getØvelsesid() {
		return øvelsesid;
		}

	public void initialize(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select navn, beskrivelse, apperatid from Øvelse where øvelsesid="+øvelsesid);
			navn = rs.getString("navn");
			beskrivelse = rs.getString("beskrivelse");
}




