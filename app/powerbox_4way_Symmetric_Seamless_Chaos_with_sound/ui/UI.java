package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos_with_sound.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos_with_sound.Powerbox_4way_Symmetric_Seamless_Chaos_with_sound;

@SuppressWarnings("serial")
public class UI extends JFrame{

  public JPanel imagepanel;

  public UI(Powerbox_4way_Symmetric_Seamless_Chaos_with_sound pbox,int w,int h){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(0,0,w,h);
    imagepanel=new ImagePanel(pbox);
    setContentPane(imagepanel);
    setVisible(true);}

}
