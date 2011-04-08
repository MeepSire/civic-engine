// @author: Philipp Jean-Jacques

package core.actors;

import core.GameCore;
import core.Trigger;
import core.actions.Action;
import core.actions.conditions.PhysicsCollisionCondition;
import core.actions.events.PhysicsCollisionEvent;
import core.graphics.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Mario extends PhysicsActor {

    protected Sprite sprite = new Sprite(new SpriteSheet(new Image("images/mario.png"), 24, 24));

    public Animation STAND = new Animation(sprite.getSpriteSheet(), 0, 0, 0, 0, false, 1, false);
    public Animation WALK = new Animation(sprite.getSpriteSheet(), 0, 0, 0, 1, false, 130, true);
    public Animation JUMP = new Animation(sprite.getSpriteSheet(), 1, 0, 1, 0, false, 1, false);
    public Animation FALL = new Animation(sprite.getSpriteSheet(), 2, 0, 2, 0, false, 1, false);

    private boolean walking = false;
    private boolean jumping = false;

    public Trigger resetJump;

    public Mario(float x, float y) throws SlickException {

        super(x, y, new Sprite(new SpriteSheet(new Image("images/mario.png"), 24, 24)), new Body("Mario", new Box(16, 24), (float) 20.0));

        // ANIMATIONS
        body.setRotatable(false);

    }

    public void act(Input input) {

        getSprite().setAnimation(STAND);
        
        // WALK LEFT
        if(input.isKeyDown(Input.KEY_LEFT)){

            getSprite().flip(true, false);

            getSprite().setAnimation(WALK);

            getBody().move((float) (getBody().getPosition().getX() - 1), getBody().getPosition().getY());
            
        }

        // WALK RIGHT
        if(input.isKeyDown(Input.KEY_RIGHT)){

            getSprite().flip(false, false);
 
            getSprite().setAnimation(WALK);

            getBody().move((float) (getBody().getPosition().getX() + 1), getBody().getPosition().getY());

        }

        // JUMP
        if(input.isKeyPressed(Input.KEY_SPACE) && Math.round(getBody().getVelocity().getY()) == 0 ) {

            getBody().addForce(new Vector2f(0, -150000));

        }

        if(input.isKeyPressed(Input.KEY_N)){

            setPosition(GameCore.width/2,0);

        }

        if(input.isKeyPressed(Input.KEY_I)){

            try {
                new EmptyItemBox(GameCore.width / 2, 0);
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if(Math.abs(body.getVelocity().getY()) >= 5){
            if(body.getVelocity().getY() > 0){
                getSprite().setAnimation(FALL);
            }else{
                getSprite().setAnimation(JUMP);
            }
        }

    }

}