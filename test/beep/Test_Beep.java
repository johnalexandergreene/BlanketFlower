package org.fleen.blanketFlower.test.beep;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class Test_Beep{
  
  public Test_Beep(){
    
    AudioFormat af = new AudioFormat( (float )44100, 8, 1, true, false );
    SourceDataLine sdl=null;
    try{
      sdl= AudioSystem.getSourceDataLine( af );
      sdl.open();
    }catch(Exception x){}    
    sdl.start();
    
    beep(sdl);
    try{
      Thread.sleep(1500,0);
    }catch(Exception x){}
    beep(sdl);
    try{
      Thread.sleep(1500,0);
    }catch(Exception x){}
    beep(sdl);
    
    sdl.drain();
    sdl.stop();
    
   
  }
  
  private void beep(SourceDataLine sdl){
    
    byte[] buf0=new byte[256];
    buf0[0]=7;
    
    byte[] buf = new byte[1];
    for( int i = 0; i < 1000 * (float )44100 / 1000; i++ ) {
      double angle = i / ( (float )44100 / 440 ) * 2.0 * Math.PI;
      buf[ 0 ] = (byte )( Math.sin( angle ) * 100 );
      
      if(i%2==0)
        buf=new byte[]{0};
      else
        buf=new byte[]{7};
      
      sdl.write( buf0, 0, 256 );}
    
//    sdl.drain();
//    sdl.flush();
    
  }
  
  public static final void main(String[] a){
    new Test_Beep();
    
//    Synthesizer syn=null;
//    try{
//      syn = MidiSystem.getSynthesizer();
//      syn.open();
//    }catch(Exception x){}
//    final MidiChannel[] mc = syn.getChannels();
//    Instrument[] instr = syn.getDefaultSoundbank().getInstruments();
//    syn.loadInstrument(instr[10]);
//    
//    mc[5].noteOn(33,600);
////    mc[5].allNotesOff();
//    try{
//      Thread.sleep(3000,0);
//    }catch(Exception x){}
////    System.out.println("done");
    
    
  }

}
