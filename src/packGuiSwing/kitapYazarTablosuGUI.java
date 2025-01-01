/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import packDatabase.DatabaseIslemler;

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


public class kitapYazarTablosuGUI {
    pencereGUI kitapyazartablosu_frame;
    pencereGUI_Component kyt_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public kitapYazarTablosuGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("kitapYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        kitapyazartablosu_frame = new pencereGUI("KITAP YAZAR YONETIM", 800, 750);
        kyt_component = new pencereGUI_Component(kitapyazartablosu_frame,this.databaseIslemler);

        
    }
    
 
    public void initkitapYazarTablosuGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("kitapYazarTablosuGUI.initkitapYazarTablosuGUI CAGRILDI");
        kitapyazartablosu_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            kitapyazartablosu_frame.initLayout();
            kyt_component.infoBar(
                    "KITAP YONETIM : Kitap Adı , ISBN , Kategori , Sayfa Sayısı , Yayınevi , Yazar Adı , Yazar Soyadı EKSİKSİZ GİRİLEREK EKLEME YAPILIR",
                    "T_KITAP-C", 
                    "T_KITAP-G");
            
                                     //  0    1            2           3          4     
            String kolonIsimleri[] = { "KitapID","YazarID", "Kitap Adı", "Yazar Tam Adı"};
          
            JTable jtable_kitapListesi = kyt_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_KITAPYAZAR_JOINT_LIST);
            
            //EKLEME CIKARTMA
            JTextField tfield_kAd = kyt_component.addTextField("kitapID", 25, ButonPozisyon.UST); 
            JTextField tfieField_kStok = kyt_component.addTextField("yazarID", 25, ButonPozisyon.UST);
            
            
//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN
            String[] girdiler = new String[kolonIsimleri.length-2]; //-2( foreign keyleri cikarttik )
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            textfieldArrayList.add(tfield_kAd);
            textfieldArrayList.add(tfieField_kStok);      
            kyt_component.ListenerTextField(textfieldArrayList, girdiler);            
//TEXTFILED GIRDILERINI YAKALAYABILMEK VE SAKLAYABILMEK ICIN
            

            
            JButton button_kitapCikart = kyt_component.addButtonParams("Yazarı Değiştir", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_YAZARI_DEGISTIR,girdiler);
            kyt_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapCikart);
            
            JButton button_kitapGuncelle = kyt_component.addButtonParams("Kitabı Değiştir", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_KITABI_DEGISTIR,girdiler);
            kyt_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_kitapGuncelle);
            
        }
            
        
        kyt_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI managekitapYonetimFrame(){
        return kitapyazartablosu_frame;
    }
  
    
    
}

