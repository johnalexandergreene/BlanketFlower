package org.fleen.blanketFlower.bComposition;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.geom_Boxy.BYard;
import org.fleen.blanketFlower.geom_Boxy.GB;

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
   * PARENT GRID GEOMETRY
   * get local geometry in terms of parent grid
   * in the case of the root shape this is the same as absolute
   * ################################
   */
  
  /*
   * get local origin point (0,0) in terms of parent grid
   */
  public BVertex getOrigin_Parent(){
    if(shape instanceof BPolygon)
      return ((BPolygon)shape).vertices.get(0);
    else
      return((BYard)shape).polygons.get(0).vertices.get(0);}
  
  /*
   * get local north in terms of parent grid
   */
  public int getNorth_Parent(){
    BVertex v0,v1;
    if(shape instanceof BPolygon){
      v0=((BPolygon)shape).vertices.get(1);
      v1=((BPolygon)shape).vertices.get(1);
    }else{
      v0=((BYard)shape).polygons.get(0).vertices.get(0);
      v1=((BYard)shape).polygons.get(0).vertices.get(1);}
    return GB.getDirection(v0,v1);}
  
  /*
   * get local east in terms of parent grid
   */
  public int getEast_Parent(){
    //get the polygon to examine
    BPolygon p;
    if(shape instanceof BPolygon)
      p=(BPolygon)shape;
    else
      p=((BYard)shape).polygons.get(0);
    //
    boolean ptwist=p.getTwist();
    int north=getNorth_Parent(),east;
    if(p.getTwist()==GB.TWIST_CW){
      east=GB.getDirectionAtDelta(north,GB.TURN_RIGHT);
    }else{//p.getTwist()==GB.TWIST_CCW
      east=GB.getDirectionAtDelta(north,GB.TURN_LEFT);}
    return east;}
  
  /*
   * get local south in terms of parent grid
   */
  public int getSouth_Parent(){
    return GB.getOppositeDirection(getNorth_Parent());}
  
  /*
   * get local west in terms of parent grid
   */
  public int getWest_Parent(){
    return GB.getOppositeDirection(getEast_Parent());}
  
  /*
   * get arbitrary local vertex in terms of parent grid
   * get the origin
   * get the vertex in terms of nesw offset from origin
   * TODO test this
   */
  public BVertex getVertex_Parent(BVertex v){
    BVertex v0=new BVertex(getOrigin_Parent());
    if(v.x>0){
      int peast=getEast_Parent();
      v0=v0.getVertex(peast,v.x);
    }else if(v.x<0){
      int pwest=getEast_Parent();
      v0=v0.getVertex(pwest,-v.x);}
    if(v.y>0){
      int pnorth=getNorth_Parent();
      v0=v0.getVertex(pnorth,v.y);
    }else if(v.y<0){
      int psouth=getSouth_Parent();
      v0=v0.getVertex(psouth,-v.y);}
    return v0;}
  
  /*
   * ################################
   * ABSOLUTE GRID GEOMETRY
   * get local geometry in terms of absolute grid
   * in the case of the root shape this is the same was the parent
   * ################################
   */
  

  public BVertex getOrigin_Absolute(){
    BVertex origin=getOrigin_Parent();
    while(!shape.isRoot()){
      shape=(BShape)shape.getParent();
      origin=shape.grid.getVertex_Parent(origin);}
    return origin;}
  
  public int getNorth_Absolute(){
    if(shape.isRoot())return getNorth_Parent();
    
  }
  
  public int getEast_Absolute(){
    if(shape.isRoot())return getEast_Parent();
    
  }
  
  public int getSouth_Absolute(){
    if(shape.isRoot())return getSouth_Parent();
    
  }
  
  public int getWest_Absolute(){
    if(shape.isRoot())return getWest_Parent();
    
  }
  
  public BVertex getVertex_Absolute(BVertex vertex){
    if(shape.isRoot())return getVertex_Parent(vertex);
    
  }
  
  public int getCell_Absolute(BCell cell){
    if(shape.isRoot())return getCell_Parent(cell);
    
  }

}
