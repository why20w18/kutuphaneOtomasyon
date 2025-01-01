/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import packDatabase.DatabaseIslemler;

/**
 *
 * @author archw
 */
import packDatabase.DatabaseIslemler;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_SIVIL_ISLEMLERI_CIKARTMA;
import packGuiSwing.pencereGUI;


public class uyeYonetimSivilIslemleriGUI {
    pencereGUI uyeyonetimSivilIslemleri_frame;
    pencereGUI_Component uysi_component;
    DatabaseIslemler databaseIslemler;

    
    
        ////////////////////////////CONSTRUCTORLAR
    public uyeYonetimSivilIslemleriGUI(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;

        uyeyonetimSivilIslemleri_frame = new pencereGUI("UYE YONETIM -> SIVIL ISLEMLERI", 1300, 200);
        uysi_component = new pencereGUI_Component(uyeyonetimSivilIslemleri_frame,this.databaseIslemler);
    }
        //////////////////////////
    
 
        //////////////////////////
    public void inituyeYonetimiSivilIslemleriGUI(boolean isLayoutActive){
        uyeyonetimSivilIslemleri_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            uyeyonetimSivilIslemleri_frame.initLayout();
            uysi_component.infoBar("SIVIL ISLEMLER: Tüm alanlar eksiksiz girilerek ekleme yapılır", 
                    "SIVIL ISLEMLER: üyeID girilerek kaldırılmak istenen kaldırılır",
                    "ODUNCG");
            
         
            JTextField uyeID = uysi_component.addTextField("ID", 25, ButonPozisyon.UST);
            JTextField uyeAd = uysi_component.addTextField("Ad", 25, ButonPozisyon.UST);
            JTextField uyeSoyad = uysi_component.addTextField("Soyad", 25, ButonPozisyon.UST);
            JTextField uyeTC = uysi_component.addTextField("TC", 25, ButonPozisyon.UST);
            
            String seceneklerCinsiyet[] = {"Kadın","Erkek"};
            JComboBox<String> uyeCinsiyet = uysi_component.addDoldurucuComboBox(seceneklerCinsiyet, 30, 20, 15, ButonPozisyon.UST);
            
            JTextField uyeUcret = uysi_component.addTextField("ÜyelikÜcret", 25, ButonPozisyon.UST);                          
            JTextField uyeIndirimMiktari = uysi_component.addTextField("İndirimMiktari", 25, ButonPozisyon.UST);
            
            JTextField sivilGelirMiktari = uysi_component.addTextField("gelirMiktari", 25, ButonPozisyon.UST);

          
//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN
            String[] girdiler = new String[7];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            textfieldArrayList.add(uyeID);
            textfieldArrayList.add(uyeAd);
            textfieldArrayList.add(uyeSoyad);
            textfieldArrayList.add(uyeTC);         
            textfieldArrayList.add(uyeUcret);
            textfieldArrayList.add(uyeIndirimMiktari);
            textfieldArrayList.add(sivilGelirMiktari);
            uysi_component.ListenerTextField(textfieldArrayList, girdiler);            
//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN

//COMBOBOX GIRDILERINI DINLEME VE SAKLAMA
            int comboBoxGirdiler[] = new int[1];
            uysi_component.setListenerCombobox(uyeCinsiyet, CagrilacakFonksiyon.CF_COMBOBOX_INDEX_CEVIR,comboBoxGirdiler,0); //CINSIYET 0.INDEX
//COMBOBOX GIRDILERINI DINLEME VE SAKLAMA

            JButton button_sivilEkle = uysi_component.addButtonParams("Sivil Ekle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_SIVIL_ISLEMLERI_EKLE,girdiler,comboBoxGirdiler);
            uysi_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_sivilEkle);
            
            JButton button_sivilCikart = uysi_component.addButtonParams("Sivil Çıkart",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_SIVIL_ISLEMLERI_CIKARTMA,girdiler,comboBoxGirdiler);
            uysi_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_sivilCikart);
            
            JButton button_sivilGuncelle = uysi_component.addButtonParams("Sivil Güncelle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_TANIMLANMADI,girdiler,comboBoxGirdiler);
            uysi_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_sivilGuncelle);
            
            
            
          
            
        }
            
        
        uysi_component.updateComponent();
    }    
    //////////////////////////


    
    
        //////////////////////////
    public pencereGUI manageuyeyonetimSivilIslemleriFrame(){
        return uyeyonetimSivilIslemleri_frame;
    }
        //////////////////////////
}


