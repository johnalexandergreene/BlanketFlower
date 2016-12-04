package org.fleen.blanketFlower.test.jigPatternFill;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.jig.patternFill.Jig_PatternFill;

/*
 * test jig pattern fill
 * create a polygon
 * apply jigpatternfill to it
 * render that
 * 
 */
public class Test_JigPatternFill{
  
  public static final int 
    UIWIDTH=800,
    UIHEIGHT=800;

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test_JigPatternFill(){
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
   * ROOT POLYGON
   * ################################
   */

  static final BVertex[] ROOT={
    new BVertex(0,0),
    new BVertex(0,50),
    new BVertex(50,50),
    new BVertex(50,0)};
  
  BPolygon root=null;
  
  BPolygon getRootPolygon(){
    if(root==null)root=new BPolygon(ROOT);
    return root;}
  
  /*
   * ################################
   * TEST
   * ################################
   */
  
  BShape shape;
  
  private void doTest(){
    System.out.println("#############################");
    System.out.println("TEST JIG PATTERN FILL : START");
    System.out.println("#############################");
    //clear
    root=null;
    //get root polygon
    BPolygon root=getRootPolygon();
    //do pattern fill
    Jig_PatternFill j=new Jig_PatternFill(root);
    j.execute(0);
    
    //render
    System.out.println("render");
    render();
    
    System.out.println("###########################");
    System.out.println("TEST JIG PATTERN FILL : END");
    System.out.println("###########################");
    
  }
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    new Test_JigPatternFill();
    
  }
  
  

}
