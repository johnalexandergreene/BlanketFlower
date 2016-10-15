package org.fleen.blanketFlower.gSquid;

public class SSeg{
  
  public SSeg(SVertex v0,SVertex v1){
    this.v0=v0;
    this.v1=v1;}
  
  SVertex v0,v1;
  
  /*
   * return v0 -> v1 diresction
   */
  public int getForward(){
    return v0.getDirection(v1);}
  
  public int getLength(){
    return v0.getDistance(v1);}
  
}
