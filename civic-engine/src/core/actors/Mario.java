// @author: Philipp Jean-Jacques

package core.actors;

// CIVIC
import core.GameCore;
import core.Trigger;
import core.actions.Action;
import core.actions.conditions.PhysicsCollisionCondition;
import core.actions.events.PhysicsCollisionEvent;
import core.graphics.Sprite;

// JAVA SE 6
import java.util.logging.Level;
import java.util.logging.Logger;

// PHYS2D
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;

// SLICK
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
    public Animation DUCK = new Animation(sprite.getSpriteSheet(), 3, 0, 3, 0, false, 1, false);

    public Trigger resetJump;

    public Mario(float x, float y) throws SlickException {

        super(x, y, new Sprite(new SpriteSheet(new Image("images/mario.png"), 24, 24)), new Body("Mario", new Box(16, 24), (float) 10.0));

        // ANIMATIONS
        body.setRotatable(true);
        body.setRestitution(1);

        // TRIGGA
        resetJump = new Trigger();
        resetJump.addEvent(new PhysicsCollisionEvent(null));
        resetJump.addCondition(new PhysicsCollisionCondition(this,true));
        resetJump.addAction(new ResetRotation());

    }

    public void act(Input input) {

        getSprite().setAnimation(STAND);
        
        // WALK LEFT
        if(input.isKeyDown(Input.KEY_LEFT) && (!input.isKeyDown(Input.KEY_DOWN) || Math.abs(body.getVelocity().getY()) >= 1)){

            getSprite().flip(true, false);

            getSprite().setAnimation(WALK);

            getBody().move((float) (getBody().getPosition().getX() - 5), getBody().getPosition().getY());
            
        }

        // WALK RIGHT
        if(input.isKeyDown(Input.KEY_RIGHT) && (!input.isKeyDown(Input.KEY_DOWN) || Math.abs(body.getVelocity().getY()) >= 1)){

            getSprite().flip(false, false);
 
            getSprite().setAnimation(WALK);

            getBody().move((float) (getBody().getPosition().getX() + 5), getBody().getPosition().getY());

        }

        // JUMP
        if(input.isKeyPressed(Input.KEY_SPACE) && Math.round(getBody().getVelocity().getY()) == 0 ) {

            getBody().addForce(new Vector2f(0, -200000));

        }

        // FALL (RELEASE JUMP)
        if(!input.isKeyDown(Input.KEY_SPACE) && Math.round(getBody().getVelocity().getY()) < 0 ){

            getBody().addForce(new Vector2f(0, 30000));

        }

        //SHOOT
        if(input.isKeyPressed(Input.KEY_X)){
            
            int dir = 1;
            
            if(this.getSprite().flipH){
                dir = -1;
            }

            try {
                Fire box = new Fire(getBody().getPosition().getX()+dir*16, getBody().getPosition().getY());
                box.getBody().addForce(new Vector2f(dir*100000,-100000));
                box.getBody().setRotation((float)(Math.random()*180/Math.PI));
                box.getBody().setRestitution(1);
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // JUMP/FALL ANIMATION
        if(Math.abs(body.getVelocity().getY()) >= 1){
            if(body.getVelocity().getY() > 0){
                getSprite().setAnimation(FALL);
            }else{
                getSprite().setAnimation(JUMP);
            }
        }

        // RESET POSITION
        if(input.isKeyPressed(Input.KEY_N)){

            getBody().move(GameCore.width/2,GameCore.height/2);

        }

        // PUT TILE
        if(input.isKeyPressed(Input.KEY_P)){

            try {
                new ItemBox(getBody().getPosition().getX(), getBody().getPosition().getY());
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // DUCK
        if(input.isKeyDown(Input.KEY_DOWN)){

            getSprite().setAnimation(DUCK);

            getBody().setShape(new Box(16, 20));

        }else{

            getBody().setShape(new Box(16, 24));

        }

    }

    private class ResetRotation extends Action {

        @Override
        public void execute(){
            
            PhysicsCollisionEvent pce = (PhysicsCollisionEvent)resetJump.getTriggeredEvent();

            if(pce.getTypeA().equals("ItemBox") || pce.getTypeB().equals("ItemBox")){
                getBody().setRotation(0);
            }
            
        }

    }

}