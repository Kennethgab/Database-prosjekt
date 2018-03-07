/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


//package avtalebok;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;

public class Apparat extends ActiveDomainObject {
    private int apparatid;
    private String beskrivelse;
    private String navn;


    public Apparat (int apparatid) {
        this.apparatid = apparatid;
    }
    
    public void setBeskrivelse(String beskrivelse) {
    	this.beskrivelse = beskrivelse;
    }
    public void setNavn(String navn) {
    	this.navn = navn;
    }
    public String getBeskrivelse() {
    	return beskrivelse;
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
            ResultSet rs = stmt.executeQuery("select navn, beskrivelse from Apparat where apparatid=" + apparatid);
            while (rs.next()) {
                navn =  rs.getString("navn");
                beskrivelse = rs.getString("beskrivelse");
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
            ResultSet rs = stmt.executeQuery("update Apparat set navn="+navn+", beskrivelse="+beskrivelse+"where apparatid="+apparatid);
        } catch (Exception e) {
            System.out.println("db error during update of apparat="+e);
            return;
        }
    }

}
