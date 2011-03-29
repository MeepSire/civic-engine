// @author: Philipp Jean-Jacques

package core;

import core.interfaces.*;
import net.phys2d.raw.Body;

public abstract class Actor implements Actable {

    private Key[] key;
    protected Body body;

    public Actor() {
    }

    public void act(Key key) {

    }

    public Body getBody(){
        return body;
    }

    public void setKeys(Key[] key){
        this.key = key;
    }

    public Key[] getKeys() {
        return key;
    }

}
