package packDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//veritabani iletisimi baslatacak class
public class databaseBaglanti {
    
    final String USERNAME = "root";
    final String PASSWORD = "";
    final String HOST = "localhost";
    final String JDBC = "jdbc:mysql://";
    final String DRIVER = "com.mysql.cj.jdbc.Driver";
    final int PORT = 3306;
    
    
}
