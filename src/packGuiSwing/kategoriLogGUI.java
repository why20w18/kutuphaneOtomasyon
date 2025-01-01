/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packGuiSwing;

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


public class kategoriLogGUI {
    pencereGUI kategorilog_frame;
    pencereGUI_Component kategorilog_component;
    DatabaseIslemler databaseIslemler;

    
    
    //CONSTRUCTORLAR
    public kategoriLogGUI(DatabaseIslemler databaseIslemler){
        pencereGUI.debugPrint("kategoriLogGUI BASLATILDI");
        this.databaseIslemler = databaseIslemler;

        kategorilog_frame = new pencereGUI("KATEGORI LOGLARI", 800, 750);
        kategorilog_component = new pencereGUI_Component(kategorilog_frame,this.databaseIslemler);

        
    }
    
 
    public void initkategoriLogGUI(boolean isLayoutActive){
        pencereGUI.debugPrint("kategoriLogGUI CAGRILDI");
        kategorilog_frame.InitPencereGUI(true);
        
        if(isLayoutActive == true){
            kategorilog_frame.initLayout();
            
            String kolonAdlari[] = {"TLOG_ID","Eski Ad","Yeni Ad","Eski KategoriID","Durum","Zaman"};
            JTable kategoriLogTable = kategorilog_component.addTable(kolonAdlari, 30, 15, CF_TABLE_KATEGORI_LOG);
            
            TableColumn id_col = kategoriLogTable.getColumnModel().getColumn(0);
            id_col.setMaxWidth(60);
            TableColumn eskiAdColumn = kategoriLogTable.getColumnModel().getColumn(1);
            eskiAdColumn.setPreferredWidth(150);
            TableColumn yAdColumn = kategoriLogTable.getColumnModel().getColumn(2);
            yAdColumn.setPreferredWidth(150);
            
        }   
        kategorilog_component.updateComponent();
    }
    public pencereGUI managekategoriLogGUIFrame(){
        return kategorilog_frame;
    }
  
    
    
}

