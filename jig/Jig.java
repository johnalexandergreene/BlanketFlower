package org.fleen.blanketFlower.jig;

import org.fleen.blanketFlower.geom_Boxy.BShape;


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
 * +++++++++++++++++++++++++++++
 * ABOUT ORIENTED
 * +++++++++++++++++++++++++++++
 * 
 * An oriented Jig delivers a uniform product over identical shapes
 *   we ignore global nesw. we use local orientation
 * we are guided by 
 *   V0
 *   dir(V0,V1)
 *   polygon twist
 *   
 * this gvies us a local origin and nesw
 * 
 * by using oriented Jigs and chorus-index guided jig selection we can 
 *   create multiple-shape-spanning symmetric geometries
 *   
 */
public interface Jig{

  //do the thing to the target
  //frameindex gives us some idea of how far along in our sequence we are
  //  some jigs use it, some don't
  //  it's good for rhythmic effects, ramping effects
  //  if we design the jig to fit a certain frame sequence 
  //  length we can program up some complex behavior
  void execute(int frameindex);
  
  //duplicate this jig for use in symmetric structures
  //we can't just use the same jig over and over, we are tracking unique sets of shapes
  Object clone();
  
  //the target is the shape that we're editing the shapes of
  void setTarget(BShape target);
  
  BShape getTarget();
  
}
