package org.fleen.blanketFlower.gSquid;

public class SVertex{
  
  public SVertex(int x,int y){
    this.x=x;
    this.y=y;}
  
  public int x,y;
  
  public int getDirection(SVertex v){
    return Util.getDirection(this,v);}
  
  public int getDistance(SVertex v){
    return Util.getDistance(this,v);}

}
