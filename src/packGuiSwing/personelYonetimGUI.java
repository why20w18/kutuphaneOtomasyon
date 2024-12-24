package packGuiSwing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import packDatabase.DatabaseIslemler;

import packGuiSwing.pencereGUI;


public class personelYonetimGUI {
    pencereGUI personelyonetim_frame;
    pencereGUI_Component py_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public personelYonetimGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("personelYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        personelyonetim_frame = new pencereGUI("PERSONEL YONETIM", 800, 750);
        py_component = new pencereGUI_Component(personelyonetim_frame,this.databaseIslemler);

        
    }
    
 
    public void initpersonelYonetimGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("personelYonetimGUI.initpersonelYonetimGUI CAGRILDI");
        personelyonetim_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            personelyonetim_frame.initLayout();
            py_component.infoBar(
                    "ad ,soyad ,maas ,personelTuru=HIZMETLI/YONETICI ,ekbilgi=BIRIM/GOREV ekleme yapilir", 
                    "sadece personelID girerek cikartma islemi yapilir", 
                    "personelID + ad , soyad , personelTuru=HIZMETLI/YONETICI,ekbilgi=BIRIM/GOREV KOLONLARINDAN BIRINI SECMEK GUNCELLEME YAPAR");
            /*
            //BUTONLAR
            0-ACILIR ACILMAZ LISTELEYECEK   +
            1-FILTRELEME                    -
            2-EKLEME                        +
            3-CIKARTMA                      +
            4-GUNCELLEME                    +
            */
            
            String kolonIsimleri[] = { "ID", "Ad", "Soyad", "Maaş","Personel Türü","Görevi/Birimi"};
            JTable jtable_personelListesi = py_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_PERSONEL_LIST);
            
            //EKLEME CIKARTMA
            JTextField tfield_pID = py_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_pAd = py_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_pSoyad = py_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_pMaas = py_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_pTuru = py_component.addTextField("YONETICI", 25, ButonPozisyon.UST);
            JTextField tfield_pEkBilgi = py_component.addTextField("", 25, ButonPozisyon.UST);

            
            String[] girdiler = new String[kolonIsimleri.length];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            
            textfieldArrayList.add(tfield_pID);
            textfieldArrayList.add(tfield_pAd);
            textfieldArrayList.add(tfield_pSoyad);
            textfieldArrayList.add(tfield_pMaas);
            textfieldArrayList.add(tfield_pTuru);
            textfieldArrayList.add(tfield_pEkBilgi);
            py_component.ListenerTextField(textfieldArrayList, girdiler);
            
            JButton button_personelEkle = py_component.addButtonParams("Personel Ekle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_PERSONEL_EKLE,girdiler);
            py_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_personelEkle);
            
            JButton button_personelCikart = py_component.addButtonParams("Personel Çıkart", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_PERSONEL_CIKART,girdiler);
            py_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_personelCikart);
            
            JButton button_personelGuncelle = py_component.addButtonParams("Personel Güncelle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_PERSONEL_GUNCELLE,girdiler);
            py_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_personelGuncelle);
            
        
        }
            
        
        py_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI managePersonelYonetimFrame(){
        return personelyonetim_frame;
    }
  
    
    
}

