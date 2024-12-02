package packGuiSwing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

enum ButonPozisyon{
        UST, 
        ALT, 
        SAG, 
        SOL, 
        ORTA
}

enum CagrilacakFonksiyon{
    CF_TANIMLANMADI,
    CF_KUZEY,
    CF_GUNEY
}

public class pencereGUI_Component {
    private pencereGUI mevcutPencere;
    private boolean isUpdate;
    private int panelCounter[];
    
    JPanel centerPanel;
    JPanel westPanel;
    JPanel southPanel;
    JPanel northPanel;
    JPanel eastPanel;
    
    public pencereGUI_Component(pencereGUI mevcutPencere){
        this.mevcutPencere = mevcutPencere;
        isUpdate = false;
        panelCounter = new int[4];
        
        for(int i = 0 ; i < 4 ; i++)
            panelCounter[i] = 0;
        
        centerPanel = new JPanel();
        westPanel = new JPanel();
        southPanel = new JPanel();
        northPanel = new JPanel();
        eastPanel = new JPanel();
    
        //PANELLERIN BOYUTLARI
        westPanel.setPreferredSize(new Dimension(200, 0));
        eastPanel.setPreferredSize(new Dimension(200,0));
        northPanel.setPreferredSize(new Dimension(0,50));
        southPanel.setPreferredSize(new Dimension(0,50));
    }
    
    //BUTON ISLEMLERI
    public JButton addButton(String buttonText , ButonPozisyon butonPozisyon , CagrilacakFonksiyon butonFonksiyonu){
        //pencereGUI.debugPrint(countUpdate+"COMPONENTLER GUNCELLENDI MI : "+this.isUpdate + "<------");

        
        JButton button = new JButton(buttonText);
       button.setMinimumSize(new Dimension(100, 50));

       

        switch(butonPozisyon){
            case UST:   
                        panelCounter[0] += 1;
                        northPanel.setLayout(new GridLayout(1, panelCounter[0])); //1satir3sutun
                        northPanel.add(button);
                        this.mevcutPencere.getPencereGUIComponent_Addr().add(northPanel , BorderLayout.NORTH);
                        break;
            case ALT:   
                        panelCounter[1] += 1;
                        southPanel.setLayout(new GridLayout(1,panelCounter[1]));
                        southPanel.add(button);
                        this.mevcutPencere.getPencereGUIComponent_Addr().add(southPanel , BorderLayout.SOUTH);
                        break;
                
            case SAG:   
                        panelCounter[2] += 1;
                        eastPanel.setLayout(new GridLayout(panelCounter[2], 1)); //3satir1sutun
                        eastPanel.add(button);
                        this.mevcutPencere.getPencereGUIComponent_Addr().add(eastPanel , BorderLayout.EAST);
                        break;
            case SOL: {
                        panelCounter[3] += 1;
                        westPanel.setLayout(new GridLayout(panelCounter[3], 1)); //3satir1sutun
                        westPanel.add(button);
                        this.mevcutPencere.getPencereGUIComponent_Addr().add(westPanel, BorderLayout.WEST);        
                      }
            break;
                
            case ORTA:{
                button.setPreferredSize(new Dimension(250,100));
                centerPanel.add(button);
                
                
                this.mevcutPencere.getPencereGUIComponent_Addr().add(centerPanel, BorderLayout.CENTER);
                pencereGUI.debugPrint("pencereGUI_Component.addButton CENTER | COUNT UPDATE : "+pencereGUI.getCounter());

            }
            break;
            
            
            
        }//switch
        
        

       butonFonksiyonlari CF_Caller = new butonFonksiyonlari(button, mevcutPencere);
       
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                switch(butonFonksiyonu){
                    case CF_TANIMLANMADI:
                        CF_Caller.CF_TANIMLANMADI_Func();
                        break;
                    
                    case CF_GUNEY:
                        CF_Caller.CF_GUNEY_Func();
                        break;
                        
                        
                    case CF_KUZEY:
                        CF_Caller.CF_KUZEY_Func();
                        break;
                        
                        
                        
                        
                        
                        
                }
            }
        });
        

        return button;
    }
    
    public JButton addButton(String buttonText , int x , int y , int genislik , int yukseklik){
        //pencereGUI.debugPrint(countUpdate+"COMPONENTLER GUNCELLENDI MI : "+this.isUpdate + "<------");
        
        JButton button = new JButton(buttonText);
        button.setBounds(x, y, genislik , yukseklik);
        this.mevcutPencere.getPencereGUIComponent_Addr().add(button);
        
       button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ISLEMLER


                JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),"BUTON TIKLAMASI");
            }
        });
        
        
        return button;
    }
    
    //COMPONENTLERI GUNCELLEME SON ADIMDA CAGRILMASI GEREKIR
    public pencereGUI updateComponent(){
        isUpdate = true;
        //pencereGUI.debugPrint(countUpdate+"COMPONENTLER GUNCELLENDI MI : "+isUpdate+ "<------");
        this.mevcutPencere.getPencereGUIComponent_Addr().revalidate(); 
        this.mevcutPencere.getPencereGUIComponent_Addr().repaint(); 
        return this.mevcutPencere;
    }
    
    
