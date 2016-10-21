package org.fleen.blanketFlower.geom_Boxy;

public class BSeg{
  
  public BSeg(BVertex v0,BVertex v1){
    this.v0=v0;
    this.v1=v1;}
  
  BVertex v0,v1;
  
  /*
   * return v0 -> v1 diresction
   */
  public int getForward(){
    return v0.getDirection(v1);}
  
  public int getLength(){
    return v0.getDistance(v1);}
  
}
