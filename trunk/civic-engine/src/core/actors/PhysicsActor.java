// @author: Philipp Jean-Jacques

package core.actors;

import core.*;
import core.graphics.Sprite;
import core.interfaces.Drawable;
import net.phys2d.raw.Body;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class PhysicsActor extends Actor implements Drawable {

    protected Body body;
    protected Sprite sprite;

    public PhysicsActor(float x, float y, Sprite sprite, Body body){
        super(x, y);
        this.body = body;
        this.sprite = sprite;
        setPosition(x,y);
        GameCore.gamecore.addActor(this);
    }

    public Body getBody(){
        return body;
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite(){
        return sprite;
    }

    @Override
    public void setPosition(float x, float y){
        body.setPosition(x, y);
    }

    public void draw(Graphics g) {
        Image img = sprite.getActiveFrame();
        img.rotate(body.getRotation());
        img.draw(body.getPosition().getX(), body.getPosition().getY());
    }

}