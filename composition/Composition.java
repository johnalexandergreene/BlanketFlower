package org.fleen.blanketFlower.composition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fleen.blanketFlower.cellSystem.CellSystem;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.util.tree.TreeNodeIterator;

/*
 * A tree of shapes 
 * Init, access 
 */
public class Composition{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Composition(Shape root){
    this.root=root;}
  
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
  
  private CellSystem grid;
  
  private void initGrid(){
    grid=new CellSystem(width,height);}
  
  public CellSystem getGrid(){
    return grid;}
  
  /*
   * ################################
   * SHAPE TREE
   * ################################
   */
  
  private static final String[] INITROOTTAGS={};//TODO?
  
  //the root is a grid-fitting rectangle
  private Shape root;
  
  private void initRoot(){
    BVertex[] v={new BVertex(0,0),new BVertex(0,height),new BVertex(width,height),new BVertex(width,0)};
    root=new BPolygon(
      Arrays.asList(v),
      0,
      Arrays.asList(INITROOTTAGS));}
  
  public Shape getRoot(){
    return root;}
  
  public TreeNodeIterator getShapeIterator(){
    return new TreeNodeIterator(root);}
  
  public List<Shape> getShapes(){
    TreeNodeIterator i=new TreeNodeIterator(root);
    List<Shape> shapes=new ArrayList<Shape>();
    Shape shape;
    while(i.hasNext()){
      shape=(Shape)i.next();
      shapes.add(shape);}
    return shapes;}
  
  public List<Shape> getLeafShapes(){
    TreeNodeIterator i=new TreeNodeIterator(root);
    List<Shape> leaves=new ArrayList<Shape>();
    Shape shape;
    while(i.hasNext()){
      shape=(Shape)i.next();
      if(shape.isLeaf())
        leaves.add(shape);}
    return leaves;}
  
}
