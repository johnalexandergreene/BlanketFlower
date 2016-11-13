package org.fleen.blanketFlower.geom_Boxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fleen.blanketFlower.bComposition.BShape;

/*
 * arbitrarily contiguous
 * 
 * we've got various tests and transformations here
 * 
 * a grid of cells
 * add, remove
 * test neighbors
 * 
 * used in the arbitrarily contiguous cell group polygonizer
 * 
 * used for whenever we need to do cellular ajacentness-rules type ops
 */
public class BCellGroup extends HashSet<BCell>{

  private static final long serialVersionUID=-8452300624413871017L;
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public BCellGroup(){}
  
  public BCellGroup(BPolygon polygon){
    init(polygon);}
  
  public BCellGroup(BYard yard){
    init(yard);}
  
  public BCellGroup(BShape shape){
    if(shape instanceof BPolygon)
      init((BPolygon)shape);
    else
      init((BYard)shape);}
  
  /*
   * ################################
   * INIT
   * ################################
   */
  
  private void init(BPolygon polygon){
    addAll(getCells(polygon));}
  
  /*
   * get the cells contained by the yard's outer polygon (at index 0) 
   * get the cells for all of the inner polygon (holes)
   * remove the hole cells from the outer polygon cells 
   */
  private void init(BYard yard){
    List<Set<BCell>> pcells=new ArrayList<Set<BCell>>();
    for(BPolygon p:yard.polygons)
      pcells.add(new HashSet<BCell>(getCells(p)));
    Set<BCell> outer=pcells.remove(0);
    for(Set<BCell> hole:pcells)
      outer.removeAll(hole);
    addAll(outer);}
  
  /*
   * ################################
   * STATIC POLYGON CELL FILL
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
   * ARBITRARILY CONTIGUOUS CELL GROUP TO LIST OF CONTIGUOUS SUBGROUPES
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
   * ARBITRARILY CONTIGUOUS CELL GROUP TO POLYGONS
   *
   * given cell group : BCellGroup M
   * get contiguous subgroupes within M : list C (see above)
   * for each contiguous group within C create one polygon.
   * ################################
   */
  
  public static final List<BPolygon> getPolygons(BCellGroup agroup){
    List<BCellGroup> cgroups=getContiguousSubgroups(agroup);
    List<BPolygon> polygons=new ArrayList<BPolygon>(cgroups.size());
    BPolygon polygon;
    for(BCellGroup cgroup:cgroups){
      polygon=getPolygon(cgroup);
      polygons.add(polygon);}
    return polygons;}
  
  /*
   * ################################
   * CONTIGUOUS GROUP TO POLYGON
   * 
   * given contiguous cell group
   * get the polygon described by its edge
   * the group had better be contiguous
   * 
   * get an edge cell. A cell that is adjacent to < 4 other cells in this group.
   * get an edge cell adjacent to it
   * and so on until we have the loop of edge cells in a list
   * then piece together their exposed edges to make the polygon
   * 
   * I think we can handle degenerate polygons that self-touch. Edge-edge or point-point. But why would we want to?
   *  
   * ################################
   */
  
  public static BPolygon getPolygon(BCellGroup cgroup){
    
  }
  
  
  
  
  
  
  
}
