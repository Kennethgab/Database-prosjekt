package treningdagbok;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import treningdagbok.DBConn;
import treningdagbok.SQLConn;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TreningsDagbokController {
	//new apparat
	@FXML private Button newApparatButton;
	@FXML private TextField apparatIDText;
	@FXML private TextField apparatNavnText;
	@FXML private TextField apparatBeskrivelseText;
	@FXML private TextArea debugTextArea;
		//new øvelse
	@FXML private Button newOvelseButton;
	@FXML private TextField ovelseIDText;
	@FXML private TextField ovelseNavnText;
	@FXML private TextField ovelseBeskrivelseText;
	@FXML private TextField ovelseApparatIDText;
		//get øvelser som bruker apparatid
	@FXML private TextField getOvelserApparatID;
	@FXML private Button getOvelserButton;
		//new øvelsesgruppe
	@FXML private TextField øvelsesgruppeIDText;
	@FXML private TextField øvelsesgruppeBeskrivelseText;
	@FXML private Button newØvelsesgruppeButton;
	
	@FXML private TextField getGrupperOvelseIDText;
	@FXML private Button getGrupperButton;
		//legg øvelse i en gruppe
	@FXML private TextField ØvelseGruppeØvelseIDText;
	@FXML private TextField ØvelseGruppeGruppeIDText;
	@FXML private Button ØvelseGruppeButton;
	//getall funksjoner (knappene på bunnen av fxml)
	@FXML private Button getØvelserButton;
	@FXML private Button getApparatButton;
	@FXML private Button getØkterButton;
	@FXML private Button getAllGrupperButton;
	//get n siste økter
	@FXML private Button getnØkterButton;
	@FXML private TextField nØkterText;
	//make new treningsøkt
	@FXML private TextField treningsøktIDText;
	@FXML private TextField treningsøktVarighetText;
	@FXML private TextField treningsøktTidspunktText;
	@FXML private TextField treningsøktFormText;
	@FXML private TextField treningsøktPrestasjonText;
	@FXML private TextField treningsøktNotatText;
	@FXML private Button newTreningsøktButton;
	
	@FXML private ChoiceBox<String> typeØvelse;
	@FXML private TextField ØvelseØktØvelsesIDText;
	@FXML private TextField ØvelseØktØktIDText;
	@FXML private TextField ØvelseØktResultatText;
	@FXML private TextField ØvelseØktKiloText;
	@FXML private TextField ØvelseØktSettText;
	@FXML private Button addØvelseToØktButton;
		//usecase 3 ovelseresultat
	@FXML private TextField OvelseResultatIDText;
	@FXML private TextField OvelseResultatTS1Text;
	@FXML private TextField OvelseResultatTS2Text;
	@FXML private Button OvelseResultatButton;
	//connection to database
	private DBConn conn;

	@FXML
	private void initialize() {
		try {
			this.conn = new SQLConn();
			conn.connect();
		} catch (Exception e) {
			System.out.println("Error connecting to database: "+e);
			//e.printStackTrace();
		}
	}
	
	@FXML
	public void getOvelserForApparat() {
		List<Øvelse> list = SQLQuery.getOvelserTilApparat(conn.conn, Integer.parseInt(getOvelserApparatID.getText()));
		debug(list);
	}
	@FXML 
	public void getOvelserForGruppe() {
		List<Øvelse> list = SQLQuery.getOvelserInGroup(conn.conn, Integer.parseInt(getGrupperOvelseIDText.getText()));
		debug(list);
	}

	@FXML
	public void newApparat() {
		try {
			Apparat a = new Apparat(Integer.parseInt(apparatIDText.getText()));
			a.setNavn(apparatNavnText.getText());
			a.setBeskrivelse(apparatBeskrivelseText.getText());
			String s = a.toString();
			a.refresh(conn.conn);
	        if(!a.toString().equals(s)) {
	        	throw new IllegalStateException("Apparat med ID="+a.getApparatID()+" finnes allerede og er ulik input");
	        }
			a.save(conn.conn);
			debug("Apparat ble lagt til /eksisterer allerede:\n"+a.toString());
		}
		catch (Exception e) {
			String s = "Error med å lage nytt apparat: \n\t"+e;
			System.out.println(s);
			debug(s.toString());
		}
	}

	@FXML
	public void newOvelse() {
		try {
			Øvelse o = new Øvelse(Integer.parseInt(ovelseIDText.getText()));
			o.setNavn(ovelseNavnText.getText());
			o.setBeskrivelse(ovelseBeskrivelseText.getText());
			int apid = ovelseApparatIDText.getText().equals("") ? 0 : Integer.parseInt(ovelseApparatIDText.getText());
			o.setApparatid(apid);
			System.out.println("lol");
			o.save(conn.conn);
			debug(o.toString());
		} catch (Exception e) {
			System.out.println("error med å lage ny øvelse: "+e);
		}
	}
	
	@FXML
	public void newOvelsesGruppe() {
		try {
			ØvelsesGruppe og = new ØvelsesGruppe(Integer.parseInt(øvelsesgruppeIDText.getText()));
			og.setBeskrivelse(this.øvelsesgruppeBeskrivelseText.getText());
			og.save(conn.conn);
			debug(og.toString());
			} catch (Exception e) {
				System.out.println("error med å lage ny øvelsesgruppe: "+e);
			}
	}
	@FXML
	public void newTreningsøkt() {
		try {
			Treningsøkt t = new Treningsøkt(Integer.parseInt(treningsøktIDText.getText()));
			t.setVarighet(Integer.parseInt(this.treningsøktVarighetText.getText()));
			DateFormat formatter;
		    formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		    java.util.Date date = formatter.parse(this.treningsøktTidspunktText.getText());
		    java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
			t.setTidspunkt(timeStampDate);
			t.setForm(Integer.parseInt(this.treningsøktFormText.getText()));
			t.setPrestasjon(Integer.parseInt(this.treningsøktPrestasjonText.getText()));
			t.setNotat(this.treningsøktNotatText.getText());
			t.save(conn.conn);
			debug("treningsøkt added");
			} catch (Exception e) {
				String error = "error med å lage ny treningsøkt "+e;
				System.out.println(error);
				debug(error);
			}
	}
	@FXML
	public void addToGroup() {
		try {
			int øvelseid = Integer.parseInt(this.ØvelseGruppeØvelseIDText.getText());
			int gruppeid = Integer.parseInt(this.ØvelseGruppeGruppeIDText.getText());
			Statement stmt = conn.conn.createStatement();
			stmt.executeUpdate("insert into ØvelseTilhørerGruppe values ("+gruppeid+","+ øvelseid+")");
		}catch(Exception e) {
			System.out.println("Error inserting øvelse in group: "+e);
	 	    }
		}
	@FXML
	public void addØvelseToØkt() {
		try {
			int øvelseid = Integer.parseInt(this.ØvelseØktØvelsesIDText.getText());
			int øktid = Integer.parseInt(this.ØvelseØktØktIDText.getText());
			String resultat = StaticMethods.toQuote(this.ØvelseØktResultatText.getText());
			Statement stmt = conn.conn.createStatement();
			System.out.println(typeØvelse.getValue());
			if(typeØvelse.getValue().equals("friØvelse")) { //if friøvelse
				Statement stmta = conn.conn.createStatement();
				ResultSet rs = stmta.executeQuery("select apparatid from Øvelse where øvelsesid ="+øvelseid);
				rs.next();
				int apparatid = rs.getInt("apparatid"); 
				if(apparatid != 0) {//throws exception if apparat != null, friøvelse cant have apparat
					throw new IllegalStateException("Cant add øvelse with apparatid as a friøvelse");//throws exception if apparat, friøvelse cant have apparat
				}
				stmt.executeUpdate("insert into FriØvelse values ("+øvelseid+","+ øktid+","+resultat+")");
			} else { //if apparatøvelse
				Statement stmta = conn.conn.createStatement();
				ResultSet rs = stmta.executeQuery("select apparatid from Øvelse where øvelsesid ="+øvelseid);
				rs.next();
				int apparatid = rs.getInt("apparatid"); //throws exception if no apparat, apparatøvelse needs apparat
				if(apparatid == 0) {
					throw new IllegalStateException("Prøvde å legge til apparatøvelse med øvelse uten apparat");
				}
				if (this.ØvelseØktKiloText.getText().equals("") || this.ØvelseØktSettText.getText().equals("") ) {
					stmt.executeUpdate("insert into ApparatØvelse values ("+øvelseid+","+ øktid+","+null+","+null+","+resultat+")");
				} else {
					int kilo = Integer.parseInt(this.ØvelseØktKiloText.getText());
					int sett = Integer.parseInt(this.ØvelseØktSettText.getText());
					stmt.executeUpdate("insert into ApparatØvelse values ("+øvelseid+","+ øktid+","+kilo+","+sett+","+resultat+")");
				}
			}
		}catch(Exception e) {
			System.out.println("Error adding øvelse to økt: "+e);
		}
	}
	@FXML
	public void getnØkter() {
		try {
			int n= Integer.parseInt(this.nØkterText.getText());
			debug(SQLQuery.getNSisteØkter(conn.conn, n));
		}catch(Exception e) {
			System.out.println("Error fetching n siste økter: "+e);
 	    }
	}
	@FXML
	public void getOvelseResultat() {
		try {
			int øvelsesid= Integer.parseInt(this.OvelseResultatIDText.getText());
			DateFormat formatter;
		    formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		    java.util.Date date = formatter.parse(this.OvelseResultatTS1Text.getText());
		    java.sql.Timestamp TS1 = new Timestamp(date.getTime());
		    date = formatter.parse(this.OvelseResultatTS2Text.getText());
		    java.sql.Timestamp TS2 = new Timestamp(date.getTime());
			debug(SQLQuery.getOvelseResultat(conn.conn, øvelsesid, TS1,TS2));
		}catch(Exception e) {
			String s = "Error med getOvelseResultat: \n\t"+e;
			System.out.println(s);
			debug(s);
 	    }
	}
	
	/*public void debug(Object o) {
		try {
			this.debugTextArea.setText(o.toString());
		}
		catch (Exception e) {
		}
	}
	*/
	public void debug(String s) {
		this.debugTextArea.setText(s);
	}
	public void debug(List<?> l) {
		String s = "";
		for (Object o : l) {
			s+=o.toString() + "\n----------------------\n";
		}
		debugTextArea.setText(s);
	}
	public void debugØkt(List<Treningsøkt> l) { //under construction
		String s = "";
		for (Treningsøkt t : l) {
			s+=t.toString() + "\n";
			try {
				String toString = "Friøvelser:\n";
				Statement stmt = conn.conn.createStatement();
				ResultSet rs = stmt.executeQuery("select O.øvelsesid, O.øvelsenavn, O.øvelsebeskrivelse,O.apparatid,F.øktid,F.resultat from FriØvelse AS F JOIN Øvelse AS O on F.øvelsesid = O.øvelsesID WHERE F.øktid = "+t.getØktid());
				//FriØvelse o;
				//List<FriØvelse> list = new ArrayList<FriØvelse>();
				boolean first = true;
				while(rs.next()) {
					if(first == false) {
						toString+="*********\n";
					}
					first = false;
					toString += "\tØvelsesID: "+rs.getInt("øvelsesid")+"\n";
					toString += "\tØvelsesnavn: "+rs.getString("øvelsenavn")+"\n";
					toString += "\tØvelsesbeskrivelse: "+rs.getString("øvelsebeskrivelse")+"\n";
					toString += "\tApparatID: "+rs.getString("apparatid")+"\n";
					toString += "\tResultat: "+rs.getString("resultat")+"\n";
					toString += "\tØktID: "+rs.getString("øktid")+"\n";
				}
				s+=toString;
			} catch(Exception e) {
				System.out.println("Error getting øktinfo: "+e);
			}
			try {
				String toString = "Apparatøvelser:\n";
				Statement stmt = conn.conn.createStatement();
				ResultSet rs = stmt.executeQuery("select O.øvelsesid, O.øvelsenavn, O.øvelsebeskrivelse,O.apparatid,F.øktid,F.resultat,F.antallkilo,F.antallsett from ApparatØvelse AS F JOIN Øvelse AS O on F.øvelsesid = O.øvelsesID WHERE F.øktid = "+t.getØktid());
				//FriØvelse o;
				//List<FriØvelse> list = new ArrayList<FriØvelse>();
				boolean first = true;
				while(rs.next()) {
					if(first == false) {
						toString+="*********\n";
					}
					first = false;
					toString += "\tØvelsesID: "+rs.getInt("øvelsesid")+"\n";
					toString += "\tØvelsesnavn: "+rs.getString("øvelsenavn")+"\n";
					toString += "\tØvelsesbeskrivelse: "+rs.getString("øvelsebeskrivelse")+"\n";
					toString += "\tApparatID: "+rs.getString("apparatid")+"\n";
					toString += "\tResultat: "+rs.getString("resultat")+"\n";
					toString += "\tKilo: "+rs.getString("antallkilo")+"\n";
					toString += "\tSett: "+rs.getString("antallsett")+"\n";
					toString += "\tØktID: "+rs.getString("øktid")+"\n";
				}
				s+=toString;
			} catch(Exception e) {
				System.out.println("Error getting øktinfo: "+e);
			}
			s+="\n----------------------\n";
		}
		debugTextArea.setText(s);
	}
	@FXML
	public void getØvelser() {
		try {
			Statement stmt = conn.conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Øvelse");
			Øvelse o;
			List<Øvelse> l = new ArrayList<Øvelse>();
			while(rs.next()) {
				o = new Øvelse(rs.getInt("øvelsesid"));
				o.setNavn(rs.getString("øvelsenavn"));
				o.setBeskrivelse(rs.getString("øvelsebeskrivelse"));
				o.setApparatid(rs.getInt("apparatid"));
				l.add(o);
			}
			debug(l);
			}catch(Exception e) {
				System.out.println("Error fetching all øvelser: "+e);
	 	    }
	}
	@FXML
	public void getApparat() {
		try {
			Statement stmt = conn.conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Apparat");
			List<Apparat> l = new ArrayList<Apparat>();
			Apparat a;
			while(rs.next()) {
				a = new Apparat(rs.getInt("apparatid"));
				a.setBeskrivelse(rs.getString("apparatbeskrivelse"));
				a.setNavn(rs.getString("apparatnavn"));
				l.add(a);
			}
			debug(l);
			}catch(Exception e) {
				System.out.println("Error fetching all apparater: "+e);
	 	    }
	}
	@FXML
	public void getØkter() {
		try {
		Statement stmt = conn.conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from Treningsøkt");
		List<Treningsøkt> l = new ArrayList<Treningsøkt>();
		Treningsøkt t;
		while(rs.next()) {
			t = new Treningsøkt(rs.getInt("øktid"));
			t.setVarighet(rs.getInt("varighet"));
			t.setTidspunkt(rs.getTimestamp("tidspunkt"));
			t.setForm(rs.getInt("form"));
			t.setPrestasjon(rs.getInt("prestasjon"));
			t.setNotat(rs.getString("notat"));
			l.add(t);
		}
		debugØkt(l);
		}catch(Exception e) {
			System.out.println("Error fetching all apparater: "+e);
 	    }
	}
	@FXML
	public void getGrupper() {
		try {
			debug(SQLQuery.getGrupper(conn.conn));
			/*Statement stmt = conn.conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ØvelsesGruppe");
			ØvelsesGruppe g;
			List<ØvelsesGruppe> l = new ArrayList<ØvelsesGruppe>();
			while(rs.next()) {
				g = new ØvelsesGruppe(rs.getInt("gruppeid"));
				g.setBeskrivelse(rs.getString("gruppebeskrivelse"));
				l.add(g);
			}
			debug(l);*/
			}catch(Exception e) {
				System.out.println("Error i getGruppe:\n\t"+e);
	 	    }
	}



}
