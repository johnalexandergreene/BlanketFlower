package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.stripeSystem;


/*
 * the rectangle across which the stripe rectangles flow
 * the unscaled viewport
 * the power box has 1 of these
 */
public class Base extends Rectangle{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Base(int x,int y,int width,int height){
    this.x=x;
    this.y=y;
    this.width=width;
    this.height=height;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  int x,y,width,height;
  
  
  //sw corner point coordinate X
  public int getX(){
    return x;}
  
  //sw corner point coordinate Y
  public int getY(){
    return y;}
  
  public int getWidth(){
    return width;}
  
  public int getHeight(){
    return height;}

  

}
