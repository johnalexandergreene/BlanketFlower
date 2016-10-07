package org.fleen.squarzy.gSquid;

public class SVertex{
  
  public int x,y;
  
  public int getDirection(SVertex v){
    return Util.getDirection(this,v);}
  
  public int getDistance(SVertex v){
    return Util.getDistance(this,v);}

}
