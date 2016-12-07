package org.fleen.blanketFlower.powerBox4WaySeamless;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UI extends JFrame{

  public JPanel imagepanel;

  public UI(PBox pbox,int w,int h){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(50,50,w,h);
    imagepanel=new ImagePanel(pbox);
    setContentPane(imagepanel);
    setVisible(true);}

}
