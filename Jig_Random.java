package org.fleen.squarzy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.squarzy.gSquid.SCell;
import org.fleen.squarzy.gSquid.SGrid;
import org.fleen.squarzy.gSquid.SShape;

/*
 * add 0 to 5 to each cell's value
 */
public class Jig_Random implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_Random(SShape target){
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
  
  SShape target;
  
  public void setTarget(SShape target){
    this.target=target;}

  public SShape getTarget(){
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
