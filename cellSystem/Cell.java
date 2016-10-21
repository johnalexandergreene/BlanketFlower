package org.fleen.blanketFlower.cellSystem;

public class Cell{
  
  /*
   * ################################
   * CONSTRUCTOR 
   * ################################
   */
  
  public Cell(int x,int y){
    this.x=x;
    this.y=y;}
  
  /*
   * ################################
   * GEOMETRY 
   * ################################
   */
  
  public int x,y;
  
  public Cell getEast(){
    return new Cell(x+1,y);}
  
  public Cell getWest(){
    return new Cell(x-1,y);}
  
  public Cell getNorth(){
    return new Cell(x,y+1);}
  
  public Cell getSouth(){
    return new Cell(x,y-1);}
  
  /*
   * ################################
   * OBJECT 
   * ################################
   */
  
  public int hashCode(){
    return x+37*y;}
  
  public boolean equals(Object a){
    Cell b=(Cell)a;
    return b.x==x&&b.y==y;}
  
  public String toString(){
    return "["+x+","+y+"]";}
  
  

}
