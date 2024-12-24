package packSourceCode;

import packGuiSwing.pencereGUI;
import packGuiSwing.mainGUI;
import packDatabase.databaseBaglanti;
import packDatabase.DatabaseIslemler;


public class Main {
    
    public static void main(String[] args){
        System.out.println("Main");
        pencereGUI.setDebugOUTPUT(false);
        
        //VERITABANI BAGLANTISI
        databaseBaglanti database = new databaseBaglanti("qw","kutuphaneOtomasyon2");
        database.databaseBaglan();
        
        DatabaseIslemler dbIslemler = new DatabaseIslemler(database);
        
        //ANA PENCEREYI BASLATMA
        mainGUI home_frame = new mainGUI(dbIslemler);
        home_frame.initMainGUI(true);
        
      
        
      
        
        //dbIslemler.SQL_Q_oduncKitaplarVeUyeler();
        //System.out.println("");
        
        
        
        //database.databaseSonlandir();
    }
    
}
