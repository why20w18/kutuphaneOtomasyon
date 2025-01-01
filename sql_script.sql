CREATE DATABASE IF NOT EXISTS kutuphaneOtomasyon3;

USE kutuphaneOtomasyon3;

CREATE TABLE T_KATEGORI(
	kategoriID INT AUTO_INCREMENT PRIMARY KEY,
    kategoriAd VARCHAR(40)
);

CREATE TABLE T_YAYINEVI(
	yayineviID INT AUTO_INCREMENT PRIMARY KEY,
    yayineviAd VARCHAR(40),
    yayineviUlke VARCHAR(20)
);

-- YAZAR ILE YAYINEVI N:N BAGLANTILI JOINT TABLE YAPACAGIZ VE
-- T_KITAPTA T_YAYINEVI ILE ILISKILI BU SEBEPLE O TABLO OLUSTURULMADAN BAGLANTIYI KURMAMIZ GEREKIYOR
CREATE TABLE T_YAZAR(
	yazarID INT AUTO_INCREMENT PRIMARY KEY,
    yazarUlke VARCHAR(15),
    yazarAd VARCHAR(20),
    yazarSoyad VARCHAR(20)
);

ALTER TABLE T_YAZAR
MODIFY COLUMN yazarUlke VARCHAR(45);

ALTER TABLE T_YAZAR
MODIFY COLUMN yazarAd VARCHAR(45);

ALTER TABLE T_YAZAR
MODIFY COLUMN yazarSoyad VARCHAR(45);

-- YAZAR VE YAYINEVI PRIMARY KEYINI ALACAGIZ
CREATE TABLE T_YAZAR_YAYINEVI_JT( -- BU TABLO UZERINDEN N:N ILISKIYI YAZAR<-1:N->JT<-N:1->YAYINEVI SEKLINDE IMPLEMENTE ETTIK
	yazarID INT,
    yayineviID INT,
    PRIMARY KEY(yazarID,yayıneviID),

    FOREIGN KEY (yazarID) REFERENCES T_YAZAR(yazarID),
    FOREIGN KEY (yayineviID) REFERENCES T_YAYINEVI(yayineviID)

    -- YAZAR TAM AD ICIN PROSEDUR YAZILACAK
);

CREATE TABLE T_KITAP(
	kitapID INT AUTO_INCREMENT PRIMARY KEY,
    kitapAd VARCHAR(40) ,
    kitapStok INT,
    kitapFiyat DECIMAL(6,2),
    kitapISBN CHAR(13),
    kitapSayfaSayisi INT,

    kategoriID INT,
	yayineviID INT,


    FOREIGN KEY (kategoriID) REFERENCES T_KATEGORI(kategoriID), -- 1 KATEGORI N KITAP BAGLANTISI
    FOREIGN KEY (yayineviID) REFERENCES T_YAYINEVI(yayineviID)  -- 1 YAYINEVI N KITAP BAGLANTISI
);

ALTER TABLE T_KITAP
MODIFY kitapISBN CHAR(20);

ALTER TABLE T_KITAP
MODIFY kitapAd VARCHAR(50);

CREATE TABLE T_KITAP_YAZAR_JT (
    kitapID INT,
    yazarID INT,
    PRIMARY KEY (kitapID, yazarID),
    FOREIGN KEY (kitapID) REFERENCES T_KITAP(kitapID),
    FOREIGN KEY (yazarID) REFERENCES T_YAZAR(yazarID)
);



-- UYELER VE ODUNC TABLOLARI ------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE T_UYELER(
	uyeID INT AUTO_INCREMENT PRIMARY KEY,
    uyeAd VARCHAR(40) NOT NULL,
    uyeSoyad VARCHAR(40) NOT NULL,
    uyeTCNO VARCHAR(11) UNIQUE NOT NULL,
    uyeCinsiyet ENUM('E','K'),
    uyelikUcreti DECIMAL(10,2),
    uyeKayitTarih TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    uyeIndirimMiktari DECIMAL(3,1),
    uyeTuru ENUM('OGRENCI','SIVIL','OZELSEKTOR')
);
SHOW WARNINGS;

USE kutuphaneOtomasyon3;
DROP TABLE T_UYELER;
DROP TABLE T_ODUNC;
DROP TABLE T_OGRENCI;
DROP TABLE T_LISANS;
DROP TABLE T_ORTAOGRETIM;
DROP TABLE T_LISANSUSTU;
DROP TABLE T_OZELSEKTOR;
DROP TABLE T_SIVIL;
SHOW WARNINGS;

