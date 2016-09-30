package org.fleen.squarzy.app.test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;

import org.fleen.squarzy.Agent;
import org.fleen.squarzy.Agent_Frogs;
import org.fleen.squarzy.Agent_Random;
import org.fleen.squarzy.Cell;
import org.fleen.squarzy.Grid;
import org.fleen.squarzy.Renderer;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    initGrid();
    initAgents();
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
  
  Grid grid;
  
  Agent[] agents={};
  
  void initGrid(){
    grid=new Grid(GRIDWIDTH,GRIDHEIGHT);}
  
  void incrementGrid(){
    
    zeroCells();
    
    applyAgentsToGrid();
    renderGrid();}
  
  private void zeroCells(){
    int 
    gridwidth=grid.getWidth(),
    gridheight=grid.getHeight();
    Cell cell;
    for(int x=0;x<gridwidth;x++){
      for(int y=0;y<gridheight;y++){
          cell=grid.getCell(x,y);
          cell.test=0;}}}
  
  void applyAgentsToGrid(){
    for(Agent a:agents)
      a.run();}
  
  private void initAgents(){
    agents=new Agent[]{
      new Agent_Random(grid),
      new Agent_Frogs(grid)};
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
    for(int i=0;i<300;i++){
      test.incrementGrid();
      test.export();
      try{
        Thread.sleep(10,0);
      }catch(Exception x){x.printStackTrace();}
    }
    
  }

}
