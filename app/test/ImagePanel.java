package org.fleen.squarzy.app.test;

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
  
  ImagePanel(Test sampler){
    this.sampler=sampler;}
  
  /*
   * ################################
   * SAMPLER
   * ################################
   */
  
  Test sampler;
  
  /*
   * ################################
   * PAINT
   * ################################
   */
  
  private static final int PADDINGSPAN=30;
  private static AffineTransform PAD=AffineTransform.getTranslateInstance(PADDINGSPAN,PADDINGSPAN);
  
  public void paint(Graphics g){
    super.paint(g);
    if(sampler==null||sampler.image==null)return;
    Graphics2D g2=(Graphics2D)g;
    g2.drawImage(sampler.image,PAD,null);}

}
