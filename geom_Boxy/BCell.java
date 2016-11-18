package org.fleen.blanketFlower.geom_Boxy;

import java.awt.geom.Path2D;

public class BCell{
  
  /*
   * ################################
   * CONSTRUCTOR 
   * ################################
   */
  
  public BCell(int x,int y){
    this.x=x;
    this.y=y;}
  
  /*
   * ################################
   * GEOMETRY 
   * ################################
   */
  
  public int x,y;

  public BCell getNorth(){
    return new BCell(x,y+1);}
  
  public BCell getEast(){
    return new BCell(x+1,y);}

  public BCell getSouth(){
    return new BCell(x,y-1);}
  
  public BCell getWest(){
    return new BCell(x-1,y);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * get the cell if it is in the group, return null otherwise
   * ++++++++++++++++++++++++++++++++
   */
  
  public BCell getNorth(BCellGroup g){
    BCell c=new BCell(x,y+1);
    if(g.contains(c))
      return c;
    else return null;}
  
  public BCell getEast(BCellGroup g){
    BCell c=new BCell(x+1,y);
    if(g.contains(c))
      return c;
    else return null;}

  public BCell getSouth(BCellGroup g){
    BCell c=new BCell(x,y-1);
    if(g.contains(c))
      return c;
    else return null;}
    
  public BCell getWest(BCellGroup g){
    BCell c=new BCell(x-1,y);
    if(g.contains(c))
      return c;
    else return null;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * adjacentness
   * ++++++++++++++++++++++++++++++++
   */
  
  /*
   * returns true if the specified cell is 
   * adjacent-flat (as opposed to adjacent-corner) to this cell.
   */
  public boolean isAdjacentFlat(BCell c){
    if(c.x==x&&c.y==y+1)return true;
    if(c.x==x+1&&c.y==y)return true;
    if(c.x==x&&c.y==y-1)return true;
    if(c.x==x-1&&c.y==y)return true;
    return false;}
  
  
  /*   
   *   x,y+1      x+1,y+1
   *    1          2        
   *    o----------o
   *    |          |
   *    |          |
   *    |          |
   *    |          |
   *    o----------o
   *    0          3
   *   x,y        x+1,y
   *    
   */
  public BPolygon getSquare(){
    BPolygon p=new BPolygon(
      new BVertex(x,y),
      new BVertex(x,y+1),
      new BVertex(x+1,y+1),
      new BVertex(x+1,y));
    return p;}
  
  public Path2D getPath2D(){
    BPolygon p=getSquare();
    Path2D path=new Path2D.Double();
    BVertex v=p.vertices.get(0);
    path.moveTo(v.x,v.y);
    v=p.vertices.get(1);
    path.lineTo(v.x,v.y);
    v=p.vertices.get(2);
    path.lineTo(v.x,v.y);
    v=p.vertices.get(3);
    path.lineTo(v.x,v.y);
    path.closePath();
    return path;}
  
  /*
   * ################################
   * OBJECT 
   * ################################
   */
  
  public int hashCode(){
    return x+37*y;}
  
  public boolean equals(Object a){
    BCell b=(BCell)a;
    return b.x==x&&b.y==y;}
  
  public String toString(){
    return "["+x+","+y+"]";}
  
  

}
