package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.Powerbox_4way_Symmetric_Seamless_Chaos;

@SuppressWarnings("serial")
public class UI extends JFrame{

  public JPanel imagepanel;

  public UI(Powerbox_4way_Symmetric_Seamless_Chaos pbox,int w,int h){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(0,0,w,h);
    imagepanel=new ImagePanel(pbox);
    setContentPane(imagepanel);
    setVisible(true);}

}
