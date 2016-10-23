package org.fleen.blanketFlower.geom_Boxy;

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
  
  public BCell getEast(){
    return new BCell(x+1,y);}
  
  public BCell getWest(){
    return new BCell(x-1,y);}
  
  public BCell getNorth(){
    return new BCell(x,y+1);}
  
  public BCell getSouth(){
    return new BCell(x,y-1);}
  
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
