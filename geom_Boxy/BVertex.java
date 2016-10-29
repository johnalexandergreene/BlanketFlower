package org.fleen.blanketFlower.geom_Boxy;

public class BVertex{
  
  public BVertex(int x,int y){
    this.x=x;
    this.y=y;}
  
  public int x,y;
  
  public int getDirection(BVertex v){
    return GB.getDirection(this,v);}
  
  public int getDistance(BVertex v){
    return GB.getDistance(this,v);}

}
