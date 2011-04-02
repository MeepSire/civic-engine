// @author: Philipp Jean-Jacques

package core.actors;

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

        super(x, y, new Sprite(new SpriteSheet(new Image("images/mario.png"), 24, 24)), new Body("Mario", new Box(24, 24), (float) 20.0));

        // ANIMATIONS
        getSprite().addAnimation(new Animation("walk", getSprite(), 0, 0, 0, 1, 100, true));
        getSprite().addAnimation(new Animation("jump", getSprite(), 1, 0, 1, 0, 254, false));
        getSprite().addAnimation(new Animation("fall", getSprite(), 2, 0, 2, 0, 254, false));
        getSprite().setAnimation("walk");

    }

    public void act(Input input) {

        // WALK LEFT
        if(input.isKeyDown(input.KEY_LEFT)){

            getSprite().flip(true, false);

            getSprite().setAnimation("walk");
            
            getBody().addForce(new Vector2f(-20, 0));
    
        }

        // WALK RIGHT
        if(input.isKeyDown(input.KEY_RIGHT)){

            getSprite().flip(false, false);
 
            getSprite().setAnimation("walk");

            getBody().addForce(new Vector2f(20, 0));

        }


        if(input.isKeyPressed(input.KEY_SPACE)){

            getSprite().setAnimation("jump");

            getBody().addForce(new Vector2f(0, -20000));

        }

    }

}