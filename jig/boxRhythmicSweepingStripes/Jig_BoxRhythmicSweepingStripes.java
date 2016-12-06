package org.fleen.blanketFlower.jig.boxRhythmicSweepingStripes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.geom_Boxy.GB;
import org.fleen.blanketFlower.jig.Jig;

/*
 * a haze of static
 * given the target
 * clear existing dots
 *   a dot is a 1-square shape
 * create new dots
 */
public class Jig_BoxRhythmicSweepingStripes implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_BoxRhythmicSweepingStripes(BShape target){
    setTarget(target);}
  
  public Jig_BoxRhythmicSweepingStripes(){}
  
  /*
   * ################################
   * CLONE
   * ################################
   */
  
  public Object clone(){
    return new Jig_BoxRhythmicSweepingStripes();}
  
  /*
   * ################################
   * TARGET
   * ################################
   */
  
  BShape target;
  
  public void setTarget(BShape target){
    this.target=target;}

  public BShape getTarget(){
    return target;}

  /*
   * ################################
   * EXECUTE
   * ################################
   */
  
  public void execute(int frameindex){
    create4Stripes(frameindex);
    moveStripes();
    setExitStripesForCull();
    cullExitStripesAndShapes();
    updateShapeLocations();
    createNewShapes();}
  
  /*
   * ################################
   * STRIPES AND SHAPES
   * ################################
   */
  
  List<Stripe> stripes=new ArrayList<Stripe>();
  Map<Stripe,BShape> shapebystripe=new HashMap<Stripe,BShape>();
  
  /*
   * ################################
   * CREATE STRIPE
   * ################################
   */
  
