package org.fleen.blanketFlower.geom_Boxy;

public class BVertex{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public BVertex(int x,int y){
    this.x=x;
    this.y=y;}
  
  public BVertex(BVertex v){
    x=v.x;
    y=v.y;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public int x,y;
  
  public int getDirection(BVertex v){
    return GB.getDirection(this,v);}
  
  public int getDistance(BVertex v){
    return GB.getDistance(this,v);}
  
  public BVertex getVertex(int dir,int dis){
    BVertex v;
    if(dir==GB.DIR_NORTH){
      v=new BVertex(x,y+dis);
    }else if(dir==GB.DIR_EAST){
      v=new BVertex(x+dis,y);
    }else if(dir==GB.DIR_SOUTH){
      v=new BVertex(x,y-dis);
    }else if(dir==GB.DIR_WEST){
      v=new BVertex(x-dis,y);
    }else{
      throw new IllegalArgumentException("invalid dir");}
    return v;}

}
