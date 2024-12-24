package packGuiSwing;

import com.sun.source.tree.BreakTree;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

import packDatabase.DatabaseIslemler;
import static packGuiSwing.ButonPozisyon.ALT;
import static packGuiSwing.ButonPozisyon.ORTA;
import static packGuiSwing.ButonPozisyon.SAG;
import static packGuiSwing.ButonPozisyon.SOL;
import static packGuiSwing.ButonPozisyon.UST;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_GUNEY;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_KUZEY;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_NW_PERSONEL_YONETIM;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_PERSONEL_CIKART;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_PERSONEL_EKLE;
import static packGuiSwing.CagrilacakFonksiyon.CF_BUTTON_TANIMLANMADI;

/*
GUI KOMPONENT VE FONKSIYON BAGLAMA ADIMLARI
1-ILK ONCE FONKSIYON ICIN ENUM DEGERI OLUSTURULUR
2-FONKSIYON TANIMI componentadiFonksiyonlari CLASSLARINDA YAPILIR
3-COMPONENT TIPINE GORE O COMPONENTIN FONKSIYONUNUN ICINE EKLENIR
*/

enum ButonPozisyon{
        UST, 
        ALT, 
        SAG, 
        SOL, 
        ORTA
}

enum CagrilacakFonksiyon{
    CF_BUTTON_TANIMLANMADI,
    CF_BUTTON_KUZEY,
    CF_BUTTON_GUNEY,
    
    //YENI PENCERELER
    CF_BUTTON_NW_PERSONEL_YONETIM, // personel yonetim penceresini baslatir
    CF_BUTTON_NW_KITAP_YONETIM,   //kitap yonetim penceresini baslatir,
    CF_BUTTON_NW_KATEGORI_TABLOSU,//kategori tablosunu iceren pencereyi baslatir | personel yonetim icinde
    CF_BUTTON_NW_YAZAR_YONETIM,   //mainGUI icindeki yazar yonetim penceresini baslatir
    CF_BUTTON_NW_YAYINEVI_YONETIM, //mainGUI yayinevi init
    
    
    //T_PERSONEL
    CF_TABLE_PERSONEL_LIST,         //databaseIslemlerdeki personel list fonksiyonunu calistirir
    CF_BUTTON_PERSONEL_EKLE,         //personel ekleme
    CF_BUTTON_PERSONEL_CIKART,        //personel cikartma   
    CF_BUTTON_PERSONEL_GUNCELLE, //personel guncelleme
    
    //T_KITAP    
    CF_TABLE_KITAP_LIST,          //databaseIslemlerdeki kitap list fonksiyonunu calistirir
    CF_TABLE_GET_MEVCUT_KATEGORILER, //mevcut kategorileri getirme
    
    CF_BUTTON_KATEGORI_EKLE,      //kitap kategorileri ekleme
    CF_BUTTON_KATEGORI_CIKART,     //kitap kategori cikartma
    
    //KITABIN KATEGORISINI GUNCELLEYECEK KATEGORI TABLOSUNU DEGIL
    CF_BUTTON_KATEGORI_GUNCELLE,   //kitap kategori guncelleme  ///TANIMLANMADI
    
    CF_BUTTON_KATEGORI_EKLE_KATEGORITABLOSU, //kategori ekler ama kategori tablosu icin ozellesmistir sadece parametre degisir
    CF_BUTTON_KATEGORI_CIKART_KATEGORITABLOSU,
    CF_BUTTON_KATEGORI_GUNCELLE_KATEGORITABLOSU,
    
    CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER, //kitap ekle kisminda combobox mevcut kategorileri cekebilsin
    CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID, //doldurulan comboboxin idsini ceker
    
    //T_YAZAR
    CF_BUTTON_YAZAR_EKLE,   //yazar ekleme
    CF_TABLE_YAZAR_LIST, //yazar penceresindeki ilk tablo
    CF_BUTTON_YAZAR_CIKART,
    CF_BUTTON_YAZAR_GUNCELLE,
    
