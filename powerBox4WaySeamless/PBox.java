package org.fleen.blanketFlower.powerBox4WaySeamless;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
    //base. the sw corner is (0,0)
    BASEWIDTH=320,
    BASEHEIGHT=180;
  
  //stripe thickness
  public static final int[] STRIPETHICKNESS={1,2,4,8,16,32,64,128,256};
    
  public static final int
    //stripe speed
    STRIPESPEED_SLOW=1,
    STRIPESPEED_MED=2,
    STRIPESPEED_FAST=4,
    STRIPESPEED_SUPERFAST=8,
    //stripe type
    STRIPETYPE_NORTHWARD=0,
    STRIPETYPE_EASTWARD=1,
    STRIPETYPE_SOUTHWARD=2,
    STRIPETYPE_WESTWARD=3,
    //square. span and sw corner coors
    REFERENCESQUARESPAN=BASEWIDTH+STRIPETHICKNESS[8],
    REFERENCESQUAREX=-STRIPETHICKNESS[8]/2,
    REFERENCESQUAREY=-(REFERENCESQUARESPAN-BASEHEIGHT)/2,
    //RENDERING
    COLORCOUNT=16;
  
  
  
  ReferenceSquare rsquare=new ReferenceSquare();
  Base base=new Base();
 
  public PBox(){
    System.out.println("START");
    initUI();
    createContinuousStripes();
    createChaosStripes();
    for(int i=0;i<REFERENCESQUARESPAN*44;i++){//TODO should just be SQUARESPAN in production
      System.out.println(i+"/"+REFERENCESQUARESPAN);
      renderToUI();
      export(i);
      moveStripes();}
    System.out.println("END");
    
    
  }
  
  List<Stripe> stripes=new ArrayList<Stripe>();
  
  private void moveStripes(){
    for(Stripe stripe:stripes)
      stripe.move();}
  
  Random rnd=new Random();
  
  /*
   * ################################
   * CREATE CHAOS STRIPES
   * ################################
   */
  
  private void createChaosStripes(){
    
  }
  
  /*
   * a random multiple of speed
   */
  private int getRandomLocation(int speed){
    int a=PBox.REFERENCESQUARESPAN/speed;//TODO test
    return rnd.nextInt(a)*speed;}
  
  /*
   * ################################
   * CREATE CONTINUOUS STRIPES
   * 1) create a random course of stripe thicknesses that sums to slightly more than REFERENCESQUARESPAN
   *   the last size should be a value where using value/2 guies us less than REFERENCESQUARESPAN and
   *   using value gives us greater than REFERENCESQUARESPAN
   *   that is to say, the edge of the reference box would lay in the latter half of the stripe's thickness
   * 2) calculate a random color for each of those stripe thicknesses
   * 3) the rest is trivial 
   *  
   * ################################
   */
  
  /*
   * 4 continuous courses of stripes
   *   speed 1
   *   color 0..3 or thereabouts. totally random?
   * place random thickness first stripe flush with respective edge
   * place next stripe flush with that, and so on, until we run off the edge of the square
   */
  private void createContinuousStripes(){
    List<Integer> thicknesses=getRandomThicknessesForContinuousStripes();
    int stripecount=thicknesses.size();
    List<Integer> 
      colors=getRandomColorsForContinuousStripes(stripecount),
      initprogresses=getInitProgressesForContinuousStripes(thicknesses);
    //do northward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,PBox.STRIPETYPE_NORTHWARD,STRIPETHICKNESS[thicknesses.get(i)],colors.get(i),initprogresses.get(i)));
    //do eastward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,PBox.STRIPETYPE_EASTWARD,STRIPETHICKNESS[thicknesses.get(i)],colors.get(i),initprogresses.get(i)));
    //do southward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,PBox.STRIPETYPE_SOUTHWARD,STRIPETHICKNESS[thicknesses.get(i)],colors.get(i),initprogresses.get(i)));
    //do westward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,PBox.STRIPETYPE_WESTWARD,STRIPETHICKNESS[thicknesses.get(i)],colors.get(i),initprogresses.get(i)));}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GET INIT PROGRESSES FOR CONTINUOUS STRIPES
   * ++++++++++++++++++++++++++++++++
   */
  
  private List<Integer> getInitProgressesForContinuousStripes(List<Integer> thicknesses){
    List<Integer> initprogresses=new ArrayList<Integer>();
    int stripecount=thicknesses.size(),pprior=0,tprior=0,t,p;
    for(int i=0;i<stripecount;i++){
      if(i==0){
        pprior=0;
        tprior=STRIPETHICKNESS[thicknesses.get(0)];
        initprogresses.add(0);
      }else{
        t=STRIPETHICKNESS[thicknesses.get(i)];
        p=pprior+tprior/2+t/2;
        initprogresses.add(p);
        pprior=p;
        tprior=t;}}
    return initprogresses;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GET COLORS FOR CONTINUOUS STRIPES
   * ++++++++++++++++++++++++++++++++
   */
  
  private List<Integer> getRandomColorsForContinuousStripes(int stripecount){
    List<Integer> colors=new ArrayList<Integer>(stripecount);
    for(int i=0;i<stripecount;i++)
      colors.add(rnd.nextInt(COLORCOUNT));
    return colors;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GET THICKNESSES FOR CONTINUOUS STRIPES
   * I have yet to see it take more than 7 tries. Usually gets it in 1.
   * ++++++++++++++++++++++++++++++++
   */
  
  private static final int
    MINCONTINUOUSSTRIPETHICKNESS=5,
    MAXTRIES=100;
  
  /*
   * get a list of integers for which the sum is greater than REFERENCESQUARESPAN
   * and (lastinteger/2)>(sum-REFERENCESQUARESPAN)
   * try it till we get it. it should work in a couple tries  
   * 
   */
  private Thicknesses getRandomThicknessesForContinuousStripes(){
    Thicknesses thicknesses=null;
    int tries=0,lastthicknessvalue;
    while(tries<MAXTRIES){
      tries++;
      thicknesses=new Thicknesses();
      lastthicknessvalue=STRIPETHICKNESS[thicknesses.get(thicknesses.size()-1)];
      if(thicknesses.sum-REFERENCESQUARESPAN<lastthicknessvalue/2){
        System.out.println("got it in "+tries+" tries");
        return thicknesses;}}
    throw new IllegalArgumentException("couldn't get thicknesses");}
  
  @SuppressWarnings("serial")
  private class Thicknesses extends ArrayList<Integer>{
    
    int sum=0;
    
    Thicknesses(){
      int newthickness=0;
      while(sum<REFERENCESQUARESPAN){
        newthickness=rnd.nextInt(STRIPETHICKNESS.length-MINCONTINUOUSSTRIPETHICKNESS)+MINCONTINUOUSSTRIPETHICKNESS;
        add(newthickness);
        sum+=STRIPETHICKNESS[newthickness];}}}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  static final String TITLE="blanketflower";
  static final int UIWIDTH=1500,UIHEIGHT=1010;
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
  
  private static final int UICELLSPAN=2;
  BufferedImage image;
  Renderer renderer;
  
  private void renderToUI(){
    Renderer renderer=getRenderer();
    image=renderer.render(UICELLSPAN);//TODO
    if(ui!=null)
      ui.imagepanel.repaint();}
  
  private Renderer getRenderer(){
    if(renderer==null)
      renderer=new Renderer_Production(this);
//      renderer=new Renderer_Test(this);
    return renderer;}
  
  /*
   * ################################
   * EXPORT
   * ################################
   */
  
  static final String EXPORTDIR="/home/john/Desktop/bfexport";
  
  RasterExporter rasterexporter=new RasterExporter(new File(EXPORTDIR));
  
  void export(int index){
    System.out.println("export");
    BufferedImage exportimage=renderer.render(UICELLSPAN);
    rasterexporter.export(exportimage,index);}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    new PBox();
  }

}
