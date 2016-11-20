package org.fleen.blanketFlower.jig.patternFill;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.jig.Jig;

/*
 * convert a collection of png 1 bit images into cell patterns
 *   for each pattern, all 4 orientations, flipped, etc
 *   ---we are using pretty patterns here but this could also be a logo or something
 * 
 * params for the op
 *   a fill pattern. A 2d boolean array
 *   x and y offset from the shape's cell[0,0]
 *   
 * ALG
 *   
 * get a set of cells that is the fill of that pattern in the shape's domain : P
 *   maybe just fill the bounding box, it's fast
 *   that is to say, use the bounding box to determine where the fill-blocks stop
 *   
 * get all of the cells in the shape domain : S
 * 
 * Intersect S with P to get the fill pattern cells that fill the domain of the shape
 * get that intersection group : K
 * 
 * from K get contiguous subgroups, polygons.
 * from those polygons create shapes.
 * 
 * 
 *   
 * 
 *   
 */
public class Jig_PatternFill implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_PatternFill(BShape target){
    setTarget(target);}
  
  public Jig_PatternFill(){}
  
  /*
   * ################################
   * CLONE
   * ################################
   */
  
  public Object clone(){
    return new Jig_ArrayFill();}
  
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
   * PATTERN
   * we can set a pattern or we can get a pattern at random from our assets
   * ################################
   */
  
  

  /*
   * ################################
   * EXECUTE
   * init pattern
   * do cell dance
   * create shapes
   * ################################
   */
  
  Random rnd=new Random();
  List<BShape> dots=new ArrayList<BShape>();
  
  public void execute(){
    init}

}
