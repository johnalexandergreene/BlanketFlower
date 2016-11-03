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
 * ---
 * 
 * At a shape we have 3 coordinate systems into which we need to transform the local vertices
 * 
 * the local coordinate system. All local vertices are as they are defined to be
 * 
 * the parent coordinate system. All vertices are transformed to the parent shape's coordinate system
 * 
 * the absolute coordinate system. Convert local to parent, then that to parent's parent, and so on, until we hit root 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class BGrid{
  
  public BGrid(BShape shape){
    this.shape=shape;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  BShape shape;
  
  private BPolygon getGridDefiningPolygon(){
    if(shape instanceof BPolygon)
      return (BPolygon)shape;
    else
      return((BYard)shape).polygons.get(0);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * LOCAL GRID GEOMETRY
   * origin is (0,0)
   * 
   * ++++++++++++++++++++++++++++++++
   */
  
  /*
   * ++++++++++++++++++++++++++++++++
   * PARENT GRID GEOMETRY
   * get local geometry in terms of parent grid
   * in the case of the root shape this is the same as absolute
   * 
   * this is used within the geometry stack for getting absolute geometry
   * ++++++++++++++++++++++++++++++++
   */
  
  /*
   * get local origin point (0,0), aka polygon0.v0, in terms of parent grid
   */
  BVertex getOrigin_Parent(){
    BPolygon p=getGridDefiningPolygon();
    return p.vertices.get(0);}
  
  /*
   * get local direction in terms of parent grid
   */
  int getDirection_Parent(int d){
    if(d==GB.DIR_NORTH)
      return getNorth_Parent();
    else if(d==GB.DIR_EAST)
      return getEast_Parent();
    else if(d==GB.DIR_SOUTH)
      return getSouth_Parent();
    else if(d==GB.DIR_WEST)
      return getWest_Parent();
    else
      throw new IllegalArgumentException("invalid direction specified : "+d);}
  
  /*
   * get local north in terms of parent grid
   */
  int getNorth_Parent(){
    BPolygon p=getGridDefiningPolygon();
    BVertex 
      v0=p.vertices.get(0),
      v1=p.vertices.get(1);
    int pdir=GB.getDirection(v0,v1);
    return pdir;}
  
  /*
   * get local east in terms of parent grid
   */
  int getEast_Parent(){
    BPolygon p=getGridDefiningPolygon();
    int north=getNorth_Parent(),east;
    if(p.getTwist()==GB.TWIST_CW){
      east=GB.getDirectionAtDelta(north,GB.TURN_RIGHT);
    }else{//p.getTwist()==GB.TWIST_CCW
      east=GB.getDirectionAtDelta(north,GB.TURN_LEFT);}
    return east;}
  
  /*
   * get local south in terms of parent grid
   */
  int getSouth_Parent(){
    return GB.getOppositeDirection(getNorth_Parent());}
  
  /*
   * get local west in terms of parent grid
   */
  int getWest_Parent(){
    return GB.getOppositeDirection(getEast_Parent());}
  
  /*
   * get arbitrary local vertex in terms of parent grid
   * get the origin
   * get the vertex in terms of nesw offset from origin
   * TODO test this
   */
  BVertex getVertex_Parent(BVertex v){
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
   * ++++++++++++++++++++++++++++++++
   * ABSOLUTE GRID GEOMETRY
   * get local geometry in terms of absolute grid
   * in the case of the root shape this is the same was the parent
   * ++++++++++++++++++++++++++++++++
   */
  
  /*
   * return the local origin in terms of absolute grid
   */
  public BVertex getOrigin_Absolute(){
    BVertex origin=getOrigin_Parent();
    BShape s=shape;
    while(!s.isRoot()){
      s=(BShape)s.getParent();
      origin=s.grid.getVertex_Parent(origin);}
    return origin;}
  
  public int getNorth_Absolute(){
    int d=getNorth_Parent();
    BShape s=shape;
    while(!s.isRoot()){
      s=(BShape)s.getParent();
      d=s.grid.getDirection_Parent(d);}
    return d;}
  
  public int getEast_Absolute(){
    int d=getEast_Parent();
    BShape s=shape;
    while(!s.isRoot()){
      s=(BShape)s.getParent();
      d=s.grid.getDirection_Parent(d);}
    return d;}
  
  public int getSouth_Absolute(){
    int d=getSouth_Parent();
    BShape s=shape;
    while(!s.isRoot()){
      s=(BShape)s.getParent();
      d=s.grid.getDirection_Parent(d);}
    return d;}
  
  public int getWest_Absolute(){
    int d=getWest_Parent();
    BShape s=shape;
    while(!s.isRoot()){
      s=(BShape)s.getParent();
      d=s.grid.getDirection_Parent(d);}
    return d;}
  
  public int getDirection_Absolute(int d){
    if(d==GB.DIR_NORTH)
      return getNorth_Absolute();
    else if(d==GB.DIR_EAST)
      return getEast_Absolute();
    else if(d==GB.DIR_SOUTH)
      return getSouth_Absolute();
    else if(d==GB.DIR_WEST)
      return getWest_Absolute();
    else
      throw new IllegalArgumentException("invalid direction specified : "+d);}
  
  public BVertex getVertex_Absolute(BVertex vertex){
    BVertex a=getVertex_Parent(vertex);
    BShape s=shape;
    while(!s.isRoot()){
      s=(BShape)s.getParent();
      a=s.grid.getVertex_Parent(a);}
    return a;}
  
  public BCell getCell_Absolute(BCell cell){
    BVertex v=getVertex_Absolute(new BVertex(cell.x,cell.y));
    BCell c=new BCell(v.x,v.y);
    return c;}

}
