package packGuiSwing;

import com.sun.source.tree.BreakTree;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.color.CMMException;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import packDatabase.DatabaseIslemler;
import static packGuiSwing.ButonPozisyon.ALT;
import static packGuiSwing.ButonPozisyon.ORTA;
import static packGuiSwing.ButonPozisyon.SAG;
import static packGuiSwing.ButonPozisyon.SOL;
import static packGuiSwing.ButonPozisyon.UST;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_GUNEY;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_KUZEY;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_NW_ODUNC_ISLEMLERI;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_NW_PERSONEL_YONETIM;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_PERSONEL_CIKART;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_PERSONEL_EKLE;
import static packGuiSwing.CagrilacakFonksiyon.CF_TABLE_KATEGORI_YAYINEVI_TUMU;
import static packGuiSwing.CagrilacakFonksiyon.CF_TANIMLANMADI;

enum ButonPozisyon{
        UST, 
        ALT, 
        SAG, 
        SOL, 
        ORTA,
        BOSLUK_TAMAMI,
}

enum CagrilacakFonksiyon{
    CF_TANIMLANMADI,
    CF_BUTTON_KUZEY,
    CF_BUTTON_GUNEY,
    
    //YENI PENCERELER
    CF_BUTTON_NW_PERSONEL_YONETIM, // personel yonetim penceresini baslatir
    CF_BUTTON_NW_KITAP_YONETIM,   //kitap yonetim penceresini baslatir,
    CF_BUTTON_NW_KATEGORI_TABLOSU,//kategori tablosunu iceren pencereyi baslatir | personel yonetim icinde
    CF_BUTTON_NW_YAZAR_YONETIM,   //mainGUI icindeki yazar yonetim penceresini baslatir
    CF_BUTTON_NW_YAYINEVI_YONETIM, //mainGUI yayinevi init
    CF_BUTTON_NW_ODUNC_YONETIM, //odunc yonetim penceresi baslat
    CF_BUTTON_NW_KITAP_YAZAR_TABLOSU,
    
    
    //T_PERSONEL
    CF_TABLE_PERSONEL_LIST,         //databaseIslemlerdeki personel list fonksiyonunu calistirir
    CF_BUTTON_PERSONEL_EKLE,         //personel ekleme
    CF_BUTTON_PERSONEL_CIKART,        //personel cikartma   
    CF_BUTTON_PERSONEL_GUNCELLE, //personel guncelleme
    
    //T_KITAP    
    CF_TABLE_KITAP_LIST,          //databaseIslemlerdeki kitap list fonksiyonunu calistirir
    CF_TABLE_GET_MEVCUT_KATEGORILER, //mevcut kategorileri getirme
    
    CF_BUTTON_KATEGORI_EKLE,      //BOSA CIKAN ENUMLAR
    CF_BUTTON_KATEGORI_CIKART,     //BOSA CIKAN ENUMLAR
    CF_BUTTON_KATEGORI_GUNCELLE,   //BOSA CIKAN ENUMLAR
    
    CF_BUTTON_KATEGORI_EKLE_KATEGORITABLOSU, //kategori ekler ama kategori tablosu icin ozellesmistir sadece parametre degisir
    CF_BUTTON_KATEGORI_CIKART_KATEGORITABLOSU,
    CF_BUTTON_KATEGORI_GUNCELLE_KATEGORITABLOSU,
    
    CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER, //kitap ekle kisminda combobox mevcut kategorileri cekebilsin
    CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID, //doldurulan comboboxin idsini ceker
    CF_COMBOBOX_DOLDUR_MEVCUT_YAYINEVLERI,
    CF_COMBOBOX_FUNC_GET_SELECT_YAYINEVI_ID,
    
    CF_TABLE_KITAPYAZAR_JOINT_LIST,
    
    CF_BUTTON_KITAP_EKLE,
    CF_BUTTON_KITAP_CIKART,
    CF_BUTTON_YAZARI_DEGISTIR,
    CF_BUTTON_KITABI_DEGISTIR,
    CF_BUTTON_KITAP_GUNCELLE,
    
    
    //T_YAZAR
    CF_BUTTON_YAZAR_EKLE,   //yazar ekleme
    CF_TABLE_YAZAR_LIST, //yazar penceresindeki ilk tablo
    CF_BUTTON_YAZAR_CIKART,
    CF_BUTTON_YAZAR_GUNCELLE,
    
    //T_YAYINEVI
    CF_TABLE_YAYINEVI_LIST, //yayinevi penceresindeki tablo
    CF_BUTTON_YAYINEVI_EKLE,
    CF_BUTTON_YAYINEVI_CIKART,
    CF_BUTTON_YAYINEVI_GUNCELLE,
    
    //T_ODUNC
    CF_TABLE_ODUNC_LIST,
    
    //T_UYELER
    CF_BUTTON_NW_UYE_YONETIM,
    
    
    //EK_ISLEMLER_NFO OVERLOAD
    //1.SEKME 
    CF_1_ISTATISTIKLER_KITAP_YONETIM,
    
    //2.SEKME
    CF_2_ARTTIRIM_AZALTIM_KITAP_YONETIM,
    
    //3.SEKME
    CF_3_KITAP_FILTRELEME_KITAP_YONETIM,
    
    //KITAP YONETIM ISTATISTIKLER LABEL FONKSIYONLARI
    CF_LABEL_KATEGORI_KITAP_SAYISI, //kullanilmadi
    CF_LABEL_KATEGORI_KITAP_SAYFA_SAYISI,//kullanilmadi
    CF_KATEGORI_LISTELE_LABEL,
    
    //KITAP FILTRELEME 
    CF_LISTELE_KATEGORI_FILTRE,
    CF_LISTELE_TUMU_FILTRE,
    CF_LISTELE_YAYINEVI_FILTRE,
    
    CF_TABLE_KATEGORI_YAYINEVI_TUMU,
    CF_LISTEYI_TEMIZLE,
    CF_TABLE_TEMIZLE,
    
    //ARTTIRIM AZALTIM KITAP YONETIMI
    CF_GUNCELLEME_KATEGORIYE_GORE,
    CF_GUNCELLEME_YAYINEVINE_GORE,
    CF_GUNCELLEME_TUMUNE_GORE,
    
    
    CF_TABLE_KATEGORI_LOG,
    CF_TABLE_YAZAR_LOG,
    
    
    //T_UYELER
    CF_TABLE_UYE_LIST,
    CF_BUTTON_NW_UYE_YONETIM_OGRENCI_ISLEMLERI,
    CF_BUTTON_NW_UYE_YONETIM_SIVIL_ISLEMLERI,
    CF_BUTTON_NW_UYE_YONETIM_OZELSEKTOR_ISLEMLERI,
    
    CF_BUTTON_OGRENCI_ISLEMLERI_EKLE,
    CF_BUTTON_OGRENCI_ISLEMLERI_CIKART,
    CF_BUTTON_OGRENCI_ISLEMLERI_GUNCELLE,
    CF_COMBOBOX_INDEX_CEVIR,
    
    CF_BUTTON_SIVIL_ISLEMLERI_EKLE,
    CF_BUTTON_SIVIL_ISLEMLERI_CIKARTMA,
    CF_BUTTON_OZELSEKTOR_ISLEMLERI_EKLEME,
    CF_BUTTON_OZELSEKTOR_ISLEMLERI_CIKARTMA,
    
    //T_ODUNC
    CF_BUTTON_NW_ODUNC_ISLEMLERI,
    CF_COMBOBOX_DOLDUR_MEVCUT_YAZARLAR,
    CF_COMBOBOX_DOLDUR_MEVCUT_KITAPLAR,
    CF_COMBOBOX_FUNC_GET_SELECT_KITAP_ID,
    CF_COMBOBOX_FUNC_GET_SELECT_YAZAR_ID,
    CF_BUTTON_ODUNC_EKLEME,
    CF_COMBOBOX_DOLDUR_MEVCUT_UYE_AD_SOYAD,
    CF_COMBOBOX_FUNC_GET_SELECT_UYE_ID,
    CF_BUTTON_ODUNC_CIKARTMA,
    
}

public class pencereGUI_Component {
    //pencereGUI_Component ATTR
    private pencereGUI mevcutPencere;
    private boolean isUpdate;
    private int panelCounter[];
    public int comboboxRetID;
    
    private DatabaseIslemler databaseIslemler;
    
    JPanel centerPanel;
    JPanel westPanel;
    JPanel southPanel;
    JPanel northPanel;
    JPanel eastPanel;
    
    //pencereGUI_Component CONSTRUCTOR
    public pencereGUI_Component(pencereGUI mevcutPencere,DatabaseIslemler databaseIslemler){
        this.databaseIslemler = databaseIslemler;
        this.mevcutPencere = mevcutPencere;
        isUpdate = false;
        panelCounter = new int[4];
        
        
        for(int i = 0 ; i < 4 ; i++)
            panelCounter[i] = 0;
        
        centerPanel = new JPanel();
        westPanel = new JPanel();
        southPanel = new JPanel();
        northPanel = new JPanel();
        eastPanel = new JPanel();
    
        //PANELLERIN BOYUTLARI
        westPanel.setPreferredSize(new Dimension(300, 0));
        eastPanel.setPreferredSize(new Dimension(200,0));
        northPanel.setPreferredSize(new Dimension(0,50));
        southPanel.setPreferredSize(new Dimension(0,50));
    }
    
    public void setPanelBoyutlari(int solSize , int sagSize , int ustSize , int altSize){
        if(solSize == -1)
                   westPanel.setPreferredSize(new Dimension(300, 0));
        else 
        westPanel.setPreferredSize(new Dimension(solSize, 0));            
        
        if(sagSize == -1)
                    eastPanel.setPreferredSize(new Dimension(200,0));
        else 
        eastPanel.setPreferredSize(new Dimension(sagSize,0));
        
        if(ustSize == -1)
                    northPanel.setPreferredSize(new Dimension(0,50));
        else 
        northPanel.setPreferredSize(new Dimension(0,ustSize));
        
        if(altSize == -1)
                    southPanel.setPreferredSize(new Dimension(0,50));
        else 
        southPanel.setPreferredSize(new Dimension(0,altSize));
    }
    
    public JTextArea konsolEkran(ButonPozisyon pozisyon){
        JTextArea konsol = new JTextArea();
        komponentPozisyonlandiricisi(konsol, pozisyon);
        
        konsol.setEditable(false);
        konsol.setFont(new Font("Courier New", Font.BOLD, 15));

        konsol.setBackground(Color.WHITE);
        konsol.setForeground(Color.BLACK);
        
        JScrollPane scroll = new JScrollPane(konsol);
        komponentPozisyonlandiricisi(scroll, pozisyon);
        
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
     
        return konsol;
    }
    
