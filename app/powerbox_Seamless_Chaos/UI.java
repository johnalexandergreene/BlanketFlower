package org.fleen.blanketFlower.app.powerbox_Seamless_Chaos;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UI extends JFrame{

  public JPanel imagepanel;

  public UI(Powerbox_Seamless_Chaos pbox,int w,int h){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(0,0,w,h);
    imagepanel=new ImagePanel(pbox);
    setContentPane(imagepanel);
    setVisible(true);}

}
