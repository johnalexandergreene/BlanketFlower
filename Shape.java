package org.fleen.squarzy;

/*
 * A vector shape
 * either a polygon or a yard (a yard is a polygon with 1..n polygonal holes)
 * vertex coordinates are integers, corrosponding to cells on the grid
 *   we draw orthogonal sequences of cells to draw our shape-edges, then fill them in
 *   
 * ex
 * 
 *  1,6            6,6 
 *   #  #  #  #  #  #
 *   
 *   #              #
 *   
 *   #              #
 *   
 *   #              #
 *   
 *   #  #  #  #  #  #  6,2
 *  1,2
 *  
 *  Like in Forsythia, we use a metagon to get a scale, orientation and location independent 
 *  model of the polygon and we use that to guide our constructive process (ie Jigs) 
 *  
 *  TODO 
 *  
 *  We need squaregeometry (think of a better name : square grid = squid? GSquid) 
 *    eg squaregrid, 2 axii, 4 directions, integer coors
 *   
 */
public class Shape{
  
  

}