 public void enterListener(JTextField cmdInput , JTextArea konsol , konsolCommand cmd){
        cmdInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = cmdInput.getText();
                if (!inputText.isEmpty()){
                    konsol.append("KULLANICI_ISTEK >> " + inputText + "\n");
                    konsol.append("\n[VERİTABANI]:" + cmd.komutIslem(inputText,konsol,15) + "\n\n");
                    cmdInput.setText("");
                    konsol.setCaretPosition(konsol.getDocument().getLength());
                }
            }
        });
    }
    

 
    
    
    //BUTON ISLEMLERI
    public JButton addButton(String buttonText , ButonPozisyon butonPozisyon , CagrilacakFonksiyon butonFonksiyonu){
        //pencereGUI.debugPrint(countUpdate+"COMPONENTLER GUNCELLENDI MI : "+this.isUpdate + "<------");

        
       JButton button = new JButton(buttonText);
       button.setMinimumSize(new Dimension(100, 50));
       komponentPozisyonlandiricisi(button, butonPozisyon);
       //Border border = new LineBorder(Color.BLACK, 2);
       //button.setBorder(border);

       
        

       butonFonksiyonlari CF_Caller = new butonFonksiyonlari(button, mevcutPencere,databaseIslemler);
       
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                switch(butonFonksiyonu){
                    case CF_TANIMLANMADI:
                        CF_Caller.CF_TANIMLANMADI_Func();
                        break;
                    
                    case CF_BUTTON_GUNEY:
                        CF_Caller.CF_GUNEY_Func();
                        break;
                        
                        
                    case CF_BUTTON_KUZEY:
                        CF_Caller.CF_KUZEY_Func();
                        break;
                        
                    case CF_BUTTON_NW_PERSONEL_YONETIM:
                        CF_Caller.CF_BUTTON_NW_PERSONEL_YONETIM_Func();
                        break;
                        
                    case CF_BUTTON_NW_KITAP_YONETIM:
                        CF_Caller.CF_BUTTON_NW_KITAP_YONETIM_Func();
                        break;
                    case CF_BUTTON_NW_KATEGORI_TABLOSU:
                        CF_Caller.CF_BUTTON_NW_KATEGORI_TABLOSU_Func();
                        break;
                    case CF_BUTTON_NW_YAZAR_YONETIM:
                        CF_Caller.CF_BUTTON_NW_YAZAR_YONETIM_Func();
                        break;
                        
                    case CF_BUTTON_NW_YAYINEVI_YONETIM:
                        CF_Caller.CF_BUTTON_NW_YAYINEVI_YONETIM_Func();
                        break;
                        
                    case CF_BUTTON_NW_ODUNC_YONETIM:
                        CF_Caller.CF_BUTTON_NW_ODUNC_YONETIM_Func();
                        break;
                        
                    case CF_BUTTON_NW_KITAP_YAZAR_TABLOSU:
                        CF_Caller.CF_BUTTON_NW_KITAP_YAZAR_TABLOSU_Func();
                        break;
                        
                    case CF_BUTTON_NW_UYE_YONETIM:
                        CF_Caller.CF_BUTTON_NW_UYE_YONETIM_Func();
                        break;
                        
                    case CF_BUTTON_NW_UYE_YONETIM_OGRENCI_ISLEMLERI:
                        CF_Caller.CF_BUTTON_NW_UYE_YONETIM_OGRENCI_ISLEMLERI_Func();
                        break;
                        
                    case CF_BUTTON_NW_UYE_YONETIM_SIVIL_ISLEMLERI:
                        CF_Caller.CF_BUTTON_NW_UYE_YONETIM_SIVIL_ISLEMLERI_Func();
                        break;
                        
                    case CF_BUTTON_NW_UYE_YONETIM_OZELSEKTOR_ISLEMLERI:
                        CF_Caller.CF_BUTTON_NW_UYE_YONETIM_OZELSEKTOR_ISLEMLERI_Func();
                        break;
                        
                    case CF_BUTTON_NW_ODUNC_ISLEMLERI:
                        CF_Caller.CF_BUTTON_NW_ODUNC_ISLEMLERI_Func();
                        break;
                                default:
            System.out.println("TANIMLANMAMIS ENUM DEGERI :: enum CagrilacakFonksiyon | addButton | pencereGUI_Component");
            break;
                                                
                        
                        
                }
            }
        });
        
        
       
        

        return button;
    }

     public JButton addButtonParams(String buttonText , ButonPozisyon butonPozisyon , CagrilacakFonksiyon butonFonksiyonu,String girdilerString[]){
       JButton button = new JButton(buttonText);
       button.setMinimumSize(new Dimension(100, 50));
       komponentPozisyonlandiricisi(button, butonPozisyon); 



       butonFonksiyonlari CF_Caller = new butonFonksiyonlari(button, mevcutPencere,databaseIslemler);
       

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean paramControl = true;
                switch(butonFonksiyonu){
                    case CF_TANIMLANMADI:
                        CF_Caller.CF_TANIMLANMADI_Func();
                        break;
                    
                    case CF_BUTTON_GUNEY:
                        CF_Caller.CF_GUNEY_Func();
                        break;
                        
                        
                    case CF_BUTTON_KUZEY:
                        CF_Caller.CF_KUZEY_Func();
                        break;
                    
                    case CF_BUTTON_PERSONEL_EKLE:
                        boolean paramCond2 = true;
                        if(!checkGirdiStringISNULL_ISEMPTY(1,girdilerString.length, girdilerString, CF_Caller, "EKSIK KOLONLAR VAR BU SEKILDE EKLEMEYE IZIN YOK !"))
                            if(paramControl == true && ((girdilerString[4].equals("HIZMETLI") ) || (girdilerString[4].equals("YONETICI")))){
                                CF_Caller.CF_BUTTON_PERSONEL_EKLE_Func(girdilerString[1],girdilerString[2],Double.parseDouble(girdilerString[3]),girdilerString[4],girdilerString[5]);
                                CF_Caller.messageBoxOK("PERSONEL EKLENDI !");
                                 paramCond2 = false;
                            }
                      
                        else if(paramCond2){
                            CF_Caller.messageBoxOK("PERSONEL TURU HIZMETLI VEYA YONETICI OLMALIDIR");
                            paramControl = true;
                        }
                        break;
                        
                    case CF_BUTTON_PERSONEL_CIKART:
                        CF_Caller.CF_BUTTON_PERSONEL_CIKART_Func(Integer.parseInt(girdilerString[0]));
                        CF_Caller.messageBoxOK("PERSONEL SILINDI !");
                        break;    
                        
                        
                    case CF_BUTTON_PERSONEL_GUNCELLE:
                        Integer personelID = Integer.parseInt(girdilerString[0]);
                        String personelAd = girdilerString[1].isEmpty() ? null : girdilerString[1];
                        String personelSoyad = girdilerString[2].isEmpty() ? null : girdilerString[2];
                        Double personelMaas = girdilerString[3].isEmpty() ? null : Double.parseDouble(girdilerString[3]);
                        String personelTuru = girdilerString[4].isEmpty() ? null : girdilerString[4];
                        String ekBilgiTHizmetliTYonetici = girdilerString[5].isEmpty() ? null : girdilerString[5];
                        
                        System.out.println("EKBILGI ::: " + girdilerString[5]);
                        CF_Caller.CF_BUTTON_PERSONEL_GUNCELLE_Func(personelID, personelAd, personelSoyad, personelMaas,personelTuru,ekBilgiTHizmetliTYonetici);
                        CF_Caller.messageBoxOK("PERSONEL GUNCELLENDI !");
                        
                        
                        break;
                        
                        
                    case CF_BUTTON_KATEGORI_CIKART_KATEGORITABLOSU:
                        if(!checkGirdiStringISNULL_ISEMPTY(0, 1, girdilerString, CF_Caller,"ÇIKARTMA İŞLEMİ İÇİN GEREKLİ PARAMETRELERİ GİRMEMİŞSİNİZ !"))
                            CF_Caller.CF_BUTTON_KATEGORI_CIKART_Func(Integer.parseInt(girdilerString[0]));    
                        
                        
                        break;
                        
                    case CF_BUTTON_KATEGORI_EKLE_KATEGORITABLOSU:
                         paramControl = true;
                        String mevcutKategorilerKategoriTablosu[] = databaseIslemler.SQL_Q_mevcutKategoriler();
                        
                        for(String s : mevcutKategorilerKategoriTablosu){
                            if(girdilerString[1].toLowerCase().equals(s.toLowerCase())){
                                CF_Caller.messageBoxOK("BU KATEGORİ DAHA ÖNCEDEN EKLENMİŞTİR !");
                                paramControl = false;
                            }
                        }
                        if(girdilerString[1] != null && paramControl == true && !girdilerString[1].isBlank()){
                            CF_Caller.CF_BUTTON_KATEGORI_EKLE_Func(girdilerString[1]);
                            CF_Caller.messageBoxOK("KATEGORI EKLENMISTIR !");
                        }
                        break;
                        
                    case CF_BUTTON_KATEGORI_EKLE:
                        paramControl = true;
                        String mevcutKategoriler[] = databaseIslemler.SQL_Q_mevcutKategoriler();
                        
                        for(String s : mevcutKategoriler){
                            if(girdilerString[0].toLowerCase().equals(s.toLowerCase())){
                                CF_Caller.messageBoxOK("BU KATEGORİ DAHA ÖNCEDEN EKLENMİŞTİR !");
                                paramControl = false;
                            }
                        }
                        if(girdilerString[3] != null && paramControl == true && !girdilerString[3].isBlank()){
                            CF_Caller.CF_BUTTON_KATEGORI_EKLE_Func(girdilerString[3]);
                            CF_Caller.messageBoxOK("KATEGORI EKLENMISTIR !");
                        }
                       break;
                       
                    case CF_BUTTON_KATEGORI_CIKART:
                        //TAŞINDI
                        
                        break;
                        
                        
                    case CF_BUTTON_KATEGORI_GUNCELLE_KATEGORITABLOSU:
                        int guncellenenSatir = 0;
                        paramControl = true;
                        
                        if(!girdilerString[1].isEmpty()){
                            guncellenenSatir = CF_Caller.CF_BUTTON_KATEGORI_GUNCELLE_KATEGORITABLOSU_Func(Integer.parseInt(girdilerString[0]), girdilerString[1]);
                            paramControl = false;
                        }
                        
                        if(guncellenenSatir <= 0 && paramControl == false){
                            CF_Caller.messageBoxOK("KATEGORI GUNCELLENEMEDI : GUNCELLENEN SATIR SAYISI : "+guncellenenSatir);
                        }
                        break;
                        
                    case CF_BUTTON_YAZAR_EKLE:
                        CF_Caller.CF_BUTTON_YAZAR_EKLE_Func(girdilerString[1], girdilerString[2], girdilerString[3]);
                        break;
                        
                    case CF_BUTTON_YAZAR_CIKART:
                        CF_Caller.CF_BUTTON_YAZAR_CIKART_Func(Integer.parseInt(girdilerString[0]));
                                break;
                                
                                
                    case CF_BUTTON_YAZAR_GUNCELLE:
                        CF_Caller.CF_BUTTON_YAZAR_GUNCELLE_Func(Integer.parseInt(girdilerString[0]), girdilerString[1], girdilerString[2], girdilerString[3]);
                                break;
                        
                    case CF_BUTTON_YAYINEVI_EKLE:
                        paramControl = true;
                        
                        if(!checkGirdiStringISNULL_ISEMPTY(1,girdilerString.length, girdilerString, CF_Caller, "EKSIK KOLONLAR VAR BU SEKILDE EKLEMEYE IZIN YOK !")){
                            CF_Caller.CF_BUTTON_YAYINEVI_EKLE_Func(girdilerString[1], girdilerString[2]);
                            CF_Caller.messageBoxOK("YAYINEVI EKLENMISTIR !");
                        }
                        break;
                        
                    case CF_BUTTON_YAYINEVI_CIKART:
                         if(!checkGirdiStringISNULL_ISEMPTY(0,1, girdilerString, CF_Caller, "yayıneviID GIRILMEDEN CIKARTMA YAPMAYA IZIN YOK !")){
                            CF_Caller.CF_BUTTON_YAYINEVI_CIKART_Func(Integer.parseInt(girdilerString[0]));
                            CF_Caller.messageBoxOK("YAYINEVI CIKARTILMISTIR !");
                        }
                        break;
                    case CF_BUTTON_YAYINEVI_GUNCELLE:
                      //  if(!checkGirdiStringISNULL_ISEMPTY(0, girdilerString.length,girdilerString,CF_Caller, "TUM KOLONLARI GIRMEDEN GUNCELLEME YAPMAYA IZIN YOK !")){
                        //ZATEN GUNCELLEMEDE TUM TEXTFIELDLARI DOLDURMAK GIBI BIR ZORUNLULUK YOKTUR  
                        CF_Caller.CF_BUTTON_YAYINEVI_GUNCELLE_Func(Integer.parseInt(girdilerString[0]), girdilerString[1], girdilerString[2]);
                            CF_Caller.messageBoxOK("YAYINEVI GUNCELLENMISTIR !");
                        //}
                        break;
                        
                    case CF_BUTTON_KITAP_CIKART:
                        CF_Caller.CF_BUTTON_KITAP_CIKART_Func(Integer.parseInt(girdilerString[0]));
                        break;
                        
                    case CF_BUTTON_YAZARI_DEGISTIR:
                        CF_Caller.CF_BUTTON_YAZARI_DEGISTIR_Func(Integer.parseInt(girdilerString[0]), Integer.parseInt(girdilerString[1]));
                        break;
                    
                    case CF_BUTTON_KITABI_DEGISTIR:
                        CF_Caller.CF_BUTTON_KITABI_DEGISTIR_Func(Integer.parseInt(girdilerString[0]), Integer.parseInt(girdilerString[1]));
                        break;
                        
                   
                        
                        
                        
                        default:
            System.out.println("TANIMLANMAMIS ENUM DEGERI :: enum CagrilacakFonksiyon | addButtonParams | pencereGUI_Component");
            break;
                        
                }
            }
        });
        
  

        return button;
    }
    
     
      public JButton addButtonParams(String buttonText , ButonPozisyon butonPozisyon , CagrilacakFonksiyon butonFonksiyonu,String girdilerString[] ,int girdilerInteger[]){
       JButton button = new JButton(buttonText);
       button.setMinimumSize(new Dimension(100, 50));
       komponentPozisyonlandiricisi(button, butonPozisyon); 



       butonFonksiyonlari CF_Caller = new butonFonksiyonlari(button, mevcutPencere,databaseIslemler);
       

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean paramControl = true;
                switch(butonFonksiyonu){
                    case CF_TANIMLANMADI:
                        CF_Caller.CF_TANIMLANMADI_Func();
                        break;
                 
                        
                    case CF_BUTTON_KITAP_EKLE:
                        System.out.println("girdilerInteger[0]=KATEGORI_ID="+girdilerInteger[0]+"       girdilerInteger[1]=YAYIN_EVI_ID="+girdilerInteger[1]);
                        System.out.println("GIRDILERSTRING[1]=KITAP_AD  " + girdilerString[1]);   //KITAP AD          STRING
                        System.out.println("GIRDILERSTRING[2]=KITAP_STOK  " + girdilerString[2]);   //KITAP STOK        INT
                        System.out.println("GIRDILERSTRING[3]=KITAP_ISBN  " + girdilerString[3]);   //KITAP FIYAT       DOUBLE
                        System.out.println("GIRDILERSTRING[4]=KITAP_FIYAT  " + girdilerString[4]);   //KITAP ISBN        STRING
                        System.out.println("GIRDILERSTRING[5]=KITAP_SAYFA  " + girdilerString[5]);   //KITAP SAYFA_SAY   INT
                        System.out.println("GIRDILERSTRING[6]=YAZAR_AD  " + girdilerString[6]);   //YAZAR AD          STRING
                        System.out.println("GIRDILERSTRING[7]=YAZAR_SOYAD  " + girdilerString[7]);   //YAZAR SOYAD       STRING

                        /*
                        
public void CF_BUTTON_KITAP_EKLE_Func(
            String kitapAd,
            int kitapStok,
            String ISBN,
            int kitapSayfaSayisi,
            double kitapFiyat,
            String yazarAd,
            String yazarSoyad,
            int kategoriID,
            int yayineviID)                        
                        */
                        
                        
                        if(!checkGirdiStringISNULL_ISEMPTY(1, girdilerString.length, girdilerString, CF_Caller, "EKSIK PARAMETRELERLE EKLEME YAPAMAZSINIZ"))
                         CF_Caller.CF_BUTTON_KITAP_EKLE_Func(
                                                            girdilerString[1], //kitap_ad
                                                            Integer.parseInt(girdilerString[2]), //kitapStok 
                                                            girdilerString[3], //isbn
                                                            Integer.parseInt(girdilerString[4]), //sayfa sayisi 
                                                            Double.parseDouble(girdilerString[5]), //fiyat
                                                            girdilerString[6],//yazarad 
                                                            girdilerString[7], //yazarsoyad 
                                                            girdilerInteger[0],//kategoriid
                                                            girdilerInteger[1]//yayineviid
                                                            );
                        
                        System.out.println("KITAP EKLEME FONK CAGRILDI");
                        break;
                        
  
             case CF_BUTTON_KITAP_GUNCELLE:
                 if(!checkGirdiStringISNULL_ISEMPTY(0, girdilerString.length, girdilerString, CF_Caller, "EKSIK KOLONGIRDIDIGINIZ ICIN GUNCELLEMEYE IZIN YOKTUR !"))
                        CF_Caller.CF_BUTTON_KITAP_GUNCELLE_Func(Integer.parseInt(girdilerString[0]),
                                girdilerString[1],
                                Integer.parseInt(girdilerString[2]),
                                girdilerString[3],
                                Integer.parseInt(girdilerString[4]),
                                Double.parseDouble(girdilerString[5]),
                                girdilerString[6],
                                girdilerString[7],
                                girdilerInteger[0],
                                girdilerInteger[1]
                        );
                        break;
            
            
             case CF_GUNCELLEME_TUMUNE_GORE:{
            //DBSP_tumKitapFiyatlariArttirma
                   String tumStr = girdilerString[0];
                   String pozNeg = tumStr.substring(0, 1); 
                   String sayi = tumStr.substring(1); 

                   System.out.println("ISARET: " + pozNeg);
                   System.out.println("SAYI: " + sayi);

                   if(pozNeg.equals("+")){ //KATEGORI 0 <=> YAYINEVI 1
                       //ARTTIRMA
                       databaseIslemler.DBSP_tumKitapFiyatlariArttirma(Double.parseDouble(sayi));
                   }

                   else if(pozNeg.equals("-")){
                       //AZALTMA
                       databaseIslemler.DBSP_tumKitapFiyatlariAzaltma(Double.parseDouble(sayi));
                   }
             }
                 break;
                 
                 
             case CF_GUNCELLEME_KATEGORIYE_GORE:{

                            String tumStr = girdilerString[0];
                            String pozNeg = tumStr.substring(0, 1); 
                            String sayi = tumStr.substring(1); 

                            System.out.println("ISARET: " + pozNeg);
                            System.out.println("SAYI: " + sayi);

                            if(pozNeg.equals("+")){ //KATEGORI 0 <=> YAYINEVI 1
                                //ARTTIRMA
                                databaseIslemler.DBSP_kitapFiyatlariArttirYuzdeKategoriye(Double.parseDouble(sayi),girdilerInteger[0]);
                            }

                            else if(pozNeg.equals("-")){
                                //AZALTMA
                                databaseIslemler.DBSP_kitapFiyatlariAzaltYuzdeKategoriye(Double.parseDouble(sayi), girdilerInteger[0]);
                                }
            }
            break;
                 
             case CF_GUNCELLEME_YAYINEVINE_GORE:{

                            String tumStr = girdilerString[0];
                            String pozNeg = tumStr.substring(0, 1); 
                            String sayi = tumStr.substring(1); 

                            System.out.println("ISARET: " + pozNeg);
                            System.out.println("SAYI: " + sayi);

                            if(pozNeg.equals("+")){ //KATEGORI 0 <=> YAYINEVI 1
                                //ARTTIRMA
                                databaseIslemler.DBSP_kitapFiyatlariArttirYayinevine(Double.parseDouble(sayi), girdilerInteger[1]);
                            }
                            else if(pozNeg.equals("-")){
                                //AZALTMA
                                databaseIslemler.DBSP_kitapFiyatlariAzaltmaYayinevine(Double.parseDouble(sayi), girdilerInteger[1]);
                            }
                            
            }
             break;
           
             case CF_BUTTON_OGRENCI_ISLEMLERI_EKLE:{
                 /*PARAMETRELER VE KARSILIKLARI
                 girdilerInt[0] => uyeCinsiyet 0=K 1=E
                 girdilerInt[1] => 0=Ortaöğretim 1=Lisans 2=Lisansüstü
                 girdilerInt[2] => 0=İlkokul 1=Ortaokul 3=Lise
                 
                 girdilerString[0] => uyeID
                 girdilerString[1] => uyeAd
                 girdilerString[2] => uyeSoyad
                 girdilerString[3] => uyeTCNO
                 girdilerString[4] => uyeUcret
                 girdilerString[5] => uyeIndirimMiktari
                 
                 girdilerString[6] => ogrenciOkulAd
                 girdilerString[7] => bolumSTRVeyaSinifINTOrtak
                 girdilerString[8] => ortalamaOrtak
                 */
                 /*
           CF_BUTTON_OGRENCI_ISLEMLERI_EKLE_Func(
                 String uyeAd,+
                 String uyeSoyad,+
                 String uyeTCNO,+
                 double uyeUcret,+
                 double uyeIndirimMiktar,+
                 String ogrenciOkulAd,+
                 String bolumSTRVeyaSinifINTOrtak,+
                 double ortalamaOrtak,+
                 int cinsiyet,
                 int kademelerLisansLisansustu,
                 int kademelerLiseOrtaokul,
                 )                 
                 */
                 
                 CF_Caller.CF_BUTTON_OGRENCI_ISLEMLERI_EKLE_Func(
                         girdilerString[1], 
                         girdilerString[2], 
                         girdilerString[3], 
                         Double.parseDouble(girdilerString[4]),
                         Double.parseDouble(girdilerString[5]),
                         girdilerString[6], 
                          girdilerString[7], 
                          Double.parseDouble(girdilerString[8]),
                         girdilerInteger[0],
                         girdilerInteger[1],
                         girdilerInteger[2]
                 );
                 
             }
             break;
             
             case CF_BUTTON_OGRENCI_ISLEMLERI_CIKART:{
                 CF_Caller.CF_BUTTON_OGRENCI_ISLEMLERI_CIKART_Func(Integer.parseInt(girdilerString[0]));
             }
                 break;
                 
         // SQL_Q_sivilEkle(String uyeAd , String uyeSoyad , String uyeTCNO ,int uyeCinsiyet ,double uyelikUcreti,double uyeIndirimMiktari,
        // double sivilGelirMiktari)  
             case CF_BUTTON_SIVIL_ISLEMLERI_EKLE:
             {
                 CF_Caller.CF_BUTTON_SIVIL_ISLEMLERI_EKLE_Func(
                         girdilerString[1], 
                         girdilerString[2],
                         girdilerString[3], 
                         girdilerInteger[0],
                         Double.parseDouble(girdilerString[4]),
                         Double.parseDouble(girdilerString[5]),
                         Double.parseDouble(girdilerString[6])
                 );
                 
                 
             }
                 break;
                 
                 
             case CF_BUTTON_SIVIL_ISLEMLERI_CIKARTMA:
                 CF_Caller.CF_BUTTON_SIVIL_ISLEMLERI_CIKARTMA_Func(Integer.parseInt(girdilerString[0]));
                 break;
                 
                 
             case CF_BUTTON_OZELSEKTOR_ISLEMLERI_EKLEME:
                 //SQL_Q_ozelSektorEkle(String uyeAd , String uyeSoyad , String uyeTCNO ,int uyeCinsiyet ,double uyelikUcreti,double uyeIndirimMiktari,boolean kurumAntlasmasi)
                 boolean antlasma = false;
                 if(girdilerString[6].trim().toLowerCase().equals("evet") 
                         || girdilerString[6].trim().toLowerCase().equals("e")
                         || girdilerString[6].trim().toLowerCase().equals("true")
                         || girdilerString[6].trim().toLowerCase().equals("yes")
                         || girdilerString[6].trim().toLowerCase().equals("t")
                         || girdilerString[6].trim().toLowerCase().equals("y")
                         )
                     antlasma = true;
                 else if(girdilerString[6].trim().toLowerCase().equals("hayır") 
                         || girdilerString[6].trim().toLowerCase().equals("h")
                         || girdilerString[6].trim().toLowerCase().equals("hayir")
                         || girdilerString[6].trim().toLowerCase().equals("false")
                         || girdilerString[6].trim().toLowerCase().equals("f")
                         || girdilerString[6].trim().toLowerCase().equals("no")
                         || girdilerString[6].trim().toLowerCase().equals("n")
                         )
                     antlasma = false;
                 
                 
                 CF_Caller.CF_BUTTON_OZELSEKTOR_ISLEMLERI_EKLEME_Func(
                         girdilerString[1],
                         girdilerString[2], 
                         girdilerString[3], 
                         girdilerInteger[0],
                         Double.parseDouble(girdilerString[4]),
                         Double.parseDouble(girdilerString[5]),
                         antlasma
                 );
                 
                 break;
                 
             case CF_BUTTON_OZELSEKTOR_ISLEMLERI_CIKARTMA:
                 CF_Caller.CF_BUTTON_OZELSEKTOR_ISLEMLERI_CIKARTMA_Func(Integer.parseInt(girdilerString[0]));
                 break;
             
             case CF_BUTTON_ODUNC_EKLEME:
                 CF_Caller.CF_BUTTON_ODUNC_EKLEME_Func(girdilerInteger[0], girdilerInteger[1], Integer.parseInt(girdilerString[0]));
                 break;
                 
             case CF_BUTTON_ODUNC_CIKARTMA:
                 CF_Caller.CF_BUTTON_ODUNC_CIKARTMA_Func(girdilerInteger[0], girdilerInteger[1]);
                 break;
                 
             
            default:
            System.out.println("TANIMLANMAMIS ENUM DEGERI :: enum CagrilacakFonksiyon | addButtonParams | pencereGUI_Component");
            break;
            
            
            
                }
            }
        });
        
  

        return button;
    }
       
       
   public JButton addButtonParams(String buttonText , ButonPozisyon butonPozisyon , CagrilacakFonksiyon butonFonksiyonu,int girdilerInt[],JLabel degisecekLabel[]){
       JButton button = new JButton(buttonText);
       button.setMinimumSize(new Dimension(100, 50));
       komponentPozisyonlandiricisi(button, butonPozisyon); 



       butonFonksiyonlari CF_Caller = new butonFonksiyonlari(button, mevcutPencere,databaseIslemler);
       

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean paramControl = true;
                switch(butonFonksiyonu){
                    case CF_TANIMLANMADI:
                        CF_Caller.CF_TANIMLANMADI_Func();
                        break;
                        
                    case CF_KATEGORI_LISTELE_LABEL:
                        degisecekLabel[0].setText("Kategori Kitap Sayısı: "+databaseIslemler.SQL_Q_DBF_getKitapSayisi_pKategoriID(girdilerInt[0]));
                        degisecekLabel[1].setText("Kategori Sayfa Sayısı: "+databaseIslemler.SQL_Q_DBF_getToplamSayfaSayisi_pKategoriID(girdilerInt[0]));
                        degisecekLabel[2].setText("Yayınevi Kitap Sayısı: "+databaseIslemler.SQL_Q_DBF_getYayineviKitapSayisi(girdilerInt[1]));
                        degisecekLabel[3].setText("Yayınevi Sayfa Sayısı: "+databaseIslemler.SQL_Q_DBF_getYayineviKitapSayfaSayisi(girdilerInt[1]));
                        break;
                        
                    //case CF_KATEGORI_YAYINEVI_LISTELE_LABEL: //yayinevi ve kategoriye gore filtreleme
                        
                        
                      //  break;
                 
                        
                  
            
                                              
                        
                        default:
            System.out.println("TANIMLANMAMIS ENUM DEGERI :: enum CagrilacakFonksiyon | addButtonParams | pencereGUI_Component");
            break;
            
            
                }
            }
        });
        
  

        return button;
    }
   
    
    
    public JTextField addTextField(String baslangicText , int maxKapasite , int yaziPunto , Color arkaplanRenk, ButonPozisyon textFieldPozisyon){
          
        JTextField textField = new JTextField(maxKapasite);
        komponentPozisyonlandiricisi(textField, textFieldPozisyon); 

        
        
        textField.setText(baslangicText);
        textField.setBackground(arkaplanRenk);
        textField.setFont(new Font("Arial", Font.PLAIN, yaziPunto));
        return textField;
    }
    
    public JTextField addTextField(String baslangicText , int maxKapasite , ButonPozisyon textFieldPozisyon){
        JTextField textField = new JTextField(maxKapasite);
        komponentPozisyonlandiricisi(textField, textFieldPozisyon); 

        textField.setText(baslangicText);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        return textField;
    }
    
   
    void ListenerTextField(ArrayList<JTextField> textFieldsArrayList, String[] girdiListesi) {
    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField source = (JTextField) e.getSource(); //tetiklenen textfield alindi
            String textInput = source.getText(); //tetiklenenin icindeki mesaj cekildi

            //textfield arraylistini gezip hangisinin tetiklendigini bulma
            for (int i = 0; i < textFieldsArrayList.size(); i++) {
                if (source == textFieldsArrayList.get(i)) { //eger kaynak uyusuyorsa
                    girdiListesi[i] = textInput; //indexe yerlestirme
                    break;
                }
            }

            //debugging
            System.out.println("STRING ARRAYE EKLENEN: " + textInput);
            System.out.println("MEVCUT ARRAY: ");
            for (String girdi : girdiListesi) {
                System.out.print(girdi+" ");
            }
            //girdiyi silme
            source.setText("");
        }
    };

    //listenerlari ekleme
    for (JTextField textField : textFieldsArrayList) {
        textField.addActionListener(listener);
    }
}
    
