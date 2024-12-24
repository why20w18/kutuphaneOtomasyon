package packGuiSwing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import packDatabase.DatabaseIslemler;

import packGuiSwing.pencereGUI;


public class kitapYonetimGUI {
    pencereGUI kitapyonetim_frame;
    pencereGUI_Component ky_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public kitapYonetimGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("kitapYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        kitapyonetim_frame = new pencereGUI("KITAP YONETIM", 800, 750);
        ky_component = new pencereGUI_Component(kitapyonetim_frame,this.databaseIslemler);

        
    }
    
 
    public void initkitapYonetimGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("personelYonetimGUI.initpersonelYonetimGUI CAGRILDI");
        kitapyonetim_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            kitapyonetim_frame.initLayout();
            ky_component.infoBar(
                    "T_KITAP-E",
                    "T_KITAP-C", 
                    "T_KITAP-G");
            
            /*
            //BUTONLAR
            0-ACILIR ACILMAZ LISTELEYECEK   
            1-FILTRELEME                    
            2-EKLEME                        
            3-CIKARTMA                      
            4-GUNCELLEME
            5-KATEGORI EKLEME ÇIKARTMA GUNCELLEME
            6-MEVCUT YAZARLARDAN BIRIYLE ESLESMELI
            7-MEVCUT KATEGORILERDEN BIRIYLE ESLESMELI
            */
            
                                     //  0    1            2           3          4           5         6         7             8
            String kolonIsimleri[] = { "ID", "Kitap Adı", "ISBN", "Kategori","Sayfa Sayısı","Fiyat","Yayınevi","Yazar Adı","Yazar Soyadı"};
          
            JTable jtable_kitapListesi = ky_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_KITAP_LIST);
            
            //EKLEME CIKARTMA
            JTextField tfield_kID = ky_component.addTextField("ID", 25, ButonPozisyon.UST);
            JTextField tfield_kAd = ky_component.addTextField("kitapAd", 25, ButonPozisyon.UST);
            JTextField tfield_kISBN = ky_component.addTextField("kitapISBN", 25, ButonPozisyon.UST);
            
            //JTextField tfield_kKategori = ky_component.addTextField("KategoriAd", 25, ButonPozisyon.UST);
            JComboBox<String> cbox_kKategori = ky_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.UST, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER);
            ky_component.setListenerCombobox(cbox_kKategori, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID); //comboboxRetID
            
            
            
            
            JTextField tfield_kSayfa = ky_component.addTextField("kitapSayfa", 25, ButonPozisyon.UST);
            JTextField tfield_kFiyat = ky_component.addTextField("kitapFiyat", 25, ButonPozisyon.UST);
            
            JTextField tfield_kYayinEvi = ky_component.addTextField("kitapYayınevi", 25, ButonPozisyon.UST);
            JTextField tfield_kYazarAd = ky_component.addTextField("YazarAd", 25, ButonPozisyon.UST);
            JTextField tfield_kYazarSoyad = ky_component.addTextField("YazarSoyad", 25, ButonPozisyon.UST);
            
            String[] girdiler = new String[kolonIsimleri.length];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            
            textfieldArrayList.add(tfield_kID);
            textfieldArrayList.add(tfield_kAd);
            textfieldArrayList.add(tfield_kISBN);
            //textfieldArrayList.add(tfield_kKategori);
           
            textfieldArrayList.add(tfield_kSayfa);
            textfieldArrayList.add(tfield_kFiyat);
            textfieldArrayList.add(tfield_kYayinEvi);
            textfieldArrayList.add(tfield_kYazarAd);
            textfieldArrayList.add(tfield_kYazarSoyad);
            ky_component.ListenerTextField(textfieldArrayList, girdiler);
            
            JButton button_kitapEkle = ky_component.addButtonParams("Kitap Ekle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_TANIMLANMADI,girdiler);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapEkle);
            
            JButton button_kitapCikart = ky_component.addButtonParams("Kitap Çıkart", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_TANIMLANMADI,girdiler);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapCikart);
            
            JButton button_kitapGuncelle = ky_component.addButtonParams("Kitap Güncelle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_TANIMLANMADI,girdiler);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapGuncelle);
            
            
            
            
            
            //KATEGORI ISLEMLERI
            //JButton button_kategoriEkle = ky_component.addButtonParams("Kategori Ekle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_KATEGORI_EKLE,girdiler);
            //ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kategoriEkle);
            //JButton button_kategoriCikart = ky_component.addButtonParams("Kategori Çıkart", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_KATEGORI_CIKART,girdiler);
            //ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kategoriCikart);
            //JButton button_kategoriGuncelle = ky_component.addButtonParams("Kategori Güncelle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_TANIMLANMADI,girdiler);
            //ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kategoriGuncelle);
            
            JButton button_kategoriMevcut = ky_component.addButton("Kategori Tablosu", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_NW_KATEGORI_TABLOSU);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kategoriMevcut);
        
            
        }
            
        
        ky_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI managekitapYonetimFrame(){
        return kitapyonetim_frame;
    }
  
    
    
}

