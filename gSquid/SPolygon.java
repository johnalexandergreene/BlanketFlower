package org.fleen.squarzy.gSquid;

import java.util.Collection;
import java.util.List;

import org.fleen.util.tree.TreeNode;

public class SPolygon extends SShape{
  
  private static final long serialVersionUID=1442016090376006753L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPolygon(SMetagon metagon,SAnchor anchor,int chorusindex,List<String> tags){
    super(chorusindex,tags);
    this.metagon=metagon;
    this.anchor=anchor;
    initVertices();}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  SMetagon metagon;
  SAnchor anchor;
  
  /*
   * ++++++++++++++++++++++++++++++++
   * VERTICES
   * ++++++++++++++++++++++++++++++++
   */
  
  private List<SVertex> vertices=null;
  
  public List<SVertex> getVertices(){
    return vertices;}
  
  private void initVertices(){
    vertices=metagon.getVertices(anchor);}
  
  

  @Override
  public double getDetailSize(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override
  public double getDistortionLevel(){
    // TODO Auto-generated method stub
    return 0;
  }
  
}
