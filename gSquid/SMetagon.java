package org.fleen.squarzy.gSquid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fleen.squarzy.Squarzy;

public class SMetagon implements Squarzy,Serializable{
  
  private static final long serialVersionUID=6766402848918685984L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SMetagon(int baseinterval,List<SVector> vectors){
    this.baseinterval=baseinterval;
    this.vectors=new ArrayList<SVector>(vectors);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  double baseinterval;
  List<SVector> vectors;
  

}
