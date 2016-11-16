package org.fleen.blanketFlower.test.cellGroupPolygonizer;

import java.util.List;
import java.util.Random;

import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.blanketFlower.geom_Boxy.GB;

/*
 * a random 
 */
@SuppressWarnings("serial")
class CellMess extends BCellGroup{
  
  CellMess(int width,int height){
    this.width=width;
    this.height=height;}
  
  int width,height;
  
  void generate(int cellcount){
    clear();
    int x,y;
    BCell cell;
    Random rnd=new Random();
    while(size()<cellcount){
      x=rnd.nextInt(width);
      y=rnd.nextInt(height);
      cell=new BCell(x,y);
      add(cell);}}
  
  List<BShape> getShapes(){
    return GB.getShapes(this);}

}
