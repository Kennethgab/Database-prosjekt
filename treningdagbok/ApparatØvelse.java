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

public class Apparat�velse extends ActiveDomainObject {
    private int �velsesid, �ktid, antallkilo, antallsett;
    private String resultat;

    public Apparat�velse (int �velsesid, int �ktid) {
        this.�velsesid = �velsesid;
        this.�ktid = �ktid;
    }
    public void setResultat(String resultat) {
    	this.resultat = resultat;
    }
    public void setKilo(int kilo) {
    	this.antallkilo = kilo;
    }
    public void setSett(int sett) {
    	this.antallsett = sett;
    }
    public int get�velsesID () {
        return �velsesid;
    }
    public int get�ktID () {
        return �ktid;
    }
    public int getKilo() {
    	return antallkilo;
    }
    public int getSett() {
    	return antallsett;
    }
    public String getResultat() {
    	return resultat;
    }
 
    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select resultat, antallkilo, antallsett  from Apparat�velse where �velsesid="+ �velsesid +" and �ktid="+�ktid);
            while (rs.next()) {
                resultat =  rs.getString("resultat");
                antallkilo = rs.getInt("antallkilo");
                antallsett = rs.getInt("antallsett");
            }

        } catch (Exception e) {
            System.out.println("db error during select of apparat�velse= "+e);
            return;
        }

    }

    public void refresh (Connection conn) {
        initialize (conn);
    }

    public void save (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("update Apparat�velse set resultat="+resultat+", antallkilo="+antallkilo+", antallsett = "+ antallsett +" where �velsesid="+�velsesid+" and �ktid="+�ktid);
        } catch (Exception e) {
            System.out.println("db error during update of apparat�velse="+e);
            return;
        }
    }

}
