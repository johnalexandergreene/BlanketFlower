package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.stripeSystem;


/*
 * By referencing this square the base and stripes locate themselves
 * the power box has 1 of these
 * it's just a square
 */
public class ReferenceSquare extends Rectangle{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  ReferenceSquare(int x,int y,int span){
    this.x=x;
    this.y=y;
    this.span=span;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  int x,y,span;
  
  
  //sw corner point coordinate X
  public int getX(){
    return x;}
  
  //sw corner point coordinate Y
  public int getY(){
    return y;}
  
  public int getWidth(){
    return span;}
  
  public int getHeight(){
    return span;}

}
