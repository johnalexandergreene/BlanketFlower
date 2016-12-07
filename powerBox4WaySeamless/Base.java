package org.fleen.blanketFlower.powerBox4WaySeamless;

/*
 * the rectangle across which the stripe rectangles flow
 * the unscaled viewport
 * the power box has 1 of these
 */
public class Base extends Rectangle{

  int getX(){
    return (PBox.SQUARESPAN-PBox.BASEWIDTH)/2;}

  int getY(){
    return (PBox.SQUARESPAN-PBox.BASEHEIGHT)/2;}

  int getWidth(){
    return PBox.BASEWIDTH;}

  int getHeight(){
    return PBox.BASEHEIGHT;}

  

}
