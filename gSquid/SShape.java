package org.fleen.squarzy.gSquid;

import java.io.Serializable;

import org.fleen.util.tag.Tagged;
import org.fleen.util.tree.TreeNode;

/*
 * A vector shape
 * either a polygon or a yard (a yard is a polygon with 1..n polygonal holes)
 * vertex coordinates are integers, corrosponding to topleft corners of cells on the grid
 *   
 *  Like in Forsythia, we use a metagon to get a scale, orientation and location independent 
 *  model of the polygon and we use that to guide our constructive process (ie Jigs) 
 *  
 *  TODO 
 *  
 *  We need integer square geometry (think of a better name : square grid = squid? GSquid) 
 *    eg squaregrid, 2 axii, 4 directions, integer coors
 *   
 */
public interface SShape extends TreeNode,Tagged,Serializable{
  
  

}