public boolean checkGirdiStringISNULL_ISEMPTY(int startIndex,int argc , String girdilerString[],butonFonksiyonlari CF_Caller , String MSG){
    for(int i = startIndex ; i < argc ; i++){
        if(girdilerString[i] == null || girdilerString[i].isEmpty()){
            CF_Caller.messageBoxOK(MSG);
                return true;
                    }
            }
return false; 
}
    
void all_input_textFieldButton(ArrayList<JTextField> textfieldArrayList,String girdiler[],JButton myButton){
    myButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < textfieldArrayList.size(); i++) {
            girdiler[i] = textfieldArrayList.get(i).getText();
        }
        
        System.out.println("Girdi verileri:");
        for (String girdi : girdiler) {
            System.out.println(girdi);
        }
    }
});
}


void all_input_comboBoxButton(ArrayList<JComboBox<String>> comboBoxArrayList, String[] girdiler, JButton myButton) {
    myButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < comboBoxArrayList.size(); i++) {
                girdiler[i] = comboBoxArrayList.get(i).getSelectedItem().toString();
            }
            
            System.out.println("Combobox girdileri:");
            for (String girdi : girdiler) {
                System.out.println(girdi);
            }
        }
    });
}

    
    public JComboBox<String> addDoldurucuComboBox(String comboboxSecenekleri[],int genislik,int yukseklik,int yaziPunto,ButonPozisyon pozisyon){
        JComboBox<String> comboBox = new JComboBox<>();
        komponentPozisyonlandiricisi(comboBox, pozisyon);
        
        if(genislik == -1)
           genislik = 200;
        
        if(yukseklik == -1)
            yukseklik = 30;
        
        comboBox.setPreferredSize(new Dimension(genislik, yukseklik));
        
        if(yaziPunto == -1)
            yaziPunto = 14;
        
        comboBox.setFont(new Font("Arial", Font.PLAIN, yaziPunto));

        
        for(String secenek : comboboxSecenekleri){
            comboBox.addItem(secenek);
        }
        
        
        return comboBox;
    }
    
    //SADECE COMBOBOX ICINDE NE GOSTERECEKSE ONA GORE IMPLEMENTASYON YAPILMALIDIR
     public JComboBox<String> addDoldurucuComboBox(int genislik,int yukseklik,int yaziPunto,ButonPozisyon pozisyon,CagrilacakFonksiyon CF_COMBOBOX_DOLDUR_){
        JComboBox<String> comboBox = new JComboBox<>();
        komponentPozisyonlandiricisi(comboBox, pozisyon);
        
        if(genislik == -1)
           genislik = 200;
        
        if(yukseklik == -1)
            yukseklik = 30;
        
        comboBox.setPreferredSize(new Dimension(genislik, yukseklik));
        
        if(yaziPunto == -1)
            yaziPunto = 14;
        
        comboBox.setFont(new Font("Arial", Font.PLAIN, yaziPunto));
        
        comboboxFonksiyonlariSTR CF_Caller = new comboboxFonksiyonlariSTR(comboBox, mevcutPencere);
        switch(CF_COMBOBOX_DOLDUR_){
            case CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER:
                CF_Caller.CF_COMBOBOX_DOLDURURUCU_Func(databaseIslemler,"kategoriAd","T_KATEGORI");
                break;
                
            case CF_COMBOBOX_DOLDUR_MEVCUT_YAYINEVLERI:
                CF_Caller.CF_COMBOBOX_DOLDURURUCU_Func(databaseIslemler, "yayineviAd", "T_YAYINEVI");
                break;
                
            case CF_COMBOBOX_DOLDUR_MEVCUT_YAZARLAR:
                CF_Caller.CF_COMBOBOX_DOLDURURUCU_Func(databaseIslemler, "yazarAd","yazarSoyad", "T_YAZAR");
                break;
                
            case CF_COMBOBOX_DOLDUR_MEVCUT_KITAPLAR:
                CF_Caller.CF_COMBOBOX_DOLDURURUCU_Func(databaseIslemler, "kitapAd", "T_KITAP");
                break;
                
            case CF_COMBOBOX_DOLDUR_MEVCUT_UYE_AD_SOYAD:
                CF_Caller.CF_COMBOBOX_DOLDUR_MEVCUT_UYE_AD_SOYAD_Func(databaseIslemler, "uyeAd", "uyeSoyad", "T_UYELER");
                break;
                
          default:
            System.out.println("TANIMLANMAMIS ENUM DEGERI :: enum CagrilacakFonksiyon | addDoldurCombobox | pencereGUI_Component");
            break;
                
            
        }
        return comboBox;
    }
     
     //OLUSTURULAN COMBOBOXIN YAPMASI GEREKENLER TANIMLANIR
 public int setCombobox(JComboBox<String> comboBox,CagrilacakFonksiyon CF_COMBOBOX_FUNC_){
     comboboxFonksiyonlariSTR CF_Caller = new comboboxFonksiyonlariSTR(comboBox, mevcutPencere);
     switch(CF_COMBOBOX_FUNC_){
         case CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID:
             return CF_Caller.CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID_Func(databaseIslemler);
        
         case CF_COMBOBOX_FUNC_GET_SELECT_YAYINEVI_ID:
             return CF_Caller.CF_COMBOBOX_FUNC_GET_SELECT_YAYINEVI_ID_Func(databaseIslemler);
             
         case CF_COMBOBOX_INDEX_CEVIR:
             return comboBox.getSelectedIndex();
             
         case CF_COMBOBOX_FUNC_GET_SELECT_KITAP_ID:
            return CF_Caller.CF_COMBOBOX_FUNC_GET_SELECT_KITAP_ID_Func(databaseIslemler);
            
            
         case CF_COMBOBOX_FUNC_GET_SELECT_YAZAR_ID:
             return CF_Caller.CF_COMBOBOX_FUNC_GET_SELECT_YAZAR_ID_Func(databaseIslemler);
             
         case CF_COMBOBOX_FUNC_GET_SELECT_UYE_ID:
             return CF_Caller.CF_COMBOBOX_FUNC_GET_SELECT_UYE_ID_Func(databaseIslemler);
             
         default:
            System.out.println("TANIMLANMAMIS ENUM DEGERI :: enum CagrilacakFonksiyon | setCombobox | pencereGUI_Component");
            break;
            
            
             
     }
     
     return 0;
 }
 
 public void setListenerCombobox(JComboBox<String> comboBox, CagrilacakFonksiyon CF_COMBOBOX_FUNC_, int girdiler[], int yerlesmeIndex) {
    comboBox.addActionListener(e -> {
        girdiler[yerlesmeIndex] = setCombobox(comboBox, CF_COMBOBOX_FUNC_);
        System.out.println("YAPILAN COMBOBOX SECIM ID : " + girdiler[yerlesmeIndex]);
    });
}
 
