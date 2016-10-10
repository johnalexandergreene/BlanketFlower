package org.fleen.squarzy.gSquid;

import java.util.ArrayList;
import java.util.List;

public class SYard extends SShape{

  public SYard(int chorusindex,List<String> tags){
    super(chorusindex,tags);
    // TODO Auto-generated constructor stub
  }
  
  public List<SPolygon> polygons;
  
  public List<SVertex> getVertices(){
    List<SVertex> vertices=new ArrayList<SVertex>();
    for(SPolygon p:polygons)
      vertices.addAll(p.getVertices());
    return vertices;}

}
