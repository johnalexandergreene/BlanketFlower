package org.fleen.squarzy.gSquid;

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
        cells[x][y]=new SCell(x,y);
      }
    }
  }
  
  public SCell getCell(int x,int y){
    if(x<0||x>=cells.length||y<0||y>=cells[0].length)
      return null;
    return cells[x][y];}

}
