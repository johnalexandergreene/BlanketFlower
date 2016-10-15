package org.fleen.blanketFlower;

import org.fleen.blanketFlower.gSquid.SShape;


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

  //do the thing to the target
  void execute();
  
  //duplicate this jig
  //we can't just use the same jig over and over, we are tracking unique sets of shapes
  Jig dupe();
  
  //the target is the shape that we're editing the shapes of
  void setTarget(SShape target);
  
  SShape getTarget();
  
}
