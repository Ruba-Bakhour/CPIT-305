package pkg305project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rubab
 */
public class Database {

    public static String ConnectionURL = "jdbc:mysql://localhost:3306";

    public Database() {

    }

    public void db_ini() {

        Connection con = null;
        try {

            con = DriverManager.getConnection(ConnectionURL, "root", "1234"); // Change it to your settings
            Statement st = con.createStatement();

            //----------------------------------------------Database Creation--------------------------------------------------
            if (!st.executeQuery("show databases like 'Catopia_DB'").next()) {
                // (4) create a database
                String database = "Catopia_DB";
                st.executeUpdate("CREATE DATABASE " + database);
                // (5) connect to the database
                ConnectionURL = "jdbc:mysql://localhost:3306/Catopia_DB";
                con = DriverManager.getConnection(ConnectionURL, "root", "1234"); // Change it to your settings
                //----------------------------------------------Table Creation--------------------------------------------------
                st = con.createStatement();
                st.executeUpdate("CREATE TABLE user (userID int, name varchar(20),Email varchar(30),phone int(10), loc varchar(20))");
                st.executeUpdate("CREATE TABLE cat  (catID int, cat_name varchar(20),breed varchar(10),birth DATE, sex ENUM('F','M'), disabled BOOLEAN, personality SET('Extroverted','Introverted','Agreeable','Impulsive','LikesChildren', 'LikesCats'), adoptionFees int, ownerID int)");
                st.executeUpdate("CREATE TABLE donation  (donationID int,donationDate DATE, from_userID int, to_userID int, type ENUM('Money','Toys','Food'), description varchar(60))");

                //----------------------------------------------Dummy data--------------------------------------------------
                st.executeUpdate("INSERT INTO user VALUES(0, 'Sabah', 'Sabah@gmail.com', 0541234567, 'Jeddah')");
                st.executeUpdate("INSERT INTO cat VALUES(0, 'momo','Tabby','2019-04-15', 'F', TRUE, 'Introverted,Agreeable,LikesChildren,LikesCats',0, 0)");
                st.executeUpdate("INSERT INTO cat VALUES(1, 'mishmish','Arab','2023-04-15', 'M', TRUE, 'Extroverted,Impulsive,LikesChildren,LikesCats',50, 0)");

                System.out.println("Database, tables, and dummy data inserted successfully");
            } else {
                System.out.println("the database already exists");
                
            }
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            s.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
