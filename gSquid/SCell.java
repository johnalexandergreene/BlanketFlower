package org.fleen.blanketFlower.gSquid;

public class SCell{
  
  /*
   * ################################
   * CONSTRUCTOR 
   * ################################
   */
  
  SCell(int x,int y){
    this.x=x;
    this.y=y;}
  
  /*
   * ################################
   * GEOMETRY 
   * ################################
   */
  
  public int x,y;
  
  public SCell getEast(){
    return new SCell(x+1,y);}
  
  public SCell getWest(){
    return new SCell(x-1,y);}
  
  public SCell getNorth(){
    return new SCell(x,y+1);}
  
  public SCell getSouth(){
    return new SCell(x,y-1);}
  
  /*
   * ################################
   * OBJECT 
   * ################################
   */
  
  public int hashCode(){
    return x+37*y;}
  
  public boolean equals(Object a){
    SCell b=(SCell)a;
    return b.x==x&&b.y==y;}
  
  public String toString(){
    return "["+x+","+y+"]";}
  
  

}
