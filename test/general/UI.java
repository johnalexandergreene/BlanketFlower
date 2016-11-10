package org.fleen.blanketFlower.test.general;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UI extends JFrame{

  public JPanel imagepanel;

  public UI(Test test,int w,int h){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(50,50,w,h);
    imagepanel=new ImagePanel(test);
    setContentPane(imagepanel);
    setVisible(true);}

}
