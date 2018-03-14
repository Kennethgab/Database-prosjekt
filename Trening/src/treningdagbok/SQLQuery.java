package treningdagbok;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLQuery {
	
	public static List getOvelserTilApparat (Connection conn, int apparatid) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select øvelsesid, øvelsenavn, øvelsesbeskrivelse, apparatid from Øvelse WHERE apparatid=" + apparatid);
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

    

}
