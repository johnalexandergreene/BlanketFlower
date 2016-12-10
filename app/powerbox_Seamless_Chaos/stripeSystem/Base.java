package org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.stripeSystem;

import org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.Powerbox_Seamless_Chaos;

/*
 * the rectangle across which the stripe rectangles flow
 * the unscaled viewport
 * the power box has 1 of these
 */
public class Base extends Rectangle{

  public int getX(){
    return 0;}

  public int getY(){
    return 0;}

  public int getWidth(){
    return Powerbox_Seamless_Chaos.BASEWIDTH;}

  public int getHeight(){
    return Powerbox_Seamless_Chaos.BASEHEIGHT;}

  

}
