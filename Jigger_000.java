package org.fleen.squarzy;

import org.fleen.squarzy.gSquid.SShape;

public class Jigger_000 implements Jigger{

  Composition composition;
  
  public void setComposition(Composition composition){
    this.composition=composition;}

  public void execute(){
    for(SShape shape:composition.getShapes()){
      if(shape.isRoot()){//only do the root
        if(shape.jig==null){
          shape.jig=new Jig_MovingStripe_2DirSlowAndFast();
          shape.jig.setTarget(shape);}
        shape.jig.execute();}}
    
  }

}
