package core;

// CIVIC
import core.actions.ActionHandler;
import core.actions.events.PhysicsCollisionEvent;
import core.actors.*;
import core.interfaces.*;

// PHYS2D
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;
import net.phys2d.raw.World;

// SLICK
import net.phys2d.raw.strategies.QuadSpaceStrategy;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class GameCore extends BasicGame implements CollisionListener {

    public static GameCore gamecore;

    private World world;
    private ArrayList actors = new ArrayList();
    private ActionHandler handler = new ActionHandler();
    public static int width;
    public static int height;

    private static GameContainer ctr;

    public GameCore() {
        super("CiviC Game Core");
        this.gamecore = this;
    }

    public ActionHandler getActionHandler(){
        return handler;
    }

    public void addActor(Actor actor){
        actors.add(actor);
        if(actor instanceof PhysicsActor == true){
            PhysicsActor phActor = (PhysicsActor) actor;
            world.add(phActor.getBody());
        }
    }

    public void removeActor(Actor actor){
        actors.remove(actor);
    }

    @Override
    public void init(GameContainer container) throws SlickException {

        // MUST FIRST CREATE THE WORLD BEFORE ADDING ACTORS!
        world = new World(new Vector2f(0, (float) 10.0), 5, new QuadSpaceStrategy(20,5));

        this.world.addListener(gamecore);
        container.getInput().addListener(this);
        ctr = container;

        new Mario(500, 100); // NEW MARIO IS AUTOMATICALLY ADDED TO ACTORS AND WORLD

        width = container.getWidth();
        height = container.getHeight();

        for(int x = width/3; x <= (width/3) * 2; x += 16){
            new ItemBox((float)x, height - 100);
            x += 0;
        }

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        ctr = container;

        // NETWORKING
        // TODO: Networking

        // PHYSICS
        world.step();

        // EXECUTE TRIGGERED ACTIONS
        handler.executeActions();

        // ACT
        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i) instanceof Drawable){
                Actor actor = (Actor)actors.get(i);
                actor.act(container.getInput());
            }
        }

        globalInputAction(container.getInput());

    }

    protected void globalInputAction(Input input){

        if(input.isKeyPressed(Input.KEY_I)){

            try {
                new EmptyItemBox(GameCore.width / 2, 0);
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

        ctr = container;

        g.drawString("MOVE WITH LEFT-RIGHT, JUMP WITH SPACE", 10, 30);
        g.drawString("N: RESET POSITION", 10, 50);
        g.drawString("I: NEW BOX", 10, 70);

        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i) instanceof Drawable){
                Drawable drw = (Drawable)actors.get(i);
                drw.draw(g);
                if(actors.get(i) instanceof Mario){
                    Mario mario = (Mario)actors.get(i);
                    g.drawString("POSITION: " + Math.round(mario.getBody().getPosition().getX()) + ", " + Math.round(mario.getBody().getPosition().getY()), 10, 110);
                    g.drawString("VELOCITY: " + Math.round(mario.getBody().getVelocity().getX()) + ", " + Math.round(mario.getBody().getVelocity().getY()), 10, 130);
                }
            }
        }
        
    }

    // START
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new GameCore());
            app.setDisplayMode(1680, 1050, true);
            app.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void collisionOccured(CollisionEvent evt) {
        new PhysicsCollisionEvent(evt).trigger();
    }

    private class Timer {
        
        private long startTime;
        
        public Timer(){
        }
        
        public void start(){
            startTime = System.currentTimeMillis();
        }
        
        public long stop(){
            return System.currentTimeMillis() - startTime;
        }
        
    }

}