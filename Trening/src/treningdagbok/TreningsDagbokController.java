package treningdagbok;

import java.sql.*;
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


	private DBConn conn;
	private Apparat a;
	private Øvelse o;
	private ØvelsesGruppe og;

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
		String output = "";
		for (Øvelse o : list) {
			output += "øvelsesid: " + o.getØvelsesid() + "\nnavn: " + o.getNavn() + "\nbeskrivelse: " + o.getBeskrivelse() + "\napparatid:" + o.getApparatid()+"\n\n";
		}
		debugTextArea.setText(output);
	}
	
	@FXML public void getOvelserForGruppe() {
		List<Øvelse> list = SQLQuery.getOvelserInGroup(conn.conn, Integer.parseInt(getGrupperOvelseIDText.getText()));
		debugList(list);
	}

	@FXML
	public void newApparat() {
		try {
			Apparat a = new Apparat(Integer.parseInt(apparatIDText.getText()));
			this.a = a;
			a.setNavn(apparatNavnText.getText());
			a.setBeskrivelse(apparatBeskrivelseText.getText());
			a.refresh(conn.conn);
			a.save(conn.conn);
			update();
		}
		catch (Exception e) {
			System.out.println("Error med å lage nytt apparat: "+e);
		}
	}

	@FXML
	public void newOvelse() {
		try {
		Øvelse o = new Øvelse(Integer.parseInt(ovelseIDText.getText()));
		this.o = o;
		o.setNavn(ovelseNavnText.getText());
		o.setBeskrivelse(ovelseBeskrivelseText.getText());
		o.setApparatid(Integer.parseInt(ovelseApparatIDText.getText()));
		o.save(conn.conn);
		refresh();
		} catch (Exception e) {
			System.out.println("error med å lage ny øvelse: "+e);
		}
		
	}
	
	@FXML
	public void newOvelsesGruppe() {
		try {
			ØvelsesGruppe og = new ØvelsesGruppe(Integer.parseInt(ovelseIDText.getText()));
			this.og = og;
			og.setBeskrivelse(this.øvelsesgruppeBeskrivelseText.getText());
			og.save(conn.conn);
			//refresh();
			} catch (Exception e) {
				System.out.println("error med å lage ny øvelsesgruppe: "+e);
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
			Øvelse øvelse = (Øvelse) o;
			s+=øvelse.getNavn() + "\n\n";
		}
		this.debugTextArea.setText(s);
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
	public void update() {
		refresh();
	}

}
