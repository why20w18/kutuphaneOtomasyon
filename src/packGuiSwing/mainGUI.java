package packGuiSwing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import packDatabase.DatabaseIslemler;

import packGuiSwing.pencereGUI;

public class mainGUI {
    pencereGUI home_frame;
    pencereGUI_Component hf_component;
    DatabaseIslemler databaseIslemler;
    boolean splashAcikMi;
    
    //CONSTRUCTORLAR
    public mainGUI(DatabaseIslemler databaseIslemler , boolean splashAcikMi) {
        pencereGUI.debugPrint("mainGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;
        home_frame = new pencereGUI("KUTUPHANE OTOMASYON PROGRAMI", 800, 750);
        hf_component = new pencereGUI_Component(home_frame,this.databaseIslemler);
        this.splashAcikMi = splashAcikMi;
    }
    
 
    public void initMainGUI(boolean isLayoutActive){
        if(splashAcikMi){    
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.showSplash();
        }
        
        
        
        pencereGUI.debugPrint("mainGUI.initMainGUI CAGRILDI");
        home_frame.InitPencereGUI(false);
        
       
        //GUI ELEMANLARININ YERLESTIRILECEGI ALAN
        if(isLayoutActive == true){
            home_frame.initLayout();
            hf_component.addMenuBar();
            
            ////////////////////
            JTextArea konsol = hf_component.konsolEkran(ButonPozisyon.BOSLUK_TAMAMI);
            konsol.setText("KÜTÜPHANE OTOMASYON PROGRAMI KONSOL EKRANI:");
           
            JButton buton1 = hf_component.addButton("Personel Yönetimi",ButonPozisyon.UST,CagrilacakFonksiyon.CF_BUTTON_NW_PERSONEL_YONETIM);
            JButton buton8 = hf_component.addButton("Üye Yönetimi",ButonPozisyon.UST,CagrilacakFonksiyon.CF_BUTTON_NW_UYE_YONETIM);

            JButton buton2 = hf_component.addButton("Yazar Yönetimi",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_NW_YAZAR_YONETIM);
            
            
            //JButton buton9 = hf_component.addButton("Yayınevi Yönetimi",ButonPozisyon.ALT,CagrilacakFonksiyon.CF_BUTTON_NW_YAYINEVI_YONETIM);
            JButton button_kitapYazarTablosu = hf_component.addButton("Kitap Yazar Yönetimi", ButonPozisyon.ALT, CagrilacakFonksiyon.CF_BUTTON_NW_KITAP_YAZAR_TABLOSU);
            
            
            
            JTextField messager = hf_component.addTextField("Konsol:", 400, ButonPozisyon.ALT);
            konsolCommand cmd = new konsolCommand(konsol, databaseIslemler);
            hf_component.enterListener(messager, konsol,cmd);
            

            JButton buton4 = hf_component.addButton("Ödünç Yönetimi",ButonPozisyon.SOL,CagrilacakFonksiyon.CF_BUTTON_NW_ODUNC_YONETIM);
            JButton buton7 = hf_component.addButton("Kitap Yönetimi",ButonPozisyon.SOL,CagrilacakFonksiyon.CF_BUTTON_NW_KITAP_YONETIM);
            
            
            
            //JButton buton5 = hf_component.addButton("HIZLI2ERISIM",ButonPozisyon.ORTA,CagrilacakFonksiyon.CF_TANIMLANMADI);
            //JButton buton6 = hf_component.addButton("HIZLI3ERISIM",ButonPozisyon.ORTA,CagrilacakFonksiyon.CF_TANIMLANMADI);

            
            pencereGUI.debugPrint(buton1.getText());
        
        }
            

        
        hf_component.updateComponent();
    }
    
    
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI manageHomeFrame(){
        return home_frame;
    }
  
    
    
}

class SplashScreen{
    private JWindow window;

    public SplashScreen(){
        window = new JWindow();
        window.setLayout(new BorderLayout());
        window.setSize(300, 150);
        
        JLabel label = new JLabel(new ImageIcon("packImages/splashLogo.png"));
        window.getContentPane().add(label, BorderLayout.CENTER);
        JLabel text = new JLabel("PROGRAM BAŞLATILIYOR", JLabel.CENTER);
        text.setFont(new Font("Arial", Font.BOLD, 16));
        window.getContentPane().add(text, BorderLayout.SOUTH);
    }
    
    public void showSplash() {
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }
    
}