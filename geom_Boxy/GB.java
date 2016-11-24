package org.fleen.blanketFlower.geom_Boxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fleen.blanketFlower.geom_Boxy.contiguousCellGroupShaper.ContiguousCellGroupShaper;
import org.fleen.geom_2D.GD;

/*
 * Boxy geometry
 * 
 * an array of square cells
 * 
 * all coordinates are integers
 * 
 * north is up, Y+ 
 * east is right, X+
 * 
 * cell group contiguity connection is via flats
 * 
 * 4 valid directions for distance and direction checking
 * (?)
 * 
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
   * get dir(v0,v1) and dir(v1,v2)
   * get the direction delta
   * aka the angle
   */
  public static final int getDirectionDelta(BVertex v0,BVertex v1,BVertex v2){
    int 
      d0=v0.getDirection(v1),
      d1=v1.getDirection(v2);
    int dd=getDirectionDelta(d0,d1);
    return dd;}
  
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
  
  /*
   * ################################
   * POLYGON CELL FILL
   * Given a polygon, get the cells contained within that polygon
   * ################################
   */
  
  public static final List<BCell> getCells(BPolygon polygon){
    //get twist
    boolean twist=polygon.getTwist();
    //get headcells and tailcells
    //that's the vertical strands of cells to the left and right of open spaces
    Set<BCell> 
      heads=new HashSet<BCell>(),
      tails=new HashSet<BCell>();
    int dir;
    if(twist==GB.TWIST_CW){
      for(BSeg seg:polygon.getSegs()){
        dir=seg.getForward();
        if(dir==GB.DIR_NORTH)
          heads.addAll(getCellsOnRight(seg,GB.DIR_NORTH));
        else if(dir==GB.DIR_SOUTH)
          tails.addAll(getCellsOnRight(seg,GB.DIR_SOUTH));}
    }else{//twist==TWIST_CCW
      for(BSeg seg:polygon.getSegs()){
        dir=seg.getForward();
        if(dir==GB.DIR_SOUTH)
          heads.addAll(getCellsOnLeft(seg,GB.DIR_SOUTH));
        else if(dir==GB.DIR_NORTH)
          tails.addAll(getCellsOnLeft(seg,GB.DIR_NORTH));}}
    //fill in between heads and tails
    List<BCell> fill=new ArrayList<BCell>();
    for(BCell h:heads)
      fillRow(h,tails,fill);
    fill.addAll(tails);
    return fill;}
  
  private static final void fillRow(BCell head,Set<BCell> tails,List<BCell> fillz){
    BCell a=head;
    while(!tails.contains(a)){
      fillz.add(a);
      a=a.getEast();}}
  
  private static final List<BCell> getCellsOnRight(BSeg seg,int heading){
    List<BCell> cells=new ArrayList<BCell>();
    if(heading==GB.DIR_NORTH){
      for(int y=seg.v0.y;y<seg.v1.y;y++)
        cells.add(new BCell(seg.v0.x,y));  
    }else{//heading==DIR_SOUTH
      for(int y=seg.v0.y-1;y>seg.v1.y-1;y--)
        cells.add(new BCell(seg.v0.x-1,y));}
    return cells;}
  
  private static final List<BCell> getCellsOnLeft(BSeg seg,int heading){
    List<BCell> cells=new ArrayList<BCell>();
    if(heading==GB.DIR_NORTH){
      for(int y=seg.v0.y;y<seg.v1.y;y++)
        cells.add(new BCell(seg.v0.x-1,y));  
    }else{//heading==DIR_SOUTH
      for(int y=seg.v0.y-1;y>seg.v1.y-1;y--)
        cells.add(new BCell(seg.v0.x,y));}
    return cells;}
  
  /*
   * ################################
   * 
   * ARBITRARILY CONTIGUOUS CELL GROUP TO LIST OF CONTIGUOUS SUBGROUPS
   * 
   * get mutually contiguous sets of cells
   * given our arbitrarily contiguous square cell group : M
   * remove an arbitrary cell from M : C
   * create new contiguouscellsset from C: S(C)
   * for each cell in M : C0
   *   if C0 is adjacent to any cell in S
   *     remove C0 from M
   *     add C0 to S
   * keep doing this until we can find no cell in M that is adjacent to any cell in S.
   * We now have a contiguous set : S0
   * get S1, S2 etc 
   * get another arbitrary cell C, etc, until M is empty
   * ################################
   */
  
  public static final List<BCellGroup> getContiguousSubgroups(BCellGroup target){
    List<BCell> targetpool=new ArrayList<BCell>(target);
    List<BCellGroup> subgroups=new ArrayList<BCellGroup>();
    BCellGroup subgroup;
    while(!targetpool.isEmpty()){
      subgroup=getContiguousSubGroup(targetpool);
      subgroups.add(subgroup);}
    return subgroups;}
  
  private static BCellGroup getContiguousSubGroup(List<BCell> targetpool){
    BCellGroup subgroup=new BCellGroup();
    BCell cell=targetpool.remove(targetpool.size()-1);
    subgroup.add(cell);
    boolean moved=true;
    while(moved)
      moved=moveCellFromPoolToGroup(targetpool,subgroup);
    return subgroup;}
  
  /*
   * test every cell (c0) in targetpool against every cell (c1) in subgroup 
   *   if c0 is adjacent-flat to c1 (adjacent flat-to-flat, as opposed to corner-to-corner) then 
   *     remove c0 from targetpool
   *     add c0 to subgroup
   *     return true
   * if no cell in targetpool tested agacent against any cell in subgroup then return false. 
   */
  private static boolean moveCellFromPoolToGroup(List<BCell> targetpool,BCellGroup subgroup){
    for(BCell c0:targetpool)
      for(BCell c1:subgroup)
        if(c0.isAdjacentFlat(c1)){
          targetpool.remove(c0);
          subgroup.add(c0);
          return true;}
    return false;}
  
  /*
   * ################################
   * CELL GROUP TO SHAPE
   * ################################
   */
  
  private static final ContiguousCellGroupShaper ccgs=new ContiguousCellGroupShaper();
  
  /*
   * given cell group : BCellGroup g
   * get contiguous subgroups within g : cgroups
   * for each contiguous group within cgroups create one shape (polygon or yard).
   */
  public static final List<BShape> getShapes(BCellGroup g){
    List<BCellGroup> cgroups=getContiguousSubgroups(g);
    List<BShape> shapes=new ArrayList<BShape>(cgroups.size());
    BShape shape;
    for(BCellGroup cgroup:cgroups){
      shape=getShapeFromContiguousGroup(cgroup);
      shapes.add(shape);}
    return shapes;}
  
  /*
   * given a contiguous cell group return a shape
   */
  public static final BShape getShapeFromContiguousGroup(BCellGroup contiguous){
    BShape shape=ccgs.getShape(contiguous);
    return shape;}
  
  
  
 
}
