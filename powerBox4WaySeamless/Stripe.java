package org.fleen.blanketFlower.powerBox4WaySeamless;

import java.util.Random;

public class Stripe extends Rectangle{
  
  /*
   * a stripe is defined thusly
   * type 
   *   northward : spans the square horizontally, moves from south to north
   *   eastward  : spans the square vertically, moves from west to east
   *   southward : spans the square horizontally, moves from north to south
   *   westward : spans the square vertically, moves from east to west
   *   
   * thickness
   *   the dimension of the stripe-rectangle along its axis of motion
   *   for nothward and southward that's the height
   *   for eastward and westward that's the width
   *   
   * speed
   *   the distance the stripe moves per movement cycle
   *   
   * color
   *   an integer, summed at cells, interpreted by a color array
   *   we might make it change, for blinking
   *   we will need multiple colors and a rhythim then
   *   
   * progress
   *   the offset of the stripe on its axis of movement
   *   how far along it is in its course
   *   this gets incremented and such
   * 
   */

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  /*
   * for the system of continuous stripes
   * specify everything except speed
   * speed=1 is assumed
   */
  Stripe(PBox pbox,int type,int thickness,int color,int progress){
    this.pbox=pbox;
    this.type=type;
    this.thickness=thickness;
    speed=PBox.STRIPESPEED_SLOW;
    this.color=color;
    this.progress=progress;}
  
  /*
   * for the system of chaos stripes
   * specify everything
   */
  Stripe(PBox pbox,int type,int thickness,int speed,int color,int progress){
    this.pbox=pbox;
    this.type=type;
    this.thickness=thickness;
    this.speed=speed;
    this.color=color;
    this.progress=progress;}
  
  /*
   * ################################
   * PBOX
   * ################################
   */
  
  PBox pbox;
  
  /*
   * ################################
   * DEFINITION
   * ################################
   */
  
  
  
  int 
    type,
    thickness,
    speed,
    color,
    progress;

  //get the x value of the sw corner point coordinates within the control square
  //the "index point", the point that traverses the reference box from edge to edge, is in the middle of the stripe thickness. 
  //  Implicitly anyway. We never actually refer to it. See test renderer for an illustration of this. It's simpler than it sounds.
  int getX(){
    if(type==PBox.STRIPETYPE_NORTHWARD){
      return pbox.rsquare.getX();
    }else if(type==PBox.STRIPETYPE_EASTWARD){
      return pbox.rsquare.getX()+progress-thickness/2;
    }else if(type==PBox.STRIPETYPE_SOUTHWARD){
      return pbox.rsquare.getX();
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return pbox.rsquare.getX()+pbox.rsquare.getWidth()-progress-thickness/2;}}

  int getY(){
    if(type==PBox.STRIPETYPE_NORTHWARD){
      return pbox.rsquare.getY()+progress-thickness/2;
    }else if(type==PBox.STRIPETYPE_EASTWARD){
      return pbox.rsquare.getY();
    }else if(type==PBox.STRIPETYPE_SOUTHWARD){
      return pbox.rsquare.getY()+pbox.rsquare.getHeight()-progress-thickness/2;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return pbox.rsquare.getY();}}

  int getWidth(){
    if(type==PBox.STRIPETYPE_NORTHWARD){
      return PBox.REFERENCESQUARESPAN;
    }else if(type==PBox.STRIPETYPE_EASTWARD){
      return thickness;
    }else if(type==PBox.STRIPETYPE_SOUTHWARD){
      return PBox.REFERENCESQUARESPAN;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return thickness;}}

  int getHeight(){
    if(type==PBox.STRIPETYPE_NORTHWARD){
      return thickness;
    }else if(type==PBox.STRIPETYPE_EASTWARD){
      return PBox.REFERENCESQUARESPAN;
    }else if(type==PBox.STRIPETYPE_SOUTHWARD){
      return thickness;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return PBox.REFERENCESQUARESPAN;}}
  
  void move(){
    progress+=speed;
    if(progress==PBox.REFERENCESQUARESPAN)
      progress=0;
    //DEBUG
    if(progress>PBox.REFERENCESQUARESPAN)throw new IllegalArgumentException("fuck");}

}
