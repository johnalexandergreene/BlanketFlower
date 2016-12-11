package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.stripeSystem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
 *   
 *   160x90?
 *   320x180?
 *   
 *   given 320x180
 *   at 720p the cells are 4px
 *   at 1080p : 6
 *   at 4k : 12
 *   
 *   given 160x90
 *   720p:8px
 *   1080p:12px
 *   4k:24px
 *   
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
 */
public class StripeSystem{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################        
   */
  
  public StripeSystem(int resolution,int complexity,int excitement,int palettesize){
    this.resolution=resolution;
    this.complexity=complexity;
    this.excitement=excitement;
    initPalette(palettesize);}
  
  public StripeSystem(int resolution,int complexity,int excitement,Color[] palette){
    this.resolution=resolution;
    this.complexity=complexity;
    this.excitement=excitement;
    initPalette(palette);}
  
  /*
   * ################################
   * GEOMETRY
   * The resolution of this system depends on the dimensions of the base
   *   The base is the visible rectangle. A block of cells. The thing you look at.
   * We have 2 resolutions, high and low
   * ################################        
   */
  
  /*
   * stripe speeds
   * all are factors of all stripe thicknesses, so the moving stripe always hits the edge of the square exactly
   */
  public static final int[]
    STRIPESPEED={1,2,4};
  
  public static final int
    //stripe type
    STRIPETYPE_NORTHWARD=0,
    STRIPETYPE_EASTWARD=1,
    STRIPETYPE_SOUTHWARD=2,
    STRIPETYPE_WESTWARD=3;
  
  public static final int 
    REZ_HI=0,
    REZ_LO=1;
  
  //++++++++++++++++++++++++++++++++
  //PARAMS FOR HIGH RESOLUTION STRIPE SYSTEM
  public static final int 
    BASEWIDTH_HI=320,
    BASEHEIGHT_HI=180;
  
  public static final int[]
    STRIPETHICKNESS_HI_X0={128,192,256},//excitement level 0
    STRIPETHICKNESS_HI_X1={96,128,192,256},//excitement level 1
    STRIPETHICKNESS_HI_X2={64,96,128,192,256},//excitement level 2
    STRIPETHICKNESS_HI_X3={32,64,96,128,192,256};//excitement level 3
  
  //pixel sizes, ie scale factors, for various renderings of the base rectangle
  public static final int
    UIPIXEL_HI=4,
    EXPORTPIXEL_HI_720P=4,
    EXPORTPIXEL_HI_1080P=6,
    EXPORTPIXEL_HI_4K=12;
  
  //++++++++++++++++++++++++++++++++
  //PARAMS FOR LOW RESOLUTION STRIPE SYSTEM
  public static final int 
    BASEWIDTH_LO=160,
    BASEHEIGHT_LO=90;
    
  public static final int[]
    STRIPETHICKNESS_LO_X0={96,112,128},//excitement level 0
    STRIPETHICKNESS_LO_X1={80,96,112,128},//excitement level 1
    STRIPETHICKNESS_LO_X2={64,80,96,112,128},//excitement level 2
    STRIPETHICKNESS_LO_X3={48,64,80,96,112,128};//excitement level 3
  
  //pixel sizes, ie scale factors, for various renderings of the base rectangle
  public static final int
    UIPIXEL_LO=8,
    EXPORTPIXEL_LO_720P=8,
    EXPORTPIXEL_LO_1080P=12,
    EXPORTPIXEL_LO_4K=24;
  
  //++++++++++++++++++++++++++++++++
  
  public int 
    resolution,
    excitement,
    complexity;
  
  public int[] getBaseDimensions(){
    if(resolution==REZ_HI){
      return new int[]{BASEWIDTH_HI,BASEHEIGHT_HI};
    }else{//resolution==REZ_LO
      return new int[]{BASEWIDTH_LO,BASEHEIGHT_LO};}}
  