public void setListenerCombobox(JComboBox<String> comboBox, CagrilacakFonksiyon CF_COMBOBOX_FUNC_, int girdiler[], int yerlesmeIndex,JComboBox<String> tetikenecekBox){
    comboBox.addActionListener(e -> {
        girdiler[yerlesmeIndex] = setCombobox(comboBox, CF_COMBOBOX_FUNC_);
        System.out.println("YAPILAN COMBOBOX SECIM ID : " + girdiler[yerlesmeIndex]);
        
        if(girdiler[yerlesmeIndex] == 0){
            tetikenecekBox.setVisible(true);
            komponentPozisyonlandiricisi(tetikenecekBox, UST);
        }
        else{
            tetikenecekBox.setVisible(false);
            komponentPozisyonlandiricisi(tetikenecekBox, SOL);
        }
        
    });
}

    
 public <T> void komponentPozisyonlandiricisi(T object , ButonPozisyon pozisyon){
        switch(pozisyon){
            case UST:   
                        panelCounter[0] += 1;
                        northPanel.setLayout(new GridLayout(1, panelCounter[0])); //1satir3sutun
                        northPanel.add((Component)object);
                        this.mevcutPencere.getPencereGUIComponent_Addr().add(northPanel , BorderLayout.NORTH);
                        break;
            case ALT:   
                        panelCounter[1] += 1;
                        southPanel.setLayout(new GridLayout(1,panelCounter[1]));
                        southPanel.add((Component)object);
                        this.mevcutPencere.getPencereGUIComponent_Addr().add(southPanel , BorderLayout.SOUTH);
                        break;
                
            case SAG:   
                        panelCounter[2] += 1;
                        eastPanel.setLayout(new GridLayout(panelCounter[2], 1)); //3satir1sutun
                        eastPanel.add((Component)object);
                        this.mevcutPencere.getPencereGUIComponent_Addr().add(eastPanel , BorderLayout.EAST);
                        break;
            case SOL: {
                        panelCounter[3] += 1;
                        westPanel.setLayout(new GridLayout(panelCounter[3], 1)); //3satir1sutun
                        westPanel.add((Component)object);
                        this.mevcutPencere.getPencereGUIComponent_Addr().add(westPanel, BorderLayout.WEST);        
                      }
            break;
                
            case ORTA:{
                ((Component)object).setPreferredSize(new Dimension(250,100));
                centerPanel.add((Component)object);
                
                this.mevcutPencere.getPencereGUIComponent_Addr().add(centerPanel, BorderLayout.CENTER);
                pencereGUI.debugPrint("pencereGUI_Component.addGeneric CENTER | COUNT UPDATE : "+pencereGUI.getCounter());
            }
            break;
           
            case BOSLUK_TAMAMI:
                ((Component)object).setPreferredSize(new Dimension(500,500));
                centerPanel.setLayout(new GridLayout(1, 4));
                centerPanel.add((Component)object);
                this.mevcutPencere.getPencereGUIComponent_Addr().add(centerPanel, BorderLayout.CENTER);
            break;
            
           
        }//switch
 }
    
    
    
    //COMPONENTLERI GUNCELLEME SON ADIMDA CAGRILMASI GEREKIR
