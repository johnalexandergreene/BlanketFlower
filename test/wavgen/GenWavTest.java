package org.fleen.blanketFlower.test.wavgen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class GenWavTest {
  
    public static void main(String[] args) throws  IOException {

    double sampleRate = 44100.0;
    double frequency = 440;
    double frequency2 = 90;
    double amplitude = 1.0;
    double seconds = 12.0;
    double twoPiF = 2 * Math.PI * frequency;
    double piF = Math.PI * frequency2;
    float[] buffer = new float[(int) (seconds * sampleRate)];
    for (int sample = 0; sample < buffer.length; sample++) 
    {
        double time = sample / sampleRate;
//        buffer[sample] = (float) (amplitude * Math.cos((double)piF *time)* Math.sin(twoPiF * (time)));
        buffer[sample] = (float) (amplitude * Math.cos((double)piF *time*time));
//        buffer[sample] = (float) (amplitude * Math.sin(twoPiF * time));
    }
    final byte[] byteBuffer = new byte[buffer.length * 2];
    int bufferIndex = 0,val=0,b;
    for (int i = 0; i < byteBuffer.length; i++) {
      if(i%1000>500){
        val=32767;
      }else{
//        val=(i/(i%1000+1))*100;
        val=(int)(i*1000*Math.sin(i));
      }
      
//      final int x = (int) (buffer[bufferIndex++] * 32767.0);
      
      
      
      
      
      
      byteBuffer[i] = (byte) val;
      i++;
      byteBuffer[i] = (byte) (val >>> 8);
    }
    File out = new File("/home/john/Desktop/generated_wav/test000.wav");
    boolean bigEndian = false;
    boolean signed = true;
    int bits = 16;
    int channels = 1;
    AudioFormat format;
    format = new AudioFormat((float)sampleRate, bits, channels, signed, bigEndian);
    ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
    AudioInputStream audioInputStream;
    audioInputStream = new AudioInputStream(bais, format,buffer.length);
    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
    audioInputStream.close();
    }

}