    //T_YAYINEVI
    CF_TABLE_YAYINEVI_LIST, //yayinevi penceresindeki tablo
    
    
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
        westPanel.setPreferredSize(new Dimension(200, 0));
        eastPanel.setPreferredSize(new Dimension(200,0));
        northPanel.setPreferredSize(new Dimension(0,50));
        southPanel.setPreferredSize(new Dimension(0,50));
    }
    
    //BUTON ISLEMLERI
    public JButton addButton(String buttonText , ButonPozisyon butonPozisyon , CagrilacakFonksiyon butonFonksiyonu){
        //pencereGUI.debugPrint(countUpdate+"COMPONENTLER GUNCELLENDI MI : "+this.isUpdate + "<------");

        
       JButton button = new JButton(buttonText);
       button.setMinimumSize(new Dimension(100, 50));
       komponentPozisyonlandiricisi(button, butonPozisyon); 

        
        

       butonFonksiyonlari CF_Caller = new butonFonksiyonlari(button, mevcutPencere,databaseIslemler);
       
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                switch(butonFonksiyonu){
                    case CF_BUTTON_TANIMLANMADI:
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
                    case CF_BUTTON_TANIMLANMADI:
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
                        for(int i = 1 ; i < 6 ; i++){
                            if(girdilerString[i] == null || girdilerString[i].isEmpty()){
                              CF_Caller.messageBoxOK("EKSIK PERSONEL BILGISI !");
                              paramControl = false;
                              paramCond2 = false;
                              break;
                            }
                        }
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
                          paramControl = true;
                        if(girdilerString[1].isBlank()){
                            paramControl = false;
                            CF_Caller.messageBoxOK("KATEGORI ADI BOS OLAMAZ !");
                        }
                        if(girdilerString[1] != null && paramControl == true){
                            int silinenSatirSay = CF_Caller.CF_BUTTON_KATEGORI_CIKART_Func(girdilerString[1]);
                            CF_Caller.messageBoxOK("KATEGORI CIKARTILMISTIR ! | SILINEN SATIR SAYISI "+silinenSatirSay);
                        }
                        
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
                        paramControl = true;
                        if(girdilerString[3].isBlank()){
                            paramControl = false;
                            CF_Caller.messageBoxOK("KATEGORI ADI BOS OLAMAZ !");
                        }
                        if(girdilerString[3] != null && paramControl == true){
                            int silinenSatirSay = CF_Caller.CF_BUTTON_KATEGORI_CIKART_Func(girdilerString[3]);
                            CF_Caller.messageBoxOK("KATEGORI CIKARTILMISTIR ! | SILINEN SATIR SAYISI "+silinenSatirSay);
                        }
                        
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
                CF_Caller.CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER_Func(databaseIslemler);
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
         
         default:
            System.out.println("TANIMLANMAMIS ENUM DEGERI :: enum CagrilacakFonksiyon | setCombobox | pencereGUI_Component");
            break;
             
     }
     
     return 0;
 }
 
 public void setListenerCombobox(JComboBox<String> comboBox , CagrilacakFonksiyon CF_COMBOBOX_FUNC_){
        comboBox.addActionListener(e -> {
                    comboboxRetID = setCombobox(comboBox,CF_COMBOBOX_FUNC_);
                    System.out.println("YAPILAN COMBOBOX SECIM ID : " + comboboxRetID);
                    
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
     
    //menu cubugu
    JMenuBar menuBar = new JMenuBar();
    
    //menu sekmeleri
    JMenu fileMenu = new JMenu("Dosya");
    JMenu settingsMenu = new JMenu("Ayarlar");
     
    JMenuItem accountSettingMenu = new JMenuItem("Hesaplar");
    JMenuItem yetkiKontrolSettingMenu = new JMenuItem("Erisim Kontrol");
    
    JMenuItem disaAktarFileMenu = new JMenuItem("Dışa Aktar");
    JMenuItem iceAktarFileMenu = new JMenuItem("İçe Aktar");
    JMenuItem yedeklemeFileMenu = new JMenuItem("Yedekleme");    
    
    fileMenu.add(disaAktarFileMenu);
    fileMenu.add(iceAktarFileMenu);
    fileMenu.add(yedeklemeFileMenu);

    settingsMenu.add(accountSettingMenu);
    settingsMenu.add(yetkiKontrolSettingMenu);
    
    menuBar.add(fileMenu);
    menuBar.add(settingsMenu);
    
    mevcutPencere.getPencereGUIComponent_Addr().setJMenuBar(menuBar);
    
    accountSettingMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
          pencereGUI.debugPrint("ACCOUNT SETTINGS");           
          }
    });
    
      yetkiKontrolSettingMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
          pencereGUI.debugPrint("YETKI KONTROL SETTINGS");
           }
    });
      
      
        disaAktarFileMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pencereGUI.debugPrint("DISA AKTAR FILE");
           }
    });
        
        
          iceAktarFileMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pencereGUI.debugPrint("ICE AKTAR FILE"); 
           }
    });
          
          
            yedeklemeFileMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
               pencereGUI.debugPrint("YEDEKLEME FILE");
           }
    });
 
 }///////////////////////////////////////////////////////////////

//

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


//



public JTable addTable(String kolonAdlari[],int satirYukseklik,int yaziPunto,CagrilacakFonksiyon cagrilacakSorguFonksiyon) {
    DefaultTableModel tableModel = new DefaultTableModel(kolonAdlari,0);
    tableFonksiyonlari CF_Caller = new tableFonksiyonlari(tableModel, mevcutPencere);
    
    
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
    
    public int CF_BUTTON_KATEGORI_CIKART_Func(String kategoriAd){
        return databaseIslemler.SQL_Q_kategoriCikart(kategoriAd);
    }
    
    public int CF_BUTTON_KATEGORI_GUNCELLE_KATEGORITABLOSU_Func(int kategoriID , String kategoriAd){
        return databaseIslemler.SQL_Q_kategoriGuncelle(kategoriID, kategoriAd);
    }
    
    public void CF_BUTTON_YAZAR_EKLE_Func(String yazarAd,String yazarSoyad , String yazarUlke){
        databaseIslemler.SQL_Q_YazarEkle(yazarAd, yazarSoyad, yazarUlke);
    }
    
    public void CF_BUTTON_YAZAR_CIKART_Func(int yazarID){
        databaseIslemler.SQL_Q_YazarCikart(yazarID);
    }
    
    public void CF_BUTTON_YAZAR_GUNCELLE_Func(int yazarID , String yazarAd , String yazarSoyad , String yazarUlke){
        databaseIslemler.SQL_Q_YazarGuncelle(yazarID, yazarAd, yazarSoyad, yazarUlke);
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
    
    public void CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER_Func(DatabaseIslemler databaseIslemler){
        databaseIslemler.SQL_Q_ComboboxMevcutKategorilerDoldur(comboBox);
    }
    
    public int CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID_Func(DatabaseIslemler databaseIslemler){
        return databaseIslemler.SQL_Q_ComboboxSecilenIDKategoriler(comboBox);
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
    
}//class tableFonksiyonlari







//////MENU BAR FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////MENU BAR FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
//////MENU BAR FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
class menuBarFonksiyonlari{
    
    
}//class menuBarFonksiyonlari