CREATE TABLE T_OGRENCI( -- T_UYELER TABLOSUNDAN OVERLAP
	ogrenciID INT AUTO_INCREMENT PRIMARY KEY,
    ogrenciOkulAd VARCHAR(50),
    ogrenciTuru ENUM('LISANS','LISANSUSTU','ORTAOGRETIM'),

    uyeID INT,
    FOREIGN KEY (uyeID) REFERENCES T_UYELER(uyeID) -- 1 T_UYE <-> N T_OGRENCI
);
-- T_OGRENCI TABLOSUNDAN DISJOINT YAPILMISTIR
CREATE TABLE T_LISANS(
	lisansID INT AUTO_INCREMENT PRIMARY KEY,
    lisansBolum VARCHAR(25),
    lisansAGNO DECIMAL(3,2),
    CHECK(lisansAGNO <= 4.0),

    ogrenciID INT UNIQUE,
    FOREIGN KEY (ogrenciID) REFERENCES T_OGRENCI(ogrenciID)
);

-- T_OGRENCI TABLOSUNDAN DISJOINT YAPILMISTIR
CREATE TABLE T_ORTAOGRETIM(
	ortaogretimID INT AUTO_INCREMENT PRIMARY KEY,
	ortaogretimSinif INT,
    ortaogretimOrtalama DECIMAL(4,2),
    ortaOgretimTuru ENUM('ILKOKUL','ORTAOKUL','LISE'),

    ogrenciID INT UNIQUE,
    FOREIGN KEY (ogrenciID) REFERENCES T_OGRENCI(ogrenciID)
);

-- T_OGRENCI TABLOSUNDAN DISJOINT YAPILMISTIR
CREATE TABLE T_LISANSUSTU(
	lisansUstuID INT AUTO_INCREMENT PRIMARY KEY,
	lisansBolum VARCHAR(25),
    lisansUstuAGNO DECIMAL(3,2), -- AGNOYA KISITLMA KOYULMALI 4.00 UZERINE GECEMEMELIDIR
    CHECK (lisansUstuAGNO <= 4.00),

    ogrenciID INT UNIQUE,
	FOREIGN KEY (ogrenciID) REFERENCES T_OGRENCI(ogrenciID)
);

CREATE TABLE T_SIVIL( -- T_UYELER TABLOSUNDAN OVERLAP				-- --------EMEKLI OLARAK DEGISTIRILEBILIRS
	sivilID INT AUTO_INCREMENT PRIMARY KEY,
    sivilGelirMiktar DECIMAL(10,2),

    uyeID INT,
	FOREIGN KEY (uyeID) REFERENCES T_UYELER(uyeID) -- 1 T_UYE <-> N T_SIVIL
);

CREATE TABLE T_OZELSEKTOR( -- T_UYELER TABLOSUNDAN OVERLAP
	ozelSektorID INT AUTO_INCREMENT PRIMARY KEY,
    ozelSektorKurumAntlasma BOOL,
    uyeID INT,
	FOREIGN KEY (uyeID) REFERENCES T_UYELER(uyeID) -- 1 T_UYE <-> N T_OZELSEKTOR
);


-- ZAYIF VARLIK : KITAP ILE ARASINDA VAROLMA BAGIMLILIGI VAR
CREATE TABLE T_ODUNC(
	oduncID INT AUTO_INCREMENT PRIMARY KEY,
    oduncAlmaTarih TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    iadeEtmeTarih DATETIME,
    oduncIzinGunSayisi INT,
    cezaKesilenMiktar DECIMAL(10,2),
    kitapID INT , -- ZAYIF VARLIK OLDUGUNDAN ICINDE BAGIMLI OLDUGUNUN PK'SINI EKLEDIK
    uyeID INT,

    FOREIGN KEY (kitapID) REFERENCES T_KITAP(kitapID),
    FOREIGN KEY (uyeID) REFERENCES T_UYELER(uyeID)
);


-- ---------------------------------------------------------------------------------------------------------------------------------------------

-- PERSONELIN KITAPLA MANTIKSAL BIR BAGLANTISI YOK
CREATE TABLE T_PERSONEL(
	personelID INT AUTO_INCREMENT PRIMARY KEY,
    personelAd VARCHAR(20),
    personelSoyad VARCHAR(20),
    personelMaas DECIMAL(7,2),
    personelTuru ENUM('HIZMETLI','YONETICI')
);