public pencereGUI updateComponent(){
        isUpdate = true;
        //pencereGUI.debugPrint(countUpdate+"COMPONENTLER GUNCELLENDI MI : "+isUpdate+ "<------");
        this.mevcutPencere.getPencereGUIComponent_Addr().revalidate(); 
        this.mevcutPencere.getPencereGUIComponent_Addr().repaint(); 
        return this.mevcutPencere;
    }
    
    
public void addMenuBar(){
     //LOGLAMA TEK SECIMLE KAPATILABILIR OLSUN CHECKBOX KOY AYARLAR SEKMESI EKLE
    //menu cubugu
    JMenuBar menuBar = new JMenuBar();
    
    //menu sekmeleri
    JMenu logMENU = new JMenu("Log Tabloları");
    
    JMenuItem kitapYonetimLogMenuItem = new JMenuItem("Kitap Yönetim Loglar");
    JMenuItem yazarYonetimLogMenuItem = new JMenuItem("Yazar Yönetim Loglar");
    JMenuItem personelYonetimLogMenuItem = new JMenuItem("Personel Yönetim Loglar");
    JMenuItem kategoriLogMenuItem = new JMenuItem("Kategori Tablosu Loglar");
    JMenuItem kitapYazarDegisimMenuItem = new JMenuItem("Kitap Yazar Tablosu Loglar");
    JMenuItem  sistemLogMenuItem = new JMenuItem("Sistem Loglar");
    
    
    logMENU.add(kitapYonetimLogMenuItem);
    logMENU.add(yazarYonetimLogMenuItem);
    logMENU.add(personelYonetimLogMenuItem);
    logMENU.add(kategoriLogMenuItem);
    logMENU.add(kitapYazarDegisimMenuItem);
    logMENU.add(sistemLogMenuItem);
    
    
    menuBar.add(logMENU);
    
    mevcutPencere.getPencereGUIComponent_Addr().setJMenuBar(menuBar);
   
      
      
        kitapYonetimLogMenuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pencereGUI.debugPrint("KITAP YONETIM LOG");
              
              
              
           }
    });
          sistemLogMenuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pencereGUI.debugPrint("SISTEM LOG");
              
              
              
           }
    });
        
          kitapYazarDegisimMenuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pencereGUI.debugPrint("KITAP YAZAR DEGISIM LOG");
              
              
              
           }
    });
        
        
          kategoriLogMenuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pencereGUI.debugPrint("KATEGORI LOG"); 
              NW_NFOBAR_KATEGORI_LOG_PVFunc();   
           }
    });
          
          
            personelYonetimLogMenuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
               pencereGUI.debugPrint("PERSONEL YONETIM LOG");
           }
    });
            
            
                yazarYonetimLogMenuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
               pencereGUI.debugPrint("YAZAR YONETIM LOG");
               NW_NFOBAR_YAZAR_LOG_PVFunc();
               
           }
    });
 
 }

