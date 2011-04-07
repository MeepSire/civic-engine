// @author: Philipp Jean-Jacques

package core.actions.events;

import core.Key;

public class KeyPressedEvent extends Event {

    private Key key;

    public KeyPressedEvent(Key key){
        this.key = key;
    }

    public Key getKey(){
        return key;
    }

    @Override
    public void trigger(){
        for(int i = 0; i < listeners.size(); i++){
            EventListener listener = (EventListener)listeners.get(i);
            listener.eventTriggered(this);
        }
    }

    @Override
    public String toString(){
        return "Key Event: Pressed (" + key.getKeyCode() + ")";
    }

}