CREATE TABLE T_HIZMETLI(
	hizmetliID INT AUTO_INCREMENT PRIMARY KEY,
    hizmetliGorev VARCHAR(1000),

    personelID INT UNIQUE,
    FOREIGN KEY (personelID) REFERENCES T_PERSONEL(personelID)
);

CREATE TABLE T_YONETICI(
	yoneticiID INT AUTO_INCREMENT PRIMARY KEY,
    yoneticiBirim VARCHAR(50),

    personelID INT UNIQUE,
    FOREIGN KEY (personelID) REFERENCES T_PERSONEL(personelID)
);

-- FONKSIYONLAR: KITAP YONETIM ISTATISTIKLER KISMI ICIN
DELIMITER $$
CREATE FUNCTION DBF_getKitapSayisi_pKategoriID(ktgID INT)
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE ks INT DEFAULT -1;
    SELECT COUNT(*) INTO ks
    FROM T_KITAP
    WHERE kategoriID = ktgID;
    RETURN ks;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION DBF_getKitapSayisi() RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE ks INT DEFAULT -1;
    SELECT COUNT(*) INTO ks
    FROM T_KITAP;
    RETURN ks;
END $$
DELIMITER ;



DELIMITER $$
CREATE FUNCTION DBF_getToplamSayfaSayisi()
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE sayfaSayisi INT DEFAULT -1;
	SELECT SUM(kitapSayfaSayisi) INTO sayfaSayisi
    FROM T_KITAP;
    RETURN sayfaSayisi;
END $$
DELIMITER ;

DROP FUNCTION DBF_getToplamSayfaSayisi;

DELIMITER $$
CREATE FUNCTION DBF_getToplamSayfaSayisi_pKategoriID(ktgID INT)
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE sayfaSayisi INT;
    SELECT SUM(kitapSayfaSayisi) INTO sayfaSayisi
    FROM T_KITAP
    WHERE kategoriID = ktgID;

	RETURN sayfaSayisi;
END $$
DELIMITER ;


DELIMITER $$
CREATE FUNCTION DBF_getYayineviSayisi()
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE yayineviSayisi INT DEFAULT 0;

    SELECT COUNT(*) INTO yayineviSayisi
    FROM T_YAYINEVI;

	RETURN yayineviSayisi;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION DBF_getToplamYazarSayisi()
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE yazarSayisi INT DEFAULT 0;
    SELECT COUNT(*) INTO yazarSayisi
    FROM T_YAZAR;

    RETURN yazarSayisi;
END $$
DELIMITER ;

DELIMITER //
CREATE FUNCTION DBF_getKategoriSayisi()
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE kategoriSayisi INT DEFAULT 0;
	SELECT COUNT(*) INTO kategoriSayisi
    FROM T_KATEGORI;

    RETURN kategoriSayisi;
END //
DELIMITER ;

DELIMITER $$
CREATE FUNCTION DBF_getYayineviKitapSayisi(yayinID INT)
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE yeKitapSayisi INT DEFAULT 0;

    SELECT COUNT(*) INTO yeKitapSayisi
    FROM T_KITAP
    WHERE yayineviID = yayinID;

    RETURN yeKitapSayisi;
END $$
DELIMITER ;
SHOW WARNINGS;

DELIMITER $$
CREATE FUNCTION DBF_getYayineviKitapSayfaSayisi(yayinID INT)
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE ye_kitapSayfaSayisi INT;

    SELECT SUM(kitapSayfaSayisi) INTO ye_kitapSayfaSayisi
    FROM T_KITAP
    WHERE yayineviID = yayinID;

    RETURN ye_kitapSayfaSayisi;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION maxKitapSayfa()
RETURNS INT
BEGIN
	DECLARE maxSayfa INT DEFAULT 0;
    SELECT MAX(kitapSayfaSayisi) INTO maxSayfa
    FROM T_KITAP;

    RETURN maxSayfa;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION minKitapSayfa()
RETURNS INT
BEGIN
	DECLARE minSayfa INT DEFAULT 0;
    SELECT MIN(kitapSayfaSayisi) INTO minSayfa
    FROM T_KITAP;

    RETURN minSayfa;