  public int[] getStripeThicknessArray(){
    if(resolution==REZ_HI){
      switch(excitement){
      case 0:return STRIPETHICKNESS_LO_X0;
      case 1:return STRIPETHICKNESS_LO_X1;
      case 2:return STRIPETHICKNESS_LO_X2;
      case 3:return STRIPETHICKNESS_LO_X3;
      default:throw new IllegalArgumentException("invalid excitement:"+excitement);}
    }else{//resolution==REZ_LO
      switch(excitement){
      case 0:return STRIPETHICKNESS_HI_X0;
      case 1:return STRIPETHICKNESS_HI_X1;
      case 2:return STRIPETHICKNESS_HI_X2;
      case 3:return STRIPETHICKNESS_HI_X3;
      default:throw new IllegalArgumentException("invalid excitement:"+excitement);}}}
   
  //++++++++++++++++++++++++++++++++
  //BASE
  //the rectangle across which the stripe rectangles flow
  //the unscaled viewport
  //the power box has 1 of these
  
  private Base base=null;
  
  public Base getBase(){
    if(base==null)initBase();
    return base;}
  
  private void initBase(){
    int[] d=getBaseDimensions();
    base=new Base(0,0,d[0],d[1]);}
 
  
  //++++++++++++++++++++++++++++++++
  //REFERENCE SQUARE
  //square. span and sw corner coors
  //this defines the limits of our system. The square contains the base.
  
  private ReferenceSquare referencesquare=null;
  
  public ReferenceSquare getReferenceSquare(){
    if(referencesquare==null)
      initReferenceSquare();
    return referencesquare;}
  
  private void initReferenceSquare(){
    int[] 
      d=getBaseDimensions(),
      t=getStripeThicknessArray();
    int maxstripethickness=t[t.length-1];
    //the span is the width of the base + the greatest stripe thickness
    int squarespan=d[0]+maxstripethickness;
    //the sw referencesquare corner x is half the greatest stripe thickness to the 
    //west of the base rectangle sw corner, which is (0,0)
    int squarex=-maxstripethickness/2;
    //the sw referencesquare corner y is 
    int squarey=-(squarespan-d[1])/2;
    referencesquare=new ReferenceSquare(squarex,squarey,squarespan);}
  
  /*
   * ################################
   * COLOR
   * ################################        
   */
  
  private Color[] palette;
  
  public int getPaletteSize(){
    return palette.length;}
  
  public Color[] getPalette(){
    return palette;}
   
  private void initPalette(Color[] palette){
    this.palette=palette;}
  
