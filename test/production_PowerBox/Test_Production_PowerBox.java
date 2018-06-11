package org.fleen.blanketFlower.test.production_PowerBox;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;

import org.fleen.blanketFlower.bComposition.BComposition;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.jig.Jigger;
import org.fleen.blanketFlower.renderer.Renderer_Blender_PaletteMother;

public class Test_Production_PowerBox{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test_Production_PowerBox(){
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
          ui=new UI(Test_Production_PowerBox.this,UIWIDTH,UIHEIGHT);
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
    
    
//    box 4px cells
    BShape r=new BPolygon(
        new BVertex(0,0),
        new BVertex(0,128),
        new BVertex(128,128),
        new BVertex(128,0));
    
//    //for 640x480 4px cell gif
//    BShape r=new BPolygon(
//        new BVertex(0,0),
//        new BVertex(0,120),
//        new BVertex(160,120),
//        new BVertex(160,0));
    
    composition=new BComposition(r);}
  
  /*
   * ################################
   * JIGGER
   * ################################
   */
  
  Jigger jigger;
  
  void executeJigger(int frameindex){
    jigger.execute(frameindex);}
  
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
  
  //reddened that pale pink
  public static final Color[] P_TOY_STORY_ADJUSTED2=new Color[]{
    new Color(168,67,39),
    new Color(251,206,89),
    new Color(88,184,121),
    new Color(154,94,154),
    new Color(234,61,65),
    new Color(248,237,23),
    new Color(249,139,90),
    new Color(0,146,232),
    new Color(254,178,213)};
  
  public static final Color[] P_PORCO_ROSSO=new Color[]{
    new Color(227,237,76),
    new Color(184,194,105),
    new Color(226,235,232),
    new Color(174,202,224),
    new Color(94,132,197),
    new Color(248,203,161),
    new Color(179,145,120),
    new Color(220,178,164),
    new Color(90,14,0),
    new Color(189,53,31)};
  
  
  static final int CELLSPAN=4;
  
  BufferedImage image;
  Renderer_Blender_PaletteMother renderer=new Renderer_Blender_PaletteMother(P_TOY_STORY_ADJUSTED2);
  
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
    Test_Production_PowerBox test=new Test_Production_PowerBox();
    for(int i=0;i<300;i++){
//    for(int i=0;i<303;i++){//a nice looping gif 15 sec
      test.executeJigger(i);
      test.renderComposition();
      test.export(i);
      System.out.println("FRAME#"+i);
      try{
        Thread.sleep(10,0);
      }catch(Exception x){x.printStackTrace();}}
    System.out.println("----end process");
    }

}
