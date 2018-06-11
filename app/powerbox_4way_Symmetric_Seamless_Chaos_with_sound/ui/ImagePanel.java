package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos_with_sound.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos_with_sound.Powerbox_4way_Symmetric_Seamless_Chaos_with_sound;

public class ImagePanel extends JPanel{

  private static final long serialVersionUID=581500866418502553L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  ImagePanel(Powerbox_4way_Symmetric_Seamless_Chaos_with_sound pbox){
    this.pbox=pbox;}
  
  /*
   * ################################
   * SAMPLER
   * ################################
   */
  
  Powerbox_4way_Symmetric_Seamless_Chaos_with_sound pbox;
  
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
    if(pbox==null||pbox.uiimage==null)return;
    Graphics2D g2=(Graphics2D)g;
    g2.drawImage(pbox.uiimage,pad,null);}

}
