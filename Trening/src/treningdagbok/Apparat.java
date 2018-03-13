/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package treningdagbok;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;

public class Apparat extends ActiveDomainObject {
    private int apparatid;
    private String apparatbeskrivelse;
    private String navn;


    public Apparat (int apparatid) {
        this.apparatid = apparatid;
    }

    public void setBeskrivelse(String apparatbeskrivelse) {
    	this.apparatbeskrivelse = apparatbeskrivelse;
    }
    public void setNavn(String navn) {
    	this.navn = navn;
    }
    public String getBeskrivelse() {
    	return apparatbeskrivelse;
    }
    public int getApparatID () {
        return apparatid;
    }
    public String getNavn() {
    	return this.navn;
    }
    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select apparatnavn, apparatbeskrivelse from Apparat where apparatid=" + apparatid);
            while (rs.next()) {
                navn =  rs.getString("apparatnavn");
                System.out.println(navn);
                apparatbeskrivelse = rs.getString("apparatbeskrivelse");
            }

        } catch (Exception e) {
            System.out.println("db error during select of bruker= "+e);
            return;
        }

    }

    public void refresh (Connection conn) {
        initialize (conn);
    }

    public void save (Connection conn) {
        try {
        	Statement stmt = conn.createStatement();
        	String nameString = "\"" +navn+"\"";
    		String beskrivelseString = "\"" + apparatbeskrivelse+"\"";
        	try {
        		stmt.executeUpdate("insert into Apparat values ("+apparatid+","+ nameString +","+beskrivelseString+")");
        		return;
        	} catch(Exception e) {
        		System.out.println("Error inserting: "+e);
        	}
            stmt.executeUpdate("update Apparat set apparatnavn="+nameString+", apparatbeskrivelse="+beskrivelseString+"where apparatid="+apparatid);
        } catch (Exception e) {
            System.out.println("db error during update of apparat="+e);
            return;
        }
    }

}