public void NW_NFOBAR_KATEGORI_LOG_PVFunc(){
    kategoriLogGUI kategorilog_frame = new kategoriLogGUI(databaseIslemler);
    kategorilog_frame.initkategoriLogGUI(true);
}

public void NW_NFOBAR_YAZAR_LOG_PVFunc(){
    yazarLogGUI yazarlog_frame = new yazarLogGUI(databaseIslemler);
    yazarlog_frame.inityazarLogGUI(true);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void infoBar(String eklemeMSG , String cikartmaMSG , String guncellemeMSG){
     
    //menu cubugu
    JMenuBar menuBar = new JMenuBar();
    
    //menu sekmeleri
    JMenu nfoHelp = new JMenu("Yardım");
     
    JMenuItem eklemeYardim = new JMenuItem("Ekleme Yardım");    
    JMenuItem cikartmaYardim = new JMenuItem("Çıkartma Yardım");
    JMenuItem guncellemeYardim = new JMenuItem("Güncelleme Yardım");    
    
    nfoHelp.add(eklemeYardim);
    nfoHelp.add(cikartmaYardim);
    nfoHelp.add(guncellemeYardim);
    menuBar.add(nfoHelp);
    
    mevcutPencere.getPencereGUIComponent_Addr().setJMenuBar(menuBar);
    
    eklemeYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),eklemeMSG);
          }
    });
    
      cikartmaYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              System.out.println(cikartmaMSG);
              JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),cikartmaMSG);

           }
    });
      
      
        guncellemeYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),guncellemeMSG);
           }
    });
 }


public void infoBar(String sekmeAd,String msg1,String item1 , String msg2 , String item2 , String msg3 , String item3){
     
    //menu cubugu
    JMenuBar menuBar = new JMenuBar();
    
    //menu sekmeleri
    JMenu nfoHelp = new JMenu(sekmeAd);
     
    JMenuItem eklemeYardim = new JMenuItem(item1);    
    JMenuItem cikartmaYardim = new JMenuItem(item2);
    JMenuItem guncellemeYardim = new JMenuItem(item3);    
    
    nfoHelp.add(eklemeYardim);
    nfoHelp.add(cikartmaYardim);
    nfoHelp.add(guncellemeYardim);
    menuBar.add(nfoHelp);
    
    mevcutPencere.getPencereGUIComponent_Addr().setJMenuBar(menuBar);
    
    eklemeYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),msg1);
          }
    });
    
      cikartmaYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),msg2);

           }
    });
      
      
        guncellemeYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),msg3);
           }
    });
 }





////////////////////////////////////////////////////////////////////////////////
//ekSekmeFunc1 CF_1... ile baslayanlar
//ekSekmeFunc2 CF_2... ile baslayanlar
//ekSekmeFunc3 CF_3... ile baslayanlar 
//eklenmeyecek sekmeler icin null parametresi girilmelidir
public void infoBar(String sekmeAd,String eklemeMSG , String cikartmaMSG , String guncellemeMSG,
        String ekSekme1 , String ekSekme2,String ekSekme3,
       CagrilacakFonksiyon ekSekme1Func , CagrilacakFonksiyon ekSekme2Func , CagrilacakFonksiyon ekSekme3Func
        ){
     
    //menu cubugu
    JMenuBar menuBar = new JMenuBar();
    
    //menu sekmeleri
    JMenu nfoHelp = new JMenu(sekmeAd);
    
    JMenuItem eklemeYardim = new JMenuItem("Ekleme Yardım");    
    JMenuItem cikartmaYardim = new JMenuItem("Çıkartma Yardım");
    JMenuItem guncellemeYardim = new JMenuItem("Güncelleme Yardım");
    
    JMenuItem ekSekmeITEM_1 = null;
    JMenuItem ekSekmeITEM_2 = null;
    JMenuItem ekSekmeITEM_3 = null;
    
    if(ekSekme1 != null)
        ekSekmeITEM_1 = new JMenuItem(ekSekme1);
    
    if(ekSekme2 != null)
    ekSekmeITEM_2 = new JMenuItem(ekSekme2);

    if(ekSekme3 != null)
        ekSekmeITEM_3 = new JMenuItem(ekSekme3);
    
    nfoHelp.add(eklemeYardim);
    nfoHelp.add(cikartmaYardim);
    nfoHelp.add(guncellemeYardim);
    
    if(ekSekme1 != null)
        nfoHelp.add(ekSekmeITEM_1);
    
    if(ekSekme2 != null)
    nfoHelp.add(ekSekmeITEM_2);
    
    if(ekSekme3 != null)
        nfoHelp.add(ekSekmeITEM_3);
    
    menuBar.add(nfoHelp);
    mevcutPencere.getPencereGUIComponent_Addr().setJMenuBar(menuBar);
    
    eklemeYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),eklemeMSG);
          }
    });
    
    
      cikartmaYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              System.out.println(cikartmaMSG);
              JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),cikartmaMSG);

           }
    });
      
      
      
        guncellemeYardim.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),guncellemeMSG);
           }
    });
        
        
        //1-ISTATISTIKLER
        if(ekSekme1 != null)
         ekSekmeITEM_1.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              System.out.println(ekSekme1);
              
              switch(ekSekme1Func){
                  case CF_1_ISTATISTIKLER_KITAP_YONETIM:
                      NW_NFOBAR_ISTATISTIKLER_KITAP_YONETIM_PVFunc();
                      break;
              }
              
           }
    });
         
         
         //2-kitapYonetimGUI_arttirimAzaltim
         if(ekSekme2 != null)
         ekSekmeITEM_2.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              System.out.println(ekSekme2);
                switch(ekSekme2Func){
                    case CF_2_ARTTIRIM_AZALTIM_KITAP_YONETIM:
                        NW_NFOBAR_ARTTIRIM_AZALTIM_KITAP_YONETIM_PVFunc();
                        break;
                }              
              
           }
    });
         
         
         if(ekSekme3 != null)
         ekSekmeITEM_3.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              System.out.println(ekSekme3);
                switch(ekSekme3Func){
                    case CF_3_KITAP_FILTRELEME_KITAP_YONETIM:
                        NW_NFOBAR_KITAP_FILTRELEME_KITAP_YONETIM_PVFunc();
                        break;
                }              
              
           }
    });
          
          
 }

public void NW_NFOBAR_ARTTIRIM_AZALTIM_KITAP_YONETIM_PVFunc(){
    kitapYonetimGUI_ArttirimAzaltim kyg_aa_frame = new kitapYonetimGUI_ArttirimAzaltim(databaseIslemler);
    kyg_aa_frame.initkitapYonetimGUI_ArttirimAzaltim(true);
}

public void NW_NFOBAR_ISTATISTIKLER_KITAP_YONETIM_PVFunc(){
   kitapYonetimIstatistikler kyg_Istatistikler_frame = new kitapYonetimIstatistikler(databaseIslemler);
   kyg_Istatistikler_frame.initkitapYonetimIstatistikler(true);
}

public void NW_NFOBAR_KITAP_FILTRELEME_KITAP_YONETIM_PVFunc(){
    kitapYonetimFiltrelemeGUI kyf_frame = new kitapYonetimFiltrelemeGUI(databaseIslemler);
    kyf_frame.initkitapYonetimGUI_ArttirimAzaltim(true);
}


////////////////////////////////////////////////////////////////////////////////



public JTable addTable(String kolonAdlari[],int satirYukseklik,int yaziPunto,CagrilacakFonksiyon cagrilacakSorguFonksiyon) {
    DefaultTableModel tableModel = new DefaultTableModel(kolonAdlari,0);
    tableFonksiyonlari CF_Caller = new tableFonksiyonlari(tableModel, mevcutPencere);
    
    //HERSEYI BELIRLENMIS TABLOLAR
    //SADECE TABLE OLACAK SEKILDE IMPLEMENTE EDILMIS FONKSIYONLAR
    switch(cagrilacakSorguFonksiyon){
        case CF_TABLE_PERSONEL_LIST:
            CF_Caller.CF_TABLE_PERSONEL_LIST_Func(databaseIslemler);
            break;
        case CF_TABLE_KITAP_LIST:
            CF_Caller.CF_TABLE_KITAP_LIST_Func(databaseIslemler);
            break;
        case CF_TABLE_GET_MEVCUT_KATEGORILER:
            CF_Caller.CF_TABLE_GET_MEVCUT_KATEGORILER_Func(databaseIslemler);
            break;
        case CF_TABLE_YAZAR_LIST:
            CF_Caller.CF_TABLE_YAZAR_LIST_Func(databaseIslemler);
            break;
            
        case CF_TABLE_YAYINEVI_LIST:
               CF_Caller.CF_TABLE_YAYINEVI_LIST_Func(databaseIslemler);
            break;
            
        case CF_TABLE_KITAPYAZAR_JOINT_LIST:
            CF_Caller.CF_TABLE_KITAPYAZAR_JOINT_LIST_Func(databaseIslemler);
            break;
            
        case CF_TABLE_ODUNC_LIST:
            CF_Caller.CF_TABLE_ODUNC_LIST_Func(databaseIslemler);
            break;
            
        case CF_TABLE_KATEGORI_LOG:
            CF_Caller.CF_TABLE_KATEGORI_LOG_Func(databaseIslemler);
            break;
            
        case CF_TABLE_YAZAR_LOG:
            CF_Caller.CF_TABLE_YAZAR_LOG_Func(databaseIslemler);
            break;
            
        case CF_TABLE_UYE_LIST:
            CF_Caller.CF_TABLE_UYE_LIST_Func(databaseIslemler);
            break;
            
        default:
            System.out.println("TANIMLANMAMIS ENUM DEGERI :: enum CagrilacakFonksiyon | addTable | pencereGUI_Component");
            break;
    }
   
    
     JTable table = new JTable(tableModel);
     JScrollPane scrollPane = new JScrollPane(table);
     
     table.setRowHeight(satirYukseklik); 
     table.setFont(new Font("Arial", Font.PLAIN, yaziPunto));

    mevcutPencere.getPencereGUIComponent_Addr().add(scrollPane, BorderLayout.CENTER);
    mevcutPencere.getPencereGUIComponent_Addr().setSize(500, 300);
    
    return table;
}



