// @author: Philipp Jean-Jacques

package core.actors;

import core.graphics.Animation;
import core.graphics.Sprite;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ItemBox extends StaticPhysicsActor {

    public ItemBox(float x, float y) throws SlickException {

        super(x, y, new Sprite(new SpriteSheet(new Image("images/box.png"), 16, 16)), new Body("ItemBox", new Box(17,17), (float) 20.0));

        // ANIMATIONS
        getSprite().addAnimation(new Animation("stand", getSprite(), 0, 0, 0, 3, 100, true));
        getSprite().setAnimation("stand");

    }

    public void act(Input input) {
        // CANNOT ACT
    }



}
