package org.fleen.squarzy.gSquid;

public class SCell{
  
  SCell(int x,int y){
    this.x=x;
    this.y=y;
  }
  
  public int x,y;
  
  public int test=0;
  

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
