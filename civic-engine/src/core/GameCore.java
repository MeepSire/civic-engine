package core;

// CIVIC
import core.actions.ActionHandler;
import core.actors.*;
import core.interfaces.*;

// PHYS2D
import java.util.ArrayList;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.World;

// SLICK
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

public class GameCore extends BasicGame {

    public static GameCore gamecore;

    private World world;
    private ArrayList actors = new ArrayList();
    private ActionHandler handler = new ActionHandler();
    public static int width;
    public static int height;

    public GameCore() {
        super("CiviC Game Core");
        this.world = world;
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
        world = new World(new Vector2f(0, (float) 50), 100);

        new Mario(200, 100); // NEW MARIO IS AUTOMATICALLY ADDED TO ACTORS AND WORLD

        width = container.getWidth();
        height = container.getHeight();

        for(int x = width/3; x <= (width/3) * 2; x += 16){
            new ItemBox((float)x, height - 100);
            x += 1;
        }

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        // NETWORKING
        // TODO: Networking

        // PHYSICS
        world.step();

        // ACT
        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i) instanceof Drawable){
                Actor actor = (Actor)actors.get(i);
                actor.act(container.getInput());
            }
        }

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawString("MOVE WITH LEFT-RIGHT, FLY WITH SPACE", 10, 30);
        g.drawString("N: RESET POSITION", 10, 50);
        g.drawString("I: NEW BOX", 10, 70);
        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i) instanceof Drawable){
                Drawable drw = (Drawable)actors.get(i);
                drw.draw(g);
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

}