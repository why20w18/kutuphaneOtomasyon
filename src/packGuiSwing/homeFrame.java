package packGuiSwing;

import javax.swing.*;

public class homeFrame{
    
    private JFrame home_frame;
    private String pencereAdi;
    private int hf_genislik;
    private int hf_yukseklik;

    public homeFrame(String pencereAdi , int pencereGenislik , int pencereYukseklik){
        this.pencereAdi =  pencereAdi;
        this.hf_genislik = pencereGenislik;
        this.hf_yukseklik = pencereYukseklik;
    }
    
    public void startFrame(){
        home_frame = new JFrame(this.pencereAdi);
        home_frame.setVisible(true);
        home_frame.setSize(hf_genislik,hf_yukseklik);
        home_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void hideFrame(){
        home_frame.setVisible(false);
    }
    
    public String getTittle(){
        return this.pencereAdi;
    }
}
