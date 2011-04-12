// @author: Philipp Jean-Jacques

package core;

import core.interfaces.Actable;

public abstract class Actor implements Actable {
      
    private boolean deleteme =false;

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



    public void delete(){
        deleteme = true;
    }

    public boolean isDeleteable(){
        return deleteme;
    }

}