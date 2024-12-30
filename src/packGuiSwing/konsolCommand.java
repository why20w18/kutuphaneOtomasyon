package packGuiSwing;

import java.awt.Font;
import javax.swing.JTextArea;
import packDatabase.DatabaseIslemler;

public class konsolCommand{
        DatabaseIslemler databaseIslemler;
        JTextArea konsolEkran;
        
    public konsolCommand(JTextArea konsolEkran , DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;
        this.konsolEkran = konsolEkran;
    }
    
    
    public String komutIslem(String command,JTextArea konsol,int varsayilanPunto){
        
    
        if ("help".equalsIgnoreCase(command)){
            return "\n-----KOMUTLAR-----\n"+
                   "KOMUT_1 -> sql:SQL_QUERY :: DML KOMUTLARINI DOĞRUDAN KONSOLDAN ÇALIŞTIRABİLİRSİNİZ\n"+
                   "KOMUT_2 -> tab :: VERİTABANINDAKİ TABLO ADLARINI ÇEVİRİR\n"+
                   "KOMUT_3 -> col TABLO_ADI :: MEVCUT TABLONUN NİTELİKLERİNİ ÇEVİRİR\n";
                    
        }
       
        else if ("exit".equalsIgnoreCase(command)){
            databaseIslemler.baglantiyiKes();
            System.exit(0);
        }
        
        else if("cls".equalsIgnoreCase(command)){
            konsol.setText("");
            return "KONSOL TEMİZLENDİ";
        }
        else if("+".equals(command)){
          konsol.setFont(new Font("Courier New", Font.BOLD, varsayilanPunto + 5));
          return "+ PUNTO="+varsayilanPunto;
        }
        else if("-".equals(command)){
            konsol.setFont(new Font("Courier New", Font.BOLD, varsayilanPunto - 5));
            return "- PUNTO="+varsayilanPunto;
        }
        
        else if("tab".equalsIgnoreCase(command)){
            return "\n"+databaseIslemler.getTab().toString();
        }
         
        else if(command.length() > 3 && "col".equalsIgnoreCase(command.substring(0,3))){
            String tabloAdi = command.substring(3).trim();
            return "\n"+databaseIslemler.SQL_Q_EXEC_Query("DESCRIBE "+tabloAdi);
        }
        
        else if(command.length() > 4 && "sql:".equalsIgnoreCase(command.substring(0,4))){
            String sql = command.substring(4);
            return "\n"+databaseIslemler.SQL_Q_EXEC_Query(sql.trim());
        }
        
      ////AGGRETE ISLEMLER:
      
        

        
        return "GEÇERSİZ İSTEK GİRDİNİZ help KOMUTU İLE YARDIM ALABİLİRSİNİZ";
    }

    
}
