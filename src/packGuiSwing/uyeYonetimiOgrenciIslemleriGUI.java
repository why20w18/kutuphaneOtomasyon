/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import packDatabase.DatabaseIslemler;

/**
 *
 * @author archw
 */

import packDatabase.DatabaseIslemler;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_OGRENCI_ISLEMLERI_EKLE;
import packGuiSwing.pencereGUI;


public class uyeYonetimiOgrenciIslemleriGUI {
    pencereGUI uyeyonetimOgrenciIslemleri_frame;
    pencereGUI_Component uyoi_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public uyeYonetimiOgrenciIslemleriGUI(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;

        uyeyonetimOgrenciIslemleri_frame = new pencereGUI("UYE YONETIM -> OGRENCI ISLEMLERI", 1300, 500);
        
        uyoi_component = new pencereGUI_Component(uyeyonetimOgrenciIslemleri_frame,this.databaseIslemler);

        
    }
    
 
    public void inituyeYonetimiOgrenciIslemleriGUI(boolean isLayoutActive){
        uyeyonetimOgrenciIslemleri_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            uyeyonetimOgrenciIslemleri_frame.initLayout();
            uyoi_component.infoBar("OGRENCI ISLEMLERI: Öğrenci türü ", 
                    "ODUNCC",
                    "ODUNCG");
            
         
            JTextField uyeID = uyoi_component.addTextField("ID", 25, ButonPozisyon.UST);
            JTextField uyeAd = uyoi_component.addTextField("Ad", 25, ButonPozisyon.UST);
            JTextField uyeSoyad = uyoi_component.addTextField("Soyad", 25, ButonPozisyon.UST);
            JTextField uyeTC = uyoi_component.addTextField("TC", 25, ButonPozisyon.UST);
            
            String seceneklerCinsiyet[] = {"Kadın","Erkek"};
            JComboBox<String> uyeCinsiyet = uyoi_component.addDoldurucuComboBox(seceneklerCinsiyet, 30, 20, 15, ButonPozisyon.UST);
            
            JTextField uyeUcret = uyoi_component.addTextField("ÜyelikÜcret", 25, ButonPozisyon.UST);            
                                    
            JTextField uyeIndirimMiktari = uyoi_component.addTextField("İndirimMiktari", 25, ButonPozisyon.UST);
           //uye turu otomatik OGRENCI olarak eklenecek

            JTextField ogrenciOkulAd = uyoi_component.addTextField("ÖğrenciOkulAd", 25, ButonPozisyon.UST);
            
            String seceneklerOgrenciTur[] = {"Ortaöğretim","Lisans","Lisansüstü"};
            JComboBox<String> ogrenciTuru = uyoi_component.addDoldurucuComboBox(seceneklerOgrenciTur, 30, 20, 15, ButonPozisyon.UST);
            
            JTextField bolumSTRVeyaSinifINTOrtak = uyoi_component.addTextField("BölümAd/Sınıf", 25, ButonPozisyon.UST);
            JTextField ortalamaOrtak = uyoi_component.addTextField("Ortalama", 25, ButonPozisyon.UST);
            
            
            String secenekOrtaOgretim[] = {"İlkokul","Ortaokul","Lise"};
            JComboBox<String> ortaogretimTurleri = uyoi_component.addDoldurucuComboBox(secenekOrtaOgretim, 30, 20, 15, ButonPozisyon.SOL);
            ortaogretimTurleri.setVisible(false);
            

//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN
            String[] girdiler = new String[9];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            textfieldArrayList.add(uyeID);
            textfieldArrayList.add(uyeAd);
            textfieldArrayList.add(uyeSoyad);
            textfieldArrayList.add(uyeTC);         
            textfieldArrayList.add(uyeUcret);
            textfieldArrayList.add(uyeIndirimMiktari);
            textfieldArrayList.add(ogrenciOkulAd);
            textfieldArrayList.add(bolumSTRVeyaSinifINTOrtak);
            textfieldArrayList.add(ortalamaOrtak);
            uyoi_component.ListenerTextField(textfieldArrayList, girdiler);            
//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN

//COMBOBOX GIRDILERINI DINLEME VE SAKLAMA
            int comboBoxGirdiler[] = new int[3];
            uyoi_component.setListenerCombobox(uyeCinsiyet, CagrilacakFonksiyon.CF_COMBOBOX_INDEX_CEVIR,comboBoxGirdiler,0); //CINSIYET 0.INDEX
            uyoi_component.setListenerCombobox(ogrenciTuru, CagrilacakFonksiyon.CF_COMBOBOX_INDEX_CEVIR,comboBoxGirdiler,1,ortaogretimTurleri); //TUR 1.INDEX
            uyoi_component.setListenerCombobox(ortaogretimTurleri, CagrilacakFonksiyon.CF_COMBOBOX_INDEX_CEVIR, comboBoxGirdiler, 2); //ORTAOGRETIM TURLERI
//COMBOBOX GIRDILERINI DINLEME VE SAKLAMA

            JButton button_kitapEkle = uyoi_component.addButtonParams("Öğrenci Ekle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_OGRENCI_ISLEMLERI_EKLE,girdiler,comboBoxGirdiler);
            uyoi_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapEkle);
            
            JButton button_kitapCikart = uyoi_component.addButtonParams("Öğrenci Çıkart", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_TANIMLANMADI,girdiler,comboBoxGirdiler);
            uyoi_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapCikart);
            
            JButton button_kitapGuncelle = uyoi_component.addButtonParams("Öğrenci Güncelle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_TANIMLANMADI,girdiler,comboBoxGirdiler);
            uyoi_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapGuncelle);
            
            
            
            
            
            
            
          
            
        }
            
        
        uyoi_component.updateComponent();
    }
    
    
    
    public pencereGUI manageuyeyonetimOgrenciIslemleriFrame(){
        return uyeyonetimOgrenciIslemleri_frame;
    }
  
    
    
}