public JLabel addLabel(String metin,int punto,ButonPozisyon pozisyon){
    JLabel label = new JLabel(metin);
    label.setPreferredSize(new Dimension(300,100));
    komponentPozisyonlandiricisi(label, pozisyon);
    
    
    label.setFont(new Font("Arial", Font.BOLD, punto));
    
    return label;
}


} //pencereGUI_Component SON




////////////////////COMPONENT FONKSIYONLARI ICIN AYRI CLASSLAR//////////////////





//////BUTON FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////BUTON FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////BUTON FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
class butonFonksiyonlari{
    
    //ORNEK PARAMETRELER : ORNEK PARAMETRELER DURUMA GORE ARTTIRILABILIR
    //butonNesnesi , (butonla alakali islemler yapilabilir) 
    //pencereGUI_Component , (uyari mesajlarini kullanmak icin)
    
    private JButton buton;
    private pencereGUI mevcutPencere;
    private DatabaseIslemler databaseIslemler;
    
    public butonFonksiyonlari(JButton buton , pencereGUI mevcutPencere , DatabaseIslemler databaseIslemler){
        this.buton = buton;
        this.mevcutPencere = mevcutPencere;
        this.databaseIslemler = databaseIslemler;
    }
    
    ///////////////////////////////////////////UYARI KUTULARI FONKSIYON ICINDE KULLANILMAK UZERE
    public void messageBoxOK(String msg){
        pencereGUI.debugPrint("butonFonksiyonlari.messageBoxOK CAGRILDI");
        JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),msg);
    }
    
    
    
    public int messageBoxYesNo(String msgSoru , String okButonText){
      int secim = JOptionPane.showConfirmDialog(mevcutPencere.getPencereGUIComponent_Addr(),msgSoru , okButonText , JOptionPane.YES_NO_OPTION);
      if(secim == JOptionPane.OK_OPTION){
          pencereGUI.debugPrint("butonFonksiyonlari.messageBoxYesNo CAGRILDI: YAPILAN SECIM :"+1);
          return 1;
      }
      else if(secim == JOptionPane.NO_OPTION){
          pencereGUI.debugPrint("butonFonksiyonlari.messageBoxYesNo CAGRILDI: YAPILAN SECIM :"+0);
          return 0;          
      }
      
      pencereGUI.debugPrint("butonFonksiyonlari.messageBoxYesNo : YAPILAN SECIM :"+(-1));
      return -1;
    }
    
    
    //FONKSIYON ADLANDIRMA: ENUM_ADI_Func
    public void CF_TANIMLANMADI_Func(){
        messageBoxOK("BU BUTON DAHA PROGRAMLANMADI !");
    }
    
    
//DENEME FONKSIYONLARI
    public void CF_KUZEY_Func(){
        messageBoxOK("KUZEY BUTONUNA TIKLANDI !");
    }
    
    public void CF_GUNEY_Func(){
        messageBoxYesNo("GUNEY BUTONUNA MI TIKLADINIZ ?", "Evet");
    }
    
    public void CF_BUTTON_NW_PERSONEL_YONETIM_Func(){
        personelYonetimGUI personelyonetim_frame = new personelYonetimGUI(databaseIslemler);
        personelyonetim_frame.initpersonelYonetimGUI(true);
    }
    
    public void CF_BUTTON_NW_KITAP_YONETIM_Func(){
        kitapYonetimGUI kitapyonetim_frame = new kitapYonetimGUI(databaseIslemler);
        kitapyonetim_frame.initkitapYonetimGUI(true);
    }
    
    public void CF_BUTTON_NW_KATEGORI_TABLOSU_Func(){
        kategoriTablosuGUI kategoritablosu_frame = new kategoriTablosuGUI(databaseIslemler);
        kategoritablosu_frame.initkategoriTablosuGUI(true);
    }
    
    public void CF_BUTTON_NW_YAZAR_YONETIM_Func(){
        yazarYonetimGUI yazaryonetim_frame = new yazarYonetimGUI(databaseIslemler);
        yazaryonetim_frame.inityazarYonetimGUI(true);
    }
    
    public void CF_BUTTON_NW_YAYINEVI_YONETIM_Func(){
        yayineviYonetimGUI yayineviyonetim_frame = new yayineviYonetimGUI(databaseIslemler);
        yayineviyonetim_frame.inityayineviYonetimGUI(true);
    }
    
    public void CF_BUTTON_NW_ODUNC_YONETIM_Func(){
        oduncYonetimGUI oduncyonetim_frame = new oduncYonetimGUI(databaseIslemler);
        oduncyonetim_frame.initoduncYonetimGUI(true);
    }
    
    public void CF_BUTTON_NW_KITAP_YAZAR_TABLOSU_Func(){
        kitapYazarTablosuGUI kitapyazartablosu_frame = new kitapYazarTablosuGUI(databaseIslemler);
        kitapyazartablosu_frame.initkitapYazarTablosuGUI(true);
    }
    
    
    //////////////////////////UYE YONETIM//////////////////////////////
    public void CF_BUTTON_NW_UYE_YONETIM_Func(){
        uyeYonetimiGUI uyeyonetim_frame = new uyeYonetimiGUI(databaseIslemler);
        uyeyonetim_frame.inituyeYonetimiGUI(true);
    }
    
    public void CF_BUTTON_NW_UYE_YONETIM_OGRENCI_ISLEMLERI_Func(){
        uyeYonetimiOgrenciIslemleriGUI uyeYonetimiOgrenciIslemleri_frame = new uyeYonetimiOgrenciIslemleriGUI(databaseIslemler);
        uyeYonetimiOgrenciIslemleri_frame.inituyeYonetimiOgrenciIslemleriGUI(true);
    }
    
    public void CF_BUTTON_NW_UYE_YONETIM_SIVIL_ISLEMLERI_Func(){
        uyeYonetimSivilIslemleriGUI uyeYonetimSivilIslemleri_frame = new uyeYonetimSivilIslemleriGUI(databaseIslemler);
        uyeYonetimSivilIslemleri_frame.inituyeYonetimiSivilIslemleriGUI(true);
    }
    
    public void CF_BUTTON_NW_UYE_YONETIM_OZELSEKTOR_ISLEMLERI_Func(){
        uyeYonetimOzelSektorIslemleri ozelsektor_frame = new uyeYonetimOzelSektorIslemleri(databaseIslemler);
        ozelsektor_frame.inituyeYonetimiOzelSektorIslemleriGUI(true);
    }
    
    public void CF_BUTTON_NW_ODUNC_ISLEMLERI_Func(){
        oduncYonetimOduncIslemleriGUI oyislemleri_frame = new oduncYonetimOduncIslemleriGUI(databaseIslemler);
        oyislemleri_frame.initoduncYonetimOduncIslemleriGUI(true);
    }
    
    
    
    //////////////////////////////////////////////////////////////////////////
    
    public void CF_BUTTON_PERSONEL_EKLE_Func(String personelAd,String personelSoyad , double personelMaas,String personelTuru,String EkBilgi){
        databaseIslemler.SQL_Q_personelEkle(personelAd, personelSoyad, personelMaas, personelTuru,EkBilgi);
    }
    
    public void CF_BUTTON_PERSONEL_CIKART_Func(int personelID){
        databaseIslemler.SQL_Q_personelCikart(personelID);
    }
    
    public void CF_BUTTON_PERSONEL_GUNCELLE_Func(int personelID, String personelAd, String personelSoyad, Double personelMaas, String personelTuru,String ekBilgi){
        databaseIslemler.SQL_Q_personelGuncelle(personelID, personelAd, personelSoyad, personelMaas, personelTuru,ekBilgi);
    }
    
    public void CF_BUTTON_KATEGORI_EKLE_Func(String kategoriAd){
        databaseIslemler.SQL_Q_kategoriEkle(kategoriAd);
    }
    
    public void CF_BUTTON_KATEGORI_CIKART_Func(int kategoriID){
        if(databaseIslemler.SQL_Q_kategoriCikart(kategoriID) == -1){    
            messageBoxOK("GİRİLEN KATEGORİYE BAĞLI KİTAPLAR VARDIR SİLEMEZSİNİZ\nİLK ÖNCE KİTAPLARI KALDIRIN !");
        }
        else{
            messageBoxOK("KATEGORİ SİLİNMİŞTİR !");
       }
    }
    
    public int CF_BUTTON_KATEGORI_GUNCELLE_KATEGORITABLOSU_Func(int kategoriID , String kategoriAd){
        return databaseIslemler.SQL_Q_kategoriGuncelle(kategoriID, kategoriAd);
    }
    
    public void CF_BUTTON_YAZAR_EKLE_Func(String yazarAd,String yazarSoyad , String yazarUlke){
        int retVal = 0;
        if((retVal=databaseIslemler.SQL_Q_YazarEkle(yazarAd, yazarSoyad, yazarUlke)) == -1){
            messageBoxOK("YAZAR EKLENEMEDİ HATA : -1");
        }
        else if(retVal == 1)
            messageBoxOK("YAZAR BAŞARIYLA EKLENDİ");
    }
    
    public void CF_BUTTON_YAZAR_CIKART_Func(int yazarID){
        int retval = 0;
        if((retval = databaseIslemler.SQL_Q_YazarCikart(yazarID)) == -1){
            messageBoxOK("BU YAZARIN KİTAPLARI VARDIR SİLEMEZSİNİZ !\nİLK ÖNCE KİTAPLARI SİLİN");
        }
        else if(retval == 1)
            messageBoxOK("YAZAR BAŞARIYLA SİLİNDİ !");
    
    }
    
    public void CF_BUTTON_YAZAR_GUNCELLE_Func(int yazarID , String yazarAd , String yazarSoyad , String yazarUlke){
        databaseIslemler.SQL_Q_YazarGuncelle(yazarID, yazarAd, yazarSoyad, yazarUlke);
    }
        
    public void CF_BUTTON_YAYINEVI_EKLE_Func(String yayineviAd,String yayineviUlke){
        databaseIslemler.SQL_Q_YayineviEkle(yayineviAd, yayineviUlke);
    }
    
    public void CF_BUTTON_YAYINEVI_CIKART_Func(int yayineviID){
        databaseIslemler.SQL_Q_YayineviCikart(yayineviID);
    }
    
    public void CF_BUTTON_YAYINEVI_GUNCELLE_Func(int yayineviID , String yayineviAd , String yayineviUlke){
        databaseIslemler.SQL_Q_YayineviGuncelle(yayineviID, yayineviAd, yayineviUlke);
    }
    
    public void CF_BUTTON_KITAP_EKLE_Func(
            String kitapAd,
            int kitapStok,
            String ISBN,
            int kitapSayfaSayisi,
            double kitapFiyat,
            String yazarAd,
            String yazarSoyad,
            int kategoriID,
            int yayineviID){
        databaseIslemler.SQL_Q_KitapEkle(kitapAd, kitapStok, kitapFiyat, ISBN, kitapSayfaSayisi, kategoriID, yayineviID, yazarAd, yazarSoyad);
    }
    
    public void CF_BUTTON_KITAP_CIKART_Func(int kitapID){
        databaseIslemler.SQL_Q_kitapCikart(kitapID);
    }
    
    public void CF_BUTTON_YAZARI_DEGISTIR_Func(int kitapID , int yazarID){
        databaseIslemler.SQL_kitapYazarTablosu_yazariDegistir(kitapID, yazarID);
    }
    
    public void CF_BUTTON_KITABI_DEGISTIR_Func(int kitapID , int yazarID){
        databaseIslemler.SQL_kitapYazarTablosu_kitabiDegistir(kitapID, yazarID);
    }
    
    public void CF_BUTTON_KITAP_GUNCELLE_Func(int kitapID, String kitapAd, int kitapStok, String kitapISBN, int kitapSayfaSayisi, 
                                double kitapFiyat, String yazarAd, String yazarSoyad,int kategoriID, int yayineviID){
        
    
        databaseIslemler.SQL_Q_kitapGuncelle(kitapID, kitapAd, kitapStok, kitapISBN, kitapSayfaSayisi, kitapFiyat, kategoriID, yayineviID, yazarAd, yazarSoyad);
    }
    
    
    public void CF_BUTTON_OGRENCI_ISLEMLERI_EKLE_Func(String uyeAd,String uyeSoyad,String uyeTCNO,double uyeUcret , double uyeIndirimMiktar , String ogrenciOkulAd,
            String bolumSTRVeyaSinifINTOrtak, double ortalamaOrtak ,int cinsiyet,int kademelerLisansLisansustu,int kademelerLiseOrtaokul){
        
        if(kademelerLisansLisansustu == 0)
databaseIslemler.SQL_Q_ogrenciEkle(uyeAd, uyeSoyad, uyeTCNO, cinsiyet, uyeUcret, uyeIndirimMiktar, "ORTAOGRETIM", ogrenciOkulAd, bolumSTRVeyaSinifINTOrtak, ortalamaOrtak, kademelerLiseOrtaokul);
        else if(kademelerLisansLisansustu == 1)            
databaseIslemler.SQL_Q_ogrenciEkle(uyeAd, uyeSoyad, uyeTCNO, cinsiyet, uyeUcret, uyeIndirimMiktar, "LISANS", ogrenciOkulAd, bolumSTRVeyaSinifINTOrtak, ortalamaOrtak, kademelerLiseOrtaokul);
        else if(kademelerLisansLisansustu == 2)
databaseIslemler.SQL_Q_ogrenciEkle(uyeAd, uyeSoyad, uyeTCNO, cinsiyet, uyeUcret, uyeIndirimMiktar, "LISANSUSTU", ogrenciOkulAd, bolumSTRVeyaSinifINTOrtak, ortalamaOrtak, kademelerLiseOrtaokul);
    }
    
    public void CF_BUTTON_OGRENCI_ISLEMLERI_CIKART_Func(int uyeID){
        databaseIslemler.SQL_Q_ogrenciCikart(uyeID);

    }
    
    
