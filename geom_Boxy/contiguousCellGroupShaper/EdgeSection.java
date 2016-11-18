package org.fleen.blanketFlower.geom_Boxy.contiguousCellGroupShaper;

import java.util.ArrayList;
import java.util.Arrays;

import org.fleen.blanketFlower.geom_Boxy.BVertex;

/*
 * edge sections look like this. we have 4 models
 * 
 * o------o
 * 
 * o------o
 *        |
 *        |
 *        |
 *        o
 *        
 * o------o
 *        |
 *        |
 *        |
 * o------o
 * 
 * o------o
 * |      |
 * |      |
 * |      |
 * o------o
 * 
 * An edge section is a list of 2, 3 or 4 vertices
 * the vertices are always addressed in clockwise, index+ direction
 *          
 */

@SuppressWarnings("serial")
class EdgeSection extends ArrayList<BVertex>{
  
  EdgeSection(BVertex... v){
    super(Arrays.asList(v));}
  
  //we use this for when the section is all 4 sides of the cell. closed is true
  EdgeSection(boolean closed,BVertex... v){
    super(Arrays.asList(v));
    this.closed=closed;}
  
  /*
   * used for whole cell edge section
   * if we've got one of these then our contiguous group must either consist 
   * of just 1 cell or it is noncontiguous and we have an exception
   */
  boolean closed=false;
  
}
