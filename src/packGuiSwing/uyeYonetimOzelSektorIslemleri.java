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
public class uyeYonetimOzelSektorIslemleri {
    pencereGUI uyeyonetimOzelSektorIslemleri_frame;
    pencereGUI_Component uyeyonetimOzelSektorIslemleri_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public uyeYonetimOzelSektorIslemleri(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;

        uyeyonetimOzelSektorIslemleri_frame = new pencereGUI("UYE YONETIM -> OZEL SEKTOR ISLEMLERI", 800, 750);
        uyeyonetimOzelSektorIslemleri_component = new pencereGUI_Component(uyeyonetimOzelSektorIslemleri_frame,this.databaseIslemler);

        
    }
    
 
    public void inituyeYonetimiOzelSektorIslemleriGUI(boolean isLayoutActive){
        uyeyonetimOzelSektorIslemleri_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            uyeyonetimOzelSektorIslemleri_frame.initLayout();
            uyeyonetimOzelSektorIslemleri_component.infoBar("ODUNCE", 
                    "ODUNCC",
                    "ODUNCG");
            
         
            
            
          
            
        }
            
        
        uyeyonetimOzelSektorIslemleri_component.updateComponent();
    }
    
    
    
    public pencereGUI manageuyeyonetimOzelSektorFrame(){
        return uyeyonetimOzelSektorIslemleri_frame;
    }
  
    
    
}


