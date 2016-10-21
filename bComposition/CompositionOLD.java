package org.fleen.blanketFlower.bComposition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.grid.Grid;
import org.fleen.util.tree.TreeNodeIterator;

/*
 * A tree of shapes 
 * Init, access 
 */
public class CompositionOLD{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public CompositionOLD(BShape root){
    this.root=root;}
  
  public CompositionOLD(int w,int h){
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
  
  private Grid grid;
  
  private void initGrid(){
    grid=new Grid(width,height);}
  
  public Grid getGrid(){
    return grid;}
  
  /*
   * ################################
   * SHAPE TREE
   * ################################
   */
  
  private static final String[] INITROOTTAGS={};//TODO?
  
  //the root is a grid-fitting rectangle
  private BShape root;
  
  private void initRoot(){
    BVertex[] v={new BVertex(0,0),new BVertex(0,height),new BVertex(width,height),new BVertex(width,0)};
    root=new BPolygon(
      Arrays.asList(v),
      0,
      Arrays.asList(INITROOTTAGS));}
  
  public BShape getRoot(){
    return root;}
  
  public TreeNodeIterator getShapeIterator(){
    return new TreeNodeIterator(root);}
  
  public List<BShape> getShapes(){
    TreeNodeIterator i=new TreeNodeIterator(root);
    List<BShape> shapes=new ArrayList<BShape>();
    BShape shape;
    while(i.hasNext()){
      shape=(BShape)i.next();
      shapes.add(shape);}
    return shapes;}
  
  public List<BShape> getLeafShapes(){
    TreeNodeIterator i=new TreeNodeIterator(root);
    List<BShape> leaves=new ArrayList<BShape>();
    BShape shape;
    while(i.hasNext()){
      shape=(BShape)i.next();
      if(shape.isLeaf())
        leaves.add(shape);}
    return leaves;}
  
}
