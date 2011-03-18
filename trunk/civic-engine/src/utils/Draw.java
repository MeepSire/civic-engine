/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

/**
 *
 * @author Basti
 */
public class Draw extends JPanel implements Runnable{

    public static Sound sound;
    private MouseMotionListener mouse = new MmLis();

    public Draw(){
        this.setVisible(true);
        this.setFocusable(true);
        new Thread(this).start();
    }

    public void run() {
        this.setVisible(true);
        this.setFocusable(true);
        sound = new Sound("/Users/Philipp/Desktop/axloadcomplete.wav");
        this.addMouseMotionListener(mouse);
        while(true){
            repaint();
        }
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.drawOval(500, 100, 2, 2);
    }

}