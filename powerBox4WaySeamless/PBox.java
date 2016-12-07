package org.fleen.blanketFlower.powerBox4WaySeamless;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.blanketFlower.renderer.Renderer_Blender_PaletteMother;

/* 
 * just stripes. rectangles.
 * a symmetric square structure of moving stripes
 * a window into this structure. 720p or 1080p or 4k or 100x200 or whatever
 * 
 * a stripe's dance goes like this, for example
 * the stripe starts at an arbitrary location
 * it moves southwards
 * it moves until it passes beyond the viewport, to the south edge of the square
 * when it hits the south edge it teleports to the opposite edge of the square, to the north edge
 * then it keeps moving.
 * we move the stripe a number of movement increments (1,2,4 or whatever) so that the sum of those increments is an
 *   integer multiple of the span of the square. Thus smooth looping is achieved.
 *   
 * we start with a desired viewport. A rectangle
 * we define the box so that stripes that appear and disappear do so outside of view
 * we create all of our stripes at init
 * 
 * ---
 * RESOLUTION
 * 
 * 4k = (3840 x 2160) : 3840/2160 = 1.778
 * 1080p (1920x1080) : 1920/1080= 1.778
 * 720p (1280x720) : 1280/720= 1.778
 * 
 * a good visible cell array rez is 128x128, with 4 pix cells, that's our old power box, which looks good
 * 
 * so our visible cell array (VCA) should be (trying to keep the height around 128)
 *   128x72?
 *   320x180?
 *   
 *   320x180 it is
 *   
 *   at 720p the cells are 4px
 *   at 1080p : 6
 *   at 4k : 12
 * 
 * ---
 * 
 * our background should never be exposed. It's sloppy. 
 * To keep it covered with a seamless row of stripes is probably the way to go
 * WE WILL HAVE 2 SETS OF STRIPES
 *   the base stripes, the seamless course, and the chaos stripes, thrown on at random
 * 
 * ---
 * 
 * max stripe thickness shall be the width of the visible cell array : 320
 * therefor the square span shall be VCA.width+(MAXSTRIPETHICKNESS*2)
 * SQUARESPAN = 960
 * 
 * ---
 * 
 * stripe speeds, small common factors of 320 and 180. 
 *   1,2,4,6.. higher? experiment with that.
 *   
 * ---
 * 
 * It works like this
 * we have a viewport polygon. A rectangle
 * we have a collection of stripe polygons, more rectangles
 * we render cells shared by viewport and stripes
 * we move stripes
 *  
 *   
 *   
 * 
 */
public class PBox{
  
  public static final int 
    //square
    SQUARESPAN=960,
    //base
    BASEWIDTH=320,
    BASEHEIGHT=180,
    //thickness
    MAXSTRIPETHICKNESS=BASEWIDTH,
    //speed
    SPEED_SLOW=1,
    SPEED_MED=2,
    SPEED_FAST=4,
    SPEED_SUPERFAST=8,
    //stripe type
    STRIPETYPE_NORTHWARD=0,
    STRIPETYPE_EASTWARD=1,
    STRIPETYPE_SOUTHWARD=2,
    STRIPETYPE_WESTWARD=3;
  
  Base base=new Base();
 
  public PBox(){
    System.out.println("START");
    initUI();
    createContinuousStripes();
//    createChaosStripes();
    for(int i=0;i<SQUARESPAN;i++){
      System.out.println(i+":"+SQUARESPAN);
      renderToUI();
      //export();
      moveStripes();}
    System.out.println("END");
    
    
  }
  
  List<Stripe> stripes=new ArrayList<Stripe>();
  
  private void moveStripes(){
    for(Stripe stripe:stripes)
      stripe.move();}
  
  /*
   * 4 continuous courses of stripes
   *   speed 1
   *   color 0..3 or thereabouts. totally random?
   * place random thickness first stripe flush with respective edge
   * place next stripe flush with that, and so on, until we run off the edge of the square
   */
  private void createContinuousStripes(){
    
  }
  
  /*
   * a random multiple of speed
   */
  private int getRandomLocation(int speed,Random rnd){
    int a=PBox.SQUARESPAN/speed;//TODO test
    return rnd.nextInt(a)*speed;}
  
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
          ui=new UI(PBox.this,UIWIDTH,UIHEIGHT);
          ui.setVisible(true);
          ui.setTitle(TITLE);
          ui.getContentPane().setBackground(Color.black);
         }catch(Exception e){
           e.printStackTrace();}}});}
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  BufferedImage image;
  Renderer_Blender_PaletteMother renderer=new Renderer_Blender_PaletteMother();
  
  private void renderToUI(){
//    image=renderer.render(composition,CELLSPAN);
    if(ui!=null)
      ui.imagepanel.repaint();}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    new PBox();
  }

}
