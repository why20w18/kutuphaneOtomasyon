package packSourceCode;

import packGuiSwing.pencereGUI;
import packGuiSwing.mainGUI;
import packDatabase.databaseBaglanti;
import packDatabase.DatabaseIslemler;

/*
EKLEME:PENCERE_ADI	ZORUNLU_ALANLAR İLE EKLENİR
ÇIKARTMA:PENCERE_ADI	ZORUNLU_ALANLAR İLE ÇIKARTILIR
GÜNCELLEME:PENCERE_ADI	İD DEĞERİ VE İSTENEN ALANLAR GİRİLEREK GÜNCELLENİR
*/

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
   
//////////////////////////////////////////////////////////////////////////////////////////////
//        System.out.println(dbIslemler.SQL_Q_DBF_getKitapSayisi());
//        System.out.println(dbIslemler.SQL_Q_DBF_getKitapSayisi_pKategoriID(1));
//        
//        System.out.println(dbIslemler.SQL_Q_DBF_getToplamSayfaSayisi());
//        System.out.println(dbIslemler.SQL_Q_DBF_getToplamSayfaSayisi_pKategoriID(1));
//        
//        System.out.println(dbIslemler.SQL_Q_DBF_getYayineviSayisi());
//        System.out.println(dbIslemler.SQL_Q_DBF_getToplamYazarSayisi());
//        System.out.println(dbIslemler.SQL_Q_DBF_getKategoriSayisi());
//////////////////////////////////////////////////////////////////////////////////////////////
    }
    
}
