/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;

import packDatabase.DatabaseIslemler;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import packDatabase.DatabaseIslemler;


public class kitapYonetimFiltrelemeGUI{
    pencereGUI kitapYonetimFiltreleme_frame;
    pencereGUI_Component kyf_component;
    DatabaseIslemler databaseIslemler;
    private int[] comboBoxGirdiler = new int[2];
    
    //CONSTRUCTORLAR
    public kitapYonetimFiltrelemeGUI(DatabaseIslemler databaseIslemler) {
        this.databaseIslemler = databaseIslemler;
        kitapYonetimFiltreleme_frame = new pencereGUI("KITAP YONETIM -> KITAP FILTRELEME", 500, 400);
        kyf_component = new pencereGUI_Component(kitapYonetimFiltreleme_frame,this.databaseIslemler);
    }
    
 
    public void initkitapYonetimGUI_ArttirimAzaltim(boolean isLayoutActive){
        kitapYonetimFiltreleme_frame.InitPencereGUI(true);
        
        //GUI ELEMANLARININ YERLESTIRILECEGI ALAN
        if(isLayoutActive == true){
            kitapYonetimFiltreleme_frame.initLayout();
            
            String kolonIsimleri[] = { "ID", "Kitap Adı","Kitap Stok", "ISBN", "Kategori","Sayfa Sayısı","Fiyat","Yayınevi","Yazar Tam Adı"};
            DefaultTableModel tableModel = new DefaultTableModel(kolonIsimleri,0);
            JTable jtable_Filtrelenmis = new JTable(tableModel);
            jtable_Filtrelenmis.setRowHeight(30);
            jtable_Filtrelenmis.setFont(new Font("Arial", Font.PLAIN, 15));
            
            TableColumn id_col = jtable_Filtrelenmis.getColumnModel().getColumn(0);
            id_col.setMaxWidth(50);
            
            TableColumn kitapADColumn = jtable_Filtrelenmis.getColumnModel().getColumn(1);
            kitapADColumn.setPreferredWidth(150);
            TableColumn stokColumn = jtable_Filtrelenmis.getColumnModel().getColumn(2);
            stokColumn.setMaxWidth(50);
          
            TableColumn sayfaColumn = jtable_Filtrelenmis.getColumnModel().getColumn(5);
            sayfaColumn.setMaxWidth(50);
            
            TableColumn fiyatColumn = jtable_Filtrelenmis.getColumnModel().getColumn(6);
            fiyatColumn.setMaxWidth(50);

            
            JScrollPane scrollPane = new JScrollPane(jtable_Filtrelenmis);
            kitapYonetimFiltreleme_frame.getPencereGUIComponent_Addr().add(scrollPane);
            

            
            JComboBox<String> cbox_kKategori = kyf_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.ALT, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_KATEGORILER);
            JComboBox<String> cbox_kYayinevi = kyf_component.addDoldurucuComboBox(-1, -1, -1, ButonPozisyon.ALT, CagrilacakFonksiyon.CF_COMBOBOX_DOLDUR_MEVCUT_YAYINEVLERI);

            kyf_component.setListenerCombobox(cbox_kKategori, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_KATEGORI_ID,comboBoxGirdiler,0);
            kyf_component.setListenerCombobox(cbox_kYayinevi, CagrilacakFonksiyon.CF_COMBOBOX_FUNC_GET_SELECT_YAYINEVI_ID,comboBoxGirdiler,1);
          
            JButton listeleKategoriButon = new JButton("Listele Kategori");
            JButton listeleYayineviButon = new JButton("Listele Yayınevi");
            JButton listeleTumButon = new JButton("Tümünü Listele");
            
            kyf_component.komponentPozisyonlandiricisi(listeleKategoriButon, ButonPozisyon.ALT);
            kyf_component.komponentPozisyonlandiricisi(listeleYayineviButon, ButonPozisyon.ALT);
            kyf_component.komponentPozisyonlandiricisi(listeleTumButon, ButonPozisyon.ALT);            
            
             listeleKategoriButon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tableModel.setRowCount(0); //KATEGORI SECIMI
                       databaseIslemler.SQL_Q_kitapListesi(tableModel, comboBoxGirdiler[0], comboBoxGirdiler[1], true, false);
                    }
        });
             
             
             listeleYayineviButon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tableModel.setRowCount(0); //YAYINEVI SECIMI
                       databaseIslemler.SQL_Q_kitapListesi(tableModel, comboBoxGirdiler[0], comboBoxGirdiler[1], false, true);
                    }
        });
             
             
             
             
             listeleTumButon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tableModel.setRowCount(0); //KATEGORI VE YAYINEVINE GORE
                       databaseIslemler.SQL_Q_kitapListesi(tableModel, comboBoxGirdiler[0], comboBoxGirdiler[1], true, true);
                    }
        });
         
            
        }
            

        
        kyf_component.updateComponent();
    }
    
    //home_frame pencere ozelliklerine erisim icin yazildi main metoddan
    public pencereGUI managekitapYonetimFiltrelemeFrame(){
        return kitapYonetimFiltreleme_frame;
    }
  
    
    
}

    
