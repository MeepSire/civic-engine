// @author: Philipp Jean-Jacques

package core;

public class Player {

    private String name;
    private Actor actor = null;

    public Player(String name, Actor actor) throws NullPointerException {
        this.name = name;
        if(actor == null) throw new NullPointerException("Actor is null");
        this.actor = actor;
    }

    public void setActor(Actor actor) throws NullPointerException{
        if(actor == null) throw new NullPointerException("Actor is null");
        this.actor = actor;
    }

    public void pressKey(Key key){
        actor.act(key);
    }

}
