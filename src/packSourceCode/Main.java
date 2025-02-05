package packSourceCode;

import packGuiSwing.pencereGUI;
import packGuiSwing.mainGUI;
import packDatabase.databaseBaglanti;
import packDatabase.DatabaseIslemler;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args){        
        System.out.print("Veritabani adini girin : ");
        Scanner cin = new Scanner(System.in);
        String dbName = cin.nextLine();

        System.out.print("Veritabani parolanizi girin : ");
        String dbPass = cin.nextLine();

        System.out.print("Veritabani port numarasini girin : ");
        int portNo = cin.nextInt();

        pencereGUI.setDebugOUTPUT(false);
        //VERITABANI BAGLANTISI
        databaseBaglanti database = new databaseBaglanti(dbPass,dbName,portNo);
        database.databaseBaglan();
        
        DatabaseIslemler dbIslemler = new DatabaseIslemler(database);
        
        //ANA PENCEREYI BASLATMA
        mainGUI home_frame = new mainGUI(dbIslemler,false);
        home_frame.manageHomeFrame().setGenislik(950).setYukseklik(650);
        
        home_frame.initMainGUI(true);
        System.out.println(home_frame.manageHomeFrame().getRetPencereAdi());     
  

    }
    
}