  //PALETTE MOTHER
  void initPalette(int palettesize){
    Set<Color> a=new HashSet<Color>();
    Color c;
    Random rnd=new Random();
    for(int i=0;i<palettesize;i++){
      c=new Color(64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16);
      a.add(c);}
    palette=a.toArray(new Color[a.size()]);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * INIT STRIPE COLOR RANGE
   * For good graphics we limit the init color of stripes to only a few colors
   * other colors in the palette (accents) are achieved via overlapping stripe summative blending
   * ++++++++++++++++++++++++++++++++
   */
  
  private static final int INITSTRIPECOLORRANGEDEFAULT=3;
  
  private int getStripeInitColorRange(){
    int s=getPaletteSize();
    if(s>INITSTRIPECOLORRANGEDEFAULT)return INITSTRIPECOLORRANGEDEFAULT;
    if(s>2)return s-1;
    return s;}
  
  /*
   * ################################
   * STRIPES
   * If there are no stripes then create them
   * move the stripes
   * in our seamless system we move the stripes a number of increments equal to the reference square span
   * so every time we invoke move we increment a counter
   * we return a boolean, are we done or not?
   * ################################
   */
  
  public List<Stripe> stripes=null;
  public int movecount,movelimit;
  
  public boolean move(){
    if(stripes==null){
      createStripes();
      movecount=0;
      movelimit=getReferenceSquare().span;}
    moveStripes();
    movecount++;
    return movecount==movelimit;}
  
  private void moveStripes(){
    for(Stripe stripe:stripes)
      stripe.move();}
 
  /*
   * ################################
   * CREATE STRIPES
   * 
   * Create 1,2 or 3 a continuous courses of stripes, depending on the complexity param
   * 
   * 1) create a random course of stripe thicknesses that sums to slightly more than REFERENCESQUARESPAN
   *   the last size should be a value where using value/2 guies us less than REFERENCESQUARESPAN and
   *   using value gives us greater than REFERENCESQUARESPAN
   *   that is to say, the edge of the reference box would lay in the latter half of the stripe's thickness
   * 2) calculate a random color for each of those stripe thicknesses
   * 3) the rest is trivial 
   *  
   * ################################
   */

  private Random rnd=new Random();
  
  private void createStripes(){
    stripes=new ArrayList<Stripe>();
    if(complexity==0){
      createStripes(STRIPESPEED[0]);
    }else if(complexity==1){
      createStripes(STRIPESPEED[0]);
      createStripes(STRIPESPEED[1]);
    }else{//complexity==2
      createStripes(STRIPESPEED[0]);
      createStripes(STRIPESPEED[1]);
      createStripes(STRIPESPEED[2]);}}
  
  /*
   * 4 continuous courses of stripes
   * place random thickness first stripe flush with respective edge
   * place next stripe flush with that, and so on, until we run off the edge of the square
   */
  private void createStripes(int speed){
    List<Integer> thicknesses=getRandomThicknessesForContinuousStripes();
    int stripecount=thicknesses.size();
    List<Integer> 
      colors=getRandomColorsForContinuousStripes(stripecount),
      initprogresses=getInitProgressesForContinuousStripes(thicknesses);
    int[] stripethicknessarray=getStripeThicknessArray();
    //do northward
//    (PBox pbox,int type,int thickness,int speed,int color,int progress)
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,STRIPETYPE_NORTHWARD,stripethicknessarray[thicknesses.get(i)],speed,colors.get(i),initprogresses.get(i)));
    //do eastward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,STRIPETYPE_EASTWARD,stripethicknessarray[thicknesses.get(i)],speed,colors.get(i),initprogresses.get(i)));
    //do southward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,STRIPETYPE_SOUTHWARD,stripethicknessarray[thicknesses.get(i)],speed,colors.get(i),initprogresses.get(i)));
    //do westward
    for(int i=0;i<stripecount;i++)
      stripes.add(new Stripe(this,STRIPETYPE_WESTWARD,stripethicknessarray[thicknesses.get(i)],speed,colors.get(i),initprogresses.get(i)));}
  /*
   * ++++++++++++++++++++++++++++++++
   * GET INIT PROGRESSES FOR CONTINUOUS STRIPES
   * ++++++++++++++++++++++++++++++++
   */
  
  private List<Integer> getInitProgressesForContinuousStripes(List<Integer> thicknesses){
    List<Integer> initprogresses=new ArrayList<Integer>();
    int[] stripethicknessarray=getStripeThicknessArray();
    int stripecount=thicknesses.size(),pprior=0,tprior=0,t,p;
    for(int i=0;i<stripecount;i++){
      if(i==0){
        pprior=0;
        tprior=stripethicknessarray[thicknesses.get(0)];
        initprogresses.add(0);
      }else{
        t=stripethicknessarray[thicknesses.get(i)];
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
      colors.add(rnd.nextInt(getStripeInitColorRange()));
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
    ReferenceSquare rsquare=getReferenceSquare();
    int[] stripethicknessarray=getStripeThicknessArray();
    Thicknesses thicknesses=null;
    int tries=0,lastthicknessvalue;
    while(tries<MAXTRIES){
      tries++;
      thicknesses=new Thicknesses();
      lastthicknessvalue=stripethicknessarray[thicknesses.get(thicknesses.size()-1)];
      if(thicknesses.sum-rsquare.span<lastthicknessvalue/2){
        System.out.println("got it in "+tries+" tries");
        return thicknesses;}}
    throw new IllegalArgumentException("couldn't get thicknesses");}
  
  @SuppressWarnings("serial")
  private class Thicknesses extends ArrayList<Integer>{
    
    int sum=0;
    
    Thicknesses(){
      int[] stripethicknessarray=getStripeThicknessArray();
      ReferenceSquare rsquare=getReferenceSquare();
      int newthickness=0;
      while(sum<rsquare.span){
        newthickness=rnd.nextInt(stripethicknessarray.length);
        add(newthickness);
        sum+=stripethicknessarray[newthickness];}}}
  
}
