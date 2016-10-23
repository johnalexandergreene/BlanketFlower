package org.fleen.blanketFlower.geom_Boxy;

/*
 * Boxy Geometry
 */
public class GB{
  
  /*
   * ################################
   * DIRECTION
   * ################################
   */
  
  public static final int 
    DIR_AMBIGUOUS=-1,
    DIR_NORTH=0,
    DIR_EAST=1,
    DIR_SOUTH=2,
    DIR_WEST=3;
  
  /*
   * ################################
   * TWIST
   * AKA CHIRALITY
   * CLOCKWISE OR COUNTERCLOCKWISE
   * Given a polygon, given the direction of traversal when addressing 
   * the vertices of that polygon in the index-positive direction
   * are we moving clockwise or counterclockwise
   * ################################
   */
  
  public static final boolean TWIST_CW=true,TWIST_CCW=false;
  

}
