// @author Basti

package utils;

import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.*;
import java.io.*;
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
    
    public Sound(String url){
        this.url = url;
        reset(1,1);
    }

    public void reset(float pan, float gain){
        if(gain>6)gain=6;
        //AudioInputStream öffnen
        AudioInputStream ais=null;
        try {
            ais = AudioSystem.getAudioInputStream(new File(url));
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            clip = (Clip) AudioSystem.getLine(info);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clip.open(ais);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void startPlay(){
        reset(0,0);
        new Playing().start();
    }

    public void startPlay(long pan){
        reset(pan,0);
        new Playing().start();
    }

    public void startPlay(long pan, long gain){
        reset(pan,gain);
        new Playing().start();
    }

    public void startPlay(int distance){
        reset(0,6f-distance);
        new Playing().start();
    }

    public void startPlay(Point listener,Point sound){
        int distance = (int) listener.distance(sound);
        int pos_1 = sound.x-listener.x-100;
        int pos_2 = sound.x-listener.x+100;
        //System.out.println("playing left with: "+(6f-320-pos-distance/20));
        if(sound.x <= pos_2 && sound.x >= pos_1){
            pos_1 = 0;
            pos_2 = 0;
        }
        reset(-1f,6-Math.abs(pos_1/10) * distance/200);
        new Playing().start();
        //System.out.println("playing right with: "+(6f-320+pos-distance/20));
        reset(1f,6-Math.abs(pos_2/10) * distance/200);
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
