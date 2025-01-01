package packGuiSwing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

enum PENCERE_KAPANIS{
    TAMEMEN_KAPAT,
    MEVCUT_KAPAT
}

public class pencereGUI{
    private JFrame newWindow;
    private String pencereAdi;
    private int pencereGenislik;
    private int pencereYukseklik;
    
    private static boolean debugOUTPUT = true;  
    public static int counterPencereGUI = 0;
    
    public pencereGUI(String pencereAdi , int pencereGenislik , int pencereYukseklik){
        debugPrint("pencereGUI BASLATILDI");
        this.pencereAdi =  pencereAdi;
        this.pencereGenislik = pencereGenislik;
        this.pencereYukseklik = pencereYukseklik;
    }

    ////////////////////BU PAKETIN METODLARI///////////////////////
    
    //baska pakette pencere olusturmaya izin yok sadece bu paketteki classlar uzerinden
    protected void InitPencereGUI(boolean subWindow){
        debugPrint("debugOUTPUT: pencereGUI.getStartFrame CAGRILDI");
        
        newWindow = new JFrame(this.pencereAdi);
        newWindow.setSize(pencereGenislik,pencereYukseklik);
        
        if(subWindow == false)
        newWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        else if(subWindow == true)
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        newWindow.setVisible(true);
        newWindow.setMinimumSize(new Dimension(800,200));
    }

    public JFrame getPencereGUIComponent_Addr(){
        return this.newWindow;
    }
    
    ////////////////////PUBLIC METODLAR///////////////////////

    public String getRetPencereAdi(){
        return this.pencereAdi;
    }
    
    public pencereGUI setGenislik(int pencereGenislik){
        this.pencereGenislik = pencereGenislik;
        return this;
    }
    
    public pencereGUI setYukseklik(int pencereYukseklik){
        this.pencereYukseklik = pencereYukseklik;
        return this;
    }
    
    //DEBUG
    public static void debugPrint(String message){
        if (debugOUTPUT) {
            System.out.println("debugOUTPUT: "+message);
        }
    }
    
    public static void setDebugOUTPUT(boolean acT_kapaF){
        pencereGUI.debugOUTPUT = acT_kapaF;
    }
    
    public static int getCounter(){
        pencereGUI.counterPencereGUI += 1;
        return counterPencereGUI;
    }    
    //DEBUG
    
    
    public void initLayout(){
        pencereGUI.debugPrint("INIT LAYOUT TEST");
        this.newWindow.setLayout(new BorderLayout());
    }
    
    
}