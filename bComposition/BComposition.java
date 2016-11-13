package org.fleen.blanketFlower.bComposition;

import java.util.ArrayList;
import java.util.List;

import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.util.tree.TreeNodeIterator;

/*
 * A bounding box
 * A tree of shapes 
 * Init
 * access to verticves,polygons,cells 
 * 
 * We do NOT use nesting grids. All geometry is absolute.
 * We base geometry operations upon target shape geomerty analysis : vertices, twist, etc
 * We manages symmetry via shape signatures and target geometry
 * 
 * 
 */
public class BComposition{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public BComposition(BShape root){
    this.root=root;}
  
  /*
   * ################################
   * SHAPE TREE
   * ################################
   */
  
  private BShape root;
  
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
  
  /*
   * ################################
   * CELLS
   * The cells of the composition are the cells of the root shape
   * ################################
   */
  
  public BCellGroup getCells(){
    return root.getCells();}
  
}
