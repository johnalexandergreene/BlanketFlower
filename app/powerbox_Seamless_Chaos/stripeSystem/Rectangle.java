package org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.stripeSystem;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;


abstract class Rectangle{
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  //SW corner point x
  public abstract int getX();
  
  //SW corner point y
  public abstract int getY();
  
  public abstract int getWidth();
  
  public abstract int getHeight();
  
  public BCellGroup getCells(){
    BCellGroup g=new BCellGroup();
    int 
      xmin=getX(),xmax=xmin+getWidth(),
      ymin=getY(),ymax=ymin+getHeight();
    for(int x=xmin;x<xmax;x++){
      for(int y=ymin;y<ymax;y++){
        g.add(new BCell(x,y));}}
    return g;}
  
}
