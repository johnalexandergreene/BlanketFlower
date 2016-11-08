package org.fleen.blanketFlower.geom_Boxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fleen.blanketFlower.bComposition.BShape;

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
    this.vertices=new ArrayList<BVertex>(vertices);}
  
  public BPolygon(BVertex... vertices){
    super(0,null);
    this.vertices=new ArrayList<BVertex>(Arrays.asList(vertices));}
  
  /*
   * given an assumedly contiguous cell mass
   */
  public BPolygon(BCellMass cells){
    super(0,null);
    init(cells);}
  
  public BPolygon(BCell cell){
    super(0,null);
    BCellMass m=new BCellMass();
    m.add(cell);
    init(m);}
  
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
    return GB.getTwist(this);}
  
  public List<BVertex> getVertices(){
    return vertices;}
  
  /*
   * ################################
   * INIT WITH A MASS OF CELLS
   * ################################
   */
  
  /*
   * given an assumedly contiguous mass of cells, init the polygon described by its edge
   * if it ain't contiguous then we might have trouble
   */
  private void init(BCellMass cells){
    if(cells.isEmpty()){
      throw new IllegalArgumentException("no cells");
    }else if(cells.size()==1){
      initVerticesFor1Cell(cells.iterator().next());
    }else{
      initVerticesForNCells(cells);}}
  
  private void initVerticesFor1Cell(BCell cell){
    vertices=new ArrayList<BVertex>(4);
    vertices.add(new BVertex(cell.x,cell.y));
    vertices.add(new BVertex(cell.x+1,cell.y));
    vertices.add(new BVertex(cell.x+1,cell.y-1));
    vertices.add(new BVertex(cell.x,cell.y-1));}
  
  /*
   * for each cell in cells : c0
   *   for each of the 4 neighbors of c0 : n
   *     if n is not in in cells then get that corrosponding seg and put it in edges
   * now we have all the edges of the mass/es    
   * piece them together into strings, matching endpoints, until we have used up all the edges
   * these strings of segs are our polygons 
   * 
   *   
   */
  private void initVerticesForNCells(BCellMass cells){
    
  }
  
  
}
