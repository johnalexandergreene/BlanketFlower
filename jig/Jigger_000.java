package org.fleen.blanketFlower.jig;

import java.util.Random;

import org.fleen.blanketFlower.bComposition.BComposition;
import org.fleen.blanketFlower.bComposition.BShape;

public class Jigger_000 implements Jigger{

  BComposition composition;
  
  public void setComposition(BComposition composition){
    this.composition=composition;}

  Random rnd=new Random();
  
  public void execute(){
    int d;
    for(BShape shape:composition.getShapes()){
      d=shape.getDepth();
      if(d<2){
        //get a jig
        if(shape.jig==null){
          if(d==0){
            shape.jig=new Jig_RandomMovingStripes();
          }else{
            if(rnd.nextBoolean())
              shape.jig=new Jig_RandomDots(); 
            else
              shape.jig=new Jig_RandomMovingStripes();
          }
          shape.jig.setTarget(shape);}
        //do it
        shape.jig.execute();}}
    
  }

}
