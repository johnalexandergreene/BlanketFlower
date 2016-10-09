package org.fleen.squarzy;

import java.util.Arrays;

import org.fleen.squarzy.gSquid.SGrid;
import org.fleen.squarzy.gSquid.SPolygon;
import org.fleen.squarzy.gSquid.SShape;
import org.fleen.squarzy.gSquid.SVertex;
import org.fleen.util.tree.TreeNodeIterator;

public class Composition{
  
  public Composition(int w,int h){
    width=w;
    height=h;
    initGrid();
    initRoot();}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  private int width,height;
  
  /*
   * ################################
   * GRID
   * ################################
   */
  
  private SGrid grid;
  
  private void initGrid(){
    grid=new SGrid(width,height);}
  
  public SGrid getGrid(){
    return grid;}
  
  /*
   * ################################
   * SHAPE TREE
   * ################################
   */
  
  private static final String[] INITROOTTAGS={};//TODO?
  
  //the root is a grid-fitting rectangle
  private SShape root;
  
  private void initRoot(){
    SVertex[] v={new SVertex(0,0),new SVertex(width,0),new SVertex(width,height),new SVertex(0,height)};
    root=new SPolygon(
      Arrays.asList(v),
      0,
      Arrays.asList(INITROOTTAGS));}
  
  public SShape getRoot(){
    return root;}
  
  TreeNodeIterator getShapeIterator(){
    return new TreeNodeIterator(root);}
  
}
