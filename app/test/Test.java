package org.fleen.squarzy.app.test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;

import org.fleen.squarzy.Composition;
import org.fleen.squarzy.Jigger;
import org.fleen.squarzy.Jigger_000;
import org.fleen.squarzy.Renderer_Production;
import org.fleen.squarzy.Renderer_Test;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    initComposition();
    initJigger();
    initUI();
  }
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  static final String TITLE="blanketflower";
  static final int UIWIDTH=1000,UIHEIGHT=600;
  public UI ui;
  
  private void initUI(){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          ui=new UI(Test.this,UIWIDTH,UIHEIGHT);
          ui.setVisible(true);
          ui.setTitle(TITLE);
          ui.getContentPane().setBackground(Color.black);
         }catch(Exception e){
           e.printStackTrace();}}});}
  
  /*
   * ################################
   * COMPOSITION
   * ################################
   */

  static final int GRIDWIDTH=100,GRIDHEIGHT=50;
  
  Composition composition;
  
  void initComposition(){
    composition=new Composition(GRIDWIDTH,GRIDHEIGHT);}
  
  /*
   * ################################
   * JIGGER
   * ################################
   */
  
  Jigger jigger;
  
  void executeJigger(){
    jigger.execute();}
  
  private void initJigger(){
    jigger=new Jigger_000();
    jigger.setComposition(composition);}
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  static final int CELLSPAN=6;
  BufferedImage image;
  Renderer_Production renderer=new Renderer_Production();
  
  private void renderComposition(){
    image=renderer.render(composition,CELLSPAN);
    if(ui!=null)
      ui.imagepanel.repaint();}
  
  /*
   * ################################
   * EXPORT
   * ################################
   */
  
  static final String EXPORTDIR="/home/john/Desktop/squarzyexport";
  
  RasterExporter rasterexporter=new RasterExporter();
  
  void export(){
    System.out.println("export");
    BufferedImage exportimage=renderer.render(composition,CELLSPAN);
    File d=new File(EXPORTDIR);
    rasterexporter.setExportDir(d);
    rasterexporter.export(exportimage);}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    System.out.println("####begin process");
    Test test=new Test();
    for(int i=0;i<555;i++){
      test.executeJigger();
      test.renderComposition();
      test.export();
      try{
        Thread.sleep(10,0);
      }catch(Exception x){x.printStackTrace();}}
    System.out.println("####end process");
    }

}
