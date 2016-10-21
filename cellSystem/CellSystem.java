package org.fleen.blanketFlower.cellSystem;

import java.util.Iterator;

/*
 * A rectangular array of 1x1 cells
 * it is used to render a BlanketFlower composition
 * each cell is enclosed by 1..n shapes
 * 
 * one technique for rendering the composition is to get the sum of chorus indices for all shapes that enclose a cell : ISUM
 * then mod ISUM against an array of colors
 */
public class CellSystem{
  
  public CellSystem(int width,int height){
    initCells(width,height);}
  
  Cell[][] cells;
  
  public int getWidth(){
    return cells.length;}
  
  public int getHeight(){
    return cells[0].length;}
  
  private void initCells(int width,int height){
    cells=new Cell[width][height];
    for(int x=0;x<width;x++){
      for(int y=0;y<height;y++){
        cells[x][y]=new Cell(x,y);}}}
  
  public Cell getCell(int x,int y){
    if(x<0||x>=cells.length||y<0||y>=cells[0].length)
      return null;
    return cells[x][y];}
  
  /*
   * return the cell in this grid corrosponding to the specified
   */
  public Cell getCell(Cell c){
    return getCell(c.x,c.y);}
  
  public boolean containsCell(Cell c){
    int w=getWidth(),h=getHeight();
    boolean d=c.x>-1&&c.x<w&&c.y>-1&&c.y<h;
    return d;}
  
  public Iterator<Cell> getCellIterator(){
    return new CellIterator();}
  
  private class CellIterator implements Iterator<Cell>{
    int x=0,y=0;

    public boolean hasNext(){
      return y<cells[0].length;}

    public Cell next(){
      Cell c=cells[x][y];
      x++;
      if(x==cells.length){
        x=0;
        y++;}
      return c;}

    public void remove(){
      throw new IllegalArgumentException("not implemented");}}

}
