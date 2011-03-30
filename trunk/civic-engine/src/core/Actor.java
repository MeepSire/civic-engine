// @author: Philipp Jean-Jacques

package core;

import net.phys2d.raw.Body;

public abstract class Actor {

    protected Body body;

    public Actor(){
    }

    public Body getBody(){
        return body;
    }

}