package org.fleen.blanketFlower.geom_Boxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fleen.blanketFlower.bComposition.BShape;

/*
 * contiguous or not.
 * various tests and transformations
 */
public class BCellMass extends HashSet<BCell>{

  private static final long serialVersionUID=-8452300624413871017L;
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public BCellMass(){}
  
  public BCellMass(BPolygon polygon){
    init(polygon);}
  
  public BCellMass(BYard yard){
    init(yard);}
  
  public BCellMass(BShape shape){
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
  
}
