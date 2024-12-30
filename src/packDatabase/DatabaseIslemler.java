package packDatabase;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.protocol.Resultset;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;


/*
VERITABANINA BAGLANTI BURADA OTOMATIK YAPILIR
*/

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



    

//////////////////////////////////////////////////////////////////////T_KITAP////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////T_KITAP////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////T_KITAP////////////////////////////////////////////////////////    
public void SQL_Q_kitapListesi(DefaultTableModel tableModel) {
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

 /*
1- kitap verileri eklenmelidir
2-sonra yazar ekle SQL_Q fonksiyonu cagrilmali ve yazarUlke = "BILINMEYEN" olarak eklenmelidir
3-yayinevi ve kategoriler mevcut olan kategoriler yada yayinevlerinden secilmesi zorunlu kilindi o yuzden burada direkt ekleyecegiz
*/
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
    String sql = "SELECT TO.oduncID ,TO.oduncAlmaTarih , TO.iadeEtmeTarih , TO.kitapID , TO.uyeID  , TK.kitapAd , TYazar.yazarAd , TYazar.yazarSoyad , TK.kitapSayfaSayisi "+
                 "FROM T_ODUNC TO "+
                 "LEFT JOIN T_KITAP TK ON TK.kitapID = TO.kitapID "+
                 "LEFT JOIN T_KITAP_YAZAR_JT TJT ON TJT.kitapID = TK.kitapID "+
                 "LEFT JOIN T_YAZAR TY ON TY.yazarID = TJT.yazarID;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            int oduncID = rst.getInt("oduncID");
            String oduncAlmaTarih = rst.getString("oduncAlmaTarih");
            String iadeEtmeTarih = rst.getString("iadeEtmeTarih");
            int kitapID = rst.getInt("kitapID");
            int uyeID = rst.getInt("uyeID");
            String kitapAd = rst.getString("kitapAd");
            String yazarAd = rst.getString("yazarAd");
            String yazarSoyad = rst.getString("yazarSoyad");
            int kitapSayfaSayisi = rst.getInt("kitapSayfaSayisi");
            
            String uyeAd = SQL_Q_getUyeAd(uyeID);
            String uyeTuru = SQL_Q_getUyeTuru(uyeID);
            model.addRow(new Object[]{oduncID,oduncAlmaTarih,iadeEtmeTarih,kitapAd,(yazarAd+" "+yazarSoyad),kitapSayfaSayisi,uyeAd,uyeTuru});
            
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

public int SQL_Q_kategoriCikart(String kategoriAd){
    Connection bg = database.getBaglanti();
    String sql = "DELETE FROM T_KATEGORI WHERE kategoriAd = ?;";
    int silienenSatirSay = 0;
    
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, kategoriAd); //kategori adini sorguya gomduk
        
        silienenSatirSay = pst.executeUpdate();
        if(silienenSatirSay != 0)
            System.out.println(kategoriAd+" KATEGORISI SILINMISTIR !");
        
        else if (silienenSatirSay == 0){
            System.out.println(kategoriAd+" KATEGORISI SILINEMEDI !");
        }
    }       
    catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    return silienenSatirSay; 
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

public void SQL_Q_YazarEkle(String yazarAd , String yazarSoyad , String yazarUlke){
    Connection bg = database.getBaglanti();
    String sql = "INSERT INTO T_YAZAR(yazarAd,yazarSoyad,yazarUlke) VALUES (?,?,?);";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, yazarAd);
        pst.setString(2, yazarSoyad);
        pst.setString(3, yazarUlke);
        
        pst.execute();
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
}

public int SQL_Q_YazarCikart(int yazarID) {
    Connection bg = database.getBaglanti();
    if(SQL_Q_yazarKitabaSahipMi(yazarID)){
        System.out.println("YAZAR KITABA SAHIPTIR BU YAZARI SILEMEZSINIZ !");
        return -1;
    }
    
    /*
        1-yazarin kitaplari varsa silmeye izin verme 
        2-ilk once kitaplari kaldirilmasi gerektigini belirt
    */
    String sqlDeleteFromKitapYazarJT = "DELETE FROM T_KITAP_YAZAR_JT WHERE yazarID = ?";
    String sqlDeleteFromYazarYayineviJT = "DELETE FROM T_YAZAR_YAYINEVI_JT WHERE yazarID = ?";
    String sqlDeleteFromYazar = "DELETE FROM T_YAZAR WHERE yazarID = ?";

    
    try {
        PreparedStatement pstKitapYazarJT = bg.prepareStatement(sqlDeleteFromKitapYazarJT);
        pstKitapYazarJT.setInt(1, yazarID);
        pstKitapYazarJT.executeUpdate();

        PreparedStatement pstYazarYayineviJT = bg.prepareStatement(sqlDeleteFromYazarYayineviJT);
        pstYazarYayineviJT.setInt(1, yazarID);
        pstYazarYayineviJT.executeUpdate();

        PreparedStatement pstYazar = bg.prepareStatement(sqlDeleteFromYazar);
        pstYazar.setInt(1, yazarID);
        int rowAffected = pstYazar.executeUpdate();

        if (rowAffected > 0) {
            System.out.println("Yazar başarıyla silindi!");
        }
        else {
            System.out.println("Silme işlemi başarısız! Yazar bulunamadı.");
        }

    } catch (SQLException ex) {
        try {
            bg.rollback();
            System.out.println("Hata oluştu. İşlem geri alındı.");
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
        ex.printStackTrace();
    } finally {
        try {
            bg.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
        
        
        



}//class DatabaseIslemler SON
