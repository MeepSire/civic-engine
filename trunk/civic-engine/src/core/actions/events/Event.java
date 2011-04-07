// @author: Philipp Jean-Jacques

package core.actions.events;

import java.util.ArrayList;

public abstract class Event {

    protected static ArrayList listeners = new ArrayList();

    private Event event;

    public Event(){
    }

    public static void addEventListener(EventListener listener){
        listeners.add(listener);
    }

    public static void removeEventListener(EventListener listener){
        listeners.remove(listener);
    }

    public void trigger(){
        for(int i = 0; i < listeners.size(); i++){
            EventListener listener = (EventListener)listeners.get(i);
            listener.eventTriggered(event);
        }
    }

    @Override
    public String toString(){
        return "Generic Event";
    }

}