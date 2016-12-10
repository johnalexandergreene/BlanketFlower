package org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.stripeSystem;

import org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.Powerbox_Seamless_Chaos;

/*
 * By referencing this square the base and stripes locate themselves
 * the power box has 1 of these
 */
public class ReferenceSquare{
  
  //sw corner point coordinate X
  public int getX(){
    return Powerbox_Seamless_Chaos.REFERENCESQUAREX;}
  
  //sw corner point coordinate Y
  public int getY(){
    return Powerbox_Seamless_Chaos.REFERENCESQUAREY;}
  
  public int getWidth(){
    return Powerbox_Seamless_Chaos.REFERENCESQUARESPAN;}
  
  public int getHeight(){
    return Powerbox_Seamless_Chaos.REFERENCESQUARESPAN;}

}
