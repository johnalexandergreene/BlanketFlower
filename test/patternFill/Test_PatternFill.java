package org.fleen.blanketFlower.test.patternFill;

import java.util.Iterator;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BVertex;

public class Test_PatternFill{
  
  static final BVertex[] VERTICES={
    new BVertex(0,0),
    new BVertex(0,3),
    new BVertex(3,3),
    new BVertex(3,0)};
  
  public static final void main(String[] a){
    System.out.println("FILL POLYGON WITH CELLS TEST");
    BPolygon p=new BPolygon(VERTICES);
    BCellGroup m=new BCellGroup(p);
    System.out.println("mass size="+m.size());
    Iterator<BCell> i=m.iterator();
    BCell cell;
    System.out.println("---cells---");
    while(i.hasNext()){
      cell=i.next();
      System.out.println(cell);}
    System.out.println("TEST FINISHED");
  }

}
