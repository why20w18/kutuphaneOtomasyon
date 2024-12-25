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


public class oduncYonetimGUI {
    pencereGUI oduncyonetim_frame;
    pencereGUI_Component oduncyonetim_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public oduncYonetimGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("oduncYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        oduncyonetim_frame = new pencereGUI("ODUNC YONETIM", 800, 750);
        oduncyonetim_component = new pencereGUI_Component(oduncyonetim_frame,this.databaseIslemler);

        
    }
    
 
    public void initoduncYonetimGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("oduncYonetimGUI.initoduncYonetimGUI CAGRILDI");
        oduncyonetim_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            oduncyonetim_frame.initLayout();
            oduncyonetim_component.infoBar("ODUNCE", 
                    "ODUNCC",
                    "ODUNCG");
            
         
            
                                     //0         1
            String kolonIsimleri[] = { "ID", "Ödünç Alma Tarihi","İade Etme Tarihi","Kitap Adı","Kitap Yazar","Kitap Sayfa Sayısı","Üye Adı","Üye Türü"};
          
            JTable jtable_kitapListesi = oduncyonetim_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_ODUNC_LIST);
            
            //EKLEME CIKARTMA
            //JTextField tfield_kID = oduncyonetim_component.addTextField("ID", 25, ButonPozisyon.UST);
            //JTextField tfield_kKategori = oduncyonetim_component.addTextField("kitapKategori", 25, ButonPozisyon.UST);
            
            
            String[] girdiler = new String[kolonIsimleri.length];
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            
           //textfieldArrayList.add(tfield_kID);
           //textfieldArrayList.add(tfield_kKategori);
           oduncyonetim_component.ListenerTextField(textfieldArrayList, girdiler);   
            
            
          
            
        }
            
        
        oduncyonetim_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageoduncYonetimGUIFrame(){
        return oduncyonetim_frame;
    }
  
    
    
}

