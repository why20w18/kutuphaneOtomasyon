/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author archw
 */
package packGuiSwing;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import packDatabase.DatabaseIslemler;
import static packGuiSwing.CagrilacakFonksiyon.CF_TABLE_KATEGORI_LOG;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import packDatabase.DatabaseIslemler;
import static packGuiSwing.CagrilacakFonksiyon.CF_TABLE_KATEGORI_LOG;

import packGuiSwing.pencereGUI;


public class yazarLogGUI {
    pencereGUI yazarlog_frame;
    pencereGUI_Component yazarlog_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public yazarLogGUI(DatabaseIslemler databaseIslemler){
        pencereGUI.debugPrint("kategoriLogGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        yazarlog_frame = new pencereGUI("YAZAR YONETIM LOGLARI", 800, 750);
        yazarlog_component = new pencereGUI_Component(yazarlog_frame,this.databaseIslemler);

        
    }
    
 
    public void inityazarLogGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("kategoriLogGUI CAGRILDI");
        yazarlog_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            yazarlog_frame.initLayout();
            
            String kolonAdlari[] = {"TLOG_ID","Eski Tam Adı","Eski Ülke","Yeni TamAdı","Yeni Ülke","Durum","Zaman"};
            JTable kategoriLogTable = yazarlog_component.addTable(kolonAdlari, 30, 15, CagrilacakFonksiyon.CF_TABLE_YAZAR_LOG);
            
            TableColumn id_col = kategoriLogTable.getColumnModel().getColumn(0);
            id_col.setMaxWidth(60);
            TableColumn eskiAdColumn = kategoriLogTable.getColumnModel().getColumn(1);
            eskiAdColumn.setPreferredWidth(150);
            TableColumn yAdColumn = kategoriLogTable.getColumnModel().getColumn(2);
            yAdColumn.setPreferredWidth(150);
            
            
            
        }
            
        
        yazarlog_component.updateComponent();
    }
    public pencereGUI managekategoriLogGUIFrame(){
        return yazarlog_frame;
    }
}