END $$
DELIMITER ;
SHOW WARNINGS;

DELIMITER $$
CREATE FUNCTION enKisaKitapAdi()
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
	DECLARE enKisaKitapAd VARCHAR(50);

	SELECT kitapAd INTO enKisaKitapAd
    FROM T_KITAP
    ORDER BY kitapSayfaSayisi ASC
    LIMIT 1;

    RETURN enKisaKitapAd;
END $$
DELIMITER ;


DELIMITER $$
CREATE FUNCTION DBF_ortalamaSayfaSayisi()
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
	DECLARE ortSayfa DECIMAL(10,2) DEFAULT 0;
	SELECT AVG(kitapSayfaSayisi) INTO ortSayfa
    FROM T_KITAP;

    RETURN ortSayfa;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION DBF_enPahaliKitapAd()
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
	DECLARE enPahaliAd VARCHAR(50);

	SELECT kitapAd INTO enPahaliAd
    FROM T_KITAP
    ORDER BY kitapFiyat DESC
    LIMIT 1;

	RETURN enPahaliAd;
END $$
DELIMITER ;


DELIMITER $$
CREATE FUNCTION DBF_enPahaliKitapFiyat()
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
	DECLARE enPahaliFiyat DECIMAL(10,2);
	SELECT kitapFiyat INTO enPahaliFiyat
    FROM T_KITAP
    ORDER BY kitapFiyat DESC
    LIMIT 1;

    RETURN enPahaliFiyat;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION enUcuzKitapAd()
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
	DECLARE enUcuzAd VARCHAR(50);

	SELECT kitapAd INTO enUcuzAd
    FROM T_KITAP
    ORDER BY kitapFiyat ASC
    LIMIT 1;

	RETURN enUcuzAd;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION DBF_enUcuzKitapFiyat()
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
	DECLARE enUcuzFiyat DECIMAL(10,2);
	SELECT kitapFiyat INTO enUcuzFiyat
    FROM T_KITAP
    ORDER BY kitapFiyat ASC
    LIMIT 1;

    RETURN enUcuzFiyat;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION DBF_farkliUlkedenYazarSayisi()
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE farkliUlkeYazarlari INT DEFAULT 0;
	SELECT COUNT(DISTINCT yazarUlke) INTO farkliUlkeYazarlari
    FROM T_YAZAR;

    RETURN farkliUlkeYazarlari;
END $$
DELIMITER ;

-- PROSEDURLER: ---------------------------------------------------------------------------------------------------


-- CURSOR ILE KITAP FIYATI GUNCELLEME
DELIMITER $$
CREATE PROCEDURE DBSP_tumKitapFiyatlariArttirma(IN artimMiktari DECIMAL(10,2))
BEGIN
	DECLARE done BOOL DEFAULT FALSE;
	DECLARE c_kitap_id INT;
    DECLARE c_kitap_fiyat DECIMAL(10,2);

    DECLARE cursorGuncelleyici CURSOR FOR
    SELECT kitapID , kitapFiyat FROM T_KITAP; -- kategori yok

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cursorGuncelleyici;

    cursor_gezinti: LOOP
		FETCH cursorGuncelleyici INTO c_kitap_id , c_kitap_fiyat;

        IF done THEN
			LEAVE cursor_gezinti;
        END IF;

        UPDATE T_KITAP
        SET kitapFiyat = c_kitap_fiyat + (c_kitap_fiyat * (artimMiktari/100))
        WHERE kitapID = c_kitap_id;

    END LOOP;
	CLOSE cursorGuncelleyici;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DBSP_tumKitapFiyatlariAzaltma(IN artimMiktari DECIMAL(10,2))
BEGIN
	-- CURSOR DUURM
    DECLARE done INT DEFAULT 0; -- false
	-- CUROSRUN CEKECEKLERI
    DECLARE c_kitap_id INT;
    DECLARE c_kitap_fiyat DECIMAL(10,6);

    DECLARE cursorGuncelleyici CURSOR FOR
    SELECT kitapID , kitapFiyat FROM T_KITAP;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cursorGuncelleyici;
    cursor_gezinti: LOOP
		FETCH cursorGuncelleyici INTO c_kitap_id , c_kitap_fiyat;

        IF done THEN
			LEAVE cursor_gezinti;
        END IF;

        UPDATE T_KITAP
        SET kitapFiyat = c_kitap_fiyat - (c_kitap_fiyat * (artimMiktari/100));
    END LOOP;

    CLOSE cursorGuncelleyici;
