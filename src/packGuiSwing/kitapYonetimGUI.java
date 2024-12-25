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
        pencereGUI.debugPrint("kitapYonetimGUI.initkitapYonetimGUI CAGRILDI");
        kitapyonetim_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            kitapyonetim_frame.initLayout();
            ky_component.infoBar(
                    "KITAP YONETIM : Kitap Adı , ISBN , Kategori , Sayfa Sayısı , Yayınevi , Yazar Adı , Yazar Soyadı EKSİKSİZ GİRİLEREK EKLEME YAPILIR",
                    "KITAP YONETIM : kitapID GIRILEREK ISTENEN KITAP SILINEBILIR", 
                    "KITAP YONETIM : kitapID Kitap Adı , ISBN , Kategori , Sayfa Sayısı , Yayınevi GÜNCELLENEBİLİR | Yazar Adı VE Yazar Soyadı GÜNCELLENEMEZ");
            
       
            
                                     //  0    1            2           3          4           5           6         7             8       9
            String kolonIsimleri[] = { "ID", "Kitap Adı","Kitap Stok", "ISBN", "Kategori","Sayfa Sayısı","Fiyat","Yayınevi","Yazar Adı","Yazar Soyadı"};
          
            JTable jtable_kitapListesi = ky_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_KITAP_LIST);
            
            //EKLEME CIKARTMA
            JTextField tfield_kID = ky_component.addTextField("ID", 25, ButonPozisyon.UST);
            JTextField tfield_kAd = ky_component.addTextField("kitapAd", 25, ButonPozisyon.UST);
            JTextField tfieField_kStok = ky_component.addTextField("kitapStok", 25, ButonPozisyon.UST);
            JTextField tfield_kISBN = ky_component.addTextField("kitapISBN", 25, ButonPozisyon.UST);
            
            //JTextField tfield_kKategori = ky_component.addTextField("KategoriAd", 25, ButonPozisyon.UST);
            JComboBox<String> cbox_kKategori = ky_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.UST, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER);
            //ky_component.setListenerCombobox(cbox_kKategori, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID);
            
            
            JTextField tfield_kSayfa = ky_component.addTextField("kitapSayfa", 25, ButonPozisyon.UST);
            JTextField tfield_kFiyat = ky_component.addTextField("kitapFiyat", 25, ButonPozisyon.UST);
            
            //JTextField tfield_kYayinEvi = ky_component.addTextField("kitapYayınevi", 25, ButonPozisyon.UST);
            JComboBox<String> cbox_kYayinevi = ky_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.UST, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_YAYINEVLERI);
                                    
            JTextField tfield_kYazarAd = ky_component.addTextField("YazarAd", 25, ButonPozisyon.UST);
            JTextField tfield_kYazarSoyad = ky_component.addTextField("YazarSoyad", 25, ButonPozisyon.UST);

//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN
            String[] girdiler = new String[kolonIsimleri.length-2]; //-2( foreign keyleri cikarttik )
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            textfieldArrayList.add(tfield_kID);
            textfieldArrayList.add(tfield_kAd);
            textfieldArrayList.add(tfieField_kStok);
            textfieldArrayList.add(tfield_kISBN);         
            textfieldArrayList.add(tfield_kSayfa);
            textfieldArrayList.add(tfield_kFiyat);
            textfieldArrayList.add(tfield_kYazarAd);
            textfieldArrayList.add(tfield_kYazarSoyad);
            ky_component.ListenerTextField(textfieldArrayList, girdiler);            
//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN

//COMBOBOX GIRDILERININ ARKAPLANDA TABLOLARDAN GETIRDIGI ID DEGERLERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN
            int comboBoxGirdiler[] = new int[2];
            ky_component.setListenerCombobox(cbox_kKategori, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID,comboBoxGirdiler,0);
            ky_component.setListenerCombobox(cbox_kYayinevi, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_YAYINEVI_ID,comboBoxGirdiler,1);
//COMBOBOX GIRDILERININ ARKAPLANDA TABLOLARDAN GETIRDIGI ID DEGERLERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN
          
            

            //Comboboxin geriye cevirdigi veriyi girdilere ekleyebilmemiz lazim
            JButton button_kitapEkle = ky_component.addButtonParams("Kitap Ekle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_KITAP_EKLE,girdiler,comboBoxGirdiler);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapEkle);
            
            
            
            JButton button_kitapCikart = ky_component.addButtonParams("Kitap Çıkart", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_KITAP_CIKART,girdiler);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapCikart);
            
            JButton button_kitapGuncelle = ky_component.addButtonParams("Kitap Güncelle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_KITAP_GUNCELLE,girdiler,comboBoxGirdiler);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapGuncelle);
            
            
            
            JButton button_kategoriMevcut = ky_component.addButton("Kategori Tablosu", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_NW_KATEGORI_TABLOSU);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kategoriMevcut);
            
            JButton button_kitapYazarTablosu = ky_component.addButton("Kitap Yazar Tablosu", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_NW_KITAP_YAZAR_TABLOSU);
            ky_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapYazarTablosu);
        
            
        }
            
        
        ky_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI managekitapYonetimFrame(){
        return kitapyonetim_frame;
    }
  
    
    
}

