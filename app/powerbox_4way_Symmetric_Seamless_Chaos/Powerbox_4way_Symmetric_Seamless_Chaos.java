package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.renderer.Renderer;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.renderer.Renderer_Production;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.stripeSystem.Stripe;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.stripeSystem.StripeSystem;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.ui.UI;

public class Powerbox_4way_Symmetric_Seamless_Chaos{
  
  /*
   * ################################
   * STRIPE SYSTEM
   * ################################
   */
  
  public StripeSystem stripesystem;
  
  public void run(){
    System.out.println("START");
    initUI();
    stripesystem=new StripeSystem(StripeSystem.REZ_HI,2,3,8);
    boolean finished=false;
    int frameindex=0;
    while(!finished){
      finished=stripesystem.move();
      renderToUI();
//      export(frameindex);
      //export720p//TODO -- create directories as necessary too
      //export1080p
      //export4k
      frameindex++;
    }
    System.out.println("END");
    
  }
  
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  static final String TITLE="blanketflower";
  static final int UIWIDTH=1500,UIHEIGHT=1010;
  public UI ui;
  
  private void initUI(){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          ui=new UI(Powerbox_4way_Symmetric_Seamless_Chaos.this,UIWIDTH,UIHEIGHT);
          ui.setVisible(true);
          ui.setTitle(TITLE);
          ui.getContentPane().setBackground(Color.black);
         }catch(Exception e){
           e.printStackTrace();}}});}
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  private static final int UICELLSPAN=2;
  public BufferedImage image;
  Renderer renderer;
  
  private void renderToUI(){
    Renderer renderer=getRenderer();
    image=renderer.render(UICELLSPAN);//TODO
    if(ui!=null)
      ui.imagepanel.repaint();}
  
  private Renderer getRenderer(){
    if(renderer==null)
      renderer=new Renderer_Production(this);
//      renderer=new Renderer_Test(this);
    return renderer;}
  
  /*
   * ################################
   * EXPORT
   * ################################
   */
  
  static final int EXPORTCELLSPAN=4;//720p
  //TODO we will export to 3 dirs at the same time : 720p,1080p,4k
  
  static final String EXPORTDIR="/home/john/Desktop/bfexport";
  
  RasterExporter rasterexporter=new RasterExporter(new File(EXPORTDIR));
  
  void export(int index){
    System.out.println("export");
    BufferedImage exportimage=renderer.render(EXPORTCELLSPAN);
    rasterexporter.export(exportimage,index);}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    new Powerbox_4way_Symmetric_Seamless_Chaos().run();}

}
