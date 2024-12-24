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


public class kategoriTablosuGUI {
    pencereGUI kategoritablosu_frame;
    pencereGUI_Component kategoritablosu_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public kategoriTablosuGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("kitapYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        kategoritablosu_frame = new pencereGUI("KITAP YONETIM -> KATEGORI TABLOSU", 800, 750);
        kategoritablosu_component = new pencereGUI_Component(kategoritablosu_frame,this.databaseIslemler);

        
    }
    
 
    public void initkategoriTablosuGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("personelYonetimGUI.initpersonelYonetimGUI CAGRILDI");
        kategoritablosu_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            kategoritablosu_frame.initLayout();
            kategoritablosu_component.infoBar("sadece kategori adi girerek ekleme yapilir", 
                    "sadece kategori adi girerek cikartma yapilir",
                    "hem kategori id hem kategori adi girerek guncelleme yapilir");
            
         
            
                                     //0         1
            String kolonIsimleri[] = { "ID", "Kategori Adı"};
          
            JTable jtable_kitapListesi = kategoritablosu_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_GET_MEVCUT_KATEGORILER);
            
            //EKLEME CIKARTMA
            JTextField tfield_kID = kategoritablosu_component.addTextField("ID", 25, ButonPozisyon.UST);
            JTextField tfield_kKategori = kategoritablosu_component.addTextField("kitapKategori", 25, ButonPozisyon.UST);
            
            
            String[] girdiler = new String[kolonIsimleri.length];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            
           textfieldArrayList.add(tfield_kID);
            textfieldArrayList.add(tfield_kKategori);
            kategoritablosu_component.ListenerTextField(textfieldArrayList, girdiler);   
            
            
            //KATEGORI ISLEMLERI
            JButton button_kategoriEkle = kategoritablosu_component.addButtonParams("Kategori Ekle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_KATEGORI_EKLE_KATEGORITABLOSU,girdiler);
            kategoritablosu_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kategoriEkle);
            
            JButton button_kategoriCikart = kategoritablosu_component.addButtonParams("Kategori Çıkart", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_KATEGORI_CIKART_KATEGORITABLOSU,girdiler);
            kategoritablosu_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kategoriCikart);
            
            JButton button_kategoriGuncelle = kategoritablosu_component.addButtonParams("Kategori Güncelle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_KATEGORI_GUNCELLE_KATEGORITABLOSU,girdiler);
            kategoritablosu_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kategoriGuncelle);
            
        }
            
        
        kategoritablosu_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI managekategoriTablosuFrame(){
        return kategoritablosu_frame;
    }
  
    
    
}

