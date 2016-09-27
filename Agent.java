package org.fleen.squarzy;

import java.util.List;

public interface Agent{
  
  /*
   * does some cell ops to a grid
   * returns a list of effected cells 
   */
  List<Cell> invoke(Grid grid);

}
