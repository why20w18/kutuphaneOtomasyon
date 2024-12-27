package packGuiSwing;

import java.awt.BorderLayout;
import javax.swing.JButton;
import packDatabase.DatabaseIslemler;

import packGuiSwing.pencereGUI;

/*
1-HER PENCERE ICIN YENI CLASS ACILMALIDIR
2-HER PENCERE/SEKME KENDI CLASSI ICINDE .getStartFrame() ILE BASLATILMALIDIR
3-.getStartFrame() CONSTRUCTOR ICINE KOYULMAMALIDIR initCLASS_ADI SEKLINDE YENI METOD
ACILARAK 2.ADIMDA BASLATILMALIDIR
4-initLayout() , initCLASS_ADI ICERISINDE LAYOUT AKTIFLIGI KONTROLUNDEN SONRA BASLATILMALIDIR
*/


public class mainGUI {
    pencereGUI home_frame;
    pencereGUI_Component hf_component;
    DatabaseIslemler databaseIslemler;
    
    //CONSTRUCTORLAR
    public mainGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("mainGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;
        home_frame = new pencereGUI("KUTUPHANE OTOMASYON PROGRAMI", 800, 750);
        hf_component = new pencereGUI_Component(home_frame,this.databaseIslemler);
    }
    
 
    public void initMainGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("mainGUI.initMainGUI CAGRILDI");
        home_frame.InitPencereGUI(false);
        
        //GUI ELEMANLARININ YERLESTIRILECEGI ALAN
        if(isLayoutActive == true){
            home_frame.initLayout();
            hf_component.addMenuBar();
            
            ////////////////////
            JButton buton1 = hf_component.addButton("Personel Yönetimi",ButonPozisyon.UST,CagrilacakFonksiyon.CF_BUTTON_NW_PERSONEL_YONETIM);
            JButton buton8 = hf_component.addButton("Üye Yönetimi",ButonPozisyon.UST,CagrilacakFonksiyon.CF_BUTTON_NW_UYE_YONETIM);

            JButton buton2 = hf_component.addButton("Yazar Yönetimi",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_NW_YAZAR_YONETIM);
            JButton buton9 = hf_component.addButton("Yayınevi Yönetimi",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_NW_YAYINEVI_YONETIM);

            JButton buton4 = hf_component.addButton("Ödünç Yönetimi",ButonPozisyon.SOL,CagrilacakFonksiyon.CF_BUTTON_NW_ODUNC_YONETIM);
            JButton buton7 = hf_component.addButton("Kitap Yönetimi",ButonPozisyon.SOL,CagrilacakFonksiyon.CF_BUTTON_NW_KITAP_YONETIM);
            
            
            
            //JButton buton5 = hf_component.addButton("HIZLI2ERISIM",ButonPozisyon.ORTA,CagrilacakFonksiyon.CF_TANIMLANMADI);
            //JButton buton6 = hf_component.addButton("HIZLI3ERISIM",ButonPozisyon.ORTA,CagrilacakFonksiyon.CF_TANIMLANMADI);

            
            pencereGUI.debugPrint(buton1.getText());
        
        }
            

        
        hf_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageHomeFrame(){
        return home_frame;
    }
  
    
    
}