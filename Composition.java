package org.fleen.squarzy;

import org.fleen.squarzy.gSquid.SPolygon;
import org.fleen.squarzy.gSquid.SShape;

public class Composition{
  
  public Composition(){
    initRoot();
  }
  
  void initRoot(){
    root=new SPolygon();
  }
  
  SShape root;

}
