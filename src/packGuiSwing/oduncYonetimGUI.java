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
    pencereGUI_Component oy_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public oduncYonetimGUI(DatabaseIslemler databaseIslemler) {
        pencereGUI.debugPrint("oduncYonetimGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        oduncyonetim_frame = new pencereGUI("ODUNC YONETIM", 800, 750);
        oy_component = new pencereGUI_Component(oduncyonetim_frame,this.databaseIslemler);

        
    }
    
 
    public void initoduncYonetimGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("oduncYonetimGUI.initoduncYonetimGUI CAGRILDI");
        oduncyonetim_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            oduncyonetim_frame.initLayout();
            oy_component.infoBar("ODUNCE", 
                    "ODUNCC",
                    "ODUNCG");
            
         
            
                                     //0         1
            String kolonIsimleri[] = { "ödünçID", "Ödünç Alma Tarihi","İade Etme Tarihi","İzin Verilen Gün","Ceza Miktarı","Üye Tam Adı","Üye Türü","Kitap Adı","Kitap Yazar"};
          
            JTable jtable_odunc = oy_component.addTable(kolonIsimleri,30,15,CagrilacakFonksiyon.CF_TABLE_ODUNC_LIST);
            
            /*
            1-TUM KITAPLARIN ADINI COMBOBOXA DOLDUR -> geriye kitapID cevirsin +
            2-TUM YAZARLARIN ADINI COMBOBOXA DOLDUR -> geriye yazarID cevirsin +
            3-TEXTFIELD IZIN VERILEN GUN SAYISI GIRILSIN
            4-BUTON olsun odunc aldi seklinde 
            5-GERI GETIRDIGI TARIHI GUNCELLEME ILE DUZELTEBILELIM
            6-ceza miktari trigger ile tetiklenip odunclog tablosunda tutulsun
            
            7-ODUNC ALINAN KITABIN STOGU OTOMATIK AZALSIN
            8-ODUNC VERILINCE KITABIN STOGU OTOMATIK ARTSIN
            9-STOK SIFIRSA EKRANA UYARI VERSIN ODUNC VERILEMEZ SEKLINDE
            */
            
            JButton oduncIslemleriButon = oy_component.addButton("Ödünç İşlemleri", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_NW_ODUNC_ISLEMLERI);
            
        }
            
        
        oy_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageoduncYonetimGUIFrame(){
        return oduncyonetim_frame;
    }
  
    
    
}

