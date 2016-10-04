package org.fleen.squarzy.gSquid;

import java.util.Collection;
import java.util.List;

import org.fleen.util.tree.TreeNode;

public class SPolygon implements SShape{
  
  private static final long serialVersionUID=1442016090376006753L;
  
  SMetagon metagon;
  SAnchor anchor;
  /*
   * ################################
   * TREE
   * ################################
   */
  
  public TreeNode getParent(){
    // TODO Auto-generated method stub
    return null;
  }
  
  public void setParent(TreeNode node){
    // TODO Auto-generated method stub
    
  }
  @Override
  public List<? extends TreeNode> getChildren(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public TreeNode getChild(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public void setChildren(List<? extends TreeNode> nodes){
    // TODO Auto-generated method stub
    
  }
  @Override
  public void setChild(TreeNode node){
    // TODO Auto-generated method stub
    
  }
  @Override
  public void addChild(TreeNode node){
    // TODO Auto-generated method stub
    
  }
  @Override
  public int getChildCount(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override
  public boolean hasChildren(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public void removeChildren(Collection<? extends TreeNode> children){
    // TODO Auto-generated method stub
    
  }
  @Override
  public void clearChildren(){
    // TODO Auto-generated method stub
    
  }
  @Override
  public boolean isRoot(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public boolean isLeaf(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public int getDepth(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override
  public TreeNode getRoot(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public TreeNode getAncestor(int levels){
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public List<TreeNode> getSiblings(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public void setTags(String...tags){
    // TODO Auto-generated method stub
    
  }
  @Override
  public void setTags(List<String> tags){
    // TODO Auto-generated method stub
    
  }
  @Override
  public List<String> getTags(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public boolean hasTags(String...tags){
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public boolean hasTags(List<String> tags){
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public void addTags(String...tags){
    // TODO Auto-generated method stub
    
  }
  @Override
  public void addTags(List<String> tags){
    // TODO Auto-generated method stub
    
  }
  @Override
  public void removeTags(String...tags){
    // TODO Auto-generated method stub
    
  }
  @Override
  public void removeTags(List<String> tags){
    // TODO Auto-generated method stub
    
  }

}
