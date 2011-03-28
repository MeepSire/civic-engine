// @author: Philipp Jean-Jacques

package core.actors;

import core.*;
import core.events.*;
import core.interfaces.*;
import core.sprite.Sprite;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;

public class TestActor extends Actor implements Drawable {

    private Sprite sprite;
    private Body body;

    private EventHandler evtHandler = new EventHandler();

    public TestActor() throws IOException{
        this.sprite = new Sprite(ImageIO.read(new File("mario.png")), new Dimension(48, 48), new Color(0, 0, 0, 0));
        body = new Body(new Box(sprite.getFrameSize().width, sprite.getFrameSize().height), 10);
    }

    public Body getBody(){
        return body;
    }

    @Override
    public void act(Key key){

        Key[] keys = this.getKeys();

        evtHandler.addToEventQueue(new PhysicsForceEvent(body, new Vector2f(0, -2000))); // force pushing body upwards
        evtHandler.addToEventQueue(new PhysicsForceEvent(body, new Vector2f(20, 0))); // force pushing body right
        evtHandler.addToEventQueue(new PhysicsForceEvent(body, new Vector2f(-20, 0))); // force pushing body left

    }

    public void draw(Graphics2D g2d) {

        float x = body.getPosition().getX(); 
        float y = body.getPosition().getY();
        
        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform)(origXform.clone());
        
        // rotate graphics around sprite
        newXform.rotate(Math.toRadians(body.getRotation()), x, y);
        g2d.setTransform(newXform);
        
        // draw sprite to the rotated graphics
        g2d.drawImage(sprite.getActiveFrame(), (int)x, (int)y, null);
        
        // rotate the graphics back
        g2d.setTransform(origXform);

    }

}
