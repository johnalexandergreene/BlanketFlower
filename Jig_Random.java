package org.fleen.squarzy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.squarzy.gSquid.SCell;
import org.fleen.squarzy.gSquid.SGrid;

/*
 * add 0 to 5 to each cell's value
 */
public class Jig_Random implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_Random(SGrid target){
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
  
  public List<SCell> run(){
    int 
      gridwidth=target.getWidth(),
      gridheight=target.getHeight(),
      z;
    SCell cell;
    List<SCell> cells=new ArrayList<SCell>();
    for(int x=0;x<gridwidth;x++){
      for(int y=0;y<gridheight;y++){
        if(rnd.nextInt(8)==0){
          cell=target.getCell(x,y);
          z=rnd.nextInt(7);
          cell.test+=z;
          cells.add(cell);}}}
    return cells;}

}
