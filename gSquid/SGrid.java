package org.fleen.blanketFlower.gSquid;

import java.util.Iterator;

public class SGrid{
  
  public SGrid(int width,int height){
    initCells(width,height);}
  
  SCell[][] cells;
  
  public int getWidth(){
    return cells.length;}
  
  public int getHeight(){
    return cells[0].length;}
  
  private void initCells(int width,int height){
    cells=new SCell[width][height];
    for(int x=0;x<width;x++){
      for(int y=0;y<height;y++){
        cells[x][y]=new SCell(x,y);}}}
  
  public SCell getCell(int x,int y){
    if(x<0||x>=cells.length||y<0||y>=cells[0].length)
      return null;
    return cells[x][y];}
  
  /*
   * return the cell in this grid corrosponding to the specified
   */
  public SCell getCell(SCell c){
    return getCell(c.x,c.y);}
  
  public boolean containsCell(SCell c){
    int w=getWidth(),h=getHeight();
    boolean d=c.x>-1&&c.x<w&&c.y>-1&&c.y<h;
    return d;}
  
  public Iterator<SCell> getCellIterator(){
    return new CellIterator();}
  
  private class CellIterator implements Iterator<SCell>{
    int x=0,y=0;

    public boolean hasNext(){
      return y<cells[0].length;}

    public SCell next(){
      SCell c=cells[x][y];
      x++;
      if(x==cells.length){
        x=0;
        y++;}
      return c;}

    public void remove(){
      throw new IllegalArgumentException("not implemented");}}

}
