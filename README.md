# Kütüphane Otomasyon Programı

<details>
<summary>pGUI KÜTÜPHANESİ HAKKINDA</summary>

* Programdaki tüm SWING/AWT Componentleri Özelleştirilmiş ve soyutlanmıştır farklı Java Programlarındada taşınabilir şekilde kullanılabilir

* pencereGUI ve pencereGUI_Component Fonksiyonların deklerasyonları ve açıklamaları aşağıdadır
```
+
```
</details>


<details>
<summary>DİZİNLER HAKKINDA</summary>

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
</details>




<details>
<summary>KONSOL HAKKINDA</summary>

 * Konsol üzerinden doğrudan DDL ve DML komut setleri çalıştırılabilir şu anda tam olarak soyutlanıp güvenlik testleri yapılmasada kullanılabilir
 ```
 +
```
 
