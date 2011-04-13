// @author: Philipp Jean-Jacques

package core.actors;

import core.*;
import core.graphics.Sprite;
import core.interfaces.Drawable;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class PhysicsActor extends Actor implements Drawable {

    protected Body body;
    protected Sprite sprite;
    protected int bodyid;


    public PhysicsActor(float x, float y, Sprite sprite, Body body){
        super(x, y);
        this.bodyid = body.getID();
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

        if(sprite.getCurrentAnimation() != null){
            Image img = sprite.getCurrentFrame();

            double rot = getBody().getRotation() * (180/Math.PI);

            img.rotate((float) rot);

            img.draw(body.getPosition().getX() - body.getShape().getBounds().getWidth()/2, body.getPosition().getY() - body.getShape().getBounds().getHeight()/2 );

            img.rotate ((float)-rot);
            
        }

    }

}