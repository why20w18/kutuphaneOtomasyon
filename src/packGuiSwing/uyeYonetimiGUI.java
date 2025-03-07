/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import packDatabase.DatabaseIslemler;

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
import javax.swing.table.TableColumn;
import packDatabase.DatabaseIslemler;

import packGuiSwing.pencereGUI;


public class uyeYonetimiGUI {
    pencereGUI uyeyonetim_frame;
    pencereGUI_Component uyeyonetim_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public uyeYonetimiGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("oduncYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        uyeyonetim_frame = new pencereGUI("UYE YONETIM", 1000, 750);
        uyeyonetim_component = new pencereGUI_Component(uyeyonetim_frame,this.databaseIslemler);
        uyeyonetim_component.setPanelBoyutlari(-1, -1, -1, 60);
        
    }
    
 
    public void inituyeYonetimiGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("oduncYonetimGUI.initoduncYonetimGUI CAGRILDI");
        uyeyonetim_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            uyeyonetim_frame.initLayout();
            uyeyonetim_component.infoBar("ODUNCE", 
                    "ODUNCC",
                    "ODUNCG");
            
         
            
                                     //0         1         2        3       4               5               6                   7           8
            String kolonIsimleri[] = { "üyeID", "Tam Ad","TC","Cinsiyet","Üyelik Ücret","Kayıt Tarihi","Üye İndirim Miktarı","Üye Türü","Üye Ek Bilgi"};
            JTable jtable_uyeler = uyeyonetim_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_UYE_LIST);
            
            TableColumn id_col = jtable_uyeler.getColumnModel().getColumn(0);
            id_col.setMinWidth(40);
            id_col.setMaxWidth(40);
            
            TableColumn tamAdColumn = jtable_uyeler.getColumnModel().getColumn(1);
            tamAdColumn.setPreferredWidth(150);
            
            TableColumn uyeIndirColumn = jtable_uyeler.getColumnModel().getColumn(6);
            uyeIndirColumn.setPreferredWidth(50);
            uyeIndirColumn.setMaxWidth(50);
            uyeIndirColumn.setMaxWidth(50);
            
            TableColumn ucret = jtable_uyeler.getColumnModel().getColumn(4);
            ucret.setPreferredWidth(50);
            ucret.setMaxWidth(50);
            ucret.setMaxWidth(50);
            
            
            TableColumn uyecinsiyet = jtable_uyeler.getColumnModel().getColumn(3);
            uyecinsiyet.setPreferredWidth(50);
            uyecinsiyet.setMaxWidth(50);
            uyecinsiyet.setMinWidth(50);
            
            TableColumn kayitColumn = jtable_uyeler.getColumnModel().getColumn(5);
            kayitColumn.setPreferredWidth(200);
            kayitColumn.setMaxWidth(200);
            kayitColumn.setMinWidth(200);
          
            TableColumn ekbilgiColumn = jtable_uyeler.getColumnModel().getColumn(8);
            ekbilgiColumn.setMaxWidth(580);
            ekbilgiColumn.setMinWidth(580);
            ekbilgiColumn.setPreferredWidth(580);
            
            
            JButton ogrenciIslemleriButon = uyeyonetim_component.addButton("Öğrenci İşlemleri", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_NW_UYE_YONETIM_OGRENCI_ISLEMLERI);
            JButton sivilIslemleriButon = uyeyonetim_component.addButton("Sivil İşlemleri", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_NW_UYE_YONETIM_SIVIL_ISLEMLERI);
            JButton ozelSektorIslemleriButon = uyeyonetim_component.addButton("Özel Sektör İşlemleri", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_NW_UYE_YONETIM_OZELSEKTOR_ISLEMLERI);
            
            
            
            
            
            
        }
        uyeyonetim_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageoduncYonetimGUIFrame(){
        return uyeyonetim_frame;
    }
  
    
    
}


