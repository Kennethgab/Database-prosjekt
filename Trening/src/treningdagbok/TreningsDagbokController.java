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
		//refresh
	@FXML private Button updateButton;
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
	//get n siste økter
	@FXML private Button getnØkterButton;
	@FXML private TextField nØkterText;
	
	@FXML private TextField treningsøktIDText;
	@FXML private TextField treningsøktVarighetText;
	@FXML private TextField treningsøktTidspunktText;
	@FXML private TextField treningsøktFormText;
	@FXML private TextField treningsøktPrestasjonText;
	@FXML private TextField treningsøktNotatText;
	@FXML private Button newTreningsøktButton;
	
	


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
		debugList(list);
		//String output = "";
		//for (Øvelse o : list) {
		//	output += "øvelsesid: " + o.getØvelsesid() + "\nnavn: " + o.getNavn() + "\nbeskrivelse: " + o.getBeskrivelse() + "\napparatid:" + o.getApparatid()+"\n\n";
		//}
		//debugTextArea.setText(output);
	}
	
	@FXML public void getOvelserForGruppe() {
		List<Øvelse> list = SQLQuery.getOvelserInGroup(conn.conn, Integer.parseInt(getGrupperOvelseIDText.getText()));
		debugList(list);
	}

	@FXML
	public void newApparat() {
		try {
			Apparat a = new Apparat(Integer.parseInt(apparatIDText.getText()));
			a.setNavn(apparatNavnText.getText());
			a.setBeskrivelse(apparatBeskrivelseText.getText());
			a.refresh(conn.conn);
			a.save(conn.conn);
			debugObject(a);
		}
		catch (Exception e) {
			System.out.println("Error med å lage nytt apparat: "+e);
		}
	}

	@FXML
	public void newOvelse() {
		try {
		Øvelse o = new Øvelse(Integer.parseInt(ovelseIDText.getText()));
		o.setNavn(ovelseNavnText.getText());
		o.setBeskrivelse(ovelseBeskrivelseText.getText());
		o.setApparatid(Integer.parseInt(ovelseApparatIDText.getText()));
		o.save(conn.conn);
		debugObject(o);
		} catch (Exception e) {
			System.out.println("error med å lage ny øvelse: "+e);
		}
		
	}
	
	@FXML
	public void newOvelsesGruppe() {
		try {
			ØvelsesGruppe og = new ØvelsesGruppe(Integer.parseInt(ovelseIDText.getText()));
			og.setBeskrivelse(this.øvelsesgruppeBeskrivelseText.getText());
			og.save(conn.conn);
			debugObject(og);
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
		    formatter = new SimpleDateFormat("dd/MM/yyyy");
		    java.util.Date date = formatter.parse(this.treningsøktTidspunktText.getText());
		    java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
			t.setTidspunkt(timeStampDate);
			t.setForm(Integer.parseInt(this.treningsøktFormText.getText()));
			t.setPrestasjon(Integer.parseInt(this.treningsøktPrestasjonText.getText()));
			t.setNotat(this.treningsøktNotatText.getText());
			t.save(conn.conn);
			debugObject(t);
			} catch (Exception e) {
				System.out.println("error med å lage ny treningsøkt "+e);
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
	public void getnØkter() {
		try {
			int n= Integer.parseInt(this.nØkterText.getText());
			debugList(SQLQuery.getNSisteØkter(conn.conn, n));
		}catch(Exception e) {
			System.out.println("Error fetching n siste økter: "+e);
 	    }
	}
			
	
	public void debugObject(Object o) {
		try {
			this.debugTextArea.setText(o.toString());
		}
		catch (Exception e) {
			
		}
	}
	
	public void debugList(List<?> l) {
		String s = "";
		for (Object o : l) {
			s+=o.toString() + "\n----------------------\n";
		}
		debugTextArea.setText(s);
	}
	
	public void refresh() {
	 /*	String s = "";
		try {
			s += a.getApparatID()+"";
			s += "\n"+a.getNavn();
			s+= "\n"+a.getBeskrivelse();
		} catch (Exception e) {
			System.out.println("error updating a: " + e);
		}
		try {
			s += o.getØvelsesid()+"";
			s += "\n"+o.getNavn();
			s+= "\n"+o.getBeskrivelse();
			s+= "\n"+ o.getApparatid();
			
		} catch (Exception e) {
			System.out.println("error updating o: "+e);
			
		}
		this.debugTextArea.setText(s);*/
		
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
			debugList(l);
			}catch(Exception e) {
				System.out.println("Error fetching all øvelser: "+e);
	 	    }
	}
	@FXML
	public void getApparat() {
		try {
			System.out.println("getapparat");
			Statement stmt = conn.conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Apparat");
			List<Apparat> l = new ArrayList<Apparat>();
			Apparat a;
			System.out.println("Q");
			while(rs.next()) {
				a = new Apparat(rs.getInt("apparatid"));
				a.setBeskrivelse(rs.getString("apparatbeskrivelse"));
				a.setNavn(rs.getString("apparatnavn"));
				l.add(a);
			}
			debugList(l);
			}catch(Exception e) {
				System.out.println("Error fetching all apparater: "+e);
	 	    }
	}
	@FXML
	public void getØkter() {
		try {
			int øvelseid = Integer.parseInt(this.ØvelseGruppeØvelseIDText.getText());
			int gruppeid = Integer.parseInt(this.ØvelseGruppeGruppeIDText.getText());
			Statement stmt = conn.conn.createStatement();
			ResultSet rs = stmt.executeQuery("insert into ØvelseTilhørerGruppe values ("+gruppeid+","+ øvelseid+")");
			while(rs.next()) {
				
			}
			}catch(Exception e) {
				System.out.println("Error inserting øvelse in group: "+e);
	 	    }
	}
	@FXML
	public void getGrupper() {
		try {
			int gruppeid = Integer.parseInt(this.ØvelseGruppeGruppeIDText.getText());
			Statement stmt = conn.conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ØvelseGruppe");
			while(rs.next()) {
				
			}
			}catch(Exception e) {
				System.out.println("Error inserting øvelse in group: "+e);
	 	    }
	}


	@FXML
	public void update() {
		refresh();
	}

}
