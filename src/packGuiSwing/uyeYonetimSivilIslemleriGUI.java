/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;
import packDatabase.DatabaseIslemler;

/**
 *
 * @author archw
 */
import packDatabase.DatabaseIslemler;
import packGuiSwing.pencereGUI;


public class uyeYonetimSivilIslemleriGUI {
    pencereGUI uyeyonetimSivilIslemleri_frame;
    pencereGUI_Component uyeyonetimSivilIslemleri_component;
    DatabaseIslemler databaseIslemler;

    
    
        ////////////////////////////CONSTRUCTORLAR
    public uyeYonetimSivilIslemleriGUI(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;

        uyeyonetimSivilIslemleri_frame = new pencereGUI("UYE YONETIM -> SIVIL ISLEMLERI", 800, 750);
        uyeyonetimSivilIslemleri_component = new pencereGUI_Component(uyeyonetimSivilIslemleri_frame,this.databaseIslemler);
    }
        //////////////////////////
    
 
        //////////////////////////
    public void inituyeYonetimiSivilIslemleriGUI(boolean isLayoutActive){
        uyeyonetimSivilIslemleri_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            uyeyonetimSivilIslemleri_frame.initLayout();
            uyeyonetimSivilIslemleri_component.infoBar("ODUNCE", 
                    "ODUNCC",
                    "ODUNCG");
            
         
            
            
          
            
        }
            
        
        uyeyonetimSivilIslemleri_component.updateComponent();
    }    
    //////////////////////////


    
    
        //////////////////////////
    public pencereGUI manageuyeyonetimSivilIslemleriFrame(){
        return uyeyonetimSivilIslemleri_frame;
    }
        //////////////////////////
}


