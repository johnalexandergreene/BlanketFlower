package org.fleen.squarzy.app.test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;

import org.fleen.squarzy.Composition;
import org.fleen.squarzy.Jig;
import org.fleen.squarzy.Jig_Frogs;
import org.fleen.squarzy.Jig_Random;
import org.fleen.squarzy.Renderer_Test;
import org.fleen.squarzy.gSquid.SCell;
import org.fleen.squarzy.gSquid.SGrid;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    initComposition();
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
   * JIG ACTION
   * ################################
   */
  
  Jig[] jigs={};
  
  void incrementGrid(){
//    zeroCells();
//    applyAgentsToGrid();
//    renderGrid();
    
  }
  
  private void zeroCells(){
    int 
    gridwidth=grid.getWidth(),
    gridheight=grid.getHeight();
    SCell cell;
    for(int x=0;x<gridwidth;x++){
      for(int y=0;y<gridheight;y++){
          cell=grid.getCell(x,y);
          cell.test=0;}}}
  
  void applyAgentsToGrid(){
    for(Jig a:jigs)
      a.run();}
  
  private void initAgents(){
    jigs=new Jig[]{
      new Jig_Random(),
      new Jig_Frogs(grid)};
    }
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  static final int CELLSPAN=8;
  BufferedImage image;
  Renderer_Test renderer=new Renderer_Test();
  
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
    BufferedImage exportimage=renderer.render(grid,CELLSPAN);
    File d=new File(EXPORTDIR);
    rasterexporter.setExportDir(d);
    rasterexporter.export(exportimage);}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    Test test=new Test();
//    for(int i=0;i<300;i++){
//      test.incrementGrid();
//      test.export();
//      try{
//        Thread.sleep(10,0);
//      }catch(Exception x){x.printStackTrace();}
//    }
    test.renderComposition();
    
  }

}
