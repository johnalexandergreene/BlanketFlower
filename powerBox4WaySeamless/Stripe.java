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
  Stripe(int type,int thickness,int color,int progress){
    this.type=type;
    this.thickness=thickness;
    speed=PBox.SPEED_SLOW;
    this.color=color;
    this.progress=progress;}
  
  /*
   * for the system of chaos stripes
   * specify everything
   */
  Stripe(int type,int thickness,int speed,int color,int progress){
    this.type=type;
    this.thickness=thickness;
    this.speed=speed;
    this.color=color;
    this.progress=progress;}
  
  /*
   * ################################
   * DEFINITION
   * ################################
   */
  
  
  
  int 
    type,
    thickness,//in terms of speed. that is to say, real thickness == thickness*speed
    speed,
    color,
    progress;

  //get the x value of the sw corner point coordinates within the control square
  int getX(){
    if(type==PBox.STRIPETYPE_NORTHWARD){
      return 0;
    }else if(type==PBox.STRIPETYPE_EASTWARD){
      return PBox.SQUARESPAN-progress;
    }else if(type==PBox.STRIPETYPE_SOUTHWARD){
      return 0;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return progress;}}

  int getY(){
    if(type==PBox.STRIPETYPE_NORTHWARD){
      return progress;
    }else if(type==PBox.STRIPETYPE_EASTWARD){
      return 0;
    }else if(type==PBox.STRIPETYPE_SOUTHWARD){
      return PBox.SQUARESPAN-progress;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return 0;}}

  int getWidth(){
    if(type==PBox.STRIPETYPE_NORTHWARD){
      return PBox.SQUARESPAN;
    }else if(type==PBox.STRIPETYPE_EASTWARD){
      return thickness*speed;
    }else if(type==PBox.STRIPETYPE_SOUTHWARD){
      return PBox.SQUARESPAN;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return thickness*speed;}}

  int getHeight(){
    if(type==PBox.STRIPETYPE_NORTHWARD){
      return thickness*speed;
    }else if(type==PBox.STRIPETYPE_EASTWARD){
      return PBox.SQUARESPAN;
    }else if(type==PBox.STRIPETYPE_SOUTHWARD){
      return thickness*speed;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return PBox.SQUARESPAN;}}
  
  void move(){
    progress+=speed;
    if(progress==PBox.SQUARESPAN)
      progress=0;
    //DEBUG
    if(progress>PBox.SQUARESPAN)throw new IllegalArgumentException("fuck");}

}
