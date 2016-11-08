package org.fleen.blanketFlower.jig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.blanketFlower.geom_Boxy.GB;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BVertex;

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
    createStripe();
    moveStripes();
    removeOffStripes();}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * MOVE STRIPES
   * ++++++++++++++++++++++++++++++++
   */
  
  private void moveStripes(){
    for(Stripe stripe:stripes){
      stripe.offset+=stripe.speed;
      stripe.updateLocation();}}
  
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
    if(stripe.dir==GB.DIR_NORTH){
      tb=target.getNorthBound();
      sb=stripe.getSouthBound();
      if(sb>tb)return true;
    }else if(stripe.dir==GB.DIR_EAST){
      tb=target.getEastBound();
      sb=stripe.getWestBound();
      if(sb>tb)return true;
    }else if(stripe.dir==GB.DIR_SOUTH){
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
    STRIPETHICKNESS_INCREMENT=16,
    MAXSTRIPETHICKNESS=4;

  void createStripe(){
    //will we create a new stripe?
    if(rnd.nextDouble()>CREATENEWSTRIPEPROBABILITY)return;//no
    //yes
    //get random thickness, direction and speed 
    int 
      thickness=rnd.nextInt(MAXSTRIPETHICKNESS)*STRIPETHICKNESS_INCREMENT+STRIPETHICKNESS_INCREMENT,
      speed=rnd.nextInt(3)+1,//TODO curve this
      dir=rnd.nextInt(4);//1 of the 4 directions  
    Stripe stripe;
    if(dir==GB.DIR_NORTH){
      stripe=createStripe_North(thickness,speed);
    }else if(dir==GB.DIR_EAST){
      stripe=createStripe_East(thickness,speed);
    }else if(dir==GB.DIR_SOUTH){
      stripe=createStripe_South(thickness,speed);
    }else{//dir==GSquid.DIR_WEST
      stripe=createStripe_West(thickness,speed);}  
    target.addChild(stripe);
    stripe.setParent(target);
    stripes.add(stripe);}
  
  //create it just over the south edge of the target, move north
  Stripe createStripe_North(int thickness,int speed){
    int[] tb=target.getBounds();//nesw
    int north=tb[2],east=tb[1],south=tb[2]-thickness,west=tb[3];
    BVertex[] vertices={ 
      new BVertex(west,south),
      new BVertex(west,north),
      new BVertex(east,north),
      new BVertex(east,south)};
    Stripe stripe=new Stripe(Arrays.asList(vertices),getChorusIndexForNewStripe(),null,GB.DIR_NORTH,speed);
    return stripe;}
  
  //create it just over the west edge of the target
  Stripe createStripe_East(int thickness,int speed){
    int[] tb=target.getBounds();//nesw
    int north=tb[0],east=tb[3],south=tb[2],west=tb[3]-thickness;
    BVertex[] vertices={ 
      new BVertex(west,south),
      new BVertex(west,north),
      new BVertex(east,north),
      new BVertex(east,south)};
    Stripe stripe=new Stripe(Arrays.asList(vertices),getChorusIndexForNewStripe(),null,GB.DIR_EAST,speed);
    return stripe;}

  //create it just over the north edge of the target
  Stripe createStripe_South(int thickness,int speed){
    int[] tb=target.getBounds();//nesw
    int north=tb[0]+thickness,east=tb[1],south=tb[0],west=0;
    BVertex[] vertices={ 
      new BVertex(west,south),
      new BVertex(west,north),
      new BVertex(east,north),
      new BVertex(east,south)};
    Stripe stripe=new Stripe(Arrays.asList(vertices),getChorusIndexForNewStripe(),null,GB.DIR_SOUTH,speed);
    return stripe;}
  
  //create it just over the east edge of the target
  Stripe createStripe_West(int thickness,int speed){
    int[] tb=target.getBounds();//nesw
    int north=tb[0],east=tb[1]+thickness,south=tb[2],west=tb[1];
    BVertex[] vertices={ 
      new BVertex(west,south),
      new BVertex(west,north),
      new BVertex(east,north),
      new BVertex(east,south)};
    Stripe stripe=new Stripe(Arrays.asList(vertices),getChorusIndexForNewStripe(),null,GB.DIR_WEST,speed);
    return stripe;}
  
  private int getChorusIndexForNewStripe(){
    return rnd.nextInt(3);
//    return 1;
  }
  
  private void updateStripeLocation(){
    
  }
  
  /*
   * ++++++++++++++++++++++++++++++++
   * STRIPE CLASS
   * speed refers to the number of cells the stripe moves at every cycle. 1, 2 or 3, generally
   * dir refers to the direction that the stripe moves
   *   ex : NORTH means the stripe starts at the south edge, runs parallel to the east-west axis, and moves north
   * ++++++++++++++++++++++++++++++++
   */
  
  @SuppressWarnings("serial")
  class Stripe extends BPolygon{

    public Stripe(List<BVertex> vertices,int chorusindex,List<String> tags,int dir,int speed){
      super(vertices,chorusindex,tags);
      this.dir=dir;
      this.speed=speed;
    }
    
    int dir,speed,offset=0;
    
    void updateLocation(){
//      stripe.translate(stripe.dir,stripe.speed);
      
      
    }
    
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
  
  BShape target;  

  public void setTarget(BShape target){
    this.target=target;}
  
  public BShape getTarget(){
    return target;}

}
