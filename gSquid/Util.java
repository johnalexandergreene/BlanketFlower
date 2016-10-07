package org.fleen.squarzy.gSquid;

import java.util.ArrayList;
import java.util.List;

public class Util{
  
  /*
   * get cells in interior of polygon
   * get twist of polygon
   * get NS segs in polygon
   * 
   * 
   * TODO test test
   * test for dupes
   * see it
   */
  public static final List<SCell> getFill(SPolygon polygon){
    //get twist
    boolean twist=polygon.getTwist();
    //get headcells and tailcells
    //it depends on twist
    List<SCell> 
      headcells=new ArrayList<SCell>(),
      tailcells=new ArrayList<SCell>();
    int dir;
    if(twist==TWIST_CW){
      for(SSeg seg:polygon.getSegs()){
        dir=seg.getForward();
        if(dir==DIR_NORTH)
          headcells.addAll(getCellsOnRight(seg));
        else if(dir==DIR_SOUTH)
          tailcells.addAll(getCellsOnRight(seg));}
    }else{//twist==TWIST_CCW
      for(SSeg seg:polygon.getSegs()){
        dir=seg.getForward();
        if(dir==DIR_SOUTH)
          headcells.addAll(getCellsOnLeft(seg));
        else if(dir==DIR_NORTH)
          tailcells.addAll(getCellsOnLeft(seg));}}
    //
    
    
  }
  
  private static final List<SCell> getCellsOnRight(SSeg seg){
    List<SCell> cells=new ArrayList<SCell>();
    int length=seg.getLength();
    for(int i=0;i<length-1;i++){
      cells.add(arg0)
    }
    
    
  }
  
  
  public static final boolean TWIST_CW=true,TWIST_CCW=false;
  /*
   * clockwise is true, counterclockwise is false
   * gather direction deltas
   */
  public static final boolean getTwist(SPolygon polygon){
    int 
      delta,deltasum=0,
      i1,i2,
      s=polygon.size(),
      d0,d1;
    SVertex v0,v1,v2;
    for(int i0=0;i0<s;i0++){
      i1=i0+1;
      if(i1==s)i1=0;
      i2=i1+1;
      if(i2==s)i2=0;
      v0=polygon.getVertex(i0);
      v1=polygon.getVertex(i1);
      v2=polygon.getVertex(i2);
      d0=getDirection(v0,v1);
      d1=getDirection(v1,v2);
      delta=getDirectionDelta(d0,d1);
      deltasum+=delta;}
    //
    if(deltasum>0)
      return TWIST_CW;
    else if(deltasum<0)
      return TWIST_CCW;
    else
      throw new IllegalArgumentException("foop");}
  
  public static final int
    //directions
    DIR_INVALID=-1,//a diagonal
    DIR_UNDEFINED=-1,//no relevant info
    DIR_NORTH=0,
    DIR_EAST=1,
    DIR_SOUTH=2,
    DIR_WEST=3,
    //turns
    TURN_STRAIGHT=0,
    TURN_RIGHT=1,
    TURN_LEFT=-1,
    TURN_REVERSE=-2;
  
  public static final int getDirection(SVertex v0,SVertex v1){
    return getDirection(v0.x,v0.y,v1.x,v1.y);}
  
  public static final int getDirection(SCell c0,SCell c1){
    return getDirection(c0.x,c0.y,c1.x,c1.y);}
  
  public static final int getDirection(int x0,int y0,int x1,int y1){
    if(x0==x1){
      if(y0<y1){
        return DIR_NORTH;
      }else if(y0>y1){
        return DIR_SOUTH;
      }else{//x0==x1 and y0==y1
        return DIR_UNDEFINED;}
    }else if(y0==y1){
      if(x0<x1){
        return DIR_EAST;
      }else if(x0>x1){
        return DIR_WEST;
      }else{//y0==y1 and x0==x1
        return DIR_UNDEFINED;
      }
    }else{//diagonal
      return DIR_INVALID;}}
  
  public static final int getDistance(SVertex v0,SVertex v1){
    return getDistance(v0.x,v0.y,v1.x,v1.y);}
  
  public static final int getDistance(int x0,int y0,int x1,int y1){
    if(x0==x1)
      return Math.abs(y0-y1);
    else
      return Math.abs(x0-x1);}
                
  /*
   * if d0==d1 then delta=0 : no change in direction
   * if we take a left turn then delta=-1
   * if we take a right turn then delta=1
   * if we reverse direction then delta=-2
   */
  public static final int getDirectionDelta(int d0,int d1){
    //normalize
    d0=d0%4;
    d1=d1%4;
    //test
    if(d0==d1)return TURN_STRAIGHT;
    if(d0==DIR_NORTH){
      if(d1==DIR_EAST){
        return TURN_RIGHT;
      }else if(d1==DIR_SOUTH){
        return TURN_REVERSE;
      }else{//d1==DIR_WEST
        return TURN_LEFT;}
    }else if(d0==DIR_EAST){
      if(d1==DIR_SOUTH){
        return TURN_RIGHT;
      }else if(d1==DIR_WEST){
        return TURN_REVERSE;
      }else{//d1==DIR_NORTH
        return TURN_LEFT;}
    }else if(d0==DIR_SOUTH){
      if(d1==DIR_WEST){
        return TURN_RIGHT;
      }else if(d1==DIR_NORTH){
        return TURN_REVERSE;
      }else{//d1==DIR_EAST
        return TURN_LEFT;}
    }else if(d0==DIR_WEST){
      if(d1==DIR_NORTH){
        return TURN_RIGHT;
      }else if(d1==DIR_EAST){
        return TURN_REVERSE;
      }else{//d1==DIR_SOUTH
        return TURN_LEFT;}}
    throw new IllegalArgumentException("foo?");}
  

}
