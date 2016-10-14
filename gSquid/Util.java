package org.fleen.squarzy.gSquid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fleen.geom_2D.GD;

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
  
  //FOR TEST
//  public static List<SCell> testheadcells,testtailcells;
  
  
  public static final List<SCell> getFill(SPolygon polygon){
    //get twist
    boolean twist=polygon.getTwist();
    //get headcells and tailcells
    //that's the vertical strands of cells to the left and right of open spaces
    Set<SCell> 
      heads=new HashSet<SCell>(),
      tails=new HashSet<SCell>();
    int dir;
    if(twist==TWIST_CW){
      for(SSeg seg:polygon.getSegs()){
        dir=seg.getForward();
        if(dir==DIR_NORTH)
          heads.addAll(getCellsOnRight(seg,DIR_NORTH));
        else if(dir==DIR_SOUTH)
          tails.addAll(getCellsOnRight(seg,DIR_SOUTH));}
    }else{//twist==TWIST_CCW
      for(SSeg seg:polygon.getSegs()){
        dir=seg.getForward();
        if(dir==DIR_SOUTH)
          heads.addAll(getCellsOnLeft(seg,DIR_SOUTH));
        else if(dir==DIR_NORTH)
          tails.addAll(getCellsOnLeft(seg,DIR_NORTH));}}
    //fill in between heads and tails
    List<SCell> fillz=new ArrayList<SCell>();
    for(SCell h:heads)
      fillRow(h,tails,fillz);
    fillz.addAll(tails);
    
    return fillz;
    
    
    
  }
  
  private static final void fillRow(SCell head,Set<SCell> tails,List<SCell> fillz){
    SCell a=head;
    while(!tails.contains(a)){
      fillz.add(a);
      a=a.getEast();}}
  
  private static final List<SCell> getCellsOnRight(SSeg seg,int heading){
    List<SCell> cells=new ArrayList<SCell>();
    if(heading==DIR_NORTH){
      for(int y=seg.v0.y;y<seg.v1.y;y++)
        cells.add(new SCell(seg.v0.x,y));  
    }else{//heading==DIR_SOUTH
      for(int y=seg.v0.y-1;y>seg.v1.y-1;y--)
        cells.add(new SCell(seg.v0.x-1,y));}
    return cells;}
  
  private static final List<SCell> getCellsOnLeft(SSeg seg,int heading){
    List<SCell> cells=new ArrayList<SCell>();
    if(heading==DIR_NORTH){
      for(int y=seg.v0.y;y<seg.v1.y;y++)
        cells.add(new SCell(seg.v0.x-1,y));  
    }else{//heading==DIR_SOUTH
      for(int y=seg.v0.y-1;y>seg.v1.y-1;y--)
        cells.add(new SCell(seg.v0.x,y));}
    return cells;}
  
  
  
  
  public static final boolean TWIST_CW=true,TWIST_CCW=false;
  /*
   * convert to double[][] and use GD.getSignedArea2D
   * 
   * clockwise is true, counterclockwise is false
   * gather direction deltas
   * 
   * TODO make a faster version
   */
  public static final boolean getTwist(SPolygon polygon){
    int s=polygon.vertices.size();
    double[][] d=new double[polygon.vertices.size()][2];
    SVertex v;
    for(int i=0;i<s;i++){
      v=polygon.vertices.get(i);
      d[i][0]=v.x;
      d[i][1]=v.y;}
    double b=GD.getSignedArea2D(d);
    if(b<0)
      return TWIST_CW;
    else
      return TWIST_CCW;}
  
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