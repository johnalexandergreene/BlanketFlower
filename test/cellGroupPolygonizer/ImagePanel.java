package org.fleen.blanketFlower.test.cellGroupPolygonizer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{

  private static final long serialVersionUID=581500866418502553L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  ImagePanel(Test_CellGroupPolygonizer test){
    this.test=test;}
  
  /*
   * ################################
   * SAMPLER
   * ################################
   */
  
  Test_CellGroupPolygonizer test;
  
  /*
   * ################################
   * PAINT
   * ################################
   */
  
  private static final int PADDING=16;
  
  public void paint(Graphics g){
    AffineTransform pad=new AffineTransform();
    pad.translate(PADDING,PADDING);
    //
    super.paint(g);
    if(test==null||test.image==null)return;
    Graphics2D g2=(Graphics2D)g;
    g2.drawImage(test.image,pad,null);}

}
