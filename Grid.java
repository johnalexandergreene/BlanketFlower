package org.fleen.squarzy;

public class Grid{
  
  public Grid(int width,int height){
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
        cells[x][y]=new Cell(x,y);
      }
    }
  }
  
  public Cell getCell(int x,int y){
    if(x<0||x>=cells.length||y<0||y>=cells[0].length)
      return null;
    return cells[x][y];}

}
