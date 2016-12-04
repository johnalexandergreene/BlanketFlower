package org.fleen.blanketFlower.jig.boxRhythmicSweepingStripes;

/*
 * a stripe in a system of stripes
 * 
 * A stripe is arranged like this
 * 
 *   1            2      N
 *   o------------o      ^
 *   |            |
 *   |            |
 *   |            |
 *   |            |
 *   o------------o
 *   0            3
 * 
 * we locate the v0 vertex of the stripe by offset from the southwest corner of the target's bounding rectangle
 */

class Stripe{
  
  Stripe(boolean orientation,int thickness,int course,int speed){
    this.orientation=orientation;
    this.thickness=thickness;
    this.course=course;
    this.speed=speed;}
  
  static final boolean 
    STRIPEORIENTATION_HORIZONTAL=true,
    STRIPEORIENTATION_VERTICAL=false;
  
  //spanning the bounding rectangle of the target
  //horizontally or vertically
  boolean orientation;
  //in terms of cells or unit increments or whatever
  int thickness;
  //the direction that the stripe is moving : nesw
  int course;  
  //speed is cells traversed per execution cycle
  int speed;
  //a reference vertex in the target, specified by index, used for stripe positioning
  int refvertexindex;
  //position of the stripe v0 relative to target v0
  int offsetx,offsety;
  //set to true when the stripe, in course, exits the target bounding rectangle
  boolean cull=false;}
