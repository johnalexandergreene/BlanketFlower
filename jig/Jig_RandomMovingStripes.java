package org.fleen.blanketFlower.jig;

import java.util.Random;

import org.fleen.blanketFlower.bComposition.BShape;

/*
 * a haze of static
 * given the target
 * clear existing dots
 *   a dot is a 1-square shape
 * create new dots
 */
public class Jig_RandomMovingStripes implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_RandomMovingStripes(BShape target){
    setTarget(target);}
  
  public Jig_RandomMovingStripes(){}
  
  /*
   * ################################
   * CLONE
   * ################################
   */
  
  public Object clone(){
    return new Jig_RandomMovingStripes();}
  
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
   * clear old dots
   * create new dots
   * our dots all get chorus index = 1
   * 
   * NOTE that our child bshapes are an abstraction of our stripe objects
   * we intersect the stripe rectangles with the target shape to get cells and from those derive our actual shapes.
   * 
   * ################################
   */
  
  public void execute(){
    createStripe();
    moveStripes();
    cullExitStripes();}
  
  /*
   * ################################
   * CREATE STRIPE
   * ################################
   */
  
  private static final double STRIPECREATIONPROBABILITY=0.03;//TODO should be param
  Random rnd=new Random();
  
  private void createStripe(){
    //sometimes we create a stripe, usually we don't
    if(rnd.nextDouble()>STRIPECREATIONPROBABILITY)return;
    //stripe across the shorter span of the target
    
    
  }
  
  
  

}
