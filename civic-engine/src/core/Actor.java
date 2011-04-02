// @author: Philipp Jean-Jacques

package core;

import core.interfaces.Actable;
import org.newdawn.slick.Input;

public abstract class Actor implements Actable {
    
    protected float x;
    protected float y;
    
    public Actor(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

}