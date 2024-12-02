package packGuiSwing;

import java.awt.BorderLayout;
import javax.swing.JButton;
import packGuiSwing.pencereGUI;

/*
1-HER PENCERE ICIN YENI CLASS ACILMALIDIR

2-HER PENCERE/SEKME KENDI CLASSI ICINDE .getStartFrame() ILE BASLATILMALIDIR

3-.getStartFrame() CONSTRUCTOR ICINE KOYULMAMALIDIR initCLASS_ADI SEKLINDE YENI METOD
ACILARAK 2.ADIMDA BASLATILMALIDIR

4-initLayout() , initCLASS_ADI ICERISINDE LAYOUT AKTIFLIGI KONTROLUNDEN SONRA BASLATILMALIDIR

*/


public class mainGUI {
    pencereGUI home_frame;
    pencereGUI_Component hf_component;
    
    //CONSTRUCTORLAR
    public mainGUI() {
        pencereGUI.debugPrint("mainGUI BASLATILDI");
        home_frame = new pencereGUI("KUTUPHANE OTOMASYON PROGRAMI", 800, 750);
        hf_component = new pencereGUI_Component(home_frame);

    }
    
 
    public void initMainGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("mainGUI.initMainGUI CAGRILDI");
        home_frame.InitPencereGUI();
        
        if(isLayoutActive == true){
            home_frame.initLayout();
            hf_component.addMenuBar();
            
            ////////////////////
            JButton buton1 = hf_component.addButton("Kuzey",ButonPozisyon.UST,CagrilacakFonksiyon.CF_KUZEY);
            JButton buton8 = hf_component.addButton("Kuzey2",ButonPozisyon.UST,CagrilacakFonksiyon.CF_KUZEY);

            JButton buton2 = hf_component.addButton("Guney",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_GUNEY);
            JButton buton9 = hf_component.addButton("Guney2",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_GUNEY);

            JButton buton3 = hf_component.addButton("Dogu",ButonPozisyon.SAG,CagrilacakFonksiyon.CF_TANIMLANMADI);
            JButton buton4 = hf_component.addButton("Bati",ButonPozisyon.SOL,CagrilacakFonksiyon.CF_TANIMLANMADI);
            JButton buton7 = hf_component.addButton("Bati2",ButonPozisyon.SOL,CagrilacakFonksiyon.CF_TANIMLANMADI);
            //JButton buton8 = hf_component.addButton("test2", 200,150, 400, 400);
            

            
            JButton buton5 = hf_component.addButton("Merkez",ButonPozisyon.ORTA,CagrilacakFonksiyon.CF_TANIMLANMADI);
            JButton buton6 = hf_component.addButton("Merkez2",ButonPozisyon.ORTA,CagrilacakFonksiyon.CF_TANIMLANMADI);

            
            pencereGUI.debugPrint(buton1.getText());
        
        }
            

        
        hf_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageHomeFrame(){
        return home_frame;
    }
  
    
    
}