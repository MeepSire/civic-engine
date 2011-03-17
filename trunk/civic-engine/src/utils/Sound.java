/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author Basti
 */

import  sun.audio.*;
import  java.io.*;

public class Sound {

    private AudioStream as;
    
    public Sound(String url) throws FileNotFoundException, IOException{
        InputStream in = new FileInputStream(url);
        as = new AudioStream(in);
    }
    public void play(){
        AudioPlayer.player.start(as);
    }
    public void stop(){
        AudioPlayer.player.stop(as);
    }
}
