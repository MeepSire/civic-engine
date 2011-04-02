// @author: Philipp Jean-Jacques

package core.actors;

import core.GameCore;
import core.exceptions.NoSuchAnimationException;
import core.graphics.Animation;
import core.graphics.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Mario extends PhysicsActor {

    public Mario(float x, float y) throws SlickException {

        super(x, y, new Sprite(new SpriteSheet(new Image("images/mario.png"), 24, 24)), new Body("Mario", new Box(16, 24), (float) 20.0));

        // ANIMATIONS
        getSprite().addAnimation(new Animation("stand", getSprite(), 0, 0, 0, 0, 254, true));
        getSprite().addAnimation(new Animation("walk", getSprite(), 0, 0, 0, 1, 100, true));
        getSprite().addAnimation(new Animation("jump", getSprite(), 1, 0, 1, 0, 254, true));
        getSprite().addAnimation(new Animation("fall", getSprite(), 2, 0, 2, 0, 254, true));
        getSprite().setAnimation("walk");

        body.setRotatable(false);

    }

    public void act(Input input) {

        getSprite().setAnimation("stand");

        // WALK LEFT
        if(input.isKeyDown(input.KEY_LEFT)){

            getSprite().flip(true, false);

            getSprite().setAnimation("walk");
            getSprite().getActiveAnimation().setAnimationRotation(true);
            
            getBody().addForce(new Vector2f(-2000, 0));
    
        }

        // WALK RIGHT
        if(input.isKeyDown(input.KEY_RIGHT)){

            getSprite().flip(false, false);
 
            getSprite().setAnimation("walk");
            getSprite().getActiveAnimation().setAnimationRotation(true);

            getBody().addForce(new Vector2f(2000, 0));

        }


        if(input.isKeyPressed(input.KEY_SPACE)){

            getBody().addForce(new Vector2f(0, -200000));

        }

        if(input.isKeyPressed(input.KEY_N)){

            setPosition(GameCore.width/2,0);

        }

        if(input.isKeyPressed(input.KEY_I)){

            try {
                new EmptyItemBox(GameCore.width / 2, 0);
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if(Math.abs(body.getVelocity().getY()) >= 10){
            if(body.getVelocity().getY() > 0){
                getSprite().setAnimation("fall");
            }else{
                getSprite().setAnimation("jump");
            }
        }

    }

}