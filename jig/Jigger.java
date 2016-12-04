package org.fleen.blanketFlower.jig;

import org.fleen.blanketFlower.bComposition.BComposition;

/*
 * a jigger selects and executes jigs for a composition
 * 
 * the way it will go is we will have a jigger for each "animation process".
 * and each jigger wil have an associated collection of jigs
 */
public interface Jigger{
  
  /*
   * set the composition who's shape tree were doing stuff to
   */
  void setComposition(BComposition composition);
  
  /*
   * address the shapes in the shape tree
   * For each shape
   *   if it does not have a jig then select a jig and store it
   *     we probably chorus jigs by shape signature, for symmetry, but that depends on the implementation of course. 
   *   if it has a jig (Shape.jig) then execute it
   *   
   * we will probably remove all off-grid or otherwise unneeded shapes here too. 
   * 
   * ---
   * 
   * frameindex gives us some idea of how far along in our sequence we are
   * some jigs use it, some don't
   * it's good for rhythmic effects, ramping effects
   * if we design the jig to fit a certain frame sequence 
   * length we can program up some complex behavior
   * 
   */
  void execute(int frameindex);
  
  

}
