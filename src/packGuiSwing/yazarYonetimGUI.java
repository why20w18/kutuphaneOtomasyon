/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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


public class yazarYonetimGUI {
    pencereGUI yazaryonetim_frame;
    pencereGUI_Component yy_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public yazarYonetimGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("yazarYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        yazaryonetim_frame = new pencereGUI("YAZAR YONETIM", 800, 750);
        yy_component = new pencereGUI_Component(yazaryonetim_frame,this.databaseIslemler);

        
    }
    
 
    public void inityazarYonetimGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("yazarYonetimGUI.inityazarYonetimGUI CAGRILDI");
        yazaryonetim_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            yazaryonetim_frame.initLayout();
            yy_component.infoBar("AD-SOYAD-ULKE kolonlariyla ekleme yapilir"
                    , "ID degeri kullanilarak cikartma yapilir", 
                    "ID degeri ve diger kolonlar doldurulursa guncelleme yapilir");
            
            //                          0       1               2               3
            String kolonIsimleri[] = { "ID", "Yazar Adı", "Yazar Soyadı", "Yazar Ülke"};
          
            JTable jtable_personelListesi = yy_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_YAZAR_LIST);
            
            //EKLEME CIKARTMA
            JTextField tfield_yID = yy_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_yAd = yy_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_ySoyad = yy_component.addTextField("", 25, ButonPozisyon.UST);
            JTextField tfield_yUlke = yy_component.addTextField("", 25, ButonPozisyon.UST);
            
            
            String[] girdiler = new String[kolonIsimleri.length];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            textfieldArrayList.add(tfield_yID);
            textfieldArrayList.add(tfield_yAd);
            textfieldArrayList.add(tfield_ySoyad);
            textfieldArrayList.add(tfield_yUlke);

            yy_component.ListenerTextField(textfieldArrayList, girdiler);
            
            JButton button_yazarEkle = yy_component.addButtonParams("Yazar Ekle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_YAZAR_EKLE,girdiler);
            yy_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_yazarEkle);
            
            JButton button_yazarCikart = yy_component.addButtonParams("Yazar Çıkart",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_YAZAR_CIKART,girdiler);
            yy_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_yazarCikart);
            
            JButton button_yazarGuncelle = yy_component.addButtonParams("Yazar Güncelle",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_YAZAR_GUNCELLE,girdiler);
            yy_component.all_input_textFieldButton(textfieldArrayList, girdiler, button_yazarGuncelle);
            
           
            
            
     
            
        }
            
        
        yy_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageyazarYonetimFrame(){
        return yazaryonetim_frame;
    }
  
    
    
}

