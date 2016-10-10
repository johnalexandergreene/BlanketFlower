package org.fleen.squarzy.gSquid;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.fleen.squarzy.Jig;
import org.fleen.squarzy.Squarzy;
import org.fleen.util.tag.TagManager;
import org.fleen.util.tag.Tagged;
import org.fleen.util.tree.TreeNode;
import org.fleen.util.tree.TreeNodeIterator;
import org.fleen.util.tree.TreeNodeServices;

/*
 * A vector shape
 * either a polygon or a yard (a yard is a polygon with 1..n polygonal holes)
 * vertex coordinates are integers, corrosponding to topleft corners of cells on the grid
 *   
 *  Like in Forsythia, we use a metagon to get a scale, orientation and location independent 
 *  model of the polygon and we use that to guide our constructive process (ie Jigs) 
 *  
 *  TODO 
 *  
 *  We need integer square geometry (think of a better name : square grid = squid? GSquid) 
 *    eg squaregrid, 2 axii, 4 directions, integer coors
 *   
 */
public abstract class SShape implements TreeNode,Serializable,Squarzy,Tagged{
  
  private static final long serialVersionUID=4092254036116132280L;

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SShape(int chorusindex,List<String> tags){
    this.chorusindex=chorusindex;
    addTags(tags);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public abstract List<SVertex> getVertices();
  
  public int[] getBounds(){
    int 
      xmin=Integer.MAX_VALUE,
      ymin=Integer.MAX_VALUE,
      xmax=Integer.MIN_VALUE,
      ymax=Integer.MIN_VALUE;
    List<SVertex> vertices=getVertices();
    for(SVertex v:vertices){
      if(v.x<xmin)xmin=v.x;
      if(v.y<ymin)ymin=v.y;
      if(v.x>xmax)xmax=v.x;
      if(v.y>ymax)ymax=v.y;}
    return new int[]{xmin,ymin,xmax,ymax};}
  
  public abstract List<SCell> getCells(SGrid grid);
  
  /*
   * ################################
   * JIG
   * ################################
   */
  
  public Jig jig=null;
  
  /*
   * ################################
   * TREENODE
   * ################################
   */
  
  public TreeNodeServices treenodeservices=new TreeNodeServices();
  
  /*
   * ++++++++++++++++++++++++++++++++
   * IMPLEMENTATION OF TreeNode INTERFACE
   */
  
  public TreeNode getParent(){
    return treenodeservices.getParent();}
  
  public void setParent(TreeNode node){
    treenodeservices.setParent(node);}
  
  public List<? extends TreeNode> getChildren(){
    return treenodeservices.getChildren();}
  
  public TreeNode getChild(){
    return treenodeservices.getChild();}
  
  public void setChildren(List<? extends TreeNode> nodes){
    treenodeservices.setChildren(nodes);}
  
  public void setChild(TreeNode node){
    treenodeservices.setChild(node);}
  
  public void addChild(TreeNode node){
    treenodeservices.addChild(node);}
  
  public int getChildCount(){
    return treenodeservices.getChildCount();}
  
  public boolean hasChildren(){
    return treenodeservices.hasChildren();}
  
  public void clearChildren(){
    treenodeservices.clearChildren();}
  
  public void removeChild(TreeNode child){
    treenodeservices.removeChild(child);}
  
  public void removeChildren(Collection<? extends TreeNode> children){
    treenodeservices.removeChildren(children);}
  
  public boolean isRoot(){
    return treenodeservices.isRoot();}
  
  public boolean isLeaf(){
    return treenodeservices.isLeaf();}
  
  public int getDepth(){
    return treenodeservices.getDepth(this);}
  
  public TreeNode getRoot(){
    return treenodeservices.getRoot(this);}
  
  public TreeNode getAncestor(int levels){
    return treenodeservices.getAncestor(this,levels);}
  
  public List<TreeNode> getSiblings(){
    return treenodeservices.getSiblings(this);}
  
  /**
   * @return nodes in the branch rooted at this node
   */
  public TreeNodeIterator getNodeIterator(){
    return new TreeNodeIterator(this);}
  
  /*
   * ################################
   * CHORUS INDEX AND GEOMETRIC CONTEXT SIGNATURE
   * 
   * Similar geometry with similiar context gets the same chorus index
   * Stuff that differs gets differing chorus index
   * 
   * The chorus indices of a geometry object and its ancestors describes a context-unique signature
   * 
   * By cultivating geometry with identical signatures identically we get symmetry
   * 
   * It's also just useful info.
   * 
   * Polygons get index 0..n
   * Yards get a constant. probably MAXINT
   * ################################
   */
  
  private int chorusindex;
  private SShapeSignature signature=null;
  
  public int getChorusIndex(){
    return chorusindex;}
  
  public SShapeSignature getSignature(){
    if(signature==null)signature=new SShapeSignature(this);
    return signature;}
  
  /*
   * ################################
   * TAGS
   * ################################
   */
  
  private TagManager tagmanager=new TagManager();
  
  public void setTags(String... tags){
    tagmanager.setTags(tags);}
  
  public void setTags(List<String> tags){
    tagmanager.setTags(tags);}
  
  public List<String> getTags(){
    return tagmanager.getTags();}
  
  public boolean hasTags(String... tags){
    return tagmanager.hasTags(tags);}
  
  public boolean hasTags(List<String> tags){
    return tagmanager.hasTags(tags);}
  
  public void addTags(String... tags){
    tagmanager.addTags(tags);}
  
  public void addTags(List<String> tags){
    tagmanager.addTags(tags);}
  
  public void removeTags(String... tags){
    tagmanager.removeTags(tags);}
  
  public void removeTags(List<String> tags){
    tagmanager.removeTags(tags);}
  
  /*
   * ################################
   * GENERAL PURPOSE OBJECT
   * ################################
   */
  
  public Object gpobject;
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return "[ "+getClass().getSimpleName()+" : "+hashCode()+" : "+tagmanager.toString()+" ]";}
  
  

}
