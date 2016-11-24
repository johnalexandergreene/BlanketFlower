package org.fleen.blanketFlower.geom_Boxy;

import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BYard extends BShape{

  private static final long serialVersionUID=2320841650019876227L;
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */

  public BYard(List<BPolygon> polygons,int chorusindex,int colorindex,List<String> tags){
    super(chorusindex,tags);
  }
  
  public BYard(BPolygon... polygons){
    this.polygons=new ArrayList<BPolygon>(Arrays.asList(polygons));
    
    
  }
  
  public BYard(){}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public List<BPolygon> polygons;
  
  public List<BPolygon> getPolygons(){
    return polygons;}
  
  public List<BVertex> getVertices(){
    List<BVertex> vertices=new ArrayList<BVertex>();
    for(BPolygon p:polygons)
      vertices.addAll(p.getVertices());
    return vertices;}

  public Path2D getPath2D(){
    Path2D path=new Path2D.Double();
    for(BPolygon polygon:polygons)
      path.append(polygon.getPath2D(),false);
    return path;}

}