END $$
DELIMITER ;


 -- CURSORSUZ
DELIMITER $$
CREATE PROCEDURE DBSP_kitapFiyatlariArttirYuzdeKategoriye(IN artimMiktar DECIMAL(10,2),IN ktgID INT)
BEGIN
    UPDATE T_KITAP
    SET kitapFiyat = kitapFiyat +(kitapFiyat*(artimMiktar/100))
    WHERE kategoriID = ktgID;
END $$
DELIMITER ;
-- DROP PROCEDURE DBSP_kitapFiyatlariArttirYuzdeKategoriye;

-- CURSORLU
DELIMITER $$
CREATE PROCEDURE DBSP_kitapFiyatlariAZALTYuzdeKategoriye(IN artimMiktar DECIMAL(10,2),IN ktgID INT)
BEGIN
	DECLARE done BOOL DEFAULT FALSE;
    DECLARE c_kitap_id INT;
    DECLARE c_mevcutFiyat DECIMAL(10,2);

    DECLARE guncelleyiciCursor CURSOR FOR
    SELECT kitapID , kitapFiyat
    FROM T_KITAP
    WHERE kategoriID = ktgID; -- cursor icine gomduk

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN guncelleyiciCursor;

    cursor_gezinti: LOOP
		FETCH guncelleyiciCursor INTO c_kitap_id , c_mevcutFiyat;

			IF done THEN
				LEAVE cursor_gezinti;
			END IF;

            UPDATE T_KITAP
            SET kitapFiyat = c_mevcutFiyat - (c_mevcutFiyat * (artimMiktar/100))
            WHERE kitapID = c_kitap_id;

    END LOOP;

    CLOSE guncelleyiciCursor;
END $$
DELIMITER ;
DROP PROCEDURE DBSP_kitapFiyatlariAZALTYuzdeKategoriye;


-- CURSORSUZ YAYINEVINE GORE FIYAT ARTTIRMA - azaltma
DELIMITER $$
CREATE PROCEDURE DBSP_kitapFiyatlariArttirYayinevine(IN artimMiktari DECIMAL(10,2),IN yayinID INT)
BEGIN
	UPDATE T_KITAP
    SET kitapFiyat = kitapFiyat + (kitapFiyat * (artimMiktari/100))
    WHERE yayineviID = yayinID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DBSP_kitapFiyatlariAzaltmaYayinevine(IN artimMiktari DECIMAL(10,2),IN yayinID INT)
BEGIN
	UPDATE T_KITAP
    SET kitapFiyat = kitapFiyat - (kitapFiyat * (artimMiktari/100))
    WHERE yayineviID = yayinID;
END $$
DELIMITER ;



