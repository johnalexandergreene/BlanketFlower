package org.fleen.blanketFlower.bComposition;

import java.util.ArrayList;
import java.util.List;

import org.fleen.util.tree.TreeNodeIterator;

/*
 * A tree of shapes 
 * Init, access 
 * 
 * Each shape defines within its domain a local grid and its children are defined within that grid
 * The root shape is defined within the default grid
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
   * METRICS
   * ################################
   */
  
  public int getWidth(){
    return root.getWidth();}
  
  public int getHeight(){
    return root.getHeight();}
  
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
  
}
