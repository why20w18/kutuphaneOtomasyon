package packSourceCode;

import packGuiSwing.pencereGUI;
import packGuiSwing.mainGUI;


public class Main {
    
    public static void main(String[] args){
        System.out.println("Main");
        pencereGUI.setDebugOUTPUT(true);
        
        //ANA PENCEREYI BASLATMA
        mainGUI home_frame = new mainGUI();
        home_frame.initMainGUI(true);
        
        

        
    }
    
}