//  private static final double STRIPECREATIONPROBABILITY=0.05;//TODO should be param
  private static final double STRIPECREATIONPROBABILITY=0.06;//TODO should be param
  Random rnd=new Random();
  
  private static final boolean
    ORIENTATION_STRIPE_N=Stripe.STRIPEORIENTATION_HORIZONTAL,
    ORIENTATION_STRIPE_E=Stripe.STRIPEORIENTATION_VERTICAL,
    ORIENTATION_STRIPE_S=Stripe.STRIPEORIENTATION_HORIZONTAL,
    ORIENTATION_STRIPE_W=Stripe.STRIPEORIENTATION_VERTICAL;
  private static final int
    COURSE_STRIPE_N=GB.DIR_NORTH,
    COURSE_STRIPE_E=GB.DIR_EAST,
    COURSE_STRIPE_S=GB.DIR_SOUTH,
    COURSE_STRIPE_W=GB.DIR_WEST;
  
  private void create4Stripes(int frameindex){
    if(rnd.nextDouble()>STRIPECREATIONPROBABILITY&&stripes.size()>1)return;
//    if(frameindex%16!=0)return;
    int 
      thickness=getThicknessForNewStripe(),
      speed=getSpeedForNewStripe();
    createStripe(ORIENTATION_STRIPE_N,thickness,COURSE_STRIPE_N,speed);
    createStripe(ORIENTATION_STRIPE_E,thickness,COURSE_STRIPE_E,speed);
    createStripe(ORIENTATION_STRIPE_S,thickness,COURSE_STRIPE_S,speed);
    createStripe(ORIENTATION_STRIPE_W,thickness,COURSE_STRIPE_W,speed);}
  
  /*
   * when we create a stripe we create 4 stripes
   */
  private void createStripe(boolean orientation,int thickness,int course,int speed){
    Stripe stripe=new Stripe(orientation,thickness,course,speed);
    initOffsetsForNewStripe(stripe);
    stripes.add(stripe);}
  
  private static final int 
    STRIPETHICKNESS_INCREMENT=16,
    MAXSTRIPETHICKNESS=4;
  
  private int getThicknessForNewStripe(){
    int thickness=rnd.nextInt(MAXSTRIPETHICKNESS)*STRIPETHICKNESS_INCREMENT+STRIPETHICKNESS_INCREMENT;
    return thickness;}
  
  private int getSpeedForNewStripe(){
    int speed=rnd.nextInt(3)+1;
    return speed;}
  
  private void initOffsetsForNewStripe(Stripe stripe){
    if(stripe.course==GB.DIR_NORTH)
      initNortherlyStripeOffsets(stripe);
    else if(stripe.course==GB.DIR_EAST)
      initEasterlyStripeOffsets(stripe);
    else if(stripe.course==GB.DIR_SOUTH)
      initSoutherlyStripeOffsets(stripe);
    else//if(stripe.course==GB.DIR_WEST)
      initWesterlyStripeOffsets(stripe);}
  
  private void initNortherlyStripeOffsets(Stripe stripe){
    stripe.offsety=-stripe.thickness;
    stripe.offsetx=0;}
  
  private void initEasterlyStripeOffsets(Stripe stripe){
    stripe.offsety=0;
    stripe.offsetx=-stripe.thickness;}
  
  private void initSoutherlyStripeOffsets(Stripe stripe){
    stripe.offsety=target.getHeight();
    stripe.offsetx=0;}
  
  private void initWesterlyStripeOffsets(Stripe stripe){
    stripe.offsety=0;
    stripe.offsetx=target.getWidth();}
  
  /*
   * ################################
   * MOVE STRIPES
   * ################################
   */
  
  private void moveStripes(){
    for(Stripe stripe:stripes)
      if(stripe.course==GB.DIR_NORTH)
        moveNortherlyStripe(stripe);
      else if(stripe.course==GB.DIR_EAST)
        moveEasterlyStripe(stripe);
      else if(stripe.course==GB.DIR_SOUTH)
        moveSoutherlyStripe(stripe);
      else//if(stripe.course==GB.DIR_WEST)
        moveWesterlyStripe(stripe);}
  
  private void moveNortherlyStripe(Stripe stripe){
    stripe.offsety+=stripe.speed;}
  
  private void moveEasterlyStripe(Stripe stripe){
    stripe.offsetx+=stripe.speed;}
  
  private void moveSoutherlyStripe(Stripe stripe){
    stripe.offsety-=stripe.speed;}
  
  private void moveWesterlyStripe(Stripe stripe){
    stripe.offsetx-=stripe.speed;}
  
  /*
   * ################################
   * SET EXIT STRIPES FOR CULL
   * ################################
   */
  
  private void setExitStripesForCull(){
    for(Stripe stripe:stripes)
      if(isExit_Northerly(stripe)||isExit_Easterly(stripe)||isExit_Southerly(stripe)||isExit_Westerly(stripe))
        stripe.cull=true;}
  
  private boolean isExit_Northerly(Stripe stripe){
    if(stripe.course!=GB.DIR_NORTH)return false;
    return stripe.offsety>target.getHeight();}
  
  private boolean isExit_Easterly(Stripe stripe){
    if(stripe.course!=GB.DIR_EAST)return false;
    return stripe.offsetx>target.getWidth();}
  
  private boolean isExit_Southerly(Stripe stripe){
    if(stripe.course!=GB.DIR_SOUTH)return false;
    return stripe.offsety<-stripe.thickness;}
  
  private boolean isExit_Westerly(Stripe stripe){
    if(stripe.course!=GB.DIR_WEST)return false;
    return stripe.offsetx<-stripe.thickness;}
  
  /*
   * ################################
   * CULL EXIT STRIPES AND SHAPES
   * ################################
   */
  
  /*
   * if the stripe is marked for cull then remove it from the stripes list
   * remove its corrosponding shape too
   */
  private void cullExitStripesAndShapes(){
    Iterator<Stripe> i=stripes.iterator();
    Stripe stripe;
    BShape shape;
    while(i.hasNext()){
      stripe=i.next();
      if(stripe.cull){
        i.remove();
        shape=shapebystripe.get(stripe);
        disconnectShape(shape);
        shapebystripe.remove(stripe);}}}
  
  private void disconnectShape(BShape shape){
    target.removeChild(shape);
    shape.setParent(null);}
  
  /*
   * ################################
   * UPDATE SHAPE LOCATION VIA STRIPE
   * ################################
   */
  
  private void updateShapeLocations(){
    BPolygon shape;
    for(Stripe stripe:stripes){
      shape=(BPolygon)shapebystripe.get(stripe);
      if(shape!=null)
        updateShapeLocation(stripe,shape);}}
  
  private void updateShapeLocation(Stripe stripe,BPolygon shape){
    int 
      refvertexx=target.getWestBound(),
      refvertexy=target.getSouthBound(),
      v0x=refvertexx+stripe.offsetx,
      v0y=refvertexy+stripe.offsety,
      v1x,v1y,v2x,v2y,v3x,v3y;
    if(stripe.orientation==Stripe.STRIPEORIENTATION_VERTICAL){
      v1x=v0x;
      v1y=v0y+target.getHeight();
      v2x=v1x+stripe.thickness;
      v2y=v1y;
      v3x=v2x;
      v3y=v0y;
    }else{ //stripe.orientation==STRIPEORIENTATION_HORIZONTAL)
      v1x=v0x;
      v1y=v0y+stripe.thickness;
      v2x=v1x+target.getWidth();
      v2y=v1y;
      v3x=v2x;
      v3y=v0y;}
    BVertex v=shape.vertices.get(0);
    v.x=v0x;
    v.y=v0y;
    v=shape.vertices.get(1);
    v.x=v1x;
    v.y=v1y;
    v=shape.vertices.get(2);
    v.x=v2x;
    v.y=v2y;
    v=shape.vertices.get(3);
    v.x=v3x;
    v.y=v3y;}
  
  
  /*
   * ################################
   * CREATE NEW SHAPES FOR STRIPES THAT LACK A CORROSPONDING SHAPE
   * ################################
   */
  
  /*
   * for each target child shape
   *   if that shape's corrosponding stripe had the cull flag set to true then 
   *   remove the stripe from the stripes list and remove the corrosponding shape from the target parent.
   *   this will trim a whole branch from the composition shape tree, of course.
   */
  
  private void createNewShapes(){
    for(Stripe stripe:stripes)
      if(!shapebystripe.containsKey(stripe))
        createShape(stripe);}
  
  private void createShape(Stripe stripe){
    BShape shape=new BPolygon(new BVertex(),new BVertex(),new BVertex(),new BVertex());
    shapebystripe.put(stripe,shape);
    shape.setChorusIndex(0);
    shape.setColorIndex(1);
    shape.setTags("stripe");
    shape.setParent(target);
    target.addChild(shape);
    updateShapeLocation(stripe,(BPolygon)shape);}
  
  
}
