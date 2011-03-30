// @author: Philipp Jean-Jacques

package core.actions.events;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Event {

    protected static EventListener[] listeners = new EventListener[0];

    public Event(){
    }

    public static void addEventListener(EventListener listener){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(listeners));
        list.add(listener);
        listeners = (EventListener[])list.toArray();
    }

    public static void removeEventListener(EventListener listener){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(listeners));
        list.remove(listener);
        listeners = (EventListener[])list.toArray();
    }

    public void trigger(){
        for(int i = 0; i < listeners.length; i++){
            listeners[i].eventTriggered(this);
        }
    }

    @Override
    public String toString(){
        return "Generic Event";
    }

}