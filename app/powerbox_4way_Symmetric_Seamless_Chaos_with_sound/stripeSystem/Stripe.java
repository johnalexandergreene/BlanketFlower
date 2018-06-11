package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos_with_sound.stripeSystem;



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
   * for the system of chaos stripes
   * specify everything
   */
  public Stripe(StripeSystem stripesystem,int type,int thickness,int speed,int color,int progress){
    this.stripesystem=stripesystem;
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
  
  StripeSystem stripesystem;
  
  /*
   * ################################
   * DEFINITION
   * ################################
   */
  
  public int 
    type,
    thickness,
    speed,
    color,
    progress;

  //get the x value of the sw corner point coordinates within the control square
  //the "index point", the point that traverses the reference box from edge to edge, is in the middle of the stripe thickness. 
  //  Implicitly anyway. We never actually refer to it. See test renderer for an illustration of this. It's simpler than it sounds.
  public int getX(){
    ReferenceSquare rsquare=stripesystem.getReferenceSquare();
    if(type==StripeSystem.STRIPETYPE_NORTHWARD){
      return rsquare.getX();
    }else if(type==StripeSystem.STRIPETYPE_EASTWARD){
      return rsquare.getX()+progress-thickness/2;
    }else if(type==StripeSystem.STRIPETYPE_SOUTHWARD){
      return rsquare.getX();
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return rsquare.getX()+rsquare.getWidth()-progress-thickness/2;}}

  public int getY(){
    ReferenceSquare rsquare=stripesystem.getReferenceSquare();
    if(type==StripeSystem.STRIPETYPE_NORTHWARD){
      return rsquare.getY()+progress-thickness/2;
    }else if(type==StripeSystem.STRIPETYPE_EASTWARD){
      return rsquare.getY();
    }else if(type==StripeSystem.STRIPETYPE_SOUTHWARD){
      return rsquare.getY()+rsquare.getHeight()-progress-thickness/2;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return rsquare.getY();}}

  public int getWidth(){
    ReferenceSquare rsquare=stripesystem.getReferenceSquare();
    if(type==StripeSystem.STRIPETYPE_NORTHWARD){
      return rsquare.span;
    }else if(type==StripeSystem.STRIPETYPE_EASTWARD){
      return thickness;
    }else if(type==StripeSystem.STRIPETYPE_SOUTHWARD){
      return rsquare.span;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return thickness;}}

  public int getHeight(){
    ReferenceSquare rsquare=stripesystem.getReferenceSquare();
    if(type==StripeSystem.STRIPETYPE_NORTHWARD){
      return thickness;
    }else if(type==StripeSystem.STRIPETYPE_EASTWARD){
      return rsquare.span;
    }else if(type==StripeSystem.STRIPETYPE_SOUTHWARD){
      return thickness;
    }else{//type==PBox.STRIPETYPE_WESTWARD
      return rsquare.span;}}
  
  public void move(){
    ReferenceSquare rsquare=stripesystem.getReferenceSquare();
    progress+=speed;
    if(progress==rsquare.span)
      progress=0;
    //DEBUG
    if(progress>rsquare.span)throw new IllegalArgumentException("fuck");}

}
