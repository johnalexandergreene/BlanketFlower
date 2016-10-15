package org.fleen.blanketFlower;

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
  void setComposition(Composition composition);
  
  /*
   * address the shapes in the shape tree
   * For each shape
   *   if it does not have a jig then select a jig and store it
   *     we probably chorus jigs by shape signature, for symmetry, but that depends on the implementation of course. 
   *   if it has a jig (Shape.jig) then execute it
   *   
   * we will probably remove all off-grid or otherwise unneeded shapes here too. 
   */
  void execute();
  
  

}
