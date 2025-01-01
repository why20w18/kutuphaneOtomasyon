package packGuiSwing;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import packDatabase.DatabaseIslemler;

public class kitapYonetimIstatistikler {
    pencereGUI kitapyonetimistatistikler_frame;
    pencereGUI_Component kyista_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public kitapYonetimIstatistikler(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;

        kitapyonetimistatistikler_frame = new pencereGUI("KITAP YONETIM -> ISTATISTIKLER", 600, 400);
        kyista_component = new pencereGUI_Component(kitapyonetimistatistikler_frame,this.databaseIslemler);
        kyista_component.setPanelBoyutlari(400, 180, -1, -1);
        
    }
    
 
    public void initkitapYonetimIstatistikler(boolean isLayoutActive){
        pencereGUI.debugPrint("yazarYonetimGUI.inityazarYonetimGUI CAGRILDI");
        kitapyonetimistatistikler_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            kitapyonetimistatistikler_frame.initLayout();


//////////////label ekleyip icinde////////////////////////////////////////////////////////////////////////////// 
            //1-toplam kitap sayisi+
            // -kategoriye gore toplam kitap sayisi++++++++ 
            //2-toplam kitap sayfa sayisi+
            //  -kategoriye gore toplam sayfa sayisi+++++++loop kurup idleri cekip icinde cagiririz
            //3-toplam yayinevi sayisi+
            //  -yayinevine gore kitap ve sayfa sayisi
            //4-toplam yazar sayisi+
            //5-kac farkli ulkeden yazar var
            //6-toplam kategori sayisi+
            //7-her kategoriden kac kitap var elimizde +
            //8-ortalama kitap fiyatlari+
            //9-en pahali kitap+
            //10-en ucuz kitap+
            //11-en kisa kitap+
            //12-en uzun kitap+
//////////////label ekleyip icinde//////////////////////////////////////////////////////////////////////////////
            int punto = 15;
            JLabel toplamKitapSayisi =      kyista_component.addLabel("Toplam Kitap Sayısı: "+databaseIslemler.SQL_Q_DBF_getKitapSayisi(), punto, ButonPozisyon.SOL);
            JLabel toplamKitapSayfaSayisi = kyista_component.addLabel("Toplam Kitap Sayfa Sayısı : "+databaseIslemler.SQL_Q_DBF_getToplamSayfaSayisi(),punto, ButonPozisyon.SOL);
            JLabel toplamKategoriSayisi   = kyista_component.addLabel("Toplam Kategori Sayısı: "+databaseIslemler.SQL_Q_DBF_getKategoriSayisi(),punto, ButonPozisyon.SOL);
            JLabel toplamYayineviSayisi  =  kyista_component.addLabel("Toplam Yayınevi Sayısı: "+databaseIslemler.SQL_Q_DBF_getYayineviSayisi(),punto, ButonPozisyon.SOL);
            JLabel toplamYazarSayisi  =     kyista_component.addLabel("Toplam Yazar Sayısı: "+databaseIslemler.SQL_Q_DBF_getToplamYazarSayisi(),punto, ButonPozisyon.SOL);
            JLabel farkliYazarSayisi = kyista_component.addLabel("Farklı Ülkelerden Yazar Sayısı: "+databaseIslemler.SQL_Q_DBF_farkliUlkeYazarSayisi(), punto, ButonPozisyon.SOL);
            
            JLabel blankLabel1 = kyista_component.addLabel("", punto, ButonPozisyon.SOL);
            
            JLabel blankLabel3 = kyista_component.addLabel("", punto, ButonPozisyon.SAG);
            JComboBox<String> cbox_kKategori = kyista_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.SAG, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER);
            JComboBox<String> cbox_kYayinevi = kyista_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.SAG, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_YAYINEVLERI);
            JLabel blankLabel4 = kyista_component.addLabel("", punto, ButonPozisyon.SAG);
            
            JLabel ortalamaKitapSayfaSayisi = kyista_component.addLabel("Ortalama Sayfa Sayısı: "+databaseIslemler.SQL_Q_DBF_ortalamaSayfaSayisi(), punto, ButonPozisyon.SOL);
            
            JLabel maxSayfaSayisi = kyista_component.addLabel("En Uzun Sayfa Sayısı: "+databaseIslemler.SQL_Q_DBF_maxKitapSayfa(), punto, ButonPozisyon.SOL);
            JLabel enUzunKitapAdi = kyista_component.addLabel("En Uzun Kitap: "+databaseIslemler.SQL_Q_DBF_enUzunKitapAdi(), punto, ButonPozisyon.SOL);  
            JLabel minSayfaSayisi = kyista_component.addLabel("En Kısa Sayfa Sayısı: "+databaseIslemler.SQL_Q_DBF_minKitapSayfa(), punto, ButonPozisyon.SOL);
            JLabel enKisaKitapAdi = kyista_component.addLabel("En Kısa Kitap: "+databaseIslemler.SQL_Q_DBF_enKisaKitapAd(), punto, ButonPozisyon.SOL);
            
            
            JLabel blankLabel2 = kyista_component.addLabel("", punto, ButonPozisyon.SOL);
            JLabel enPahaliKitapAd = kyista_component.addLabel("En Pahalı Kitap: "+databaseIslemler.SQL_Q_DBF_enPahaliKitapAdi(), punto, ButonPozisyon.SOL);
            JLabel enPahaliKitapFiyat = kyista_component.addLabel("En Yüksek Fiyat: "+databaseIslemler.SQL_Q_DBF_enPahaliKitapFiyat(), punto, ButonPozisyon.SOL);
            JLabel enUcuzKitapAd = kyista_component.addLabel("En Ucuz Kitap: "+databaseIslemler.SQL_Q_DBF_enUcuzKitapAd(), punto, ButonPozisyon.SOL);
            JLabel enUcuzKitapFiyat = kyista_component.addLabel("En Ucuz Fiyat: "+databaseIslemler.SQL_Q_DBF_enUcuzFiyat(), punto, ButonPozisyon.SOL);
            
            
            
            JLabel blankLabel = kyista_component.addLabel("", punto, ButonPozisyon.SOL);
            JLabel kategoridekiKitapSayisi= kyista_component.addLabel("Kategori Kitap Sayısı: "+(0),punto, ButonPozisyon.SOL);
            JLabel kategoridekiKSayfaSayi = kyista_component.addLabel("Kategori Sayfa Sayısı: "+(0),punto, ButonPozisyon.SOL);
            
            JLabel yayinevindekiKitapSayisi=kyista_component.addLabel("Yayınevi Kitap Sayısı: "+(0),punto, ButonPozisyon.SOL);
            JLabel yayinevindekiKSayfaSayi= kyista_component.addLabel("Yayınevi Sayfa Sayısı: "+(0),punto, ButonPozisyon.SOL);
            
            
            
            int comboBoxGirdiler[] = new int[2];
            kyista_component.setListenerCombobox(cbox_kKategori, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID,comboBoxGirdiler,0);
            kyista_component.setListenerCombobox(cbox_kYayinevi, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_YAYINEVI_ID,comboBoxGirdiler,1);
            
            
            JLabel kategoriLabelArray[] = new JLabel[4];
            kategoriLabelArray[0] = kategoridekiKitapSayisi;
            kategoriLabelArray[1] = kategoridekiKSayfaSayi;
            kategoriLabelArray[2] = yayinevindekiKitapSayisi;
            kategoriLabelArray[3] = yayinevindekiKSayfaSayi;
            
            JButton button_kategoriYayineviListele = kyista_component.addButtonParams("KATEGORI/YAYINEVI LISTELE",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_KATEGORI_LISTELE_LABEL,comboBoxGirdiler,kategoriLabelArray);
            
            
        }
            
        
        kyista_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI managekitapYonetimIstatistiklerFrame(){
        return kitapyonetimistatistikler_frame;
    }
  
    
    
}

