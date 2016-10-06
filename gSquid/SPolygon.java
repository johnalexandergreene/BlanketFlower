package org.fleen.squarzy.gSquid;

import java.util.ArrayList;
import java.util.List;

/*
 * an SPolygon is defined by a list of SVertices
 * orientation and twist matter so if we want symmetry then we need to generate symmetric figures in our jigs 
 *   
 */
public class SPolygon extends SShape{
  
  private static final long serialVersionUID=1442016090376006753L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPolygon(List<SVertex> vertices,int chorusindex,List<String> tags){
    super(chorusindex,tags);
    this.vertices=new ArrayList<SVertex>();}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  private List<SVertex> vertices;
  
  public List<SVertex> getVertices(){
    return vertices;}
  
  public void setVertices(List<SVertex> vertices){
    this.vertices=vertices;}
  
  List<SCell> getCells(){
    return Util.getFill(this);}
  
}
