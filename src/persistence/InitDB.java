package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import application.KeyWord;
/**
 * Initialisierung der Datenbank muss mit in die Auslieferung
 * 
 * @author Kerstin
 *
 */
public class InitDB {
	public static void main(String args[]) {
		
		try (Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
			Statement stmt=con.createStatement();	){
			String sql;
			//Anlegen der Tabelle config
			sql = "DROP Table If EXISTS config"; //holger
			stmt.executeUpdate(sql);             //holger
			
			sql = "CREATE TABLE config " + "( '_id' INTEGER PRIMARY_KEY  AUTO_INCREMENT   NOT NULL,"
					+ " sourceRoot           VARCHAR(255)    NOT NULL, " + " destinationRoot            VARCHAR(255)     NOT NULL, "
					+ " manualStore        CHAR(5) DEFAULT true)";
			stmt.executeUpdate(sql);
			//Anlegen der Tabelle keywords
			sql = "DROP Table If EXISTS keywords"; //holger
			stmt.executeUpdate(sql);               //holger
			
			sql = "CREATE TABLE keywords " + "( 'id' INTEGER PRIMARY KEY  AUTOINCREMENT,"
					+ " keyWord           VARCHAR(50)    NOT NULL, " + " pathName            VARCHAR(50)     NOT NULL, "
					+ " level     INTEGER  NOT NULL)";
			stmt.executeUpdate(sql);
			//Anlegen der Tabelle childParant
			sql = "DROP Table If EXISTS childParent";  //holger
			stmt.executeUpdate(sql);                   //holger
			
			sql = "CREATE TABLE childParent " + "( 'id' INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " child    INTEGER  NOT NULL, " + " parent     INTEGER  NOT NULL, " + " FOREIGN KEY(child) REFERENCES keywords(id),"
					+ " FOREIGN KEY(parent) REFERENCES keywords(id))";
			stmt.executeUpdate(sql);
			//Startwert in Tabelle keywords eintragen
			sql="INSERT INTO keywords (keyWord, pathName, level) "
					+ "VALUES('Neuer Eintrag..', 'xxx', 0);";
					
			stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		}
		
	}
}
