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
            ResultSet rs = stmt.executeQuery("select tidspunkt, resultat from FriØvelse left outer join Treningsøkt on Treningsøkt.øktid = FriØvelse.øktid "+
                                            "where øvelsesid= "+øvelsesid+" tidspunkt > TIMESTAMP "+ StaticMethods.toQuote(tid1.toString()) +" and tidspunkt < TIMESTAMP "+ StaticMethods.toQuote(tid2.toString())+" union select tidspunkt, resultat from ApparatØvelse left outer join Treningsøkt on Treningsøkt.øktid = ApparatØvelse.øktid "+
                                            "where øvelsesid= "+øvelsesid+" tidspunkt > TIMESTAMP "+ StaticMethods.toQuote(tid1.toString()) +" and tidspunkt < TIMESTAMP "+ StaticMethods.toQuote(tid2.toString()));
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
    
    public static List<Apparat> getApparat(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Apparat");
			List<Apparat> l = new ArrayList<Apparat>();
			Apparat a;
			while(rs.next()) {
				a = new Apparat(rs.getInt("apparatid"));
				a.setBeskrivelse(rs.getString("apparatbeskrivelse"));
				a.setNavn(rs.getString("apparatnavn"));
				l.add(a);
			}
			return l;
			}catch(Exception e) {
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
    
    public static List<Treningsøkt> getØkter(Connection conn){
    	try {
    		Statement stmt = conn.createStatement();
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
    		return l;
    		}catch(Exception e) {
    			throw new IllegalStateException(e);
     	    }
    }
    
    public static List<Øvelse> getØvelser(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
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
			return l;
			}catch(Exception e) {
				throw new IllegalStateException(e);
	 	    }
	}

}
    
