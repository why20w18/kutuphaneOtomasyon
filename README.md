# Kütüphane Otomasyon Programı


+ program icindeki yorum satirlari

```
+ pencereGUI_Component icinde 5 adet class 2 adet enum tanımı vardır
+ classlar:
            -ButonPozisyon enum
            -CagrilacakFonksiyon enum
            
            -pencereGUI_Component class
            -tableFonksiyonlari class
            -menuBarFonksiyonlari class
            -comboboxFonksiyonlariSTR class
            -butonFonksiyonlari class

/* => pencereGUI_Component.java
GUI KOMPONENT VE FONKSIYON BAGLAMA ADIMLARI
1-ILK ONCE FONKSIYON ICIN ENUM DEGERI OLUSTURULUR
2-FONKSIYON TANIMI componentadiFonksiyonlari CLASSLARINDA YAPILIR
3-COMPONENT TIPINE GORE O COMPONENTIN FONKSIYONUNUN ICINE EKLENIR
*/

/* => pencereGUI_Component.java
addButtonParams HAKKINDA:
    +DEKLERASYON        :   addButtonParams(String buttonText , ButonPozisyon butonPozisyon ,
                            CagrilacakFonksiyon butonFonksiyonu,String girdilerString[])
    
    +addButton'dan FARKI:   addButton sadece parametre almayan fonksiyonlari butona basildigi anda 
                            calistirabilirken addButtonParams fonksiyonu icinde parametre alan 
                            fonksiyonlari calistirmak amaclanarak yazilmistir
        
    +CALISMA MEKANIZMASI:  
     bunu yapmak icin ilk once TextFieldlarin(Kullanicinin girdi girdiği GUI komponenti)
     tum girdilerinin (ki bu textfieldlarda addTextField fonksiyonuyla olusturuluyor pencerede
     dogru konuma yerlestirilebilmesi icin) dinlenmesi gerekiyor MEVCUT PENCERE KAÇ ADET TEXTFIELD VARSA
     bunun icin ListenerTextField(ArrayList<JTextField> textFieldsArrayList, String[] girdiListesi)
     fonksiyonunu kullanacagiz , ilk parametreyi saglamak bir ArrayList icine olusturulan tum 
     TextFieldlari attiktan sonra ikinci parametre icine kullanicinin textfield icine girdi her inputu
     girdiListesi string arrayinin indexine yerlestiriyor sonrasinda girdiListesi string arrayi artik 
     kullanici inputlarini ve hangi textfielda girdigimizi gosterir haldeyken bunu addButtonParams 
     fonksiyonuna parametre olarak yolluyoruz cunku her butona ayri bir fonksiyon atadik ve bu fonksiyonlar
     kullanicinin textfieldlara girdigi parametrelere gore islem yapiyor ONEMLI NOKTA buton fonksiyonun 
     parametresi double=personelMaas yada int=yayineviID gibi olabilir bu durumda wrapper parse ile 
     cevirmemiz gerekecektir yine kullanici null yada bosluk iceren girdiler girebilir yada hatayla bunları
     deneyebilir veritabaninda bunlarin probleme yol acmamasi icin TEMEL SEVIYE guvenlik onlemi olarak boolean 
     ceviren ayri bir fonksiyon daha yazilmistir !checkGirdiStringISNULL_ISEMPTY => ilk parametre 
     kontrole baslanacak girdiListesi indexi ve  ikinci parametre hangi indexe kadar kontrol edecegidir 
                            
     EKLEME DURUMUNDA ---> ID disinda sona kadar kontrol PARAM1=1<PARAM2=girdiListe.length
     CIKARTMA DURUMUNDA--> SADECE ID KONTROLU PARAM1=0 ve PARAM2=1
                            
     ÖRNEGIN:
     yayineviYonetim Classinda 3 adet textfield var soldan saga indexleri 0,1,2 seklinde girdiListesi
     string arrayine yerlestirilecektir
*/

/* => mainGUI.java
1-HER PENCERE ICIN YENI CLASS ACILMALIDIR
2-HER PENCERE/SEKME KENDI CLASSI ICINDE .getStartFrame() ILE BASLATILMALIDIR
3-.getStartFrame() CONSTRUCTOR ICINE KOYULMAMALIDIR initCLASS_ADI SEKLINDE YENI METOD
ACILARAK 2.ADIMDA BASLATILMALIDIR
4-initLayout() , initCLASS_ADI ICERISINDE LAYOUT AKTIFLIGI KONTROLUNDEN SONRA BASLATILMALIDIR
*/

/* => databaseIslemler.java
VERITABANINA BAGLANTI BURADA OTOMATIK YAPILIR : constructora gomduk

pst --> PreparedStatement tipindendir
SELECT                      => geriye resultset cevirir :: ResultSet rst = pst.executeQuery(); 
                            :: rst icinde sonuc kumesi bulunur calisan sorgunun ve adim adim icinden veri cekilebilir

INSERT DELETE UPDATE        => geriye etkilenen satir sayisi cevirir :: int affectedRows = pst.executeUpdate()

SELECT INSERT DELETE UPDATE => geriye eger resultSet donduyse true , geriye eger etkilenen satir say donduyse false
                            :: boolean status = pst.execut();

geriye donen resultSet icinde (executQuery) veri olup olmadigini rst.next() while-if idiomlarinda kontrol edilebilir
*/
            
         
```



## Dizinler

* ./src/            : Programdaki tüm kaynak kodları içeren kısım

* ./jarBagimlilik/  : Programda MySQL ile bağlantı kurmak için gerekli olan jar dosyası
```
.
├── packDatabase
│   ├── databaseBaglanti.java
│   └── DatabaseIslemler.java
├── packGuiSwing
│   ├── kategoriLogGUI.java
│   ├── kategoriTablosuGUI.java
│   ├── kitapYazarTablosuGUI.java
│   ├── kitapYonetimFiltrelemeGUI.java
│   ├── kitapYonetimGUI_ArttirimAzaltim.java
│   ├── kitapYonetimGUI.java
│   ├── kitapYonetimIstatistikler.java
│   ├── konsolCommand.java
│   ├── mainGUI.java
│   ├── oduncYonetimGUI.java
│   ├── oduncYonetimOduncIslemleriGUI.java
│   ├── pencereGUI_Component.java
│   ├── pencereGUI.java
│   ├── personelYonetimGUI.java
│   ├── uyeYonetimiGUI.java
│   ├── uyeYonetimiOgrenciIslemleriGUI.java
│   ├── uyeYonetimOzelSektorIslemleri.java
│   ├── uyeYonetimSivilIslemleriGUI.java
│   ├── yayineviYonetimGUI.java
│   ├── yazarLogGUI.java
│   └── yazarYonetimGUI.java
├── packImages
│   └── splashLogo.png
└── packSourceCode
    └── Main.java

```






## Konsol

 * Konsol üzerinden doğrudan DDL ve DML komut setleri çalıştırılabilir şu anda tam olarak soyutlanıp güvenlik testleri yapılmasa da kullanılabilir
 ```
 > help             -> komutların kullanımını gösteren yardım sayfası
 > cls              -> konsol ekranını temizler
 > +                -> konsol ekranı puntosunu büyütür
 > -                -> konsol ekranı puntosunu küçültür
 > tab              -> tüm tabloların adını çevirir
 > col TABLO_ADI    -> girilen tablodaki kolonlar ve türleri hakkında bilgi çevirir 
 > sql:SQL_SORGUSU  -> DDL/DML komutlarını doğrudan program üzerinden çalıştırma
```
 
