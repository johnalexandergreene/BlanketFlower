package org.fleen.blanketFlower.test.cellGroupPolygonizer;

import java.awt.image.BufferedImage;

/*
 * test the arbitrarily contiguous cell group to polygons converter
 * AKA Cell Group Polygonizer
 * 
 * Create a mess of cells. Pretty random but clumpy and holey
 * trace the edges of the clumps and holes
 * 
 */
public class Test_CellGroupPolygonizer{
  
  public static final int 
    UIWIDTH=1000,
    UIHEIGHT=1000;
  
  public static final int 
    CELLMESSWIDTH=20,
    CELLMESSHEIGHT=20;
  
  public static final int CELLCOUNT=200;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test_CellGroupPolygonizer(){
    initUI();
    initCellMess();
    initRenderer();
  }
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  UI ui;
 
  private void initUI(){
    ui=new UI(this,UIWIDTH,UIHEIGHT);}
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  BufferedImage image=null;
  Renderer renderer;
  
  void initRenderer(){
    renderer=new Renderer(this);}
  
  void render(){
    image=renderer.render();
    ui.imagepanel.repaint();}
  
  /*
   * ################################
   * CELL MESS
   * ################################
   */
  
  CellMess cellmess;
  
  private void initCellMess(){
    cellmess=new CellMess(CELLMESSWIDTH,CELLMESSHEIGHT);}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    Test_CellGroupPolygonizer cgp=new Test_CellGroupPolygonizer();
    cgp.cellmess.generate(CELLCOUNT);
    cgp.render();
    
  }
  
  

}
