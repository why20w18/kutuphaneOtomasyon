/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.table.TableColumn;
import packDatabase.DatabaseIslemler;


public class kitapYonetimGUI_ArttirimAzaltim {
    pencereGUI arttirim_azaltim_frame;
    pencereGUI_Component kyaa_component;
    DatabaseIslemler databaseIslemler;
   
    //CONSTRUCTORLAR
    public kitapYonetimGUI_ArttirimAzaltim(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;
        arttirim_azaltim_frame = new pencereGUI("KITAP YONETIM -> ARTTIRIM AZALTIM ISLEMLERI", 500, 400);
        kyaa_component = new pencereGUI_Component(arttirim_azaltim_frame,this.databaseIslemler);
        kyaa_component.setPanelBoyutlari(250, 250, 75, 75);
    }
    
 
    public void initkitapYonetimGUI_ArttirimAzaltim(boolean isLayoutActive){
        arttirim_azaltim_frame.InitPencereGUI(true);
        
        //GUI ELEMANLARININ YERLESTIRILECEGI ALAN
        if(isLayoutActive == true){
            arttirim_azaltim_frame.initLayout();
            kyaa_component.infoBar("Yardım", "KITAP FIYAT ARTTIRMA : +1 GİRİLİRSE FİYATLAR %1 ARTTIRILIR", "Fiyat Arttırma Yardım",
                                             "KITAP FIYAT AZALTMA  : -3 GİRİLİRSE FİYATLAR %3 AZALTILIR", "Fiyat Azaltma Yardım"
                                            , "FARKLI SEÇENEKLER   : Kategori/Yayınevi VEYA Tüm Kitapların Fiyatlarını Uygun Butonlar\n\tİle Güncelleyebilirsiniz!", "Buton Yardım");
            //cursor ile belirli kategoride yada tum kategorilerde arttirim azaltim yada belli bir kitapta arttim azaltim % cinsinden
            
            JComboBox<String> cbox_kKategori = kyaa_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.SOL, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER);
            JComboBox<String> cbox_kYayinevi = kyaa_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.SAG, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_YAYINEVLERI);
            
            String girdilerString[] = new String[1];
            JTextField guncellemeYuzdesi = kyaa_component.addTextField("Miktar", 3, ButonPozisyon.UST);
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            textfieldArrayList.add(guncellemeYuzdesi);
            kyaa_component.ListenerTextField(textfieldArrayList, girdilerString);
            
            int comboboxGirdiler[] = new int[2];
            kyaa_component.setListenerCombobox(cbox_kKategori, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID,comboboxGirdiler,0);
            kyaa_component.setListenerCombobox(cbox_kYayinevi, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_YAYINEVI_ID,comboboxGirdiler,1);
            
            
            JButton hepsiniGuncelleme 
                    = kyaa_component.addButtonParams("Tüm Fiyatları Güncelle", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_GUNCELLEME_TUMUNE_GORE, girdilerString, comboboxGirdiler);
            kyaa_component.all_input_textFieldButton(textfieldArrayList, girdilerString, hepsiniGuncelleme);

            
            JButton yayinevineGoreGuncelleme =
                    kyaa_component.addButtonParams("Yayınevine Göre Fiyat Güncelle", ButonPozisyon.SAG, CagrilacakFonksiyon.CF_GUNCELLEME_YAYINEVINE_GORE, girdilerString, comboboxGirdiler);
            kyaa_component.all_input_textFieldButton(textfieldArrayList, girdilerString, yayinevineGoreGuncelleme);

            
            JButton kategoriyeGoreGuncelleme
                    = kyaa_component.addButtonParams("Kategoriye Göre Fiyat Güncelle", ButonPozisyon.SOL, CagrilacakFonksiyon.CF_GUNCELLEME_KATEGORIYE_GORE, girdilerString, comboboxGirdiler);
            kyaa_component.all_input_textFieldButton(textfieldArrayList, girdilerString, kategoriyeGoreGuncelleme);

                    
         
            
            
        }
            

        
        kyaa_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageinitkitapYonetimGUI_ArttirimAzaltimFrame(){
        return arttirim_azaltim_frame;
    }
  
    
    
}

    
