package org.fleen.blanketFlower.test.contiguousCellGroupShaper;

import java.awt.image.BufferedImage;

/*
 * test the arbitrarily contiguous cell group to polygons converter
 * AKA Cell Group Polygonizer
 * 
 * Create a mess of cells. Pretty random but clumpy and holey
 * trace the edges of the clumps and holes
 * 
 */
public class Test_ContiguousCellGroupShaper{
  
  public static final int 
    UIWIDTH=1000,
    UIHEIGHT=1000;
  
  public static final int 
    CELLMESSWIDTH=5,
    CELLMESSHEIGHT=5;
  
  public static final int CELLCOUNT=200;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test_ContiguousCellGroupShaper(){
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
    Test_ContiguousCellGroupShaper cgp=new Test_ContiguousCellGroupShaper();
    cgp.cellmess.generate(CELLCOUNT);
    System.out.println("cell mess cell count="+cgp.cellmess.size());
    cgp.render();
    
  }
  
  

}
