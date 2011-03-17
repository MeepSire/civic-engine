package core;

// @author Philipppackage core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

import core.actors.*;
import java.awt.Point;

public class TestFrame extends JFrame implements Runnable {

    Actor[] actors;
    Player[] player;

    public static void main(String[] args) {

        new GameCore();
        new TestFrame();

    }
    
    public TestFrame(){
        
        
        // create a test actor
        actors = new Actor[]{
          new TestActor(new Point(0, 0), "Mario")
        };
        
        actors[0].setKeys(new Key[]{ new Key(39), new Key(37), new Key(38), new Key(40) });
        
        player = new Player[]{
            new Player("Spieler 1", actors[0])
        };
        
        this.setTitle("civic");
        this.addKeyListener(new KeyPressListener());
        this.setVisible(true);
        this.setFocusable(true);

    }

    public void run() {
        while(!(GameCore.gameCore.getState() == GameCore.STOP)){
            // do nothing;
        }
    }

    private class KeyPressListener extends KeyAdapter {

        //key down event:
        @Override
        public void keyPressed(KeyEvent e){
            Key keyPressed = new Key(e.getKeyCode());

            for(int i = 0; i < actors.length; i++){
                Key[] actorKeys = actors[i].getKeys();
                for(int a = 0; a < actorKeys.length; a++){
                    if(actorKeys[a].getKeyCode() == keyPressed.getKeyCode()){
                        if(actorKeys[a].getState() == false){
                            actorKeys[a].setState(true);

                            //act actor for key
                            actors[i].act(keyPressed);

                        }
                    }
                }
            }
        }

        //key up event:
        @Override
        public void keyReleased(KeyEvent e){
            Key keyPressed = new Key(e.getKeyCode());

            for(int i = 0; i < actors.length; i++){
                Key[] actorKeys = actors[i].getKeys();
                for(int a = 0; a < actorKeys.length; a++){
                    if(actorKeys[a].getKeyCode() == keyPressed.getKeyCode()){
                        if(actorKeys[a].getState() == true){
                            actorKeys[a].setState(false);
                        }
                    }
                }
            }
        }
    }
}
