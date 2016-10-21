package org.fleen.blanketFlower.jig;

import org.fleen.blanketFlower.composition.Composition;
import org.fleen.blanketFlower.composition.Shape;

public class Jigger_000 implements Jigger{

  Composition composition;
  
  public void setComposition(Composition composition){
    this.composition=composition;}

  public void execute(){
    for(Shape shape:composition.getShapes()){
      if(shape.getDepth()<1){
        if(shape.jig==null){
          shape.jig=new Jig_MovingStripes_4way();
          shape.jig.setTarget(shape);}
        shape.jig.execute();}}
    
  }

}
