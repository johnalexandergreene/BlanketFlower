package org.fleen.blanketFlower.bComposition;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.geom_Boxy.BYard;
import org.fleen.blanketFlower.geom_Boxy.GB;
import org.fleen.blanketFlower.geom_Boxy.Util;

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
 * ---
 * 
 * the root shape is defined in absolute terms
 * 
 * the root shape's children are defined in terms of the root shape's grid
 * 
 * the root shape's children are generated and managed by the root shape's jig
 * 
 * and so on
 * 
 * 
 */
public class BGrid{
  
  public BGrid(BShape shape){
    this.shape=shape;}
  
  BShape shape;
  
  /*
   * ################################
   * DEFINITION
   * in terms of parent grid geometry
   * ################################
   * 
   */
  
  public BVertex getOrigin(){
    if(shape instanceof BPolygon)
      return ((BPolygon)shape).vertices.get(0);
    else
      return((BYard)shape).polygons.get(0).vertices.get(0);}
  
  public int getNorth(){
    BVertex v0,v1;
    if(shape instanceof BPolygon){
      v0=((BPolygon)shape).vertices.get(1);
      v1=((BPolygon)shape).vertices.get(1);
    }else{
      v0=((BYard)shape).polygons.get(0).vertices.get(0);
      v1=((BYard)shape).polygons.get(0).vertices.get(1);}
    return GB.getDirection(v0,v1);}
  
  /*
   * if this shape has the same twist as the parent then east has the same relationship to north as the parent
   * otherwise it has the opposite. That is to say, east and west are reversed, relatively speaking.
   */
  public int getEast(){
    //get the polygon to examine
    BPolygon p;
    if(shape instanceof BPolygon)
      p=(BPolygon)shape;
    else
      p=((BYard)shape).polygons.get(0);
    //
    boolean ptwist=p.getTwist();
    int north=getNorth(),east;
    if(p.getTwist()==GB.TWIST_CW){
      east=GB.getDirectionAtDelta(north,GB.TURN_RIGHT);
    }else{//p.getTwist()==GB.TWIST_CCW
      east=GB.getDirectionAtDelta(north,GB.TURN_LEFT);}
    return east;}
  
  public int getSouth(){
    return GB.getOppositeDirection(getNorth());}
  
  public int getWest(){
    return GB.getOppositeDirection(getEast());}
  
  /*
   * ################################
   * TRANSLATE TO PARENT GRID
   * ################################
   */
  
  /*
   * translate vertex in local grid to vertex in parent grid
   */
  public BVertex getParentVertex(BVertex v){
    
  }
  
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
