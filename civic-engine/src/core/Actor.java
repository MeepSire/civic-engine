// @author: Philipp Jean-Jacques

package core;

import core.interfaces.*;

public abstract class Actor implements Actable {

    private Key[] key;

    public Actor() {
    }

    public void act(Key key) {

    }

    public void setKeys(Key[] key){
        this.key = key;
    }

    public Key[] getKeys() {
        return key;
    }

}
