package packDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

//veritabani iletisimi baslatacak class
public class databaseBaglanti {
    
    //CLASS BAGIMSIZ
    private static final String USERNAME = "root";
    private static final String HOST = "localhost";
    private static final String JDBC = "jdbc:mysql://";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private int PORT = 3306;
    private String PASSWORD;
    private String DATABASE_NAME;
    private String URL;
    private Connection BAGLANTI;            //JDBC API sorgular bunun uzerinden gonderilecek database baglantisini
    
    //CONSTRUCTORLAR
    public databaseBaglanti(String PASSWORD,String DATABASE_NAME){
        this.PASSWORD = PASSWORD;
        this.DATABASE_NAME = DATABASE_NAME;
    }
    
    public databaseBaglanti(String PASSWORD,String DATABASE_NAME, int PORT){
        this.PASSWORD = PASSWORD;
        this.DATABASE_NAME = DATABASE_NAME;
        this.PORT = PORT;
    }
    
    public void databaseBaglan(){
        try{
            Class.forName(DRIVER);  //JVM icin dinamik olarak driveri yukleme 
            URL = JDBC + HOST + ":" + PORT + "/" + DATABASE_NAME;
            BAGLANTI = (Connection) DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("VERITABANI BAGLANTISI BASARILI");
        }
        catch(ClassNotFoundException | SQLException exception){
            Logger.getLogger("databaseBaglanti").log(Level.SEVERE,"VERITABANI BAGLANTISINDA CIDDI HATA",exception);
        }
    }
    
    public void databaseSonlandir(){
        try{
            if(BAGLANTI != null){
              BAGLANTI.close();
                System.out.println("VERITABANI BAGLANTISI KAPTILDI");
            }
        }
        catch(SQLException excp){
            System.out.println("BAGLANTI KAPATMA HATASI : "+excp.getMessage());
        }
    }
    
    public Connection getBaglanti(){
        return this.BAGLANTI;
    }
    
}
