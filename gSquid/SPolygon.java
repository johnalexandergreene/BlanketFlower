package org.fleen.squarzy.gSquid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * an SPolygon is defined by a list of SVertices
 * orientation and twist matter so if we want symmetry then we need to generate symmetric figures in our jigs 
 *   
 */
public class SPolygon extends SShape implements Iterable<SVertex>{
  
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
  
  public Iterator<SVertex> iterator(){
    return vertices.iterator();}
  
  public int size(){
    return vertices.size();}
  
  public SVertex getVertex(int i){
    return vertices.get(i);}
  
  List<SCell> getCells(){
    return Util.getFill(this);}
  
  List<SSeg> getSegs(){
    List<SSeg> segs=new ArrayList<SSeg>();
    int i1,
      s=vertices.size();
    SVertex v0,v1;
    SSeg seg;
    for(int i0=0;i0<s;i0++){
      i1=i0+1;
      if(i1==s)i1=0;
      v0=vertices.get(i0);
      v1=vertices.get(i1);
      seg=new SSeg(v0,v1);
      segs.add(seg);}
    return segs;}
  
  public boolean getTwist(){
    return Util.getTwist(this);}
  
}
