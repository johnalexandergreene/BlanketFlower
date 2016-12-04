package org.fleen.blanketFlower.jig;

import java.util.Random;

import org.fleen.blanketFlower.bComposition.BComposition;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.jig.patternFill.Jig_PatternFill;
import org.fleen.blanketFlower.jig.sprinkle.Jig_Sprinkle;
import org.fleen.blanketFlower.jig.sweepingStripes.Jig_SweepingStripes;

public class Jigger_000 implements Jigger{

  BComposition composition;
  
  public void setComposition(BComposition composition){
    this.composition=composition;}

  Random rnd=new Random();
  
  public void execute(int frameindex){
    int d,a;
    for(BShape shape:composition.getShapes()){
      d=shape.getDepth();
      if(d<2){
        //get a jig
        if(shape.jig==null){
          if(d==0){
            shape.jig=new Jig_SweepingStripes();
          }else{
            a=rnd.nextInt(3);
//            a=2;
            if(a==0){
              shape.jig=new Jig_Sprinkle(); 
            }else if(a==1){
              shape.jig=new Jig_SweepingStripes();
            }else{
              shape.jig=new Jig_PatternFill();
            }
          }
          shape.jig.setTarget(shape);}
        //do it
        shape.jig.execute(frameindex);}}
    
  }

}
