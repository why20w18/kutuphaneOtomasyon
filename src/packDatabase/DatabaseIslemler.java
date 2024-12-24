package packDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;


/*
VERITABANINA BAGLANTI BURADA OTOMATIK YAPILIR
*/

public class DatabaseIslemler {
    databaseBaglanti database;
    
    public DatabaseIslemler(databaseBaglanti database){
        this.database = database;
    } 
    
    
//---------------------------------- SORGULAR ----------------------------//
    
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
                 "TK.kitapID, TK.kitapAd, TK.kitapISBN, TKategori.kategoriAd, TK.kitapSayfaSayisi, TK.kitapFiyat, TYayin.yayineviAd , TYazar.yazarAd , TYazar.yazarSoyad " +
                 "FROM T_KITAP TK " +
                 "LEFT JOIN T_KATEGORI TKategori ON TKategori.kategoriID = TK.kategoriID " +    
                 "LEFT JOIN T_YAYINEVI TYayin ON TYayin.yayineviID = TK.yayineviID "+   //yazar ve yayinevi arasinda N:N baglanti var joint table uzerinden bagladik
                 "LEFT JOIN T_YAZAR_YAYINEVI_JT TJT ON TJT.yayineviID = TYayin.yayineviID "+ //joint table uzerinden eslesenleri joinleyecegiz
                 "LEFT JOIN T_YAZAR TYazar ON TJT.yazarID = TYazar.yazarID;";

    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        var res = pst.executeQuery();

        while (res.next()) {
            int kitapID = res.getInt("kitapID");
            String kitapAd = res.getString("kitapAd");
            String kitapISBN = res.getString("kitapISBN");
            String kategoriAd = res.getString("kategoriAd");
            int kitapSayfaSayisi = res.getInt("kitapSayfaSayisi");
            double kitapFiyat = res.getDouble("kitapFiyat");
            String yayinEviAd = res.getString("yayineviAd");
            String yazarAd = res.getString("yazarAd");
            String yazarSoyad = res.getString("yazarSoyad");

            tableModel.addRow(new Object[]{kitapID, kitapAd, kitapISBN, kategoriAd, kitapSayfaSayisi, kitapFiyat, yayinEviAd,yazarAd,yazarSoyad});
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
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
public void SQL_Q_ComboboxMevcutKategorilerDoldur(JComboBox<String> comboBox){ 
    Connection bg = database.getBaglanti();
    String sql = "SELECT kategoriAd FROM T_KATEGORI;";
  
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        
        while(res.next()){
            String kategoriAd = res.getString("kategoriAd");
            comboBox.addItem(kategoriAd);
        }
        
        } 
    catch (SQLException ex) {
        ex.printStackTrace();
    }
}

//SECILEN KATEGORININ IDSINI GERI CEVIRIR
public int SQL_Q_ComboboxSecilenIDKategoriler(JComboBox<String> comboBox){ 
   Connection bg = database.getBaglanti();
    String selectedKategoriAd = (String) comboBox.getSelectedItem();
    String sql = "SELECT kategoriID FROM T_KATEGORI WHERE kategoriAd = ?;";
    
    try {
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setString(1, selectedKategoriAd); 
        ResultSet res = pst.executeQuery();
        
        if (res.next()) {
            return res.getInt("kategoriID");
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

public void SQL_Q_YazarCikart(int yazarID){
    Connection bg = database.getBaglanti();
    String sql = "DELETE FROM T_YAZAR WHERE yazarID = ?;";
    
    try{
        PreparedStatement pst = bg.prepareStatement(sql);
        pst.setInt(1, yazarID); //? yerine func parametre gomme
        
        int rowAffected = pst.executeUpdate();
        
        if(rowAffected <= 0){
            System.out.println("SILME ISLEMI BASARISIZ !");
        }
        else{
            System.out.println("SILME BASARIYLA GERCEKLESTI !");
        }
    }
    catch(SQLException ex){
        ex.printStackTrace();
    }
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
        if (!isFirst) sqlBuilder.append(", ");
        sqlBuilder.append("yazarSoyad = ?");
        isFirst = false;
    }
    if (yazarUlke != null && !yazarUlke.trim().isEmpty()) {
        if (!isFirst) sqlBuilder.append(", ");
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
    
}



    
        
}//class DatabaseIslemler SON
