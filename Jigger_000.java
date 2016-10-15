package org.fleen.blanketFlower;

import org.fleen.blanketFlower.gSquid.SShape;

public class Jigger_000 implements Jigger{

  Composition composition;
  
  public void setComposition(Composition composition){
    this.composition=composition;}

  public void execute(){
    for(SShape shape:composition.getShapes()){
      if(shape.isRoot()){//only do the root
        if(shape.jig==null){
          shape.jig=new Jig_MovingStripes_4way();
          shape.jig.setTarget(shape);}
        shape.jig.execute();}}
    
  }

}
