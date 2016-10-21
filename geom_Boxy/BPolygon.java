package org.fleen.blanketFlower.geom_Boxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.blanketFlower.grid.Cell;
import org.fleen.blanketFlower.grid.Grid;

/*
 * an SPolygon is defined by a list of SVertices
 * orientation and twist matter so if we want symmetry then we need to generate symmetric figures in our jigs 
 *   
 */
public class BPolygon extends BShape{
  
  private static final long serialVersionUID=1442016090376006753L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public BPolygon(List<BVertex> vertices,int chorusindex,List<String> tags){
    super(chorusindex,tags);
    this.vertices=new ArrayList<BVertex>(vertices);
    System.out.println("polygon init vertex count = "+this.vertices.size());}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public List<BVertex> vertices;
  
  List<BSeg> getSegs(){
    List<BSeg> segs=new ArrayList<BSeg>();
    int i1,
      s=vertices.size();
    BVertex v0,v1;
    BSeg seg;
    for(int i0=0;i0<s;i0++){
      i1=i0+1;
      if(i1==s)i1=0;
      v0=vertices.get(i0);
      v1=vertices.get(i1);
      seg=new BSeg(v0,v1);
      segs.add(seg);}
    return segs;}
  
  public boolean getTwist(){
    return Util.getTwist(this);}
  
  public List<BVertex> getVertices(){
    return vertices;}
  
  /*
   * ################################
   * GET CELLS
   * Returns the 1x1 cells enclosed by by the polygon edge
   * When gleaning cells we consult the param grid
   * For each cell
   *   if the cell is contained within the grid then we return that
   *   if it isn't then we create a new one 
   * ################################
   */
  
  public List<Cell> getCells(Grid grid){
    List<Cell> 
      fillcells=Util.getFill(this),
      gridfillintersection=new ArrayList<Cell>();
    for(Cell c:fillcells){
      if(grid.containsCell(c))
        gridfillintersection.add(grid.getCell(c));
      else
        gridfillintersection.add(c);}
    return gridfillintersection;}
  
}
