package org.fleen.squarzy;

import java.util.List;

public interface Agent{
  
  void setTarget(Grid target);
  
  Grid getTarget();
  
  /*
   * does something to some or all of a grid's cells. 
   *   Changes their state or whatever. 
   * returns the list of cells that it changed
   * 
   * Some agents may consider the state of the grid when doing their thing
   * Some may have a persistent set of forms that they monitor. Shapes or whatever.
   * Some may offer an animated sequence, one frame at a time, incremented at each invocation of run()
   *  
   */
  List<Cell> run();

}
