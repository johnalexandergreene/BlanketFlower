package org.fleen.blanketFlower.app.powerbox_Seamless_Chaos;

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
  
  ImagePanel(Powerbox_Seamless_Chaos pbox){
    this.pbox=pbox;}
  
  /*
   * ################################
   * SAMPLER
   * ################################
   */
  
  Powerbox_Seamless_Chaos pbox;
  
  /*
   * ################################
   * PAINT
   * ################################
   */
  
  private static final int PADDINGSPAN=10;
  
  public void paint(Graphics g){
    AffineTransform pad=new AffineTransform();
    pad.translate(PADDINGSPAN,PADDINGSPAN);
    //
    super.paint(g);
    if(pbox==null||pbox.image==null)return;
    Graphics2D g2=(Graphics2D)g;
    g2.drawImage(pbox.image,pad,null);}

}
