package org.fleen.blanketFlower.geom_Boxy;

public class BVertex{
  
  public BVertex(int x,int y){
    this.x=x;
    this.y=y;}
  
  public int x,y;
  
  public int getDirection(BVertex v){
    return Util.getDirection(this,v);}
  
  public int getDistance(BVertex v){
    return Util.getDistance(this,v);}

}
