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
import static packGuiSwing.CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_UYE_AD_SOYAD;



public class oduncYonetimOduncIslemleriGUI {
    pencereGUI oduncyonetim_oi_frame;
    pencereGUI_Component oyoi_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public oduncYonetimOduncIslemleriGUI(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;
        oduncyonetim_oi_frame = new pencereGUI("ODUNC YONETIM -> ODUNC ISLEMLERI", 800, 750);
        oyoi_component = new pencereGUI_Component(oduncyonetim_oi_frame,this.databaseIslemler);     
    }
    
 
    public void initoduncYonetimOduncIslemleriGUI(boolean isLayoutActive){
        oduncyonetim_oi_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            oduncyonetim_oi_frame.initLayout();
            oyoi_component.infoBar("ODUNCE", 
                    "ODUNCC",
                    "ODUNCG");
            
            JTextField izinVerilenGunSayisi = oyoi_component.addTextField("izinVerilenGün", 25, ButonPozisyon.UST);
            JComboBox<String> cbox_kitaplar = oyoi_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.UST, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_KITAPLAR);
            //JComboBox<String> cbox_yazarlar = oyoi_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.UST, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_YAZARLAR);
            JComboBox<String> cbox_uyeler = oyoi_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.UST, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_UYE_AD_SOYAD);

            
            int comboBoxGirdiler[] = new int[2];
            oyoi_component.setListenerCombobox(cbox_kitaplar, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_KITAP_ID,comboBoxGirdiler,0);
            //oyoi_component.setListenerCombobox(cbox_yazarlar, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_YAZAR_ID,comboBoxGirdiler,1);
            oyoi_component.setListenerCombobox(cbox_uyeler, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_UYE_ID,comboBoxGirdiler,1);
            
            
            String[] girdiler = new String[1]; 
            ArrayList<JTextField> textfieldArrayList = new ArrayList<>();
            textfieldArrayList.add(izinVerilenGunSayisi);
          
            JButton oduncAlindi = oyoi_component.addButtonParams("Ödünç Alındı", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_ODUNC_EKLEME, girdiler, comboBoxGirdiler);
            oyoi_component.all_input_textFieldButton(textfieldArrayList, girdiler, oduncAlindi);

            JButton oduncGetirdi = oyoi_component.addButtonParams("Ödünç Getirdi", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_ODUNC_CIKARTMA, girdiler, comboBoxGirdiler);
            oyoi_component.all_input_textFieldButton(textfieldArrayList, girdiler, oduncGetirdi);
          
            
        }
            
        
        oyoi_component.updateComponent();
    }
    
        
    public pencereGUI manageoduncYonetimOduncIslemleriGUIFrame(){
        return oduncyonetim_oi_frame;
    }
  
    
    
}

