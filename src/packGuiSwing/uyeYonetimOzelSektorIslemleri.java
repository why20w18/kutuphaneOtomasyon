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
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_OZELSEKTOR_ISLEMLERI_CIKARTMA;
/**
 *
 * @author archw
 */
public class uyeYonetimOzelSektorIslemleri {
    pencereGUI uyeyonetimOzelSektorIslemleri_frame;
    pencereGUI_Component uyos_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public uyeYonetimOzelSektorIslemleri(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;

        uyeyonetimOzelSektorIslemleri_frame = new pencereGUI("UYE YONETIM -> OZEL SEKTOR ISLEMLERI", 1300, 200);
        uyos_component = new pencereGUI_Component(uyeyonetimOzelSektorIslemleri_frame,this.databaseIslemler);

        
    }
    
 
    public void inituyeYonetimiOzelSektorIslemleriGUI(boolean isLayoutActive){
        uyeyonetimOzelSektorIslemleri_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            uyeyonetimOzelSektorIslemleri_frame.initLayout();
            uyos_component.infoBar("OZEL SEKTOR ISLEMLERI: kurumAntlasmasi SECENEGINE GIRDI OLARAK EVET/HAYIR/e/h GİREBİLİRSİNİZ !", 
                    "ODUNCC",
                    "ODUNCG");
            
            
            
            JTextField uyeID = uyos_component.addTextField("ID", 25, ButonPozisyon.UST);
            JTextField uyeAd = uyos_component.addTextField("Ad", 25, ButonPozisyon.UST);
            JTextField uyeSoyad = uyos_component.addTextField("Soyad", 25, ButonPozisyon.UST);
            JTextField uyeTC = uyos_component.addTextField("TC", 25, ButonPozisyon.UST);
            
            String seceneklerCinsiyet[] = {"Kadın","Erkek"};
            JComboBox<String> uyeCinsiyet = uyos_component.addDoldurucuComboBox(seceneklerCinsiyet, 30, 20, 15, ButonPozisyon.UST);
            
            JTextField uyeUcret = uyos_component.addTextField("ÜyelikÜcret", 25, ButonPozisyon.UST);                          
            JTextField uyeIndirimMiktari = uyos_component.addTextField("İndirimMiktari", 25, ButonPozisyon.UST);
            
            JTextField kurumAntlasmasi = uyos_component.addTextField("kurumAntlasmasi", 25, ButonPozisyon.UST);

          
//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN
            String[] girdiler = new String[7];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            textfieldArrayList.add(uyeID);
            textfieldArrayList.add(uyeAd);
            textfieldArrayList.add(uyeSoyad);
            textfieldArrayList.add(uyeTC);         
            textfieldArrayList.add(uyeUcret);
            textfieldArrayList.add(uyeIndirimMiktari);
            textfieldArrayList.add(kurumAntlasmasi);
            uyos_component.ListenerTextField(textfieldArrayList, girdiler);            
//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN

//COMBOBOX GIRDILERINI DINLEME VE SAKLAMA
            int comboBoxGirdiler[] = new int[1];
            uyos_component.setListenerCombobox(uyeCinsiyet, CagrilacakFonksiyon.CF_COMBOBOX_INDEX_CEVIR,comboBoxGirdiler,0); //CINSIYET 0.INDEX
//COMBOBOX GIRDILERINI DINLEME VE SAKLAMA

            JButton button_oscEkle = uyos_component.addButtonParams("Özel Sektör Çalışanı Ekle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_OZELSEKTOR_ISLEMLERI_EKLEME,
                    girdiler,comboBoxGirdiler);
            uyos_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_oscEkle);
            
            JButton button_oscCikart = uyos_component.addButtonParams("Özel Sektör Çalışanı Çıkart",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_OZELSEKTOR_ISLEMLERI_CIKARTMA,
                    girdiler,comboBoxGirdiler);
            uyos_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_oscCikart);
            
            JButton button_oscGuncelle = uyos_component.addButtonParams("Özel Sektör Çalışanı Güncelle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_TANIMLANMADI,girdiler,comboBoxGirdiler);
            uyos_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_oscGuncelle);
            
         
            
            
          
            
        }
            
        
        uyos_component.updateComponent();
    }
    
    
    
    public pencereGUI manageuyeyonetimOzelSektorFrame(){
        return uyeyonetimOzelSektorIslemleri_frame;
    }
  
    
    
}


