package org.fleen.blanketFlower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.fleen.blanketFlower.gSquid.GSquid;
import org.fleen.blanketFlower.gSquid.SPolygon;
import org.fleen.blanketFlower.gSquid.SShape;
import org.fleen.blanketFlower.gSquid.SVertex;

/*
 * at execute
 * if there is no stripe rectangle then create one at random location
 * 
 * put a rectangle at random location inside target
 * 
 */
public class Jig_MovingStripes_4way implements Jig{

  /*
   * ################################
   * EXECUTE
   * ################################
   */
  
  List<Stripe> stripes=new ArrayList<Stripe>();
  Random rnd=new Random();
  
  /*
   * only works on rectangular polygons
   */
  public void execute(){
    System.out.println("stripecount="+stripes.size());
    createStripe();
    moveStripes();
    removeOffStripes();}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * MOVE STRIPES
   * ++++++++++++++++++++++++++++++++
   */
  
  private void moveStripes(){
    for(Stripe stripe:stripes)
      stripe.move(stripe.dir,stripe.speed);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * REMOVE OFF STRIPE
   * when the stripe passes off of the target area, remove it
   * ++++++++++++++++++++++++++++++++
   */
  
  private void removeOffStripes(){
    if(stripes.isEmpty())return;
    Iterator<Stripe> i=stripes.iterator();
    Stripe stripe;
    while(i.hasNext()){
      stripe=i.next();
      if(isOff(stripe)){
        target.removeChild(stripe);
        stripe.setParent(null);
        i.remove();}}}
  
  private boolean isOff(Stripe stripe){
    int tb,sb;//test target bound against stripe bound
    if(stripe.dir==GSquid.DIR_NORTH){
      tb=target.getNorthBound();
      sb=stripe.getSouthBound();
      if(sb>tb)return true;
    }else if(stripe.dir==GSquid.DIR_EAST){
      tb=target.getEastBound();
      sb=stripe.getWestBound();
      if(sb>tb)return true;
    }else if(stripe.dir==GSquid.DIR_SOUTH){
      tb=target.getSouthBound();
      sb=stripe.getNorthBound();
      if(sb<tb)return true;
    }else{//stripe.dir==GSquid.DIR_WEST
      tb=target.getWestBound();
      sb=stripe.getEastBound();
      if(sb<tb)return true;}
    return false;}
    
  
  /*
   * ++++++++++++++++++++++++++++++++
   * CREATE STRIPE
   * ++++++++++++++++++++++++++++++++
   */
  
  static final double CREATENEWSTRIPEPROBABILITY=0.2;
  
  private static final int 
    STRIPEWIDTH_MIN=6,
    STRIPEWIDTH_MAX=30;

  void createStripe(){
    //will we create a new stripe?
    if(rnd.nextDouble()>CREATENEWSTRIPEPROBABILITY)return;//no
    //yes
    //get random width, direction and speed 
    int 
      width=rnd.nextInt(STRIPEWIDTH_MAX-STRIPEWIDTH_MIN)+STRIPEWIDTH_MIN,
      speed=rnd.nextInt(3)+1,//TODO curve this
      dir=rnd.nextInt(4);//1 of the 4 directions  
    Stripe stripe;
    if(dir==GSquid.DIR_NORTH){
      stripe=createStripe_North(width,speed);
    }else if(dir==GSquid.DIR_EAST){
      stripe=createStripe_East(width,speed);
    }else if(dir==GSquid.DIR_SOUTH){
      stripe=createStripe_South(width,speed);
    }else{//dir==GSquid.DIR_WEST
      stripe=createStripe_West(width,speed);}  
    target.addChild(stripe);
    stripe.setParent(target);
    stripes.add(stripe);}
  
  //create it just over the south edge of the target
  Stripe createStripe_North(int width,int speed){
    int[] tb=target.getBounds();//xmin,ymin,xmax,ymax
    int north=0,east=tb[2],south=-width,west=0;
    SVertex[] vertices={ 
      new SVertex(west,south),
      new SVertex(west,north),
      new SVertex(east,north),
      new SVertex(east,south)};
    Stripe stripe=new Stripe(Arrays.asList(vertices),rnd.nextInt(3),null,GSquid.DIR_NORTH,speed);
    return stripe;}
  
  //create it just over the west edge of the target
  Stripe createStripe_East(int width,int speed){
    int[] tb=target.getBounds();//xmin,ymin,xmax,ymax
    int north=tb[3],east=0,south=0,west=-width;
    SVertex[] vertices={ 
      new SVertex(west,south),
      new SVertex(west,north),
      new SVertex(east,north),
      new SVertex(east,south)};
    Stripe stripe=new Stripe(Arrays.asList(vertices),rnd.nextInt(3),null,GSquid.DIR_EAST,speed);
    return stripe;}
  
  //create it just over the east edge of the target
  Stripe createStripe_West(int width,int speed){
    int[] tb=target.getBounds();//xmin,ymin,xmax,ymax
    int north=tb[3],east=tb[2]+width,south=0,west=tb[2];
    SVertex[] vertices={ 
      new SVertex(west,south),
      new SVertex(west,north),
      new SVertex(east,north),
      new SVertex(east,south)};
    Stripe stripe=new Stripe(Arrays.asList(vertices),rnd.nextInt(3),null,GSquid.DIR_WEST,speed);
    return stripe;}
  
  //create it just over the north edge of the target
  Stripe createStripe_South(int width,int speed){
    int[] tb=target.getBounds();//xmin,ymin,xmax,ymax
    int north=tb[3]+width,east=tb[2],south=tb[3],west=0;
    SVertex[] vertices={ 
      new SVertex(west,south),
      new SVertex(west,north),
      new SVertex(east,north),
      new SVertex(east,south)};
    Stripe stripe=new Stripe(Arrays.asList(vertices),rnd.nextInt(3),null,GSquid.DIR_SOUTH,speed);
    return stripe;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * STRIPE CLASS
   * speed refers to the number of cells the stripe moves at every cycle. 1, 2 or 3, generally
   * dir refers to the direction that the stripe moves
   *   ex : NORTH means the stripe starts at the south edge, runs parallel to the east-west axis, and moves north
   * ++++++++++++++++++++++++++++++++
   */
  
  @SuppressWarnings("serial")
  class Stripe extends SPolygon{

    public Stripe(List<SVertex> vertices,int chorusindex,List<String> tags,int dir,int speed){
      super(vertices,chorusindex,tags);
      this.dir=dir;
      this.speed=speed;
    }
    
    int dir,speed;
    
  }
  

  /*
   * ################################
   * DUPE
   * ################################
   */
  
  public Jig dupe(){
    return new Jig_MovingStripes_4way();}
  
  /*
   * ################################
   * TARGET
   * ################################
   */
  
  SShape target;  

  public void setTarget(SShape target){
    this.target=target;}
  
  public SShape getTarget(){
    return target;}

}
