package org.fleen.blanketFlower.bComposition;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BVertex;

/*
 * a coordinate system
 * local to a shape
 * defined in terms of the shape
 * used by the shape's children
 * 
 * a bunch of methods for translating between local and absolute vertices, directions and cells
 * 
 * note that translation from local to absolute means translating through 1 or more nesting grids 
 * 
 */
public class BGrid{
  
  public BGrid(BShape shape){
    this.shape=shape;}
  
  BShape shape;
  
  /*
   * translate local vertex to Absolute vertex
   */
  public BVertex getAbsoluteVertex(BVertex vertex){
    
  }
  
  /*
   * translate Absolute vertex to local vertex 
   */
  public BVertex getLocalVertex(BVertex vertex){
    
  }
  
  /*
   * translate local direction to Absolute direction
   */
  public int getAbsoluteDir(int dir){
    
  }
  
  /*
   * translate Absolute direction to local direction
   */
  public int getLocalDir(int dir){
    
  }
  
  /*
   * translate local cell to Absolute cell
   */
  public int getAbsoluteCell(BCell cell){
    
  }
  
  /*
   * translate Absolute cell to local cell
   */
  public int getLocalCell(BCell cell){
    
  }

}
