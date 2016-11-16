package org.fleen.blanketFlower.geom_Boxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fleen.blanketFlower.bComposition.BShape;

public class BYard extends BShape{

  public BYard(int chorusindex,List<String> tags){
    super(chorusindex,tags);
    // TODO Auto-generated constructor stub
  }
  
  public BYard(BPolygon... polygons){
    this.polygons=new ArrayList<BPolygon>(Arrays.asList(polygons));
    
    
  }
  
  public BYard(){}
  
  public List<BPolygon> polygons;
  
  public List<BPolygon> getPolygons(){
    return polygons;}
  
  public List<BVertex> getVertices(){
    List<BVertex> vertices=new ArrayList<BVertex>();
    for(BPolygon p:polygons)
      vertices.addAll(p.getVertices());
    return vertices;}

}
