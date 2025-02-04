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
        databaseBaglanti database = new databaseBaglanti("qw","kutuphaneOtomasyon3",3306);
        database.databaseBaglan();
        
        DatabaseIslemler dbIslemler = new DatabaseIslemler(database);
        
        //ANA PENCEREYI BASLATMA
        mainGUI home_frame = new mainGUI(dbIslemler,false);
        home_frame.manageHomeFrame().setGenislik(950).setYukseklik(650);
        
        home_frame.initMainGUI(true);
        System.out.println(home_frame.manageHomeFrame().getRetPencereAdi());     
  

    }
    
}
