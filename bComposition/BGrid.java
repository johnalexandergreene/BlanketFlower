package org.fleen.blanketFlower.bComposition;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BVertex;

/*
 * a coordinate system
 * local to a shape
 * defined in terms of the shape
 * used by the shape's children
 */
public class BGrid{
  
  public BGrid(BShape shape){
    this.shape=shape;}
  
  BShape shape;
  
  /*
   * translate local vertex to global vertex
   * TODO find a better name than "glogal"
   */
  public BVertex getGlobalVertex(BVertex vertex){
    
  }
  
  /*
   * translate global vertex to local vertex 
   */
  public BVertex getLocalVertex(BVertex vertex){
    
  }
  
  /*
   * translate local direction to global direction
   */
  public int getGlobalDir(int dir){
    
  }
  
  /*
   * translate global direction to local direction
   */
  public int getLocalDir(int dir){
    
  }
  
  /*
   * translate local cell to global cell
   */
  public int getGlobalCell(BCell cell){
    
  }
  
  /*
   * translate global cell to local cell
   */
  public int getLocalCell(BCell cell){
    
  }

}
