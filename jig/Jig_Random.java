package org.fleen.blanketFlower.jig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.grid.Grid;

/*
 * add 0 to 5 to each cell's value
 */
public class Jig_Random implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_Random(BShape target){
    setTarget(target);}
  
  public Jig_Random(){}
  
  /*
   * ################################
   * DUPE
   * ################################
   */
  
  public Jig dupe(){
    // TODO Auto-generated method stub
    return null;
  }
  
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
   * RUN
   * ################################
   */
  
  Random rnd=new Random();
  
  public void execute(){
    
    
    
    int 
      gridwidth=target.getWidth(),
      gridheight=target.getHeight(),
      z;
    BCell cell;
    List<BCell> cells=new ArrayList<BCell>();
    for(int x=0;x<gridwidth;x++){
      for(int y=0;y<gridheight;y++){
        if(rnd.nextInt(8)==0){
          cell=target.getCell(x,y);
          z=rnd.nextInt(7);
          cell.test+=z;
          cells.add(cell);}}}
    return cells;}

}
