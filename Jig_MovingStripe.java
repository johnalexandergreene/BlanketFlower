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
public class Jig_MovingStripe implements Jig{

  /*
   * ################################
   * EXECUTE
   * ################################
   */
  
  static final double CREATENEWSTRIPEPROBABILITY=0.06;
  
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
    for(SPolygon stripe:stripes)
      moveStripe(stripe);
    //remove any stripes that have run off the edge
    removeOffStripe();
    }
  
  List<SPolygon> stripes=new ArrayList<SPolygon>();
  
  
  static final int 
    STRIPEMINWIDTH=3,
    STRIPEMAXWIDTH=10;
  
  void createStripe(){
    int width=rnd.nextInt(STRIPEMAXWIDTH-STRIPEMINWIDTH)+STRIPEMINWIDTH;
    int east=0,west=-width,north=0,south=target.getBounds()[3];
    if(east-west<STRIPEMINWIDTH)return;//fail
    SVertex[] vertices={ 
      new SVertex(west,north),
      new SVertex(east,north),
      new SVertex(east,south),
      new SVertex(west,south)};
    SPolygon p=new SPolygon(Arrays.asList(vertices),rnd.nextInt(3),null);
    target.addChild(p);
    p.setParent(target);
    stripes.add(p);}
  
  /*
   * add 1 to the coors of each vertex
   */
  private void moveStripe(SPolygon stripe){
    for(SVertex v:stripe.vertices)
      v.x++;}
  
  private void removeOffStripe(){
    if(stripes.isEmpty())return;
    SPolygon p=stripes.get(0);
    SVertex v=p.vertices.get(3);
    if(v.x>target.getBounds()[2]){
      target.removeChild(p);
      p.setParent(null);}}
  

  /*
   * ################################
   * DUPE
   * ################################
   */
  
  public Jig dupe(){
    return new Jig_MovingStripe();}
  
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
