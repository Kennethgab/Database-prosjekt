/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


//package avtalebok;

/**
 *
 * @author Simon
 */

import java.sql.*;
import java.util.*;

public class ApparatØvelse extends ActiveDomainObject {
    private int øvelsesid, øktid, antallkilo, antallsett;
    private String resultat;

    public ApparatØvelse (int øvelsesid, int øktid) {
        this.øvelsesid = øvelsesid;
        this.øktid = øktid;
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
    public int getØvelsesID () {
        return øvelsesid;
    }
    public int getØktID () {
        return øktid;
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
            ResultSet rs = stmt.executeQuery("select resultat, antallkilo, antallsett  from Apparat�velse where øvelsesid="+ øvelsesid +" and øktid="+øktid);
            while (rs.next()) {
                resultat =  rs.getString("resultat");
                antallkilo = rs.getInt("antallkilo");
                antallsett = rs.getInt("antallsett");
            }

        } catch (Exception e) {
            System.out.println("db error during select of apparatøvelse= "+e);
            return;
        }

    }

    public void refresh (Connection conn) {
        initialize (conn);
    }

    public void save (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("update Apparatøvelse set resultat="+resultat+", antallkilo="+antallkilo+", antallsett = "+ antallsett +" where øvelsesid="+øvelsesid+" and øktid="+øktid);
        } catch (Exception e) {
            System.out.println("db error during update of apparatøvelse="+e);
            return;
        }
    }

}
