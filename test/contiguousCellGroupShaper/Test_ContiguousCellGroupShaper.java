package org.fleen.blanketFlower.test.contiguousCellGroupShaper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.geom_Boxy.GB;

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
    CELLMESSWIDTH=20,
    CELLMESSHEIGHT=20;
  
  public static final int CELLCOUNT=150;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test_ContiguousCellGroupShaper(){
    initUI();
    initRenderer();
    doTest();}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  UI ui;
 
  private void initUI(){
    ui=new UI(this,UIWIDTH,UIHEIGHT);
    ui.getContentPane().addMouseListener(new ML0());}
  
  class ML0 extends MouseAdapter{
    public void mouseClicked(MouseEvent arg0){
      doTest();}}
  
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
   * CELLS
   * ################################
   */
  
  CellMess cellmess;
  List<BCellGroup> cgroups;
  List<BShape> shapes;
  
  private void createCellMess(){
    cellmess=new CellMess(CELLMESSWIDTH,CELLMESSHEIGHT);
    cellmess.generate(CELLCOUNT);}
  
  /*
   * ################################
   * TEST
   * ################################
   */
  
  private void doTest(){
    System.out.println("#########################################");
    System.out.println("TEST CONTIGUOUS CELL GROUP SHAPER : START");
    System.out.println("#########################################");
    
    //create our (probably chaotic) pattern of cells
    System.out.println("cell mess init");
    createCellMess();
    System.out.println("cell mess cell count = "+cellmess.size());
    //derive contiguous groups from cell mess
    System.out.println("derive contiguous groups");
    cgroups=GB.getContiguousSubgroups(cellmess);
    System.out.println("contiguous groups count = "+cgroups.size());
    //derive shapes from contiguous groups
    System.out.println("derive shapes");
    shapes=new ArrayList<BShape>(cgroups.size());
    for(BCellGroup group:cgroups)
      shapes.add(GB.getShapeFromContiguousGroup(group));
    System.out.println("shape count = "+shapes.size());
    //render
    System.out.println("render");
    render();
    
    System.out.println("#######################################");
    System.out.println("TEST CONTIGUOUS CELL GROUP SHAPER : END");
    System.out.println("#######################################");
    
  }
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    new Test_ContiguousCellGroupShaper();
    
  }
  
  

}
