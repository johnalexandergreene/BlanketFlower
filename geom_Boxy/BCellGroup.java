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
    addAll(GB.getCells(polygon));}
  
  /*
   * get the cells contained by the yard's outer polygon (at index 0) 
   * get the cells for all of the inner polygon (holes)
   * remove the hole cells from the outer polygon cells 
   */
  private void init(BYard yard){
    List<Set<BCell>> pcells=new ArrayList<Set<BCell>>();
    for(BPolygon p:yard.polygons)
      pcells.add(new HashSet<BCell>(GB.getCells(p)));
    Set<BCell> outer=pcells.remove(0);
    for(Set<BCell> hole:pcells)
      outer.removeAll(hole);
    addAll(outer);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public boolean isContiguous(){
    return GB.getContiguousSubgroups(this).size()==1;}
  
  /*
   * tests if the specified cell is on the edge of its contiguous group within this group
   * returns true if the cell is adjacent to fewer than 4 cells in this group
   * we do not test this cell for membership in this group
   */
  public boolean isEdge(BCell cell){
    BCell c=cell.getNorth();
    if(!contains(c))return true;
    c=cell.getEast();
    if(!contains(c))return true;
    c=cell.getSouth();
    if(!contains(c))return true;
    c=cell.getWest();
    if(!contains(c))return true;
    return false;}
  
  public List<BShape> getShapes(){
    return GB.getShapes(this);}
  
  
  
}
