package org.fleen.blanketFlower.test.cellGroupPolygonizer;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class UI extends JFrame{

  JPanel imagepanel;

  UI(Test_CellGroupPolygonizer test,int w,int h){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(50,50,w,h);
    imagepanel=new ImagePanel(test);
    setContentPane(imagepanel);
    setVisible(true);}

}
