package org.fleen.blanketFlower.powerBox4WaySeamless;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;


abstract class Rectangle{
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  //the SW corner point coors
  abstract int getX();
  abstract int getY();
  
  abstract int getWidth();
  abstract int getHeight();
  
  BCellGroup getCells(){
    BCellGroup g=new BCellGroup();
    int 
      xmin=getX(),xmax=xmin+getWidth(),
      ymin=getY(),ymax=ymin+getHeight();
    for(int x=xmin;x<xmax;x++){
      for(int y=ymin;y<ymax;y++){
        g.add(new BCell(x,y));}}
    return g;}
  
}
