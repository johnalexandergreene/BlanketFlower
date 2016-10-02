package org.fleen.squarzy;


/*
 * A Jig 
 * 
 *   Upon invocation of "run", creates a system of shapes within the domain of a target shape.
 *     Upon further invocations of "run", modifies/creates/destroys shapes, thus creating new frames, for animation
 *     We invoke run multiple times to glean a sequence of frames
 *     
 *   Assigns a distinguishing index to each of these new shapes. 
 *     Symmetries are achieved by assigning the same index to multiple shapes.
 *   (One good method for rendering cells is to sum the indicies of all of the enclosing shapes of a cell : K
 *     And then take COLORCOUNT%K to get a color index)
 *     
 *   Provides a dupe method, so we can duplicate the Jig and assign it to multiple shapes, thus achieving symmetry
 *      
 */
public interface Jig{

  void run();
  
  Jig dupe();
  
  void setTarget(Shape target);
  
  Shape getTarget();
  
}