package org.fleen.squarzy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.fleen.squarzy.gSquid.SCell;
import org.fleen.squarzy.gSquid.SGrid;

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
  
  public Jig_Frogs(SGrid target){
    setTarget(target);}
  
  /*
   * ################################
   * TARGET
   * ################################
   */
  
  SGrid target;
  
  public void setTarget(SGrid target){
    this.target=target;}

  public SGrid getTarget(){
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
  
  public List<SCell> run(){
    List<SCell>
      frogcells,
      touched=new ArrayList<SCell>();
    //create
    if(rnd.nextDouble()<FROGCREATIONPROBABILITY)
      createFrog();
    //
    for(Frog frog:frogs){
      frog.x++;
      frogcells=getFrogCells(frog);
      touched.addAll(frogcells);
      for(SCell c:frogcells)
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
  
  List<SCell> getFrogCells(Frog frog){
    List<SCell> frogcells=new ArrayList<SCell>();
    SCell c=target.getCell(frog.x,frog.y);
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
