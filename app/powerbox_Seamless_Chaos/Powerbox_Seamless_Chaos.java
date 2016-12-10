package org.fleen.blanketFlower.app.powerbox_Seamless_Chaos;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.renderer.Renderer;
import org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.renderer.Renderer_Production;
import org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.stripeSystem.Base;
import org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.stripeSystem.ReferenceSquare;
import org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.stripeSystem.Stripe;

public class Powerbox_Seamless_Chaos{
  
  //FINE SYSTEM
  public static final int 
    //base. the sw corner is (0,0)
    BASEWIDTH=320,
    BASEHEIGHT=180;
  public static final int[]
    //continuous stripe course thicknesses
//      STRIPETHICKNESS={128,192,256};//excitement level 0
//      STRIPETHICKNESS={96,128,192,256};//excitement level 1
      STRIPETHICKNESS={64,96,128,192,256};//excitement level 2
//      STRIPETHICKNESS={32,64,96,128,192,256};//excitement level 3
  
  
    //COARSE SYSTEM
//  BASEWIDTH=160,
//  BASEHEIGHT=90;
//STRIPETHICKNESS={96,112,128};//excitement level 0
//STRIPETHICKNESS={80,96,112,128};//excitement level 1
//STRIPETHICKNESS={64,80,96,112,128};//excitement level 2
//STRIPETHICKNESS={48,64,80,96,112,128};//excitement level 3
  
  
  public static final int[]
    //stripe speed
    STRIPESPEED={1,2,4};
  
    
  public static final int
    //stripe type
    STRIPETYPE_NORTHWARD=0,
    STRIPETYPE_EASTWARD=1,
    STRIPETYPE_SOUTHWARD=2,
    STRIPETYPE_WESTWARD=3,
    //square. span and sw corner coors
    REFERENCESQUARESPAN=BASEWIDTH+STRIPETHICKNESS[STRIPETHICKNESS.length-1],
    REFERENCESQUAREX=-STRIPETHICKNESS[STRIPETHICKNESS.length-1]/2,
    REFERENCESQUAREY=-(REFERENCESQUARESPAN-BASEHEIGHT)/2,
    //RENDERING
    //we have 16 colors but our stripes only use the first few colors, to reduce noise
    //higher colors are achieved via stripe overlap blending summation
    //TODO  these should be params, stripe color range should be calculated
    STRIPECOLORRANGE=3,
    COLORCOUNT=16;
  
  
  
  public ReferenceSquare rsquare=new ReferenceSquare();
  public Base base=new Base();
 
  public Powerbox_Seamless_Chaos(){
    System.out.println("START");
    initUI();
    //3 systems of continuous stripes
    createStripes_Speed0();
    createStripes_Speed1();
    createStripes_Speed2();
    for(int i=0;i<REFERENCESQUARESPAN;i++){
      System.out.println((i+1)+"/"+REFERENCESQUARESPAN);
      renderToUI();
      export(i);
      moveStripes();}
    System.out.println("END");}
  
  public List<Stripe> stripes=new ArrayList<Stripe>();
  
  private void moveStripes(){
    for(Stripe stripe:stripes)
      stripe.move();}
  
  Random rnd=new Random();
  
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
  private void createStripes_Speed1(){
    List<Integer> thicknesses=getRandomThicknessesForContinuousStripes();
    int stripecount=thicknesses.size();
    List<Integer> 
      colors=getRandomColorsForContinuousStripes(stripecount),
      initprogresses=getInitProgressesForContinuousStripes(thicknesses);
    //do northward
//    (PBox pbox,int type,int thickness,int speed,int color,int progress)
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,STRIPETYPE_NORTHWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[1],colors.get(i),initprogresses.get(i)));
    //do eastward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,STRIPETYPE_EASTWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[1],colors.get(i),initprogresses.get(i)));
    //do southward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,STRIPETYPE_SOUTHWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[1],colors.get(i),initprogresses.get(i)));
    //do westward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,STRIPETYPE_WESTWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[1],colors.get(i),initprogresses.get(i)));}
  
  private void createStripes_Speed0(){
    List<Integer> thicknesses=getRandomThicknessesForContinuousStripes();
    int stripecount=thicknesses.size();
    List<Integer> 
      colors=getRandomColorsForContinuousStripes(stripecount),
      initprogresses=getInitProgressesForContinuousStripes(thicknesses);
    //do northward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,Powerbox_Seamless_Chaos.STRIPETYPE_NORTHWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[0],colors.get(i),initprogresses.get(i)));
    //do eastward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,Powerbox_Seamless_Chaos.STRIPETYPE_EASTWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[0],colors.get(i),initprogresses.get(i)));
    //do southward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,Powerbox_Seamless_Chaos.STRIPETYPE_SOUTHWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[0],colors.get(i),initprogresses.get(i)));
    //do westward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,Powerbox_Seamless_Chaos.STRIPETYPE_WESTWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[0],colors.get(i),initprogresses.get(i)));}
  
  private void createStripes_Speed2(){
    List<Integer> thicknesses=getRandomThicknessesForContinuousStripes();
    int stripecount=thicknesses.size();
    List<Integer> 
      colors=getRandomColorsForContinuousStripes(stripecount),
      initprogresses=getInitProgressesForContinuousStripes(thicknesses);
    //do northward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,Powerbox_Seamless_Chaos.STRIPETYPE_NORTHWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[2],colors.get(i),initprogresses.get(i)));
    //do eastward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,Powerbox_Seamless_Chaos.STRIPETYPE_EASTWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[2],colors.get(i),initprogresses.get(i)));
    //do southward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,Powerbox_Seamless_Chaos.STRIPETYPE_SOUTHWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[2],colors.get(i),initprogresses.get(i)));
    //do westward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,Powerbox_Seamless_Chaos.STRIPETYPE_WESTWARD,STRIPETHICKNESS[thicknesses.get(i)],STRIPESPEED[2],colors.get(i),initprogresses.get(i)));}
  
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
      colors.add(rnd.nextInt(STRIPECOLORRANGE));
    return colors;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GET THICKNESSES FOR CONTINUOUS STRIPES
   * I have yet to see it take more than 7 tries. Usually gets it in 1.
   * ++++++++++++++++++++++++++++++++
   */
  
  private static final int
//    MINCONTINUOUSSTRIPETHICKNESS=2,
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
        newthickness=rnd.nextInt(STRIPETHICKNESS.length);
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
          ui=new UI(Powerbox_Seamless_Chaos.this,UIWIDTH,UIHEIGHT);
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
  
  static final int EXPORTCELLSPAN=4;//720p
  //TODO we will export to 3 dirs at the same time : 720p,1080p,4k
  
  static final String EXPORTDIR="/home/john/Desktop/bfexport";
  
  RasterExporter rasterexporter=new RasterExporter(new File(EXPORTDIR));
  
  void export(int index){
    System.out.println("export");
    BufferedImage exportimage=renderer.render(EXPORTCELLSPAN);
    rasterexporter.export(exportimage,index);}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    new Powerbox_Seamless_Chaos();}

}
