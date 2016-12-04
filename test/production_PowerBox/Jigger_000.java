package org.fleen.blanketFlower.test.production_PowerBox;

import java.util.Random;

import org.fleen.blanketFlower.bComposition.BComposition;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.jig.Jigger;
import org.fleen.blanketFlower.jig.boxRhythmicSweepingStripes.Jig_BoxRhythmicSweepingStripes;

public class Jigger_000 implements Jigger{

  BComposition composition;
  
  public void setComposition(BComposition composition){
    this.composition=composition;}

  Random rnd=new Random();
  
  public void execute(int frameindex){
    int d,a;
    for(BShape shape:composition.getShapes()){
      d=shape.getDepth();
      if(d<1){
        //get a jig
        if(shape.jig==null){
          shape.jig=new Jig_BoxRhythmicSweepingStripes();
          shape.jig.setTarget(shape);}
        //do it
        shape.jig.execute(frameindex);}}
    
  }

}
