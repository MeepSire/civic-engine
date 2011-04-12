/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core.actors;

import core.GameCore;
import core.graphics.Sprite;
import core.interfaces.Actable;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// @author: Philipp Jean-Jacques

public class EmptyItemBox extends PhysicsActor implements Actable{


    protected Sprite sprite = new Sprite(new SpriteSheet(new Image("images/box.png"), 16, 16));

    public Animation STAND = new Animation (sprite.getSpriteSheet(), 1, 0, 1, 0, false, 1, false);

    public EmptyItemBox(float x, float y) throws SlickException {
        
        super(x, y, new Sprite(new SpriteSheet(new Image("images/box.png"), 16, 16)), new Body("Box", new Box(16, 16), (float) 20.0));

        getSprite().setAnimation(STAND);

        body.setRotDamping((float) -1);
        body.setRotatable(true);

    }

    public void act(Input input) {
        if(!insideScreen()){
            delete();
        }
    }

    public boolean insideScreen(){
        boolean s = false;
        if(body.getPosition().getX()>0&&body.getPosition().getX()<GameCore.width&&body.getPosition().getY()>0&&body.getPosition().getY()<GameCore.height){
            s = true;
        }
        return s;
    }

}
