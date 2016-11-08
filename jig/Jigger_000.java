package org.fleen.blanketFlower.jig;

import org.fleen.blanketFlower.bComposition.BComposition;
import org.fleen.blanketFlower.bComposition.BShape;

public class Jigger_000 implements Jigger{

  BComposition composition;
  
  public void setComposition(BComposition composition){
    this.composition=composition;}

  public void execute(){
    for(BShape shape:composition.getShapes()){
      if(shape.getDepth()<2){
        if(shape.jig==null){
          shape.jig=new Jig_MovingStripes_4way();
          shape.jig.setTarget(shape);}
        shape.jig.execute();}}
    
  }

}
