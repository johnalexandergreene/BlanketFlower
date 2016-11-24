package org.fleen.blanketFlower.test.production;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;

import org.fleen.blanketFlower.bComposition.BComposition;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.jig.Jigger;
import org.fleen.blanketFlower.jig.Jigger_000;
import org.fleen.blanketFlower.renderer.Renderer_Production;

public class Test_Production{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test_Production(){
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
  static final int UIWIDTH=1400,UIHEIGHT=800;
  public UI ui;
  
  private void initUI(){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          ui=new UI(Test_Production.this,UIWIDTH,UIHEIGHT);
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
  
  BComposition composition;
  
  void initComposition(){
//    BShape r=new BPolygon(
//      new BVertex(0,0),
//      new BVertex(0,100),
//      new BVertex(200,100),
//      new BVertex(200,0));
    
    BShape r=new BPolygon(
        new BVertex(0,0),
        new BVertex(0,90),
        new BVertex(160,90),
        new BVertex(160,0));
    
    composition=new BComposition(r);}
  
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
  
  //MYA PALETTE WITH A LITTLE GREEN
  private static final Color[] MYAPALETTE000=new Color[]{
    new Color(0,0,0),
    new Color(237,237,237),
    new Color(201,177,171),
    new Color(129,210,115)};
  
  //MYA PALETTE WITH A LITTLE red
  private static final Color[] MYAPALETTE001=new Color[]{
    new Color(0,0,0),
    new Color(237,237,237),
    new Color(201,177,171),
    new Color(226,84,26)};
  
  //MYA PALETTE WITH A LITTLE red and green
  private static final Color[] MYAPALETTE002=new Color[]{
    new Color(0,0,0),
    new Color(237,237,237),
    new Color(201,177,171),
    new Color(226,84,26),
    new Color(129,210,115)};
  
  //ROYG
  private static final Color[] PALETTE000=new Color[]{
    new Color(255,0,0),
    new Color(255,128,0),
    new Color(255,255,0),
    new Color(0,255,0)};
  
  //VWYG
//  private static final Color[] PALETTE000=new Color[]{
//    new Color(255,0,255),
//    new Color(255,255,255),
//    new Color(255,255,0),
//    new Color(0,255,0)};
  
  
//  static final int CELLSPAN=6;
//  static final int CELLSPAN=5;//for mya thing
  static final int CELLSPAN=8;
  
  BufferedImage image;
//  Renderer_Production renderer=new Renderer_Production(PALETTE000);
  Renderer_Production renderer=new Renderer_Production();//palette mother
  
  private void renderComposition(){
    image=renderer.render(composition,CELLSPAN);
    if(ui!=null)
      ui.imagepanel.repaint();}
  
  /*
   * ################################
   * EXPORT
   * ################################
   */
  
  static final String EXPORTDIR="/home/john/Desktop/bfexport";
  
  RasterExporter rasterexporter=new RasterExporter(new File(EXPORTDIR));
  
  void export(int index){
    System.out.println("export");
    BufferedImage exportimage=renderer.render(composition,CELLSPAN);
    rasterexporter.export(exportimage,index);}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    System.out.println("----begin process");
    Test_Production test=new Test_Production();
    for(int i=0;i<1212;i++){
      test.executeJigger();
      test.renderComposition();
      test.export(i);
      System.out.println("FRAME#"+i);
      try{
        Thread.sleep(10,0);
      }catch(Exception x){x.printStackTrace();}}
    System.out.println("----end process");
    }

}
