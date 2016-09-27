package org.fleen.squarzy.app.test;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;

import org.fleen.squarzy.Grid;
import org.fleen.squarzy.Renderer;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    initUI();
  }
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  static final String TITLE="squarzeeee";
  static final int UIWIDTH=1000,UIHEIGHT=600;
  public UI ui;
  
  private void initUI(){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          ui=new UI(Test.this,UIWIDTH,UIHEIGHT);
          ui.setVisible(true);
          ui.setTitle(TITLE);
         }catch(Exception e){
           e.printStackTrace();}}});}
  
  /*
   * ################################
   * GRID
   * ################################
   */
  
  static final int GRIDWIDTH=100,GRIDHEIGHT=50;
  
  Grid grid=new Grid(GRIDWIDTH,GRIDHEIGHT);
  
  void incrementGrid(){
    //TODO
    //blah blah
    renderGrid();
  }
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  static final int CELLSPAN=8;
  BufferedImage image;
  Renderer renderer=new Renderer();
  
  private void renderGrid(){
    image=renderer.render(grid,CELLSPAN);
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
    for(int i=0;i<100;i++){
      test.incrementGrid();
      test.export();
      try{
        Thread.sleep(100,0);
      }catch(Exception x){x.printStackTrace();}
    }
    
  }

}