public void addMenuBar(){
     
    //menu cubugu
    JMenuBar menuBar = new JMenuBar();
    
    //menu sekmeleri
    JMenu fileMenu = new JMenu("Dosya");
    JMenu settingsMenu = new JMenu("Ayarlar");
     
    JMenuItem accountSettingMenu = new JMenuItem("Hesaplar");
    JMenuItem yetkiKontrolSettingMenu = new JMenuItem("Erisim Kontrol");
    
    JMenuItem disaAktarFileMenu = new JMenuItem("Dışa Aktar");
    JMenuItem iceAktarFileMenu = new JMenuItem("İçe Aktar");
    JMenuItem yedeklemeFileMenu = new JMenuItem("Yedekleme");    
    
    fileMenu.add(disaAktarFileMenu);
    fileMenu.add(iceAktarFileMenu);
    fileMenu.add(yedeklemeFileMenu);

    settingsMenu.add(accountSettingMenu);
    settingsMenu.add(yetkiKontrolSettingMenu);
    
    menuBar.add(fileMenu);
    menuBar.add(settingsMenu);
    
    mevcutPencere.getPencereGUIComponent_Addr().setJMenuBar(menuBar);
    
    accountSettingMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
          pencereGUI.debugPrint("ACCOUNT SETTINGS");           
          }
    });
    
      yetkiKontrolSettingMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
          pencereGUI.debugPrint("YETKI KONTROL SETTINGS");
           }
    });
      
      
        disaAktarFileMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pencereGUI.debugPrint("DISA AKTAR FILE");
           }
    });
        
        
          iceAktarFileMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pencereGUI.debugPrint("ICE AKTAR FILE"); 
           }
    });
          
          
            yedeklemeFileMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
               pencereGUI.debugPrint("YEDEKLEME FILE");
           }
    });
 
 }
    
    
}

////////////////////COMPONENT FONKSIYONLARI//////////////////
class butonFonksiyonlari{
    
    //ORNEK PARAMETRELER : ORNEK PARAMETRELER DURUMA GORE ARTTIRILABILIR
    //butonNesnesi , (butonla alakali islemler yapilabilir) 
    //pencereGUI_Component , (uyari mesajlarini kullanmak icin)
    
    private JButton buton;
    private pencereGUI mevcutPencere;
    
    public butonFonksiyonlari(JButton buton , pencereGUI mevcutPencere){
        this.buton = buton;
        this.mevcutPencere = mevcutPencere;
    }
    
    ///////////////////////////////////////////UYARI KUTULARI FONKSIYON ICINDE KULLANILMAK UZERE
    public void messageBoxOK(String msg){
        pencereGUI.debugPrint("butonFonksiyonlari.messageBoxOK CAGRILDI");
        JOptionPane.showMessageDialog(mevcutPencere.getPencereGUIComponent_Addr(),msg);
    }
    
    
    
    public int messageBoxYesNo(String msgSoru , String okButonText){
      int secim = JOptionPane.showConfirmDialog(mevcutPencere.getPencereGUIComponent_Addr(),msgSoru , okButonText , JOptionPane.YES_NO_OPTION);
      if(secim == JOptionPane.OK_OPTION){
          pencereGUI.debugPrint("butonFonksiyonlari.messageBoxYesNo CAGRILDI: YAPILAN SECIM :"+1);
          return 1;
      }
      else if(secim == JOptionPane.NO_OPTION){
          pencereGUI.debugPrint("butonFonksiyonlari.messageBoxYesNo CAGRILDI: YAPILAN SECIM :"+0);
          return 0;          
      }
      
      pencereGUI.debugPrint("butonFonksiyonlari.messageBoxYesNo : YAPILAN SECIM :"+(-1));
      return -1;
    }
    
    
    
    
    //////BUTON FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
    //////BUTON FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
    //////BUTON FONKSIYON TANIMLARI///////////////////////////////////////////////////////////////////////////
    
    //FONKSIYON ADLANDIRMA: ENUM_ADI_Func
    public void CF_TANIMLANMADI_Func(){
        messageBoxOK("BU BUTON DAHA PROGRAMLANMADI !");
    }
    
    
//DENEME FONKSIYONLARI
    public void CF_KUZEY_Func(){
        messageBoxOK("KUZEY BUTONUNA TIKLANDI !");
    }
    
    public void CF_GUNEY_Func(){
        messageBoxYesNo("GUNEY BUTONUNA MI TIKLADINIZ ?", "Evet");
    }
    
    
    
}

class menuBarFonksiyonlari{
    
    
}
