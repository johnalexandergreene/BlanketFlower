package org.fleen.blanketFlower.geom_Boxy;

import org.fleen.geom_2D.GD;

/*
 * Blanketflower Geometry
 * constants and primitives
 */
public class GB{
  
  /*
   * ################################
   * DIRECTION
   * ################################
   */
  
  public static final int
    //directions
    DIR_INVALID=-2,//a diagonal
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
  
  public static final int getDirection(BVertex v0,BVertex v1){
    return getDirection(v0.x,v0.y,v1.x,v1.y);}
  
  public static final int getDirection(BCell c0,BCell c1){
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
  
  /*
   * given 2 directions, get the change in direction
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
  
  /*
   * given a direction and a delta off that direction (a turn), get the resulting direction
   */
  public static final int getDirectionAtDelta(int dir,int delta){
    if(dir==DIR_NORTH){
      if(delta==TURN_STRAIGHT){
        return DIR_NORTH;
      }else if(delta==TURN_RIGHT){
        return DIR_EAST;
      }else if(delta==TURN_REVERSE){
        return DIR_SOUTH;
      }else if(delta==TURN_LEFT){
        return DIR_WEST;
      }else{
        throw new IllegalArgumentException("invalid delta");}
    }else if(dir==DIR_EAST){
      if(delta==TURN_STRAIGHT){
        return DIR_EAST;
      }else if(delta==TURN_RIGHT){
        return DIR_SOUTH;
      }else if(delta==TURN_REVERSE){
        return DIR_WEST;
      }else if(delta==TURN_LEFT){
        return DIR_NORTH;
      }else{
        throw new IllegalArgumentException("invalid delta");}
    }else if(dir==DIR_SOUTH){
      if(delta==TURN_STRAIGHT){
        return DIR_SOUTH;
      }else if(delta==TURN_RIGHT){
        return DIR_WEST;
      }else if(delta==TURN_REVERSE){
        return DIR_NORTH;
      }else if(delta==TURN_LEFT){
        return DIR_EAST;
      }else{
        throw new IllegalArgumentException("invalid delta");}
    }else if(dir==DIR_WEST){
      if(delta==TURN_STRAIGHT){
        return DIR_WEST;
      }else if(delta==TURN_RIGHT){
        return DIR_NORTH;
      }else if(delta==TURN_REVERSE){
        return DIR_EAST;
      }else if(delta==TURN_LEFT){
        return DIR_SOUTH;
      }else{
        throw new IllegalArgumentException("invalid delta");}
    }else{
      throw new IllegalArgumentException("invalid dir");}}
  
  public static final int getOppositeDirection(int d){
    if(d==DIR_NORTH){
      return DIR_SOUTH;
    }else if(d==DIR_EAST){
      return DIR_WEST;
    }else if(d==DIR_SOUTH){
      return DIR_NORTH;
    }else if(d==DIR_WEST){
      return DIR_EAST;
    }else{
      throw new IllegalArgumentException("invalid dir");}}
  
  /*
   * ################################
   * TWIST
   * AKA CHIRALITY
   * CLOCKWISE OR COUNTERCLOCKWISE
   * Given a polygon, given the direction of traversal when addressing 
   * the vertices of that polygon in the index-positive direction
   * are we moving clockwise or counterclockwise
   * ################################
   */
  
  public static final boolean TWIST_CW=true,TWIST_CCW=false;
  
  /*
   * convert to double[][] and use GD.getSignedArea2D
   * 
   * clockwise is true, counterclockwise is false
   * gather direction deltas
   * 
   * TODO make a faster version
   */
  public static final boolean getTwist(BPolygon polygon){
    int s=polygon.vertices.size();
    double[][] d=new double[polygon.vertices.size()][2];
    BVertex v;
    for(int i=0;i<s;i++){
      v=polygon.vertices.get(i);
      d[i][0]=v.x;
      d[i][1]=v.y;}
    double b=GD.getSignedArea2D(d);
    if(b<0)
      return TWIST_CW;
    else
      return TWIST_CCW;}
  
  /*
   * ################################
   * DISTANCE
   * We measure distance our 2 axes : NS and EW
   * All other directions are invalid
   * It's a nice system. Simple.
   * ################################
   */
  
  public static final int getDistance(BVertex v0,BVertex v1){
    return getDistance(v0.x,v0.y,v1.x,v1.y);}
  
  public static final int getDistance(int x0,int y0,int x1,int y1){
    if(x0==x1)
      return Math.abs(y0-y1);
    else
      return Math.abs(x0-x1);}
 
}
