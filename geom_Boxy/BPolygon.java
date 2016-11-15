package org.fleen.blanketFlower.geom_Boxy;

import java.awt.geom.Path2D;
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
  
  public BPolygon(List<BVertex> vertices){
    super(0,null);
    this.vertices=new ArrayList<BVertex>(vertices);}
  
  public BPolygon(BVertex... vertices){
    super(0,null);
    this.vertices=new ArrayList<BVertex>(Arrays.asList(vertices));}
  
  /*
   * given an assumedly contiguous cell mass
   */
  public BPolygon(BCellGroup cells){
    super(0,null);
    init(cells);}
  
  public BPolygon(BCell cell){
    super(0,null);
    BCellGroup m=new BCellGroup();
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
  
  //TODO
  public void removeRedundantColinearVertices(){
    
    
  }
  
  public Path2D getPath2D(){
    int s=vertices.size();
    Path2D path=new Path2D.Double();
    BVertex v=vertices.get(0);
    path.moveTo(v.x,v.y);
    for(int i=1;i<s;i++){
      v=vertices.get(i);
      path.lineTo(v.x,v.y);}
    path.closePath();
    return path;}
  
  /*
   * ################################
   * INIT WITH A MASS OF CELLS
   * ################################
   */
  
  /*
   * given an assumedly contiguous mass of cells, init the polygon described by its edge
   * if it ain't contiguous then we might have trouble
   */
  private void init(BCellGroup cells){
    if(cells.isEmpty()){
      throw new IllegalArgumentException("no cells");
    }else if(cells.size()==1){
      initVerticesFor1Cell(cells.iterator().next());
    }else{
      initVerticesForNCells(cells);}}
  
  private void initVerticesFor1Cell(BCell cell){
    vertices=new ArrayList<BVertex>(cell.getSquare().vertices);}
  
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
  private void initVerticesForNCells(BCellGroup cells){
    
  }
  
  
}
