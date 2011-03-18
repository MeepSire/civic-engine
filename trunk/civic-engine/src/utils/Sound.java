/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author Basti
 */

import java.applet.AudioClip;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import  sun.audio.*;
import  java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class Sound{

    private Clip clip;
    private AudioStream as;
    private InputStream in;
    private String url;
    
    public Sound(String url) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        this.url = url;
        reset(1,1);
    }

    public void reset(float pan, float gain) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        //AudioInputStream öffnen
        AudioInputStream ais = AudioSystem.getAudioInputStream(
        new File(url)
        );
        AudioFormat format = ais.getFormat();
        //ALAW/ULAW samples in PCM konvertieren
        if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
            (format.getEncoding() == AudioFormat.Encoding.ALAW))
        {
          AudioFormat tmp = new AudioFormat(
        AudioFormat.Encoding.PCM_SIGNED,
            format.getSampleRate(),
            format.getSampleSizeInBits() * 2,
            format.getChannels(),
            format.getFrameSize() * 2,
            format.getFrameRate(),
            true
          );
          ais = AudioSystem.getAudioInputStream(tmp, ais);
          format = tmp;
        }
        //Clip erzeugen und öffnen
        DataLine.Info info = new DataLine.Info(
          Clip.class,
          format,
          ((int) ais.getFrameLength() * format.getFrameSize())
        );
        clip = (Clip)AudioSystem.getLine(info);
        clip.open(ais);
        //PAN einstellen
        FloatControl panControl = (FloatControl)clip.getControl(
          FloatControl.Type.PAN
        );
        panControl.setValue(pan);
        //MASTER_GAIN einstellen
        FloatControl gainControl = (FloatControl)clip.getControl(
          FloatControl.Type.MASTER_GAIN
        );
        gainControl.setValue(gain);
        //Clip abspielen
    }
    /*private void reset() throws FileNotFoundException, IOException{
        in = new FileInputStream(url);
        as = new AudioStream(in);
    }*/

    public void startPlay() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        reset(0,0);
        new Playing().start();
    }

    public void startPlay(long pan) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        reset(pan,0);
        new Playing().start();
    }

    public void startPlay(long pan, long gain) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        reset(pan,gain);
        new Playing().start();
    }

    public void stopPlay() throws IOException{
        clip.stop();
    }
    private class Playing extends Thread{
        @Override
        public void run(){
            clip.start();
            while (true) {
          try {
            Thread.sleep(100);
          } catch (Exception e) {
            //nothing
          }
          if (!clip.isRunning()) {
            break;
          }
        }
        clip.stop();
        clip.close();
        }
    }
}