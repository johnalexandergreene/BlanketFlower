package org.fleen.squarzy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * add 0 to 5 to each cell's value
 */
public class Jig_Random implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_Random(Grid target){
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
  
  public List<Cell> run(){
    int 
      gridwidth=target.getWidth(),
      gridheight=target.getHeight(),
      z;
    Cell cell;
    List<Cell> cells=new ArrayList<Cell>();
    for(int x=0;x<gridwidth;x++){
      for(int y=0;y<gridheight;y++){
        if(rnd.nextInt(8)==0){
          cell=target.getCell(x,y);
          z=rnd.nextInt(7);
          cell.test+=z;
          cells.add(cell);}}}
    return cells;}

}