-- LOGLAMA KATEGORI
CREATE TABLE TLOG_KATEGORI(
	tlogID INT AUTO_INCREMENT PRIMARY KEY,
    tlogKategoriEskiAd VARCHAR(50),
    tlogKategoriYeniAd VARCHAR(50),
    tlogKategoriID INT,
    tlogDurumAciklamasi VARCHAR(50),
    tlogZaman TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE TLOG_YAZAR(
	tlogID INT AUTO_INCREMENT PRIMARY KEY ,
    tlogYazarEskiAd VARCHAR(50),
    tlogYazarEskiSoyad VARCHAR(50),
    tlogYazarEskiUlke VARCHAR(50),
    tlogYazarYeniAd VARCHAR(50),
    tlogYazarYeniSoyad VARCHAR(50),
    tlogYazarYeniUlke VARCHAR(50),
    tlogDurumAciklamasi VARCHAR(50),
    tlogZaman TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
SHOW WARNINGS;


-- TLOG_KATEGORI TRIGGERLARI -------------------------------------------------------------------------------------------------
DELIMITER $$
CREATE TRIGGER trigger_updateKategoriLog
AFTER UPDATE ON T_KATEGORI
FOR EACH ROW
BEGIN
    IF OLD.kategoriAd != NEW.kategoriAd THEN
        INSERT INTO TLOG_KATEGORI(tlogKategoriEskiAd, tlogKategoriYeniAd, tlogKategoriID,tlogDurumAciklamasi)
        VALUES (OLD.kategoriAd, NEW.kategoriAd, OLD.kategoriID,'GUNCELLEME YAPILDI');
    END IF;
END $$
DELIMITER ;
SHOW WARNINGS;

-- DELETE TRIGGERDA NEW YOK
DELIMITER //
CREATE TRIGGER trigger_deleteKategoriLog
AFTER DELETE ON T_KATEGORI
FOR EACH ROW
BEGIN
	INSERT INTO TLOG_KATEGORI(tlogKategoriEskiAd,tlogKategoriYeniAd,tlogKategoriID,tlogDurumAciklamasi)
    VALUES(OLD.kategoriAd,'SILINEN_KATEGORI',OLD.kategoriID,'SILME YAPILDI');
END //
DELIMITER ;


-- INSERT TRIGGERDA OLD YOK
DELIMITER $$
CREATE TRIGGER trigger_insertKategoriLog
AFTER INSERT ON T_KATEGORI
FOR EACH ROW
BEGIN
	INSERT INTO TLOG_KATEGORI(tlogKategoriEskiAd,tlogKategoriYeniAd,tlogKategoriID,tlogDurumAciklamasi)
    VALUES('YENI_EKLENEN_KATEGORI',NEW.kategoriAd,NEW.kategoriID,'EKLEME YAPILDI');
END $$
DELIMITER ;

DROP TRIGGER trigger_updateKategoriLog;
DROP TRIGGER trigger_deleteKategoriLog;
DROP TRIGGER trigger_insertKategoriLog;
-- TLOG_YAZAR TRIGGERLARI -------------------------------------------------------------------------------------------------

DELIMITER $$
CREATE TRIGGER trigger_updateYazarLog
AFTER UPDATE ON T_YAZAR
FOR EACH ROW
BEGIN
	IF (NEW.yazarAd != OLD.yazarAd) OR (NEW.yazarSoyad != OLD.yazarSoyad) OR (NEW.yazarUlke != OLD.yazarUlke) THEN
		INSERT INTO TLOG_YAZAR(tlogYazarEskiAd, -- 1
							   tlogYazarEskiSoyad,
                               tlogYazarEskiUlke,
                               tlogYazarYeniAd,
                               tlogYazarYeniSoyad,
                               tlogYazarYeniUlke,
                               tlogDurumAciklamasi) -- 7
		VALUES (OLD.yazarAd,OLD.yazarSoyad,OLD.yazarUlke,NEW.yazarAd,NEW.yazarSoyad,NEW.yazarUlke,'GUNCELLEME YAPILDI');
    END IF ;
END $$
DELIMITER ;

DELIMITER  //
CREATE TRIGGER trigger_deleteYazarLog
AFTER DELETE ON T_YAZAR
FOR EACH ROW
BEGIN
		INSERT INTO TLOG_YAZAR(tlogYazarEskiAd, -- 1
							   tlogYazarEskiSoyad,
                               tlogYazarEskiUlke,
                               tlogYazarYeniAd,
                               tlogYazarYeniSoyad,
                               tlogYazarYeniUlke,
                               tlogDurumAciklamasi) -- 7
        VALUES(OLD.yazarAd,OLD.yazarSoyad,OLD.yazarUlke,'SILINDI','SILINDI','SILINDI','SILME YAPILDI');
END //
DELIMITER ;
SHOW WARNINGS;


-- 'Note', '4094', 'At line 6 in kutuphaneOtomasyon3.trigger_insertyazarlog' COZULDU TRIGGERDA KOLON FAZLAYDI
DELIMITER $$
CREATE TRIGGER trigger_insertYazarLog
AFTER INSERT ON T_YAZAR
FOR EACH ROW
BEGIN
	INSERT INTO TLOG_YAZAR(tlogYazarEskiAd, -- 1
						   tlogYazarEskiSoyad,
						   tlogYazarEskiUlke,
                           tlogYazarYeniAd,
                           tlogYazarYeniSoyad,
                           tlogYazarYeniUlke,
                           tlogDurumAciklamasi) -- 7
	VALUES ('YENI_EKLENDI', -- 1
			'YENI_EKLENDI',
            'YENI_EKLENDI',
            NEW.yazarAd,
            NEW.yazarSoyad,
            NEW.yazarUlke,
            'EKLEME YAPILDI'); -- 7
END $$
DELIMITER ;
SHOW WARNINGS;
DROP TRIGGER trigger_insertYazarLog;