package treningdagbok;

import java.sql.*;
import java.util.List;

import treningdagbok.DBConn;
import treningdagbok.SQLConn;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TreningsDagbokController {

	@FXML private Button newApparatButton;
	@FXML private TextField apparatIDText;
	@FXML private TextField apparatNavnText;
	@FXML private TextField apparatBeskrivelseText;
	@FXML private TextArea apparatTextArea;

	@FXML private Button updateButton;

	@FXML private Button newOvelseButton;
	@FXML private TextField ovelseIDText;
	@FXML private TextField ovelseNavnText;
	@FXML private TextField ovelseBeskrivelseText;
	@FXML private TextField ovelseApparatIDText;
	
	@FXML private TextField getOvelserApparatID;
	@FXML private Button getOvelserButton;


	private DBConn conn;
	private Apparat a;
	private Øvelse o;

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
			output += "øvelsesid: " + o.getØvelsesid() + "\nnavn: " + o.getNavn() + "\nbeskrivelse: " + o.getBeskrivelse() + "\napparatid:" + o.getApparatid();
		}
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
		o.setNavn(apparatNavnText.getText());
		o.setBeskrivelse(ovelseBeskrivelseText.getText());
		o.setApparatid(Integer.parseInt(ovelseApparatIDText.getText()));
		o.save(conn.conn);
		System.out.println("update?");
		refresh();
		System.out.println("update done?");
		} catch (Exception e) {
			System.out.println("error med å lage ny øvelse: "+e);
		}
		
	}
	
	public void refresh() {
		String s = "";
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
		this.apparatTextArea.setText(s);
		
	}

	@FXML
	public void update() {
		refresh();
	}

}