public void CF_BUTTON_SIVIL_ISLEMLERI_EKLE_Func(String uyeAd , String uyeSoyad , String uyeTCNO ,int uyeCinsiyet ,double uyelikUcreti,double uyeIndirimMiktari,
        double sivilGelirMiktari){
    
    databaseIslemler.SQL_Q_sivilEkle(uyeAd, uyeSoyad, uyeTCNO, uyeCinsiyet, uyelikUcreti, uyeIndirimMiktari, sivilGelirMiktari);
}

public void CF_BUTTON_SIVIL_ISLEMLERI_CIKARTMA_Func(int uyeID){
    databaseIslemler.SQL_Q_sivilCikart(uyeID);
}
    

public void CF_BUTTON_OZELSEKTOR_ISLEMLERI_EKLEME_Func(String uyeAd , String uyeSoyad , String uyeTCNO ,int uyeCinsiyet ,double uyelikUcreti,double uyeIndirimMiktari
        ,boolean kurumAntlasmasi){
    
    databaseIslemler.SQL_Q_ozelSektorEkle(uyeAd, uyeSoyad, uyeTCNO, uyeCinsiyet, uyelikUcreti, uyeIndirimMiktari, kurumAntlasmasi);
}

public void CF_BUTTON_OZELSEKTOR_ISLEMLERI_CIKARTMA_Func(int uyeID){
    databaseIslemler.SQL_Q_ozelSektorCikart(uyeID);
}

public void CF_BUTTON_ODUNC_EKLEME_Func(int kitapID,int uyeID,int oduncIzinGunSayisi){
    databaseIslemler.SQL_Q_oduncEkle(kitapID, uyeID, oduncIzinGunSayisi);
}

public void CF_BUTTON_ODUNC_CIKARTMA_Func(int kitapID , int uyeID){
    databaseIslemler.SQL_Q_oduncGetirdi(kitapID, uyeID);
}
    
}//class butonFonksiyonlari 

//////COMBOBOX FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////COMBOBOX FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////COMBOBOX FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////

class comboboxFonksiyonlariSTR{
    private pencereGUI mevcutPencere;
    JComboBox<String> comboBox;
    
    public comboboxFonksiyonlariSTR(JComboBox<String> combobox,pencereGUI mevcutPencere){
        this.comboBox = combobox;
        this.mevcutPencere = mevcutPencere;
    }
    
    //////////////////////////////////////
    public void CF_COMBOBOX_DOLDURURUCU_Func(DatabaseIslemler databaseIslemler,String kolonAd , String tabloAd){
        databaseIslemler.SQL_Q_ComboboxDoldurCOL_TABLO(comboBox,kolonAd,tabloAd);
    }
    
    public void CF_COMBOBOX_DOLDURURUCU_Func(DatabaseIslemler databaseIslemler,String kolonAd1,String kolonAd2 , String tabloAd){
        databaseIslemler.SQL_Q_ComboboxDoldurCOL_TABLO(comboBox,kolonAd1,kolonAd2,tabloAd);
    }
    //////////////////2 KOLON CEKMEK ICIN OVERLOADLANDI YAZAR AD + SOYAD GIBI
    public void CF_COMBOBOX_DOLDUR_MEVCUT_UYE_AD_SOYAD_Func(DatabaseIslemler databaseIslemler,String kolon1,String kolon2,String tabloAd){
                databaseIslemler.SQL_Q_ComboboxDoldurCOL_TABLO(comboBox,kolon1,kolon2,tabloAd);
    }
    
    
    
    public int CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID_Func(DatabaseIslemler databaseIslemler){
        return databaseIslemler.SQL_Q_ComboboxSecilenID(comboBox,"kategoriID","kategoriAd","T_KATEGORI");
        //secilen kategoriAd'a gore kategori id cevirecek
    }
    
    public int CF_COMBOBOX_FUNC_GET_SELECT_YAYINEVI_ID_Func(DatabaseIslemler databaseIslemler){
        return databaseIslemler.SQL_Q_ComboboxSecilenID(comboBox, "yayineviID", "yayineviAd", "T_YAYINEVI");
    }
    
    public int CF_COMBOBOX_FUNC_GET_SELECT_KITAP_ID_Func(DatabaseIslemler databaseIslemler){
        return databaseIslemler.SQL_Q_ComboboxSecilenID(comboBox, "kitapID", "kitapAd", "T_KITAP");
    }
    
    
    
    public int CF_COMBOBOX_FUNC_GET_SELECT_YAZAR_ID_Func(DatabaseIslemler databaseIslemler){
        return databaseIslemler.SQL_Q_ComboboxSecilenID(comboBox, "yazarID", "yazarAd","yazarSoyad", "T_YAZAR");
    }
    ////////OVERLOADLANMISTIR 2 SUTUNU BIRLESITRDIGIMIZDEN DOLAYI
    
    public int CF_COMBOBOX_FUNC_GET_SELECT_UYE_ID_Func(DatabaseIslemler databaseIslemler){
        return databaseIslemler.SQL_Q_ComboboxSecilenID(comboBox, "uyeID", "uyeAd", "uyeSoyad", "T_UYELER");
    }
}







//////DEFAULT_TABLE_MODEL FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////DEFAULT_TABLE_MODEL FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////DEFAULT_TABLE_MODEL FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
class tableFonksiyonlari{
     private pencereGUI mevcutPencere;
     DefaultTableModel tableModel;
     
     
    public tableFonksiyonlari(DefaultTableModel tableModel , pencereGUI mevcutPencere){
        this.tableModel = tableModel;
        this.mevcutPencere = mevcutPencere;
    }
     
     public void CF_TABLE_PERSONEL_LIST_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_personelListesi(tableModel);
    }
     
     public void CF_TABLE_KITAP_LIST_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_kitapListesi(tableModel);
    }
     
    public void CF_TABLE_GET_MEVCUT_KATEGORILER_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_mevcutKategorilerList(tableModel); 
    }
    
    public void CF_TABLE_YAZAR_LIST_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_YazarListele(tableModel);
    }
    
    public void CF_TABLE_YAYINEVI_LIST_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_yayineviList(tableModel);
    }
    
    public void CF_TABLE_KITAPYAZAR_JOINT_LIST_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_kitapYazarTablosuList(tableModel);
    }
    public void CF_TABLE_ODUNC_LIST_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_oduncYonetimListesi(tableModel);
    }
    public void CF_TABLE_UYE_LIST_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_uyeList(tableModel);
    }
    
    
    //LOG TABLOLARINI CEKME 
    public void CF_TABLE_KATEGORI_LOG_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_KategoriLOG_List(tableModel);
    }
    
    public void CF_TABLE_YAZAR_LOG_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_YazarLOG_List(tableModel);
    }
    
    
    
    //LOG TABLOLARINI CEKME
    
    
    public void CF_TABLE_KATEGORI_YAYINEVI_TUMU_Func(DatabaseIslemler databaseIslemler,int kategoriID , int yayineviID , int secilen){
        if(secilen == 1)
            databaseIslemler.SQL_Q_kitapListesi(tableModel, kategoriID, yayineviID, true, false);
        if(secilen == 2)
            databaseIslemler.SQL_Q_kitapListesi(tableModel, kategoriID, yayineviID, false, true);
        if(secilen == 3)
            databaseIslemler.SQL_Q_kitapListesi(tableModel, kategoriID, yayineviID, true, true);
    }
    
}//class tableFonksiyonlari







//////MENU BAR FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////MENU BAR FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////MENU BAR FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
class menuBarFonksiyonlari{
    
    
}//class menuBarFonksiyonlari
