package treningdagbok;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import treningdagbok.Treningsøkt;

public class SQLQuery {
	
	public static List<Øvelse> getOvelserTilApparat (Connection conn, int apparatid) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select øvelsesid, øvelsenavn, øvelsebeskrivelse, apparatid from Øvelse WHERE apparatid=" + apparatid);
            Øvelse o;
            List<Øvelse> list = new ArrayList<Øvelse>();
            while (rs.next()) {
            	o = new Øvelse(rs.getInt("øvelsesid"));
                o.setNavn(rs.getString("øvelsenavn"));
                o.setBeskrivelse(rs.getString("øvelsebeskrivelse"));
                o.setApparatid(rs.getInt("apparatid"));
                list.add(o);
            }
            return list;

        } catch (Exception e) {
            System.out.println("db error getOvelserTilApparat with apparatid = " + apparatid + "  ,"+e);
            return null;
        }

    }

    public static List<Treningsøkt> getNSisteØkter (Connection conn, int n) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Treningsøkt ORDER BY tidspunkt DESC LIMIT "+n);
            Treningsøkt økt;
            List<Treningsøkt> list = new ArrayList<Treningsøkt>();
            while (rs.next()) {
                økt = new Treningsøkt(rs.getInt("øktid"));
                økt.setVarighet(rs.getInt("varighet"));
                økt.setTidspunkt(rs.getTimestamp("tidspunkt"));
                økt.setForm(rs.getInt("form"));
                økt.setPrestasjon(rs.getInt("prestasjon"));
                økt.setNotat(rs.getString("notat"));
                list.add(økt);    
            }
            return list;

        } catch (Exception e) {
            System.out.println("db error getNSisteØkter: "+e);
            return null;
        }
    }
    
    public static List<Øvelse> getOvelserInGroup(Connection conn, int gruppeid) {
    	try {
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select Øvelse.øvelsesid, øvelsenavn, øvelsebeskrivelse, apparatid from Øvelse JOIN ØvelseTilhørerGruppe ON Øvelse.ØvelsesID= ØvelseTilhørerGruppe.ØvelsesID WHERE gruppeid=" + gruppeid);
    		Øvelse o;
    		List<Øvelse> list = new ArrayList<Øvelse>();
    		while (rs.next()) {
    			o = new Øvelse(rs.getInt("øvelsesid"));
    			o.setNavn(rs.getString("øvelsenavn"));
    			o.setBeskrivelse(rs.getString("øvelsebeskrivelse"));
    			o.setApparatid(rs.getInt("apparatid"));
    			list.add(o);
    		}
    		return list;

    	} catch (Exception e) {
    		System.out.println("db error getOvelserInGroup with groupid = " + gruppeid + "  ,"+e);
    		return null;
    	}

    }

    public static List<?> getOvelseResultat(Connection conn, int øvelsesid, java.sql.Timestamp tid1, java.sql.Timestamp tid2) {
    	try {
    		Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT øktid, tidspunkt, resultat FROM" +
                            " (SELECT * FROM (SELECT øvelsesid, resultat FROM Øvelse NATURAL JOIN ApparatØvelse AS Table1) " +
                            " UNION (SELECT øvelsesid, resultat FROM Øvelse NATURAL JOIN FriØvelse AS Table2)) AS Table3 " +
                            " NATURAL JOIN Treningsøkt WHERE Øvelse.øvelsesid = "+ øvelsesid +" AND tidspunkt > "+ tid1+ " AND tidspunkt < " +tid2 +"AS Table4");
    		Treningsøkt o;
    		List<Treningsøkt> list = new ArrayList<Treningsøkt>();
    		while (rs.next()) {
    			o = new Treningsøkt(rs.getInt("øktid"));
                o.setTidspunkt(rs.getTimestamp("tidspunkt"));
                o.setNotat(rs.getString("resultat"));
    			list.add(o);
    		}
    		return list;

    	} catch (Exception e) {
    		throw new IllegalStateException("db error getOvelseResultat: \n\t\t" +e);
    	}

    }
    
    public static List<ØvelsesGruppe> getGrupper(Connection conn){
    	try {
    	Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from ØvelsesGruppe");
		ØvelsesGruppe g;
		List<ØvelsesGruppe> l = new ArrayList<ØvelsesGruppe>();
		while(rs.next()) {
			g = new ØvelsesGruppe(rs.getInt("gruppeid"));
			g.setBeskrivelse(rs.getString("gruppebeskrivelse"));
			l.add(g);
		}
		return l;
    	}catch(Exception e) {
    		throw new IllegalStateException(e);
    	}
    }

}
