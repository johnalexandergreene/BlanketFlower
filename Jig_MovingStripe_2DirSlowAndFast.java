package org.fleen.squarzy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.fleen.squarzy.gSquid.SPolygon;
import org.fleen.squarzy.gSquid.SShape;
import org.fleen.squarzy.gSquid.SVertex;

/*
 * at execute
 * if there is no stripe rectangle then create one at random location
 * 
 * put a rectangle at random location inside target
 * 
 */
public class Jig_MovingStripe_2DirSlowAndFast implements Jig{

  /*
   * ################################
   * EXECUTE
   * ################################
   */
  
  static final double CREATENEWSTRIPEPROBABILITY=0.2;
  
  Random rnd=new Random();
  
  /*
   * only works on rectangular polygons
   */
  public void execute(){
    
    System.out.println("stripecount="+stripes.size());
    
    
    //TODO verify that target is rectangular polygon
    
    //possibly create new stripe
    if(rnd.nextDouble()<CREATENEWSTRIPEPROBABILITY||stripes.isEmpty())
      createStripe();
    //move stripes
    for(Stripe stripe:stripes)
      moveStripe(stripe);
    //remove any stripes that have run off the edge
    removeOffStripe();
    }
  
  List<Stripe> stripes=new ArrayList<Stripe>();
  
  
  static final int 
    STRIPEMINWIDTH=6,
    STRIPEMAXWIDTH=30;
  
  void createStripe(){
    int width=rnd.nextInt(STRIPEMAXWIDTH-STRIPEMINWIDTH)+STRIPEMINWIDTH;
    int east=0,west=-width,north=target.getBounds()[3],south=0;
    if(east-west<STRIPEMINWIDTH)return;//fail
    SVertex[] vertices={ 
      new SVertex(west,south),
      new SVertex(west,north),
      new SVertex(east,north),
      new SVertex(east,south)};
    
    int speed=rnd.nextInt(3)+1;
    
    Stripe stripe=new Stripe(Arrays.asList(vertices),rnd.nextInt(3),null,speed);
    target.addChild(stripe);
    stripe.setParent(target);
    stripes.add(stripe);}
  
  //---------------------------------------
  @SuppressWarnings("serial")
  class Stripe extends SPolygon{

    public Stripe(List<SVertex> vertices,int chorusindex,List<String> tags,int speed){
      super(vertices,chorusindex,tags);
      this.speed=speed;
    }
    
    int speed;
    
  }
  
  /*
   * add 1 to the coors of each vertex
   */
  private void moveStripe(Stripe stripe){
    for(SVertex v:stripe.vertices)
      v.x+=stripe.speed;}
  
  private void removeOffStripe(){
    if(stripes.isEmpty())return;
    SPolygon p=stripes.get(0);
    SVertex vsouthwest=p.vertices.get(0);
    if(vsouthwest.x>target.getBounds()[2]){
      target.removeChild(p);
      p.setParent(null);
      stripes.remove(0);}}
  

  /*
   * ################################
   * DUPE
   * ################################
   */
  
  public Jig dupe(){
    return new Jig_MovingStripe_2DirSlowAndFast();}
  
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