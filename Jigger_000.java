package org.fleen.squarzy;

import org.fleen.squarzy.gSquid.SShape;

public class Jigger_000 implements Jigger{

  Composition composition;
  
  public void setComposition(Composition composition){
    this.composition=composition;}

  public void execute(){
    for(SShape shape:composition.getShapes()){
      if(!shape.isRoot())continue;
      if(shape.jig==null){
        shape.jig=new Jig_MovingStripe();
        shape.jig.setTarget(shape);}
      shape.jig.execute();}
    
  }

}
