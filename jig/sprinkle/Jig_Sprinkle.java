package org.fleen.blanketFlower.jig.sprinkle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.jig.Jig;

/*
 * a haze of static
 * given the target
 * clear existing dots
 *   a dot is a 1-square shape
 * create new dots
 */
public class Jig_Sprinkle implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_Sprinkle(BShape target){
    setTarget(target);}
  
  public Jig_Sprinkle(){}
  
  /*
   * ################################
   * CLONE
   * ################################
   */
  
  public Object clone(){
    return new Jig_Sprinkle();}
  
  /*
   * ################################
   * TARGET
   * ################################
   */
  
  BShape target;
  
  public void setTarget(BShape target){
    this.target=target;}

  public BShape getTarget(){
    return target;}

  /*
   * ################################
   * EXECUTE
   * clear old dots
   * create new dots
   * our dots all get chorus index = 1
   * ################################
   */
  
  Random rnd=new Random();
  List<BShape> dots=new ArrayList<BShape>();
  
  public void execute(int frameindex){
    clearDots();
    createDots();}
  
  private void clearDots(){
    for(BShape dot:dots){
      dot.getParent().removeChild(dot);
      dot.setParent(null);}
    dots.clear();}
  
  //as a proportion of target cell count
  //must be in range (0,1)
  private static final double DOTCOUNT=0.02;//TODO this should be a param
  
  private void createDots(){
    List<BCell> targetcells=new ArrayList<BCell>(target.getCells());
    int s=targetcells.size();
    if(s<2)return;//fail
    int dotcount=(int)(targetcells.size()*DOTCOUNT);
    if(dotcount<1)dotcount=1;
    //get random cells
    Set<BCell> dotcells=new HashSet<BCell>();
    while(dotcells.size()<dotcount)
      dotcells.add(targetcells.get(rnd.nextInt(s)));
    //create and install shapes
    BPolygon dot;
    for(BCell dotcell:dotcells){
      dot=new BPolygon(dotcell);
      dot.setChorusIndex(0);
      dot.setColorIndex(1);
      dot.setTags("dot");
      dot.setParent(target);
      target.addChild(dot);
      dots.add(dot);}}

}
