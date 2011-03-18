/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Basti
 */
public class MmLis implements MouseMotionListener{

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        Draw.sound.startPlay(new Point(e.getX(),e.getY()), new Point(70,100));
        System.out.println("spinn ich??");
    }

}
