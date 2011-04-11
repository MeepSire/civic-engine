// @author: Philipp Jean-Jacques

package core.actors;

import core.graphics.Sprite;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ItemBox extends StaticPhysicsActor {

    protected Sprite sprite = new Sprite(new SpriteSheet(new Image("images/box.png"), 16, 16));
    public Animation STAND = new Animation (sprite.getSpriteSheet(), 0, 0, 0, 3, false, 100, true);

    public ItemBox(float x, float y) throws SlickException {

        super(x, y, new Sprite(new SpriteSheet(new Image("images/box.png"), 16, 16)), new Body("ItemBox", new Box(16, 16), (float) 20.0));
        getSprite().setAnimation(STAND);
        getBody().setRotatable(false);

    }

    public void act(Input input) {
        // CANNOT ACT
    }



}
