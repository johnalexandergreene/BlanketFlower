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
   * GRID
   * We provide this here because it's our main rendering device and
   * we use it all the time
   * ################################
   */
  
}
