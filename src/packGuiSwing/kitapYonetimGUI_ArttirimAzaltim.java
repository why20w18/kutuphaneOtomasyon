/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
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
    }
    
 
    public void initkitapYonetimGUI_ArttirimAzaltim(boolean isLayoutActive){
        arttirim_azaltim_frame.InitPencereGUI(true);
        
        //GUI ELEMANLARININ YERLESTIRILECEGI ALAN
        if(isLayoutActive == true){
            arttirim_azaltim_frame.initLayout();
            
            //cursor ile belirli kategoride yada tum kategorilerde arttirim azaltim yada belli bir kitapta arttim azaltim % cinsinden
            
            
          
        
        }
            

        
        kyaa_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageinitkitapYonetimGUI_ArttirimAzaltimFrame(){
        return arttirim_azaltim_frame;
    }
  
    
    
}

    
