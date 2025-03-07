package packGuiSwing;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import packDatabase.DatabaseIslemler;

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


public class yayineviYonetimGUI {
    pencereGUI yayineviyonetim_frame;
    pencereGUI_Component yaey_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public yayineviYonetimGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("yayineviYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        yayineviyonetim_frame = new pencereGUI("KITAP YONETIM -> YAYINEVI YONETIM", 800, 750);
        yaey_component = new pencereGUI_Component(yayineviyonetim_frame,this.databaseIslemler);

        
    }
    
 
    public void inityayineviYonetimGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("yayineviYonetimGUI.inityayineviYonetimGUI CAGRILDI");
        yayineviyonetim_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            yayineviyonetim_frame.initLayout();
            yaey_component.infoBar("yayineviAd VE yayineviUlke GİRİLEREK EKLEME YAPILIR", 
                    "SADECE yayineviID GİRİLEREK SİLME YAPILIR",
                    "yayineviID GIRILIR VE ISTENEN KOLONLAR GIRILEREK GUNCELLEME YAPILIR");
            
         
            
                                     //0         1              2
            String kolonIsimleri[] = { "ID", "Yayınevi Adı","Yayınevi Ülke"};
          
            JTable jtable_kitapListesi = yaey_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_YAYINEVI_LIST);
            
            //EKLEME CIKARTMA
            JTextField tfield_yaeyID = yaey_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_yaeyAd = yaey_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_yaeyUlke = yaey_component.addTextField("", 25, ButonPozisyon.UST);

            
            
            String[] girdiler = new String[kolonIsimleri.length];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            
            textfieldArrayList.add(tfield_yaeyID);
            textfieldArrayList.add(tfield_yaeyAd);
            textfieldArrayList.add(tfield_yaeyUlke);
            yaey_component.ListenerTextField(textfieldArrayList, girdiler);   
            
            
            //KATEGORI ISLEMLERI
            JButton button_yaeyEkle = yaey_component.addButtonParams("Yayınevi Ekle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_YAYINEVI_EKLE,girdiler);
            yaey_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_yaeyEkle);
            
            JButton button_yaeyCikart = yaey_component.addButtonParams("Yayınevi Çıkart", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_YAYINEVI_CIKART,girdiler);
            yaey_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_yaeyCikart);
            
            JButton button_yaeyGuncelle = yaey_component.addButtonParams("Yayınevi Güncelle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_YAYINEVI_GUNCELLE,girdiler);
            yaey_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_yaeyGuncelle);
            
        }
            
        
        yaey_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageyayineviYonetimGUIFrame(){
        return yayineviyonetim_frame;
    }
  
    
    
}

