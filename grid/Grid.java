package org.fleen.blanketFlower.grid;

import java.util.List;

import org.fleen.blanketFlower.bComposition.BShape;

/*
 * A collection of mutually adjacent 1x1 cells bounded by a shape 
 * 
 * ex A : a polygon
 * 
 *   000000000
 *   000000000
 *      000
 *      000
 *   000000000
 *   000000000
 *   
 * ex B : a yard
 * 
 *   000000000
 *   000000000
 *   000   000
 *   000000000
 *   000000000
 *   
 */
public class Grid{
  
  public Grid(BShape shape){
    initCells(width,height);}
  
  List<Cell> cells;

}
