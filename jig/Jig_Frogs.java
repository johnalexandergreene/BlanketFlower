package org.fleen.blanketFlower.jig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.grid.Grid;

/*
 * create a C shaped frog
 * 
 *   ###
 *   #
 *   ###
 *   
 * at random location off left edge of grid
 * at every invocation of run, increment location of C and add values of cells at C
 * 
 */
public class Jig_Frogs implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_Frogs(Grid target){
    setTarget(target);}
  
  /*
   * ################################
   * TARGET
   * ################################
   */
  
  Grid target;
  
  public void setTarget(Grid target){
    this.target=target;}

  public Grid getTarget(){
    return target;}

  /*
   * ################################
   * RUN
   * ################################
   */
  
  Random rnd=new Random();
  static final double FROGCREATIONPROBABILITY=0.3;
  Set<Frog> frogs=new HashSet<Frog>();
  static final int FROGVAL=2;
  
  public List<BCell> execute(){
    List<BCell>
      frogcells,
      touched=new ArrayList<BCell>();
    //create
    if(rnd.nextDouble()<FROGCREATIONPROBABILITY)
      createFrog();
    //
    for(Frog frog:frogs){
      frog.x++;
      frogcells=getFrogCells(frog);
      touched.addAll(frogcells);
      for(BCell c:frogcells)
        c.test+=FROGVAL;}
    //cull
    Iterator<Frog> i=frogs.iterator();
    Frog frog;
    int edge=target.getWidth()+5;
    while(i.hasNext()){
      frog=i.next();
      if(frog.x>edge)
        i.remove();}
    //
    return touched;}
  
  List<BCell> getFrogCells(Frog frog){
    List<BCell> frogcells=new ArrayList<BCell>();
    BCell c=target.getCell(frog.x,frog.y);
    if(c!=null)frogcells.add(c);
    c=target.getCell(frog.x+1,frog.y);
    if(c!=null)frogcells.add(c);
    c=target.getCell(frog.x+2,frog.y);
    if(c!=null)frogcells.add(c);
    c=target.getCell(frog.x,frog.y+1);
    if(c!=null)frogcells.add(c);
    c=target.getCell(frog.x,frog.y+2);
    if(c!=null)frogcells.add(c);
    c=target.getCell(frog.x+1,frog.y+2);
    if(c!=null)frogcells.add(c);
    c=target.getCell(frog.x+2,frog.y+2);
    if(c!=null)frogcells.add(c);
    return frogcells;}
  
  void createFrog(){
    Frog frog=new Frog();
    frogs.add(frog);
    frog.x=-3;
    frog.y=rnd.nextInt(target.getHeight());}
  
  class Frog{
    int x,y;
    
    int size;
  }

}
