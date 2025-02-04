package packDatabase;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.protocol.Resultset;
import java.net.ConnectException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;


public class DatabaseIslemler {
    databaseBaglanti database;
    
    //constructor
    public DatabaseIslemler(databaseBaglanti database){
        this.database = database;
    } 
    
    
//---------------------------------- SORGULAR ----------------------------//
    public void baglantiyiKes(){
        database.databaseSonlandir();
    }
    
public String SQL_Q_EXEC_Query(String sql) {
    Connection bg = database.getBaglanti();

    StringBuilder result = new StringBuilder();
    
    try (PreparedStatement pst = bg.prepareStatement(sql);
         ResultSet rst = pst.executeQuery()) {

        ResultSetMetaData rsmd = (ResultSetMetaData) rst.getMetaData();
        int columnCount = rsmd.getColumnCount();
        
        while (rst.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                String columnValue = rst.getString(i);
                
                result.append(columnName).append(": ").append(columnValue).append("\t\t");
            }
            result.append("\n");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        return "HATALI SORGU: " + ex.getMessage();
    }

    // Sorgu sonuçlarını döndür
    return result.length() > 0 ? result.toString() : "SONUÇ BULUNAMADI";
}


public List<String> getTab(){
 
        Connection bg = database.getBaglanti();
        List<String> tableNames = new ArrayList<>();
        String query = "SHOW TABLES";

        try {
             Statement stmt = bg.createStatement();
             ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                tableNames.add(rs.getString(1)+"\n");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableNames;
    }

    
    //odunc alinan kitaplar ve alan uyeleri listeleyen sorgu
public void SQL_Q_oduncKitaplarVeUyeler(){
        Connection bg = database.getBaglanti();
        String sql = "SELECT T_KITAP.kitapAd, T_UYELER.uyeAd, T_ODUNC.oduncAlmaTarih, T_ODUNC.iadeEtmeTarih " +
                     "FROM T_ODUNC "+
                     "JOIN T_KITAP ON T_ODUNC.kitapID = T_KITAP.kitapID " +
                     "JOIN T_UYELER ON T_ODUNC.uyeID = T_UYELER.uyeID;";
        try{
            PreparedStatement pst = bg.prepareStatement(sql);
            var res = pst.executeQuery();
            
            while(res.next()){ //KOLON CEKME
                String kitapAd = res.getString("kitapAd");
                String uyeAd = res.getString("uyeAd");
                String oduncAlmaTarih = res.getString("oduncAlmaTarih");
                String iadeEtmeTarih = res.getString("iadeEtmeTarih");
                System.out.println("KitapAd/uyeAd/oduncAlmaTarihi/iadeEtmeTarihi");
                System.out.println(kitapAd + " - " + uyeAd + " - " + oduncAlmaTarih + " - " + iadeEtmeTarih);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }








//////////////////////////////////////////////////////////////////////T_ÜYELER//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////T_ÜYELER//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////T_ÜYELER//////////////////////////////////////////////////////////////////////
///T_OGRENCI

public void SQL_Q_ogrenciEkle(String uyeAd , String uyeSoyad , String uyeTCNO ,int uyeCinsiyet ,double uyelikUcreti,double uyeIndirimMiktari,
        String ogrenciTur,String ogrenciOkulAd,String bolumVeyaSinifOrtak , double ortalamaOrtak,int ortaOgretimTur){
    Connection bg = database.getBaglanti();
    String sql_uye = "INSERT INTO T_UYELER(uyeAd,uyeSoyad,uyeTCNO,uyeCinsiyet,uyelikUcreti,uyeIndirimMiktari,uyeTuru) "+
                     "VALUES(?,?,?,?,?,?,'OGRENCI');";
    
    
    System.out.println("AD:"+uyeAd);
    System.out.println("soyad:"+uyeSoyad);
    System.out.println("tcno:"+uyeTCNO);
    System.out.println("cinsiyet:"+uyeCinsiyet);
    System.out.println("uyelikucret:"+uyelikUcreti);
    System.out.println("uyeindirim:"+uyeIndirimMiktari);
    System.out.println("ogrenci turu:"+ogrenciTur);
    System.out.println("okul ad:"+ogrenciOkulAd);
    System.out.println("bolumsinif:"+bolumVeyaSinifOrtak);
    System.out.println("ortalama:"+ortalamaOrtak);
    System.out.println("ortaogretim tur:"+ortaOgretimTur);    
    
    
    String sql_ogrenci = "INSERT INTO T_OGRENCI(ogrenciOkulAd,ogrenciTuru,uyeID) VALUES(?,?,?);";
    String sql_ogrenciTuru = null;
    int sqlOgrenciTurSorgu = -1;
    
    if(ogrenciTur.equals("LISANS")){
        sql_ogrenciTuru = "INSERT INTO T_LISANS(lisansBolum,lisansAGNO,ogrenciID) VALUES(?,?,?);";
                    sqlOgrenciTurSorgu = 0;

    }
    else if(ogrenciTur.equals("ORTAOGRETIM")){
        if(ortaOgretimTur == 0){
                    sql_ogrenciTuru = "INSERT INTO T_ORTAOGRETIM(ortaogretimSinif,ortaogretimOrtalama,ortaOgretimTuru,ogrenciID) VALUES(?,?,'ILKOKUL',?);";
            sqlOgrenciTurSorgu = 1;
        }
        
        else if(ortaOgretimTur == 1){
                    sql_ogrenciTuru = "INSERT INTO T_ORTAOGRETIM(ortaogretimSinif,ortaogretimOrtalama,ortaOgretimTuru,ogrenciID) VALUES(?,?,'ORTAOKUL',?);";
            sqlOgrenciTurSorgu = 2;
        }
        
        else if(ortaOgretimTur == 2){
                    sql_ogrenciTuru = "INSERT INTO T_ORTAOGRETIM(ortaogretimSinif,ortaogretimOrtalama,ortaOgretimTuru,ogrenciID) VALUES(?,?,'LISE',?);";
                        sqlOgrenciTurSorgu = 3;
        }
        
    
    }
    else if(ogrenciTur.equals("LISANSUSTU")){
        sql_ogrenciTuru = "INSERT INTO T_LISANSUSTU(lisansBolum,lisansUstuAGNO,ogrenciID) VALUES(?,?,?);";
                                sqlOgrenciTurSorgu = 4;
    }
  
    
    try{
        //T_UYELERE EKLEME ISLEMI
        PreparedStatement pst = bg.prepareStatement(sql_uye);
        pst.setString(1, uyeAd);
        pst.setString(2, uyeSoyad);
        pst.setString(3, uyeTCNO);
        
        if(uyeCinsiyet == 0)
            pst.setString(4, "K");
        else if(uyeCinsiyet == 1)
            pst.setString(4, "E");
        
        pst.setDouble(5, uyelikUcreti);
        pst.setDouble(6, uyeIndirimMiktari);
        
        int affected = pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("OGRENCI UYE BILGILERI UYE TABLOSUNA EKLENEMEDI !");
        else 
            System.out.println("OGRENCININ UYE BILGILERI UYE TABLOSUNA EKLENDI !");
        
        //T_OGRENCI
        //INSERT INTO T_OGRENCI(ogrenciOkulAd,ogrenciTuru) VALUES(?,?);
        PreparedStatement pst1 = bg.prepareStatement(sql_ogrenci);
       pst1.setString(1, ogrenciOkulAd);
        if(ogrenciTur.equals("LISANS"))
            pst1.setString(2, "LISANS");
        else if(ogrenciTur.equals("LISANSUSTU"))
            pst1.setString(2, "LISANSUSTU");
        else if(ogrenciTur.equals("ORTAOGRETIM"))
            pst1.setString(2, "ORTAOGRETIM");
        
        //AYRI BIR SORGUYLA UYEIDYI CEK
        int uyeID = 0;
        if((uyeID=SQL_Q_getUyeID_pTC(uyeTCNO)) == -1){
            System.out.println("OGRENCI T_UYELER TABLOSUNA EKLENEMEDI !");
            return;
        }
        pst1.setInt(3, uyeID);
        
        
        affected = 0;
        affected = pst1.executeUpdate();
        
        if(affected <= 0)
            System.out.println("OGRENCININ OKUL_ADI VE TIPI T_OGRENCIYE EKLENIRKEN HATA");
        else 
            System.out.println("OGRENCI T_OGRENCI TABLOSUNA BASARIYLA EKLENDI");
        
        
        
        
        //OGRENCI ALT DALLARINI BAGLAMA
        PreparedStatement pst2 = bg.prepareStatement(sql_ogrenciTuru);
        
        //ORTAOGRETIM SORGUSUDUR
        //                    sql_ogrenciTuru = "INSERT INTO T_ORTAOGRETIM(ortaogretimSinif,ortaogretimOrtalama,ortaOgretimTuru,ogrenciID) VALUES(?,?,'LISE',?);";
        //        sql_ogrenciTuru = "INSERT INTO T_LISANSUSTU(lisansBolum,lisansUstuAGNO,ogrenciID) VALUES(?,?);";

        if(sqlOgrenciTurSorgu > 0 && sqlOgrenciTurSorgu < 4){
            pst2.setInt(1, Integer.parseInt(bolumVeyaSinifOrtak));
        }
        else{
            pst2.setString(1, bolumVeyaSinifOrtak);
        }
       
       pst2.setDouble(2, ortalamaOrtak);
       
       int ogrenciID = 0;
       if((ogrenciID = SQL_Q_getOgrenciID_pUyeID(uyeID)) == -1){
           System.out.println("OGRENCI T_OGRENCI TABLOSUNA EKLENEMEDI !");
           return;
       }
       System.out.println("nihahi sorgu::"+pst2+"::::::OGRENCI_ID"+ogrenciID);
       pst2.setInt(3, ogrenciID);
       
       System.out.println("nihahi sorgu::"+pst2);
       
       affected = 0;
       affected = pst2.executeUpdate();
       
       if(affected <= 0)
            System.out.println("NIHAI OGRENCI TURU TABLOSUNA EKLEME BASARISIZ !");
       else 
            System.out.println("NIHAI OGRENCI TURU TABLOSUNA EKLEME BASARILIDIR !");
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

public int SQL_Q_getUyeID_pTC(String tcno){
    Connection bg = database.getBaglanti();
    String sql = "SELECT uyeID FROM T_UYELER WHERE uyeTCNO=?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, tcno);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int uyeID = rst.getInt("uyeID");
            return uyeID;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    return -1;
}

public int SQL_Q_getOgrenciID_pUyeID(int uyeID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT ogrenciID FROM T_OGRENCI WHERE uyeID=?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, uyeID);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int ogrenciID = rst.getInt("ogrenciID");
            return ogrenciID;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    return -1;
}

public void SQL_Q_ogrenciCikart(int uyeID){
    //T_UYELER CIKART --> uyeID ile 
    //T_OGRENCI CIKART --> ogrenciID ile 
    //OGRENCI TURUNE GORE SON TABLODAN CIKART
    
    Connection bg = database.getBaglanti();
    String sql_uyeler = "DELETE FROM T_UYELER WHERE uyeID = ?;";
    String sql_ogrenciler = "DELETE FROM T_OGRENCI WHERE ogrenciID = ?;";
    
    //OGRENCI IDYI CEKIYORUZ
    int ogrenciID = SQL_Q_getOgrenciID_pUyeID(uyeID);
    
    try {
        //TURU T_OGRENCIDEN SILMEDEN CEKIYORUZ
        String sql_tur = "SELECT ogrenciTuru FROM T_OGRENCI WHERE ogrenciID = ?;";
        PreparedStatement pst_tur = bg.prepareStatement(sql_tur);
        pst_tur.setInt(1, ogrenciID);
        ResultSet rst = pst_tur.executeQuery();
        
        String ogrenciTuru = null;
        if (rst.next())
            ogrenciTuru = rst.getString("ogrenciTuru");
        
        
        String sql_nihaiTablo = null;
        if (ogrenciTuru.equals("LISANS"))
            sql_nihaiTablo = "DELETE FROM T_LISANS WHERE ogrenciID = ?;";
        
        else if (ogrenciTuru.equals("LISANSUSTU")) 
            sql_nihaiTablo = "DELETE FROM T_LISANSUSTU WHERE ogrenciID = ?;";
        
        else if (ogrenciTuru.equals("ORTAOGRETIM")) 
            sql_nihaiTablo = "DELETE FROM T_ORTAOGRETIM WHERE ogrenciID = ?;";
        
        int affected = 0;
        if(sql_nihaiTablo != null){
            PreparedStatement pst_tureGoreSilme = bg.prepareStatement(sql_nihaiTablo);
            pst_tureGoreSilme.setInt(1, ogrenciID);
            affected = pst_tureGoreSilme.executeUpdate();
            
            if(affected <= 0)
                System.out.println("T_" + ogrenciTuru + " TABLOSUNDAN OGRENCI BILGILERI SILINEMEDI !");
            else
                System.out.println("T_" + ogrenciTuru + " TABLOSUNDAN OGRENCI BILGILERI BASARIYLA SILINDI !");
            
        }
        
        
        PreparedStatement pst_ogrenciler = bg.prepareStatement(sql_ogrenciler);
        pst_ogrenciler.setInt(1, ogrenciID);
        affected = 0;
        affected = pst_ogrenciler.executeUpdate();
        
        if(affected <= 0) 
            System.out.println("T_OGRENCI TABLOSUNDAN SILME BASARISIZ !");
         
        else 
            System.out.println("T_OGRENCI TABLOSUNDAN SILME BASARILI !");
        
        
        //T_UYELERDEN SILME
        PreparedStatement pst = bg.prepareStatement(sql_uyeler);
        pst.setInt(1, uyeID);
        affected = pst.executeUpdate();
        
        if (affected <= 0)
            System.out.println("T_UYELER TABLOSUNDAN SILMEDE PROBLEM !");
        else
            System.out.println("T_UYELER TABLOSUNDAN SILME BASARILIR");
        
    
    } 
    catch (SQLException ex){
        ex.printStackTrace();
    }
}

////////T_SIVIL
public void SQL_Q_sivilEkle(String uyeAd , String uyeSoyad , String uyeTCNO ,int uyeCinsiyet ,double uyelikUcreti,double uyeIndirimMiktari,
        double sivilGelirMiktari){
    Connection bg = database.getBaglanti();
    String sql_uye = "INSERT INTO T_UYELER(uyeAd,uyeSoyad,uyeTCNO,uyeCinsiyet,uyelikUcreti,uyeIndirimMiktari,uyeTuru) "+
                     "VALUES(?,?,?,?,?,?,'SIVIL');";
    
    
    System.out.println("AD:"+uyeAd);
    System.out.println("soyad:"+uyeSoyad);
    System.out.println("tcno:"+uyeTCNO);
    System.out.println("cinsiyet:"+uyeCinsiyet);
    System.out.println("uyelikucret:"+uyelikUcreti);
    System.out.println("uyeindirim:"+uyeIndirimMiktari);
    System.out.println("sivilGelirMiktari:"+sivilGelirMiktari);
    
    
    String sql_sivil = "INSERT INTO T_SIVIL(sivilGelirMiktar,uyeID) VALUES(?,?);";

    
    try{
        //T_UYELERE EKLEME ISLEMI
        PreparedStatement pst = bg.prepareStatement(sql_uye);
        pst.setString(1, uyeAd);
        pst.setString(2, uyeSoyad);
        pst.setString(3, uyeTCNO);
        
        if(uyeCinsiyet == 0)
            pst.setString(4, "K");
        else if(uyeCinsiyet == 1)
            pst.setString(4, "E");
        
        pst.setDouble(5, uyelikUcreti);
        pst.setDouble(6, uyeIndirimMiktari);
        
        int affected = pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("OGRENCI UYE BILGILERI UYE TABLOSUNA EKLENEMEDI !");
        else 
            System.out.println("OGRENCININ UYE BILGILERI UYE TABLOSUNA EKLENDI !");
     
        
        //T_UYELER tablosuna zaten eklendi tc uzerinden sorgu cevirip uyeID cektik
        int uyeID = SQL_Q_getUyeID_pTC(uyeTCNO);
        
        //                                                              String sql_sivil = "INSERT INTO T_SIVIL(sivilGelirMiktari,uyeID) VALUES(?,?);";
        PreparedStatement sivil_pst = bg.prepareStatement(sql_sivil);
        sivil_pst.setDouble(1, sivilGelirMiktari);
        
        
        sivil_pst.setInt(2, uyeID);
        affected = 0;
        
        affected = sivil_pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("SIVIL TABLOSUNA VERI EKLEMEDE PROBLEM YASANDI !");
        else 
            System.out.println("SIVIL TABLOSUNA VERI EKLEME BASARILIDIR !");
        
        
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

public void SQL_Q_sivilCikart(int uyeID){
    Connection bg = database.getBaglanti();
    String sql_uyeler = "DELETE FROM T_UYELER WHERE uyeID = ?;";
    String sql_sivil = "DELETE FROM T_SIVIL WHERE uyeID = ?;";
    
    
    try{
        //T_SIVIL TABLOSUNDAN ILK SILME
        PreparedStatement tsivil_pst = bg.prepareStatement(sql_sivil);
        tsivil_pst.setInt(1, uyeID);
        int affected = tsivil_pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("T_SIVIL TABLOSUNDAN KALDIRMA BASARISIZ !");
        else 
            System.out.println("T_SIVIl TABLOSUNDAN KALDIRMA BASARILIDIR !");
        
        //T_UYELER TABLOSUNDAN KALDIRMA
        PreparedStatement tuye_pst = bg.prepareStatement(sql_uyeler);
        tuye_pst.setInt(1, uyeID);
        affected = 0;
        affected = tuye_pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("T_UYELER TABLOSUNDAN KALDIRMA BASARISIZDIR !");
        else 
            System.out.println("T_UYELER TABLOSUNDAN KALDIRMA BASARILDIR !");
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
}

public void SQL_Q_ozelSektorEkle(String uyeAd , String uyeSoyad , String uyeTCNO ,int uyeCinsiyet ,double uyelikUcreti,double uyeIndirimMiktari,boolean kurumAntlasmasi){
    Connection bg = database.getBaglanti();
    String sql_uye = "INSERT INTO T_UYELER(uyeAd,uyeSoyad,uyeTCNO,uyeCinsiyet,uyelikUcreti,uyeIndirimMiktari,uyeTuru) "+
                     "VALUES(?,?,?,?,?,?,'OZELSEKTOR');";
    
    
    System.out.println("AD:"+uyeAd);
    System.out.println("soyad:"+uyeSoyad);
    System.out.println("tcno:"+uyeTCNO);
    System.out.println("cinsiyet:"+uyeCinsiyet);
    System.out.println("uyelikucret:"+uyelikUcreti);
    System.out.println("uyeindirim:"+uyeIndirimMiktari);
    System.out.println("kurumAntlasmasi:"+kurumAntlasmasi);
    
    
    String sql_ozelsektor = "INSERT INTO T_OZELSEKTOR(ozelSektorKurumAntlasma,uyeID) VALUES(?,?);";

    
    try{
        //T_UYELERE EKLEME ISLEMI
        PreparedStatement pst = bg.prepareStatement(sql_uye);
        pst.setString(1, uyeAd);
        pst.setString(2, uyeSoyad);
        pst.setString(3, uyeTCNO);
        
        if(uyeCinsiyet == 0)
            pst.setString(4, "K");
        else if(uyeCinsiyet == 1)
            pst.setString(4, "E");
        
        pst.setDouble(5, uyelikUcreti);
        pst.setDouble(6, uyeIndirimMiktari);
        
        int affected = pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("OGRENCI UYE BILGILERI UYE TABLOSUNA EKLENEMEDI !");
        else 
            System.out.println("OGRENCININ UYE BILGILERI UYE TABLOSUNA EKLENDI !");
     
        
        //T_UYELER tablosuna zaten eklendi tc uzerinden sorgu cevirip uyeID cektik
        int uyeID = SQL_Q_getUyeID_pTC(uyeTCNO);
        
        //                                                              String sql_sivil = "INSERT INTO T_SIVIL(sivilGelirMiktari,uyeID) VALUES(?,?);";
        PreparedStatement os_pst = bg.prepareStatement(sql_ozelsektor);
        os_pst.setBoolean(1, kurumAntlasmasi);
        
        
        os_pst.setInt(2, uyeID);
        affected = 0;
        
        affected = os_pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("OZEL SEKTOR TABLOSUNA VERI EKLEMEDE PROBLEM YASANDI !");
        else 
            System.out.println("OZEL SEKTOR TABLOSUNA VERI EKLEME BASARILIDIR !");
        
        
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

public void SQL_Q_ozelSektorCikart(int uyeID){
    Connection bg = database.getBaglanti();
    String sql_uyeler = "DELETE FROM T_UYELER WHERE uyeID = ?;";
    String sql_ozelsektor = "DELETE FROM T_OZELSEKTOR WHERE uyeID = ?;";
    
    
    try{
        //T_SIVIL TABLOSUNDAN ILK SILME
        PreparedStatement tozelsektor_pst = bg.prepareStatement(sql_ozelsektor);
        tozelsektor_pst.setInt(1, uyeID);
        int affected = tozelsektor_pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("T_OZELSEKTOR TABLOSUNDAN KALDIRMA BASARISIZ !");
        else 
            System.out.println("T_OZELSEKTOR TABLOSUNDAN KALDIRMA BASARILIDIR !");
        
        //T_UYELER TABLOSUNDAN KALDIRMA
        PreparedStatement tuye_pst = bg.prepareStatement(sql_uyeler);
        tuye_pst.setInt(1, uyeID);
        affected = 0;
        affected = tuye_pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("T_UYELER TABLOSUNDAN KALDIRMA BASARISIZDIR !");
        else 
            System.out.println("T_UYELER TABLOSUNDAN KALDIRMA BASARILDIR !");
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
}









    
//////////////////////////////////////////////////////////////////////T_PERSONEL////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////T_PERSONEL////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////T_PERSONEL////////////////////////////////////////////////////////
public void SQL_Q_personelListesi(DefaultTableModel tableModel){
        Connection bg = database.getBaglanti();
        
        //aranan bulunmasa bile sol tablo T_PERSONEL her turlu gosterilecek
         String sql = "SELECT TP.personelID, TP.personelAd, TP.personelSoyad, TP.personelMaas, TP.personelTuru, " +
                      "TPY.yoneticiBirim, TPH.hizmetliGorev " +
                      "FROM T_PERSONEL TP " + 
                      "LEFT JOIN T_YONETICI TPY ON TPY.personelID = TP.personelID " +
                      "LEFT JOIN T_HIZMETLI TPH ON TPH.personelID = TP.personelID;";
        
        try{
            PreparedStatement pst = bg.prepareStatement(sql);
            var res = pst.executeQuery();
            String personelEkBilgi = null;
            
            while(res.next()){ //KOLON CEKME
                
                int personelID = res.getInt("personelID");
                String personelAd = res.getString("personelAd");
                String personelSoyad = res.getString("personelSoyad");
                double personelMaas = res.getDouble("personelMaas");
                String personelTuru = res.getString("personelTuru");
                
                if(personelTuru.equals("HIZMETLI"))
                personelEkBilgi = res.getString("hizmetliGorev");
                
                else if (personelTuru.equals("YONETICI"))
                personelEkBilgi = res.getString("yoneticiBirim");
                        
                System.out.println(personelID+" "+personelAd+" "+personelSoyad+" "+personelMaas+" "+personelTuru + " "+ personelEkBilgi);
                tableModel.addRow(new Object[]{personelID, personelAd, personelSoyad, personelMaas, personelTuru , personelEkBilgi});
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
     
     
public void SQL_Q_personelEkle(String personelAd, String personelSoyad, Double personelMaas, String personelTuru, String ekBilgi){
    Connection bg = database.getBaglanti();
    String sqlPersonel = "INSERT INTO T_PERSONEL(personelAd, personelSoyad, personelMaas, personelTuru) VALUES (?, ?, ?, ?)";
    String sqlHizmetli = "INSERT INTO T_HIZMETLI(hizmetliGorev, personelID) VALUES (?, ?)";
    String sqlYonetici = "INSERT INTO T_YONETICI(yoneticiBirim, personelID) VALUES (?, ?)";

    try {
        PreparedStatement pstPersonel = bg.prepareStatement(sqlPersonel, Statement.RETURN_GENERATED_KEYS);
        pstPersonel.setString(1, personelAd);
        pstPersonel.setString(2, personelSoyad);
        pstPersonel.setDouble(3, personelMaas);
        pstPersonel.setString(4, personelTuru);

        int rowsAffected = pstPersonel.executeUpdate();

        if (rowsAffected > 0) {
     
            ResultSet generatedKeys = pstPersonel.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personelID = generatedKeys.getInt(1);

                if (personelTuru.equals("HIZMETLI")){
                    PreparedStatement pstHizmetli = bg.prepareStatement(sqlHizmetli);
                    pstHizmetli.setString(1, ekBilgi);// T_HIZMETLI GOREV
                    pstHizmetli.setInt(2, personelID);
                    pstHizmetli.executeUpdate();
                } 
                else if (personelTuru.equals("YONETICI")){
                    PreparedStatement pstYonetici = bg.prepareStatement(sqlYonetici);
                    pstYonetici.setString(1, ekBilgi); //T_YONETICI BIRIM
                    pstYonetici.setInt(2, personelID);
                    pstYonetici.executeUpdate();
                }

                System.out.println("PERSONEL VE ILGILI TABLOLAR BASARIYLA GUNCELLENDI !");
            }
        } else {
            System.out.println("PERSONEL EKLENEMEDİ!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

     
public void SQL_Q_personelCikart(int personelID){
    Connection bg = database.getBaglanti();

    //BAGIMLI TABLOLARDANDA SILME
    String sqlSilYonetici = "DELETE FROM T_YONETICI WHERE personelID = ?";
    String sqlSilHizmetli = "DELETE FROM T_HIZMETLI WHERE personelID = ?";
    String sqlSilPersonel = "DELETE FROM T_PERSONEL WHERE personelID = ?";
    
    try {
        //YONETICIDEN SILME
        PreparedStatement pstYonetici = bg.prepareStatement(sqlSilYonetici);
        pstYonetici.setInt(1, personelID);
        pstYonetici.executeUpdate();

        //HIZMETLIDEN SILME
        PreparedStatement pstHizmetli = bg.prepareStatement(sqlSilHizmetli);
        pstHizmetli.setInt(1, personelID);
        pstHizmetli.executeUpdate();

        //PERSONELDEN SILME
        PreparedStatement pstPersonel = bg.prepareStatement(sqlSilPersonel);
        pstPersonel.setInt(1, personelID);
        int rowsAffected = pstPersonel.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("PERSONEL BASARIYLA CIKARTILDI!");
        } else {
            System.out.println("PERSONEL CIKARTILAMADI!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

public void SQL_Q_personelGuncelle(int personelID, String personelAd, String personelSoyad, Double personelMaas, String personelTuru, String ekBilgi) {
    Connection bg = database.getBaglanti();
    StringBuilder sqlBuilder = new StringBuilder("UPDATE T_PERSONEL SET ");
    boolean isFirst = true;

    // T_PERSONEL tablosundaki temel alanların güncellenmesi
    if (personelAd != null) {
        sqlBuilder.append("personelAd = ?");
        isFirst = false;
    }
    if (personelSoyad != null) {
        if (!isFirst) sqlBuilder.append(", ");
        sqlBuilder.append("personelSoyad = ?");
        isFirst = false;
    }
    if (personelMaas != null) {
        if (!isFirst) sqlBuilder.append(", ");
        sqlBuilder.append("personelMaas = ?");
        isFirst = false;
    }
    if (personelTuru != null) {
        if (!isFirst) sqlBuilder.append(", ");
        sqlBuilder.append("personelTuru = ?");
    }
    sqlBuilder.append(" WHERE personelID = ?");

    String sql = sqlBuilder.toString();

    try (PreparedStatement pst = bg.prepareStatement(sql)) {
        int paramIndex = 1;

        if (personelAd != null) pst.setString(paramIndex++, personelAd);
        if (personelSoyad != null) pst.setString(paramIndex++, personelSoyad);
        if (personelMaas != null) pst.setDouble(paramIndex++, personelMaas);
        if (personelTuru != null) pst.setString(paramIndex++, personelTuru);
        pst.setInt(paramIndex, personelID);

        int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("PERSONEL BASARIYLA GÜNCELLENDI !");
        } else {
            System.out.println("GÜNCELLEME BAŞARISIZ");
        }

        // T_HIZMETLI veya T_YONETICI tablosunu güncelleme
        if (personelTuru != null && ekBilgi != null && !ekBilgi.trim().isEmpty()) {
            String sqlEk = null;

            if (personelTuru.equals("HIZMETLI")) {
                sqlEk = "UPDATE T_HIZMETLI SET hizmetliGorev = ? WHERE personelID = ?";
            } else if (personelTuru.equals("YONETICI")) {
                sqlEk = "UPDATE T_YONETICI SET yoneticiBirim = ? WHERE personelID = ?";
            }

            if (sqlEk != null) {
                try (PreparedStatement pstEk = bg.prepareStatement(sqlEk)) {
                    pstEk.setString(1, ekBilgi);
                    pstEk.setInt(2, personelID);

                    int ekRowsAffected = pstEk.executeUpdate();
                    if (ekRowsAffected > 0) {
                        System.out.println("EK BILGI BASARIYLA GÜNCELLENDI !");
                    } else {
                        System.out.println("EK BILGI GÜNCELLEME BAŞARISIZ !");
                    }
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


//////////////////////////////////////////////////////////////////////T_UYELER////////////////////////////////////////////////////////    
//////////////////////////////////////////////////////////////////////T_UYELER////////////////////////////////////////////////////////    
//////////////////////////////////////////////////////////////////////T_UYELER////////////////////////////////////////////////////////    
/*
T_UYELER --> T_SIVIL | T_OGRENCI | T_OZELSEKTOR
                            |
                            +
                            T_LISANS
                            T_LISANSUSTU
                            T_ORTAOGRETIM
   */

public void SQL_Q_uyeList(DefaultTableModel model){
    Connection bg = database.getBaglanti();
    String sql = "SELECT TU.uyeID,TU.uyeAd,TU.uyeSoyad,TU.uyeTCNO,TU.uyeCinsiyet,TU.uyelikUcreti,TU.uyeKayitTarih,TU.uyeIndirimMiktari,TU.uyeTuru,"+
                 "TOG.ogrenciID,TOG.ogrenciOkulAd,TOG.ogrenciTuru,TOL.lisansBolum,TOL.lisansAGNO,TOGR.ortaogretimSinif,TOGR.ortaogretimOrtalama,TOGR.ortaOgretimTuru,"+
                 "TOLU.lisansBolum,TOLU.lisansUstuAGNO,TS.sivilGelirMiktar,TOS.ozelSektorKurumAntlasma "+
                 "FROM T_UYELER TU "+
                 "LEFT JOIN T_SIVIL TS ON TU.uyeID = TS.uyeID "+
                 "LEFT JOIN T_OZELSEKTOR TOS ON TU.uyeID = TOS.uyeID "+
                 "LEFT JOIN T_OGRENCI TOG ON TU.uyeID = TOG.uyeID "+
                 "LEFT JOIN T_LISANS TOL ON TOG.ogrenciID = TOL.ogrenciID "+
                 "LEFT JOIN T_LISANSUSTU TOLU ON TOG.ogrenciID = TOLU.ogrenciID "+
                 "LEFT JOIN T_ORTAOGRETIM TOGR ON TOG.ogrenciID = TOGR.ogrenciID;";
            
    String ekBilgi = null;
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            int uyeID = rst.getInt("uyeID");
            String uyeAd = rst.getString("uyeAd");
            String uyeSoyad = rst.getString("uyeSoyad");
            String uyeTCNO = rst.getString("uyeTCNO");
            String uyeCinsiyet = rst.getString("uyeCinsiyet");
            double uyelikUcreti = rst.getDouble("uyelikUcreti");
            String uyeKayitTarih = rst.getString("uyeKayitTarih");
            double uyeIndirimMiktari = rst.getDouble("uyeIndirimMiktari");
            String uyeTuru = rst.getString("uyeTuru");
            
            //T_OGRENCI
            String ogrenciTuru = rst.getString("ogrenciTuru");
            String ogrenciOkulAd = rst.getString("ogrenciOkulAd");
            
                //T_LISANS
            String lisansBolumTOL = rst.getString("TOL.lisansBolum");
            double lisansAGNO = rst.getDouble("lisansAGNO");
                //T_LISANSUSTU
            String lisansBolumTOLU = rst.getString("TOLU.lisansBolum");
            double lisansUstuAGNO = rst.getDouble("lisansUstuAGNO");
                //T_ORTAOGRETIM
            int ortaogretimSinif = rst.getInt("ortaogretimSinif");
            int ortaogretimOrtalama = rst.getInt("ortaogretimOrtalama");
            String ortaOgretimTuru = rst.getString("ortaOgretimTuru");
            
            //T_SIVIL
            double sivilGelirMiktar = rst.getDouble("sivilGelirMiktar");
            
            //T_OZELSEKTOR
            boolean ozelSektorKurumAntlasma = rst.getBoolean("ozelSektorKurumAntlasma");            
          
            
            if(uyeTuru.equals("OGRENCI")){
                if(ogrenciTuru.equals("LISANS")){
                    ekBilgi = "|"+ogrenciTuru + "|" + lisansBolumTOL + "|" + "AGNO:"+lisansAGNO+"|"+ogrenciOkulAd+"|";
                }
                
                else if(ogrenciTuru.equals("LISANSUSTU")){
                    ekBilgi = "|"+ogrenciTuru + "|" + lisansBolumTOLU + "|" + "Lisansüstü AGNO:"+lisansUstuAGNO+"|"+ogrenciOkulAd+"|"; 
                }
                
                else if(ogrenciTuru.equals("ORTAOGRETIM")){
                    ekBilgi = "|"+ogrenciTuru + "|" + ortaOgretimTuru + "|"+"Sınıf:"+ortaogretimSinif+"|"+"Ortalama:"+ortaogretimOrtalama+"|"+ogrenciOkulAd+"|"; 
                }
            }
            
            else if(uyeTuru.equals("SIVIL")){
                ekBilgi = "|"+"Sivil Gelir:"+sivilGelirMiktar+"|";
            }
            
            else if(uyeTuru.equals("OZELSEKTOR")){
                ekBilgi = "|"+"Özel Sektör Antlaşması:"+ozelSektorKurumAntlasma+"|";
            }
            
            model.addRow(new Object[]{uyeID,uyeAd+" "+uyeSoyad,uyeTCNO,uyeCinsiyet,uyelikUcreti,uyeKayitTarih,uyeIndirimMiktari,uyeTuru,ekBilgi});
            
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    
}











    

//////////////////////////////////////////////////////////////////////T_KITAP////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////T_KITAP////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////T_KITAP////////////////////////////////////////////////////////    
public void SQL_Q_kitapListesi(DefaultTableModel tableModel){ 
    //OVERLOADLANMIS VERSIYONLARI KITAP YONETIMDEKI KITAP FILTRELEME SEKMESI ICIN SADECE SORGUDA DEGISIKLIK YAPILIR
    Connection bg = database.getBaglanti();

    
    String sql = "SELECT " +
             "TK.kitapID, " +
             "TK.kitapAd, " +
             "TK.kitapStok, " +
             "TK.kitapISBN, " +
             "TKategori.kategoriAd, " +
             "TK.kitapSayfaSayisi, " +
             "TK.kitapFiyat, " +
             "TYayin.yayineviAd, " +
             "TYazar.yazarAd, " +
             "TYazar.yazarSoyad " +
             "FROM " +
             "T_KITAP TK " +
             "LEFT JOIN " +
             "    T_KATEGORI TKategori ON TK.kategoriID = TKategori.kategoriID " +
             "LEFT JOIN " +
             "    T_YAYINEVI TYayin ON TK.yayineviID = TYayin.yayineviID " +
             "LEFT JOIN " +
             "    T_KITAP_YAZAR_JT TKYJT ON TK.kitapID = TKYJT.kitapID " +
             "LEFT JOIN " +
             "    T_YAZAR TYazar ON TKYJT.yazarID = TYazar.yazarID;";


   
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        var res = pst.executeQuery();

        while (res.next()) {
            int kitapID = res.getInt("kitapID");
            String kitapAd = res.getString("kitapAd");
            int kitapStok = res.getInt("kitapStok");
            String kitapISBN = res.getString("kitapISBN");
            String kategoriAd = res.getString("kategoriAd");
            int kitapSayfaSayisi = res.getInt("kitapSayfaSayisi");
            double kitapFiyat = res.getDouble("kitapFiyat");
            String yayinEviAd = res.getString("yayineviAd");
            String yazarAd = res.getString("yazarAd");
            String yazarSoyad = res.getString("yazarSoyad");
            
            System.out.println(kitapID+" "+kitapAd+" "+kitapStok+" "+kitapISBN+" "+kategoriAd+" "+kitapSayfaSayisi+" "+kitapFiyat+" "+yayinEviAd+" "+yazarAd+" "+yazarSoyad);
            tableModel.addRow(new Object[]{kitapID, kitapAd,kitapStok, kitapISBN, kategoriAd, kitapSayfaSayisi, kitapFiyat, yayinEviAd,yazarAd,yazarSoyad});
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

//KITAP FILTRELEME ICIN 3 FARKLI SEKILDE SQL SORGUSU CALISTIRIP TABLOYA DONDURMESI BEKLENEN FONKSIYON
//String kolonIsimleri[] = { "+", "+","+", "+", "Kategori","Sayfa Sayısı","Fiyat","Yayınevi","Yazar Adı","Yazar Soyadı"};
public void SQL_Q_kitapListesi(DefaultTableModel tableModel,int kategoriID,int yayineviID,boolean kategoriID_LOCK,boolean yayineviID_LOCK){
    String sql = "TANIMLANMADI";
    int secilen = 0;
    
    System.out.println("KATEGORI_ID_FILTRELEME : "+ kategoriID + " /// YAYINEVI_ID FILTRELEME : "+yayineviID);
    
    //ikiside kitliyse tumunu cek
    if(kategoriID_LOCK == false && yayineviID_LOCK == false){
        SQL_Q_kitapListesi(tableModel);
        secilen = 0;
        return;
    }
    
    //KATEGORI SECIMI - 1
    else if(kategoriID_LOCK == true && yayineviID_LOCK == false){
        sql = "SELECT TK.kitapID , TK.kitapAd , TK.kitapStok , TK.kitapISBN ,"+
               "TKategori.kategoriAd ,TK.kitapSayfaSayisi , TK.kitapFiyat , TYayin.yayineviAd , TYazar.yazarAd , TYazar.yazarSoyad "+
               "FROM T_KITAP TK "+
                "LEFT JOIN T_KATEGORI TKategori ON TKategori.kategoriID = TK.kategoriID "+
                "LEFT JOIN T_YAYINEVI TYayin ON TYayin.yayineviID = TK.yayineviID "+
                "LEFT JOIN T_KITAP_YAZAR_JT KYTJT ON KYTJT.kitapID = TK.kitapID "+
                "LEFT JOIN T_YAZAR TYazar ON TYazar.yazarID = KYTJT.yazarID "+
                "WHERE TK.kategoriID = ?;";
    
        secilen = 1;
    }
    
    
    //YAYINEVI SECIMI - 2
    else if(kategoriID_LOCK == false && yayineviID_LOCK == true){
        sql = "SELECT TK.kitapID , TK.kitapAd , TK.kitapStok , TK.kitapISBN ,"+
               "TKategori.kategoriAd ,TK.kitapSayfaSayisi , TK.kitapFiyat , TYayin.yayineviAd , TYazar.yazarAd , TYazar.yazarSoyad "+
               "FROM T_KITAP TK "+
                "LEFT JOIN T_KATEGORI TKategori ON TKategori.kategoriID = TK.kategoriID "+
                "LEFT JOIN T_YAYINEVI TYayin ON TYayin.yayineviID = TK.yayineviID "+
                "LEFT JOIN T_KITAP_YAZAR_JT KYTJT ON KYTJT.kitapID = TK.kitapID "+
                "LEFT JOIN T_YAZAR TYazar ON TYazar.yazarID = KYTJT.yazarID "+
                "WHERE TK.yayineviID = ?;";
        secilen = 2;
    }
     
    
    
    //KATEGORI VE YAYINEVINE GORE TUMU
    else if(kategoriID_LOCK == true && yayineviID_LOCK == true){
        sql = "SELECT TK.kitapID , TK.kitapAd , TK.kitapStok , TK.kitapISBN ,"+
               "TKategori.kategoriAd ,TK.kitapSayfaSayisi , TK.kitapFiyat , TYayin.yayineviAd , TYazar.yazarAd , TYazar.yazarSoyad "+
               "FROM T_KITAP TK "+
                "LEFT JOIN T_KATEGORI TKategori ON TKategori.kategoriID = TK.kategoriID "+
                "LEFT JOIN T_YAYINEVI TYayin ON TYayin.yayineviID = TK.yayineviID "+
                "LEFT JOIN T_KITAP_YAZAR_JT KYTJT ON KYTJT.kitapID = TK.kitapID "+
                "LEFT JOIN T_YAZAR TYazar ON TYazar.yazarID = KYTJT.yazarID "+
                "WHERE TK.kategoriID = ?  AND TK.yayineviID = ?;";
        secilen = 3;
    }
    Connection bg = database.getBaglanti();
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        if(secilen == 1)
            pst.setInt(1, kategoriID);
        else if(secilen == 2)
            pst.setInt(1, yayineviID);
        else if(secilen == 3){
            pst.setInt(1, kategoriID);
            pst.setInt(2, yayineviID);
        }
            
        var rs = pst.executeQuery();
        
        while(rs.next()){ //verileri cekme sorgudan donen sonuctan
            int kitapID = rs.getInt("kitapID");
            String kitapAd = rs.getString("kitapAd");
            int kitapStok = rs.getInt("kitapStok");
            String kitapISBN = rs.getString("kitapISBN");
            String kategoriAd = rs.getString("kategoriAd");
            int kitapSayfaSayisi = rs.getInt("kitapSayfaSayisi");
            double kitapFiyat = rs.getDouble("kitapFiyat");
            String yayineviAd = rs.getString("yayineviAd");
            String yazarAd = rs.getString("yazarAd");
            String yazarSoyad = rs.getString("yazarSoyad");
            
            tableModel.addRow(new Object[]{kitapID,kitapAd,kitapStok,kitapISBN,kategoriAd,kitapSayfaSayisi,kitapFiyat,yayineviAd,yazarAd+" "+yazarSoyad});
            
        }
        
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }   
}

public void DBSP_kitapFiyatlariArttirYuzdeKategoriye(double artimMiktari , int kategoriID){
    Connection bg = database.getBaglanti();
    String sql = "CALL DBSP_kitapFiyatlariArttirYuzdeKategoriye(?,?);";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setDouble(1, artimMiktari);
        pst.setInt(2, kategoriID);
        
        int affected = pst.executeUpdate();
        
        if(affected <= 0){
            System.out.println("FIYATLAR ARTTIRILAMADI !");
        }
        else 
            System.out.println("FIYATLAR ARTTIRILDI !");
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }   
}

public void DBSP_kitapFiyatlariAzaltYuzdeKategoriye(double artimMiktari , int kategoriID){
    Connection bg = database.getBaglanti();
    String sql = "CALL DBSP_kitapFiyatlariAZALTYuzdeKategoriye(?,?);";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setDouble(1, artimMiktari);
        pst.setInt(2, kategoriID);
        
        int affected = pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("FIYAT AZALTIM ISLEMINDE HATA MEYDANA GELDI");
        else 
            System.out.println("FIYAT AZALTIM ISLEMI BASARILI OLDU !");
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }   
}

public void DBSP_tumKitapFiyatlariArttirma(double artimMiktari){
    Connection bg = database.getBaglanti();
    String sql = "CALL DBSP_tumKitapFiyatlariArttirma(?);";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setDouble(1, artimMiktari);
        int affected = pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("TUM KITAP FIYATLARI ARTTIRMA BASARISIZ !");
        else 
            System.out.println("TUM KITAP FIYATLARI ARTTIRMA BASARILI !");
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }   
}

public void DBSP_tumKitapFiyatlariAzaltma(double artimMiktari){
    Connection bg = database.getBaglanti();
    
    String sql = "CALL DBSP_tumKitapFiyatlariAzaltma(?);";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setDouble(1, artimMiktari);
        
        int affected = pst.executeUpdate();
        if(affected <= 0)
            System.out.println("TUM KITAP FIYATLARINI AZALTMA BASARISIZ !");
        else 
            System.out.println("TUM KITAP FIYATLARINI ARTTIRMA BASARILI !");
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

public void DBSP_kitapFiyatlariArttirYayinevine(double artimMiktari , int yayineviID){
    Connection bg = database.getBaglanti();
    String sql = "CALL DBSP_kitapFiyatlariArttirYayinevine(?,?);";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setDouble(1, artimMiktari);
        pst.setInt(2, yayineviID);
        
        int affected = pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("KITAP FIYATLARI YAYINEVINE GORE ARTTIRILAMADI !");
        else 
            System.out.println("KITAP FIYATLARI YAYINEVINE GORE ARTTIRILDI BASARILI!");
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

public void DBSP_kitapFiyatlariAzaltmaYayinevine(double artimMiktari , int yayineviID){
    Connection bg = database.getBaglanti();
    String sql = "CALL DBSP_kitapFiyatlariAzaltmaYayinevine(?,?);";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setDouble(1, artimMiktari);
        pst.setInt(2, yayineviID);
        
        int affected = pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("KITAP FIYATLARI YAYINEVINE GORE AZALTILAMADI !");
        else 
            System.out.println("KITAP FIYATLARI YAYINEVINE GORE AZALTILDI BASARILI !");
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void SQL_Q_KitapEkle(String kitapAd, int kitapStok, Double kitapFiyat, String kitapISBN, 
                             int kitapSayfaSayisi, int kategoriID, int yayineviID, 
                             String yazarAd, String yazarSoyad){

    Connection bg = database.getBaglanti();
    String sqlKitapEkle = "INSERT INTO T_KITAP(kitapAd, kitapStok, kitapFiyat, kitapISBN, kitapSayfaSayisi, kategoriID, yayineviID) VALUES (?,?,?,?,?,?,?)";
    String sqlYazarEkle = "INSERT INTO T_YAZAR(yazarAd, yazarSoyad, yazarUlke) VALUES (?, ?, 'BILINMEYEN')";
    String sqlKitapYazarJTEkle = "INSERT INTO T_KITAP_YAZAR_JT(kitapID, yazarID) VALUES (?, ?)";
    String sqlYazarYayineviJTEkle = "INSERT INTO T_YAZAR_YAYINEVI_JT(yazarID, yayineviID) VALUES (?, ?)";

    try {
        
        PreparedStatement pst_KE = bg.prepareStatement(sqlKitapEkle, Statement.RETURN_GENERATED_KEYS);
        pst_KE.setString(1, kitapAd);
        pst_KE.setInt(2, kitapStok);
        pst_KE.setDouble(3, kitapFiyat);
        pst_KE.setString(4, kitapISBN);
        pst_KE.setInt(5, kitapSayfaSayisi);
        pst_KE.setInt(6, kategoriID);
        pst_KE.setInt(7, yayineviID);
        int affectedRow = pst_KE.executeUpdate();

        if (affectedRow <= 0) {
            System.out.println("KITAP EKLEMEDE PROBLEM");
            return;
        } else {
            System.out.println("KITAP VERILERI BASARIYLA EKLENDI");
        }

        ResultSet generatedKeys = pst_KE.getGeneratedKeys();
        int kitapID = -1;
        if (generatedKeys.next()){
            kitapID = generatedKeys.getInt(1);
        }

        
        int yazarID = SQL_Q_getYazarID(yazarAd, yazarSoyad);
        if (yazarID == -1){
            
            PreparedStatement pst_YE = bg.prepareStatement(sqlYazarEkle, Statement.RETURN_GENERATED_KEYS);
            pst_YE.setString(1, yazarAd);
            pst_YE.setString(2, yazarSoyad);
            affectedRow = pst_YE.executeUpdate();

            if (affectedRow <= 0) {
                System.out.println("KITABIN YAZAR VERILERI EKLENEMEDI !");
                return;
            } else {
                System.out.println("KITABIN YAZAR VERILERI BASARIYLA EKLENDI");
            }

            generatedKeys = pst_YE.getGeneratedKeys();
            if (generatedKeys.next()) {
                yazarID = generatedKeys.getInt(1);
            }
        } 
        else {
            System.out.println("YAZAR ZATEN MEVCUT !");
        }

        
        if (kitapID != -1 && yazarID != -1) {
            PreparedStatement pst_KY_JT = bg.prepareStatement(sqlKitapYazarJTEkle);
            pst_KY_JT.setInt(1, kitapID);
            pst_KY_JT.setInt(2, yazarID);
            affectedRow = pst_KY_JT.executeUpdate();

            if (affectedRow <= 0) {
                System.out.println("KITAP YAZAR ILISKISI OLUŞTURULAMADI !");
            } else {
                System.out.println("KITAP VE YAZAR ILISKISI BASARIYLA OLUŞTURULDU");
            }
        }
        
        PreparedStatement pst_YY_JT = bg.prepareStatement(sqlYazarYayineviJTEkle);
        pst_YY_JT.setInt(1, yazarID);
        pst_YY_JT.setInt(2, yayineviID);
        affectedRow = pst_YY_JT.executeUpdate();

        if (affectedRow <= 0) {
            System.out.println("YAZAR VE YAYINEVI ILISKISI OLUŞTURULAMADI !");
        } else {
            System.out.println("YAZAR VE YAYINEVI ILISKISI BASARIYLA OLUŞTURULDU");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

public void SQL_Q_kitapYazarTablosuList(DefaultTableModel model){
    Connection bg = database.getBaglanti();
    String sql_list = "SELECT TKYJT.kitapID, TKYJT.yazarID ,TK.kitapAd , TYazar.yazarAd , TYazar.yazarSoyad "+
                      "FROM T_KITAP_YAZAR_JT TKYJT "+
                      "LEFT JOIN T_KITAP TK ON TK.kitapID = TKYJT.kitapID "+
                      "LEFT JOIN T_YAZAR TYazar ON TYazar.yazarID = TKYJT.yazarID;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql_list);
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){ //gelen sorguyu adim adim gezip tabloya ekleme
            int kitapID = rst.getInt("kitapID");
            int yazarID = rst.getInt("yazarID");
            String kitapAd = rst.getString("kitapAd");
            String yazarAd = rst.getString("yazarAd");
            String yazarSoyad = rst.getString("yazarSoyad");
            
            model.addRow(new Object[]{kitapID,yazarID,kitapAd,yazarAd+" "+yazarSoyad});
        }
        
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
}

//sadece kitabin verilerini silecek yayinevleri ve yazarlar duracak , yazar-kitap baglantisi kesilmelidir
public void SQL_Q_kitapCikart(int kitapID){
    Connection bg = database.getBaglanti();
    int yazarID = SQL_Q_getKitabinYazariID(kitapID);
    
    String sql_baglantilariKes = "DELETE FROM T_KITAP_YAZAR_JT "+
                                 "WHERE kitapID = ? AND yazarID="+Integer.toString(yazarID) + ";";
    
    String sql_kitapCikart = "DELETE FROM  T_KITAP "+
                             "WHERE kitapID = ?";
    
    try{
        //1
        PreparedStatement pst = bg.prepareStatement(sql_baglantilariKes);
        pst.setInt(1, kitapID);
        int affectedRow = pst.executeUpdate();
        
        if(affectedRow <= 0)
            System.out.println("KITAPLA YAZAR ARASINDAKI BAGLANTI KESILEMEDI !");
        else 
            System.out.println("KITAPLA YAZAR ARASINDAKI BAGLANTI BASARIYLA KESILDI");
        
        //2
        affectedRow = 0;
        pst = bg.prepareStatement(sql_kitapCikart);
        pst.setInt(1, kitapID);
        affectedRow = pst.executeUpdate();
        
        if(affectedRow <= 0)
            System.out.println("KITAP YAZAR TABLOSUNDAN KALDIRILAMADI PROBLEM !");
        else 
            System.out.println("KITAP YAZAR TABLOSUNDAN BASARIYLA KALDIRILDI");
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
}

public int SQL_Q_getKitabinYazariID(int kitapID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT yazarID FROM T_KITAP_YAZAR_JT WHERE kitapID = ?;";
    int yazarID = -1;
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, kitapID);
        ResultSet rst = pst.executeQuery();
        
        while(rst.next())
        yazarID = rst.getInt("yazarID");
        
        return yazarID;
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return yazarID;
}


public void SQL_kitapYazarTablosu_yazariDegistir(int kitapID,  int yazarID){
    Connection bg = database.getBaglanti();
    String sql = "UPDATE T_KITAP_YAZAR_JT "+
                 "SET yazarID = ? "+
                 "WHERE kitapID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        
        pst.setInt(1, yazarID);
        pst.setInt(2, kitapID);
        
        int affectedRow = pst.executeUpdate();
        
        if(affectedRow <= 0)
            System.out.println("YAZAR GUNCELLENEMEDI !");
        else 
            System.out.println("YAZAR BASARIYLA GUNCELLENDI");
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

public void SQL_kitapYazarTablosu_kitabiDegistir(int kitapID ,int yazarID){
    Connection bg = database.getBaglanti();
    String sql = "UPDATE T_KITAP_YAZAR_JT "+
                 "SET kitapID = ? "+
                 "WHERE yazarID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, kitapID);
        pst.setInt(2, yazarID);
        
        int affectedRow = pst.executeUpdate();
        
        if(affectedRow <= 0)
            System.out.println("KITAP GUNCELLENEMEDI !");
        else 
            System.out.println("KITAP BASARIYLA GUNCELLENDI");
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

/*
    kitapID INT AUTO_INCREMENT PRIMARY KEY,
    kitapAd VARCHAR(40) ,
    kitapStok INT,
    kitapFiyat DECIMAL(6,2),
    kitapISBN CHAR(13),
    kitapSayfaSayisi INT,
*/

//kitap bilgileri guncellenir sonrasinda yazar guncellenir fakat yazar yoksa bu seferde insert edilmelidir
public void SQL_Q_kitapGuncelle(int kitapID, String kitapAd, int kitapStok, String kitapISBN, int kitapSayfaSayisi, 
                                double kitapFiyat,int kategoriID , int yayineviID , String yazarAd , String yazarSoyad){
    Connection bg = database.getBaglanti();

    
    String sqlKitapGuncelle = "UPDATE T_KITAP SET kitapAd = ?, kitapStok = ?, kitapFiyat = ?, kitapISBN = ?, " +
                              "kitapSayfaSayisi = ? , kategoriID = ? , yayineviID = ? WHERE kitapID = ?";
   

    try {
        PreparedStatement pstKitap = bg.prepareStatement(sqlKitapGuncelle);
        pstKitap.setString(1, kitapAd);
        pstKitap.setInt(2, kitapStok);
        pstKitap.setDouble(3, kitapFiyat);
        pstKitap.setString(4, kitapISBN);
        pstKitap.setInt(5, kitapSayfaSayisi);
        pstKitap.setInt(6, kategoriID);
        pstKitap.setInt(7, yayineviID);
        
        pstKitap.setInt(8, kitapID);
        

        int affectedRows = pstKitap.executeUpdate();
        if (affectedRows > 0){
            System.out.println("KITAP VERILERI BASARIYLA GUNCELLENDI");
        } 
        else{
            System.out.println("KITAP VERILERI GUNCELLENEMEDI !");
            return;
        } 
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public void SQL_Q_oduncYonetimListesi(DefaultTableModel model){
    Connection bg = database.getBaglanti();
    String sql = "SELECT TOD.oduncID ,TOD.oduncAlmaTarih ,TOD.iadeEtmeTarih ,TOD.oduncIzinGunSayisi,TOD.cezaKesilenMiktar ,TOD.kitapID , TOD.uyeID , "+
                 "TK.kitapAd , TY.yazarAd , TY.yazarSoyad "+
                 "FROM T_ODUNC TOD "+
                 "LEFT JOIN T_KITAP TK ON TK.kitapID = TOD.kitapID "+
                 "LEFT JOIN T_KITAP_YAZAR_JT TJT ON TJT.kitapID = TK.kitapID "+
                 "LEFT JOIN T_YAZAR TY ON TY.yazarID = TJT.yazarID;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            int oduncID = rst.getInt("oduncID");
            String oduncAlmaTarih = rst.getString("oduncAlmaTarih");
            String iadeEtmeTarih = rst.getString("iadeEtmeTarih");
            int uyeID = rst.getInt("uyeID");
            String kitapAd = rst.getString("kitapAd");
            String yazarAd = rst.getString("yazarAd");
            String yazarSoyad = rst.getString("yazarSoyad");
            int oduncIzinGunSayisi = rst.getInt("oduncIzinGunSayisi");
            double cezaKesilenMiktar = rst.getDouble("cezaKesilenMiktar");
            
            String uyeAd = SQL_Q_getUyeAd(uyeID);
            String uyeSoyad = SQL_Q_getUyeSoyad(uyeID);
            
            String uyeTuru = SQL_Q_getUyeTuru(uyeID);
            model.addRow(new Object[]{oduncID,oduncAlmaTarih,iadeEtmeTarih,oduncIzinGunSayisi,cezaKesilenMiktar,uyeAd+" "+uyeSoyad,uyeTuru,kitapAd,yazarAd+" "+yazarSoyad});
            
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
}

public void SQL_Q_oduncEkle(int kitapID , int uyeID , int oduncIzinGunSayisi){
    Connection bg = database.getBaglanti();
    String sql = "INSERT INTO T_ODUNC(oduncIzinGunSayisi,kitapID,uyeID) "+
                 "VALUES(?,?,?);";
    
    String sql_stok_gunceller = "UPDATE T_KITAP "+
                                 "SET kitapStok = kitapStok-1 "+
                                 "WHERE kitapID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, oduncIzinGunSayisi);
        pst.setInt(2, kitapID);
        pst.setInt(3, uyeID);
        
        int affected = pst.executeUpdate();
        
        int kitapOduncVerildi = 0;
        
        if(affected <= 0)
            System.out.println("T_ODUNC TABLOSUNA EKLEME BASARISIZ OLDU !");
        else{
            System.out.println("T_ODUNC TABLOSUNA EKLEME BASARILIDIR !");
            kitapOduncVerildi = 1;
        }
        
        if(kitapOduncVerildi == 1){
            PreparedStatement stokPst = bg.prepareStatement(sql_stok_gunceller);
            stokPst.setInt(1, kitapID);
            affected = 0;
            affected = stokPst.executeUpdate();
            if(affected <= 0)
                System.out.println("STOK DUSURULEMEDI ODUNC VERILMESINE RAGMEN !");
            else 
                System.out.println("STOK DUSURULMESI BASARILIDIR !");
        }
            
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }  
}

  
public void SQL_Q_oduncGetirdi(int kitapID , int uyeID){
    Connection bg = database.getBaglanti();
    String sql = "UPDATE T_ODUNC SET iadeEtmeTarih = NOW() WHERE uyeID = ? AND kitapID = ?;";
    String sql_stok_arttir = "UPDATE T_KITAP SET kitapStok = kitapStok +1 WHERE kitapID =?;";
    int oduncGeldiMi = 0;
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, uyeID);
        pst.setInt(2, kitapID);
        
        int affected = pst.executeUpdate();
        
        if(affected <= 0)
            System.out.println("KITAP GERI VERILEMEDI !");
        else{
            System.out.println("KITAP ODUNC VERME BASARILIDIR !");
            oduncGeldiMi = 1;
        }
        
        if(oduncGeldiMi == 1){
            PreparedStatement stok_pst = bg.prepareStatement(sql_stok_arttir);
            stok_pst.setInt(1, kitapID);
            affected = 0;
            affected = stok_pst.executeUpdate();
            
            if(affected <= 0)
                System.out.println("KITAP GERI ALINMASINA RAGMEN STOK ARTTIRILAMADI !");
            else 
                System.out.println("STOK ARTTIRMA BASARILIDIR !");
            
        }
             
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    
}







//verilen uyeID icin uyeAd getiren
public String SQL_Q_getUyeAd(int uyeID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT uyeAd FROM T_UYELER WHERE uyeID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, uyeID);
        
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            String uyeAd = rst.getString("uyeAd");
            return uyeAd;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return "BASARISIZ";
}
public String SQL_Q_getUyeSoyad(int uyeID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT uyeSoyad FROM T_UYELER WHERE uyeID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, uyeID);
        
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            String uyeAd = rst.getString("uyeSoyad");
            return uyeAd;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return "BASARISIZ";
}





public String SQL_Q_getUyeTuru(int uyeID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT uyeTuru FROM T_UYELER WHERE uyeID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, uyeID);
        
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            String uyeTuru = rst.getString("uyeTuru");
            return uyeTuru;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return "BASARISIZ";
}

/////////////////////////////////T_KATEGORI///////////////////////////////////
/////////////////////////////////T_KATEGORI///////////////////////////////////
/////////////////////////////////T_KATEGORI///////////////////////////////////

public void SQL_Q_kategoriEkle(String kategoriAd){
    Connection bg = database.getBaglanti();
    String sql = "INSERT INTO T_KATEGORI(kategoriAd) VALUES (?)";
   
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, kategoriAd); //kategori adini sorguya gomduk
        
        pst.execute();
        
        } 
    catch (SQLException ex) {
        ex.printStackTrace();
    }
}

public String[] SQL_Q_mevcutKategoriler(){
    Connection bg = database.getBaglanti();
    String sql = "SELECT kategoriAd FROM T_KATEGORI;";
    ArrayList<String> mevcutKategoriler = new ArrayList<>(); //statik olusturmada boyut belli olmadigindan nullPointerException
  
    int i = 0;
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        
        System.out.println("MEVCUT KATEGORI ADLARI:");
        //sorgu calisti donen sonuclari dongu ile gezecegiz
        while(res.next()){
            String kategoriAdi = res.getString("kategoriAd");
            mevcutKategoriler.add(kategoriAdi);
            System.out.print(kategoriAdi+" ");
        }
        
        } 
    catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    return mevcutKategoriler.toArray(new String[0]);
}

//JTABLE DOLDURUR
public void SQL_Q_mevcutKategorilerList(DefaultTableModel model){ 
    Connection bg = database.getBaglanti();
    String sql = "SELECT kategoriID , kategoriAd FROM T_KATEGORI;";
  
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        
        while(res.next()){
            int kategoriID = res.getInt("kategoriID");
            String kategoriAd = res.getString("kategoriAd");
            
            model.addRow(new Object[]{kategoriID,kategoriAd});
        }
        
        } 
    catch (SQLException ex) {
        ex.printStackTrace();
    }
    
}

//JCOMBOBOX DOLDURMAK ICIN 
public void SQL_Q_ComboboxDoldurCOL_TABLO(JComboBox<String> comboBox,String doldurulacakKolon , String doldurulacakTablo){ 
    Connection bg = database.getBaglanti();
    String sql = "SELECT "+doldurulacakKolon+" FROM "+doldurulacakTablo+";";
//    String sql = "SELECT kategoriAd FROM T_KATEGORI;";

  
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        
        while(res.next()){
            String colFilled = res.getString(doldurulacakKolon);
            comboBox.addItem(colFilled);
        }
        
        } 
    catch (SQLException ex) {
        ex.printStackTrace();
    }
}

public void SQL_Q_ComboboxDoldurCOL_TABLO(JComboBox<String> comboBox,String doldurulacakKolon1 , String dolduralacakKolon2 , String doldurulacakTablo){ 
    Connection bg = database.getBaglanti();
    String sql1 = "SELECT * FROM "+doldurulacakTablo+";";
//    String sql = "SELECT kategoriAd FROM T_KATEGORI;";

  
    try {
        PreparedStatement pst = bg.prepareStatement(sql1);
        ResultSet res = pst.executeQuery();
        
        while(res.next()){
            String colFilled = res.getString(doldurulacakKolon1);
            String colFilled2 = res.getString(dolduralacakKolon2);
            comboBox.addItem(colFilled+" "+colFilled2);
        }
        
        } 
    catch (SQLException ex) {
        ex.printStackTrace();
    }
}


//SECILEN KATEGORININ IDSINI GERI CEVIRIR
public int SQL_Q_ComboboxSecilenID(JComboBox<String> comboBox,String idKolonAd , String kolonAd,String tabloAd){ 
   Connection bg = database.getBaglanti();
    String selectedKolonAd = (String) comboBox.getSelectedItem();
    String sql = "SELECT " + idKolonAd + " FROM " +tabloAd + " WHERE " + kolonAd + "= ?;";
    //String sql = "SELECT kategoriID FROM T_KATEGORI WHERE kategoriAd = ?;";
    
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, selectedKolonAd); 
        ResultSet res = pst.executeQuery();
        
        if (res.next()) {
            return res.getInt(idKolonAd);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    return -1;
}

public int SQL_Q_ComboboxSecilenID(JComboBox<String> comboBox,String idKolonAd , String kolonAd1,String kolonAd2,String tabloAd){ 
   Connection bg = database.getBaglanti();
    String selectedKolonAd = (String) comboBox.getSelectedItem();
    String sql = "SELECT " + idKolonAd + " FROM " + tabloAd + " WHERE CONCAT(" + kolonAd1 + ", ' ', " + kolonAd2 + ") = ?;";
    //String sql = "SELECT kategoriID FROM T_KATEGORI WHERE kategoriAd = ?;";
    
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, selectedKolonAd); 
        ResultSet res = pst.executeQuery();
        
        if (res.next()) {
            return res.getInt(idKolonAd);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    return -1;
}




public int SQL_Q_kategoriCikart(int kategoriID){
    Connection bg = database.getBaglanti();
    
    /*
    SILMEDEN ONCE BAGLANTILI KITAP KONTROL EDILMELIDIR
    */
    
    if(SQL_Q_kategoriIcindeKitapVarMi(kategoriID)){
        //-1 donusunu buton fonksiyonunda yakalayip uyari cikartma
        return -1;
    }
    
    String sql = "DELETE FROM T_KATEGORI WHERE kategoriID = ?;";
    int silienenSatirSay = 0;
    
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, kategoriID); //kategori adini sorguya gomduk
        
        silienenSatirSay = pst.executeUpdate();
        if(silienenSatirSay != 0)
            System.out.println("KATEGORI SILINMISTIR !");
        
        else if (silienenSatirSay == 0){
            System.out.println("KATEGORI SILINEMEDI !");
        }
    }       
    catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    return -2; //baglanti problemi syntax hatasi 
}

public boolean SQL_Q_kategoriIcindeKitapVarMi(int kategoriID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT kategoriID FROM T_KITAP WHERE kategoriID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, kategoriID);
        ResultSet rst = pst.executeQuery();
         
        return rst.next();
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return false;
}


public int SQL_Q_kategoriGuncelle(int kategoriID , String kategoriAd){
    Connection bg = database.getBaglanti();
    String sql = "UPDATE T_KATEGORI SET kategoriAd = ? WHERE kategoriID = ?;";
    int guncellenenSatirSayisi = 0;
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        
        //1 ve 2 sirali yazilmalidir yoksa hata veriyor ? ? her soru isareti icin adim adim 
        pst.setString(1, kategoriAd);
        pst.setInt(2, kategoriID);
        
        
         guncellenenSatirSayisi = pst.executeUpdate();
        
        if(guncellenenSatirSayisi > 0){
            System.out.println("KATEGORI BASARIYLA GUNCELLENMISTIR !");
        }
        else{
            System.out.println("KATEGORI GUNCELLENEMEDI !");
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return guncellenenSatirSayisi;
}



/////////////////////////////////T_YAZAR///////////////////////////////////
/////////////////////////////////T_YAZAR///////////////////////////////////
/////////////////////////////////T_YAZAR///////////////////////////////////

public void SQL_Q_YazarListele(DefaultTableModel model){
    Connection bg = database.getBaglanti();
    String sql = "SELECT * FROM T_YAZAR;";

    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()){ //adim adim gelen sorgu sonuclarini isleme
         int yazarID = rs.getInt("yazarID");
         String yazarAd = rs.getString("yazarAd");
         String yazarSoyad = rs.getString("yazarSoyad");
         String yazarUlke = rs.getString("yazarUlke");
         
         model.addRow(new Object[]{yazarID,yazarAd,yazarSoyad,yazarUlke});
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }

}

public int SQL_Q_YazarEkle(String yazarAd , String yazarSoyad , String yazarUlke){
    Connection bg = database.getBaglanti();
    String sql = "INSERT INTO T_YAZAR(yazarAd,yazarSoyad,yazarUlke) VALUES (?,?,?);";
    System.out.println("yazar ekle SQL_Q : "+yazarAd+" "+yazarSoyad+" "+yazarUlke);
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, yazarAd);
        pst.setString(2, yazarSoyad);
        pst.setString(3, yazarUlke);
        System.out.println("Oluşturulan SQL: " + pst.toString());
        pst.execute();
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
        return -1;
    }
    
    return 1;
}

public int SQL_Q_YazarCikart(int yazarID) {
    Connection bg = database.getBaglanti();
    System.out.println("YAZAR ID SQL_Q : "+yazarID);
    if (SQL_Q_yazarKitabaSahipMi(yazarID)) {
        System.out.println("YAZAR KITABA SAHIPTIR BU YAZARI SILEMEZSINIZ!");
        return -1;
    }

    String sqlDeleteFromYazarYayineviJT = "DELETE FROM T_YAZAR_YAYINEVI_JT WHERE yazarID = ?;";
    String sqlDeleteFromYazar = "DELETE FROM T_YAZAR WHERE yazarID = ?;";

    try {
        PreparedStatement pstYazarYayineviJT = bg.prepareStatement(sqlDeleteFromYazarYayineviJT);
        pstYazarYayineviJT.setInt(1, yazarID);
        int rowAffected = pstYazarYayineviJT.executeUpdate();
        if(rowAffected <= 0)
            System.out.println("YAZAR YAYINEVI TABLOSUNDA YAZAR CIKARTILAMADI BASARISIZ !");
        else 
            System.out.println("YAZAR YAYINEVI TABLOSUNDA YAZAR CIKARTILDI BASARILI !");
        
        
        rowAffected = 0;
        PreparedStatement pstYazar = bg.prepareStatement(sqlDeleteFromYazar);
        pstYazar.setInt(1, yazarID);
        rowAffected = pstYazar.executeUpdate();

        if (rowAffected > 0) {
            System.out.println("SİLME BAŞARILI: YAZAR");
        } else {
            System.out.println("SİLME BAŞARISIZ! : YAZAR");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        
    }

    return 1;
}


public boolean SQL_Q_yazarKitabaSahipMi(int yazarID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT COUNT(*) FROM T_KITAP_YAZAR_JT WHERE yazarID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, yazarID);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int count = rst.getInt(1);
        
            if(count > 0){ //count > 0 ise yazar kitaba sahiptir
                return true;
            }
        }
         
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    
    return false;
}




/////////////GUNCELLEMELERDE BU TEKNIGE GORE DEGISIM
public void SQL_Q_YazarGuncelle(int yazarID, String yazarAd, String yazarSoyad, String yazarUlke) {
    Connection bg = database.getBaglanti();

    //SORGU GIRDIYE GORE OLUSTURULDU
    StringBuilder sqlBuilder = new StringBuilder("UPDATE T_YAZAR SET ");
    boolean isFirst = true;

    if (yazarAd != null && !yazarAd.trim().isEmpty()) {
        sqlBuilder.append("yazarAd = ?");
        isFirst = false;
    }
    if (yazarSoyad != null && !yazarSoyad.trim().isEmpty()) {
        if (!isFirst) 
            sqlBuilder.append(", ");
        sqlBuilder.append("yazarSoyad = ?");
        isFirst = false;
    }
    if (yazarUlke != null && !yazarUlke.trim().isEmpty()) {
        if (!isFirst)
            sqlBuilder.append(", ");
        sqlBuilder.append("yazarUlke = ?");
    }
    sqlBuilder.append(" WHERE yazarID = ?");

    String sql = sqlBuilder.toString();

    try (PreparedStatement pst = bg.prepareStatement(sql)) {
        int paramIndex = 1;

        if (yazarAd != null && !yazarAd.trim().isEmpty()) {
            pst.setString(paramIndex++, yazarAd);
        }
        if (yazarSoyad != null && !yazarSoyad.trim().isEmpty()) {
            pst.setString(paramIndex++, yazarSoyad);
        }
        if (yazarUlke != null && !yazarUlke.trim().isEmpty()) {
            pst.setString(paramIndex++, yazarUlke);
        }
        pst.setInt(paramIndex, yazarID);

        int rowAffected = pst.executeUpdate();

        if (rowAffected <= 0) {
            System.out.println("GUNCELLEME BASARISIZ !");
        } else {
            System.out.println("GUNCELLEME BASARILI !");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

//girilen yazar ad soyad -> id degeri ceviren fonk
public int SQL_Q_getYazarID(String yazarAd, String yazarSoyad) {
    Connection bg = database.getBaglanti();
    String sql = "SELECT yazarID FROM T_YAZAR WHERE yazarAd = ? AND yazarSoyad = ?;";
    int yazarID = -1; //-1 mi donecek kontrol
    
    try (PreparedStatement pst = bg.prepareStatement(sql)) {
        pst.setString(1, yazarAd);
        pst.setString(2, yazarSoyad);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            yazarID = rs.getInt("yazarID");
        } else {
            System.out.println("GIRILEN AD VE SOYADLA ESLESEN YAZAR YOK");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return yazarID;
}




/////////////////////////////////T_YAYINEVI///////////////////////////////////
/////////////////////////////////T_YAYINEVI///////////////////////////////////
/////////////////////////////////T_YAYINEVI///////////////////////////////////

public void SQL_Q_yayineviList(DefaultTableModel model){
    Connection bg = database.getBaglanti();
    String sql = "SELECT * FROM T_YAYINEVI;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        
        while(res.next()){
            int yayineviID = res.getInt("yayineviID");
            String yayineviAd = res.getString("yayineviAd");
            String yayineviUlke = res.getString("yayineviUlke");
            
            //jtable modeline yukleme pencereGUI_Componenetdeki fonksiyondan dogrudan pencereye aktarma
            model.addRow(new Object[]{yayineviID,yayineviAd,yayineviUlke});
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
}

public void SQL_Q_YayineviEkle(String yayineviAd , String yayineviUlke){
    Connection bg = database.getBaglanti();
    String sql = "INSERT INTO T_YAYINEVI(yayineviAd,yayineviUlke) VALUES (?,?);";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, yayineviAd);
        pst.setString(2, yayineviUlke);
        
        //sql_q icine gomduk
        int affectedRow = pst.executeUpdate();
        
        if(affectedRow <= 0)
            System.out.println("YAYINEVI EKLEMEDE PROBLEM !");
        else 
            System.out.println("YAYINEVI BASARIYLA EKLENMISTIR !");
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

public void SQL_Q_YayineviCikart(int yayineviID){
    Connection bg = database.getBaglanti();
    String sql = "DELETE FROM T_YAYINEVI WHERE yayineviID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, yayineviID);
        
        int affectedRows = pst.executeUpdate();
        
        if(affectedRows <= 0)
            System.out.println("SILME SIRASINDA PROBLEM !");
        else 
            System.out.println("SILME BASARIYLA GERCEKLESTI !");
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
}

public void SQL_Q_YayineviGuncelle(int yayineviID , String yayineviAd , String yayineviUlke){
    Connection bg = database.getBaglanti();
    //SQL = UPDATE T_TABLO SET COL1=X , COL2=Y, COL3=Z WHERE ID = Q;
    StringBuilder sqlBuilder = new StringBuilder("UPDATE T_YAYINEVI SET ");
    boolean isFirst = true;
    
    //DINAMIK OLARAK SORGU OLUSTURMADAN ONCE ILK ONCE FONKSIYON PARAMETRELERINI KONTROL EDECEGIZ
    if(yayineviAd != null && !yayineviAd.trim().isEmpty()){
        isFirst = false;
        sqlBuilder.append("yayineviAd = ? ");
    }
    if(yayineviUlke != null && !yayineviUlke.trim().isEmpty()){
        if(!isFirst)
            sqlBuilder.append(" ,");
        sqlBuilder.append("yayineviUlke = ? ");
        isFirst = false;
    }
    //WHERE EKLEME
    sqlBuilder.append(" WHERE yayineviID = ?;");
    
    
    
    //STRING BUILDERI STRINGE CEVIRIYORUZ DINAMIK SORGU OLUSTURULDU
    String sql = sqlBuilder.toString();
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        //SORGUYA GORE IKINCI KONTROLDE GOMULU PARAMETRELERI EKLEME ISLEMI
        int parametreIndex = 1; //sorguya gomme sirasindaki index
        
        if(yayineviAd != null && !yayineviAd.trim().isEmpty())
            pst.setString(parametreIndex++, yayineviAd);
        
        if(yayineviUlke != null && !yayineviUlke.trim().isEmpty())
            pst.setString(parametreIndex++, yayineviUlke);
        
        pst.setInt(parametreIndex, yayineviID);
        
        //SORGUYU EXECUTE ETME
        
        int affectedRows = pst.executeUpdate();
        
        if(affectedRows <= 0)
            System.out.println("YAYINEVI GUNCELLEME BASARISIZ !");
        else 
            System.out.println("YAYINEVI GUNCELLEME BASARILI !");
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    
    
    }











/////////////////////DBF FONKSIYONLARINI CAGIRMA
public int SQL_Q_DBF_getKitapSayisi_pKategoriID(int kategoriID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getKitapSayisi_pKategoriID(?) AS kategoriyeGoreKitapSayisi;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, kategoriID);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int kitapSayisi = rst.getInt("kategoriyeGoreKitapSayisi");
            return kitapSayisi;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return -1;
}

public int SQL_Q_DBF_getKitapSayisi(){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getKitapSayisi() AS kitapSayisi;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int kitapSayisi = rst.getInt("kitapSayisi");
            return kitapSayisi;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    return -1;
}

public int SQL_Q_DBF_getToplamSayfaSayisi(){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getToplamSayfaSayisi() AS sayfaSayisi;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int kitapSayisi = rst.getInt("sayfaSayisi");
            return kitapSayisi;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    return -1;
}


public int SQL_Q_DBF_getToplamSayfaSayisi_pKategoriID(int kategoriID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getToplamSayfaSayisi_pKategoriID(?) AS sayfaSayisi;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, kategoriID);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int kitapSayisi = rst.getInt("sayfaSayisi");
            return kitapSayisi;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    return -1;
}

public int SQL_Q_DBF_getYayineviSayisi(){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getYayineviSayisi() AS yayineviSayisi;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int yayineviSayisi = rst.getInt("yayineviSayisi");
            return yayineviSayisi;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return -1;
}

public int SQL_Q_DBF_getToplamYazarSayisi(){
      Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getToplamYazarSayisi() AS yazarSayisi;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int yazarSayisi = rst.getInt("yazarSayisi");
            return yazarSayisi;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return -1;
}

public int SQL_Q_DBF_getKategoriSayisi(){
     Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getKategoriSayisi() AS kategoriSayisi;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int kategoriSayisi = rst.getInt("kategoriSayisi");
            return kategoriSayisi;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return -1;
}


public int SQL_Q_DBF_getYayineviKitapSayisi(int yayineviID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getYayineviKitapSayisi(?) AS yayineviKitapSayisi;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, yayineviID);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int kitapSayisi = rst.getInt("yayineviKitapSayisi");
            return kitapSayisi;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    
    return -1;
}


public int SQL_Q_DBF_getYayineviKitapSayfaSayisi(int yayineviID){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_getYayineviKitapSayfaSayisi(?) AS yayineviKitapSayfaSayisi;";
    
    try{
        PreparedStatement ps = bg.prepareStatement(sql);
        ps.setInt(1, yayineviID);
        ResultSet rst = ps.executeQuery();
        if(rst.next()){
            int kitapSayfaSayisi = rst.getInt("yayineviKitapSayfaSayisi");
            return kitapSayfaSayisi;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return -1;
} 

public int SQL_Q_DBF_maxKitapSayfa(){
      Connection bg = database.getBaglanti();
    String sql = "SELECT maxKitapSayfa() AS maxSayfa;";
    
    try{
        PreparedStatement ps = bg.prepareStatement(sql);
        ResultSet rst = ps.executeQuery();
        if(rst.next()){
            int maxSayfa = rst.getInt("maxSayfa");
            return maxSayfa;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return -1;
}

public String SQL_Q_DBF_enUzunKitapAdi(){
    Connection bg = database.getBaglanti();
    String sql = "SELECT kitapAd FROM T_KITAP WHERE kitapSayfaSayisi = ?;";
    String enUzunAd = "BULUNAMADI !";
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, SQL_Q_DBF_maxKitapSayfa());
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            enUzunAd = rst.getString("kitapAd");
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return enUzunAd;
}

public int SQL_Q_DBF_minKitapSayfa(){
      Connection bg = database.getBaglanti();
    String sql = "SELECT minKitapSayfa() AS minSayfa;";
    
    try{
        PreparedStatement ps = bg.prepareStatement(sql);
        ResultSet rst = ps.executeQuery();
        if(rst.next()){
            int minSayfa = rst.getInt("minSayfa");
            return minSayfa;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return -1;
}


public String SQL_Q_DBF_enKisaKitapAd(){
    Connection bg = database.getBaglanti();
    String sql = "SELECT enKisaKitapAdi() AS enKisaKitap;";
    String enKisaKitap = "BULUNAMADI !";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            enKisaKitap = rst.getString("enKisaKitap");
            return enKisaKitap;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return enKisaKitap;
}

public double SQL_Q_DBF_ortalamaSayfaSayisi(){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_ortalamaSayfaSayisi() AS ortSayfa;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            double ortalamaSayfaSayisi = rst.getDouble("ortSayfa");
            return ortalamaSayfaSayisi;
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return 0.0;
}

public String SQL_Q_DBF_enPahaliKitapAdi(){
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_enPahaliKitapAd() AS enPahaliAd;";
    String enPahaliAd = "BULUNAMADI !";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
           enPahaliAd = rst.getString("enPahaliAd");
           return enPahaliAd;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
   return enPahaliAd;
}

public double SQL_Q_DBF_enPahaliKitapFiyat(){
     Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_enPahaliKitapFiyat() AS enPahaliFiyat;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
           double enPahaliFiyat = rst.getDouble("enPahaliFiyat");
           return enPahaliFiyat;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return 0.0;
}

public String SQL_Q_DBF_enUcuzKitapAd(){
     Connection bg = database.getBaglanti();
    String sql = "SELECT enUcuzKitapAd() AS enUcuzAd;";
    String enUcuzAd = "BULUNAMADI !";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
           enUcuzAd = rst.getString("enUcuzAd");
           return enUcuzAd;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
   return enUcuzAd;
}

//DBF_enUcuzKitapFiyat
public double SQL_Q_DBF_enUcuzFiyat(){
      Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_enUcuzKitapFiyat() AS enUcuzFiyat;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
           double enUcuzFiyat = rst.getDouble("enUcuzFiyat");
           return enUcuzFiyat;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
    
    return 0.0;
}


public int SQL_Q_DBF_farkliUlkeYazarSayisi(){
    //DBF_farkliUlkedenYazarSayisi
    Connection bg = database.getBaglanti();
    String sql = "SELECT DBF_farkliUlkedenYazarSayisi() AS farkliYazarSay;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        if(rst.next()){
            int farkliUlkeler = rst.getInt("farkliYazarSay");
            return farkliUlkeler;
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }

    return 0;
}
        


public void SQL_Q_KategoriLOG_List(DefaultTableModel model){
    Connection bg = database.getBaglanti();
    String sql = "SELECT tlogID,tlogKategoriEskiAd,tlogKategoriYeniAd,tlogKategoriID,tlogDurumAciklamasi,tlogZaman "+
                 "FROM TLOG_KATEGORI;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            int tlogID = rst.getInt("tlogID");
            String kategoriEskiAd = rst.getString("tlogKategoriEskiAd");
            String kategoriYeniAd = rst.getString("tlogKategoriYeniAd");
            int kategoriEskiID = rst.getInt("tlogKategoriID");
            String durumAciklamasi = rst.getString("tlogDurumAciklamasi");
            String logZaman = rst.getString("tlogZaman");
            
            model.addRow(new Object[]{tlogID,kategoriEskiAd,kategoriYeniAd,kategoriEskiID,durumAciklamasi,logZaman});
        }
        
    }
    catch(SQLException ex){
        ex.printStackTrace();
    } 
}


public void SQL_Q_YazarLOG_List(DefaultTableModel model){
    Connection bg = database.getBaglanti();
    String sql = "SELECT tlogID,tlogYazarEskiAd,tlogYazarEskiSoyad,tlogYazarEskiUlke,tlogYazarYeniAd,tlogYazarYeniSoyad,tlogYazarYeniUlke,tlogDurumAciklamasi,tlogZaman "+
                 "FROM TLOG_YAZAR;";
   
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            int tlogID = rst.getInt("tlogID");
            String tlogYazarEskiAd = rst.getString("tlogYazarEskiAd");
            String tlogYazarEskiSoyad = rst.getString("tlogYazarEskiSoyad");
            String tlogYazarEskiUlke = rst.getString("tlogYazarEskiUlke");
            String tlogYazarYeniAd = rst.getString("tlogYazarYeniAd");
            String tlogYazarYeniSoyad = rst.getString("tlogYazarYeniSoyad");
            String tlogYazarYeniUlke = rst.getString("tlogYazarYeniUlke");
            String tlogDurumAciklamasi = rst.getString("tlogDurumAciklamasi");
            String tlogZaman = rst.getString("tlogZaman");
            
            model.addRow(new Object[]{tlogID,tlogYazarEskiAd+" "+tlogYazarEskiSoyad,tlogYazarEskiUlke,
                tlogYazarYeniAd+" "+tlogYazarYeniSoyad,tlogYazarYeniUlke,tlogDurumAciklamasi,tlogZaman});
            
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }


    }
}//class DatabaseIslemler SON
