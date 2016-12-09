package sqlbase;

import airpark.AviaPort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static airpark.AviaPort.all;

public class CreateTable {

    public static void CreateDB() throws ClassNotFoundException, SQLException {

        String user = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
        DBconnect db = new DBconnect();
        Connection cnt = db.conn(url, user, pass);
        Statement stat = cnt.createStatement();

        //creating database (delete old version)
        stat.execute("DROP database if EXISTS Airport_MSQ");
        stat.execute("CREATE DATABASE Airport_MSQ;");
        stat.execute("USE Airport_MSQ");

        //creating table
        String create_table = "CREATE TABLE if not exists `Jets` (\n" +
                "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `jet_name` varchar(50) NOT NULL,\n" +
                "  `jet_ID` bigint(20) NOT NULL,\n" +
                "  `jet_speed` int(10) NOT NULL,\n" +
                "  `jet_range` int(10) NOT NULL,\n" +
                "  `jet_weight` int(11) NOT NULL,\n" +
                "  `jet_fuel` int(11) NOT NULL,\n" +
                "  `jet_tank` int(11) DEFAULT NULL,\n" +
                "  `jet_passenger` int(11) NOT NULL,\n" +
                "  `jet_passengerseat` int(11) NOT NULL,\n" +
                "  `jet_Flycost` int(11) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `id_UNIQUE` (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;\n";
        stat.execute(create_table);

        //fill table with data
        for (int i = 1; i < 10; i++) {

            String adddb = "insert into Airport_MSQ.Jets(jet_name, jet_ID, jet_speed, jet_range, jet_weight, jet_fuel, jet_tank,  jet_passenger, jet_passengerseat,  jet_Flycost) values (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prep = cnt.prepareStatement(adddb);
            AviaPort ap = null;
            ap = new AviaPort();
            all.add(ap);
            prep.setString(1, ap.getName());
            prep.setInt(2, ap.getNumber());
            prep.setInt(3, ap.getSpeed());
            prep.setInt(4, ap.getRange());
            prep.setInt(5, ap.getWeight());
            prep.setInt(6, ap.getFuel());
            prep.setInt(7, ap.getTank());
            prep.setInt(8, ap.getPassenger());
            prep.setInt(9, ap.getPassengerSeat());
            prep.setInt(10, ap.getFlyCost());
            prep.execute();
        }

    }


}