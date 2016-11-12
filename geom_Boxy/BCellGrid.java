package org.fleen.blanketFlower.geom_Boxy;

import java.util.Collection;
import java.util.HashSet;

/*
 * a grid of cells
 * add, remove
 * test neighbors
 * 
 * used in the arbitrarily contiguous cell mass polygonizer
 */
public class BCellGrid extends HashSet<BCell>{
  
  private static final long serialVersionUID=7160911469580691669L;
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */

  public void BCellGrid(){}
  
  public void BCellGrid(Collection<BCell> cells){
    addAll(cells);}
  
  
  /*
   * ################################
   * TEST
   * ################################
   */
  
  
  

}
