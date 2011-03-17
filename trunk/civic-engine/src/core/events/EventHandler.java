// @author: Philipp Jean-Jacques

package core.events;

public class EventHandler {

    private static Event[] events = new Event[99999];

    public static void handleEvents(){
        for(int i = 0; i < getEventsLength(); i++){
            events[i].execute();
        }
        clearEventsQueue();
    }

    public static void addToEventQueue(Event evt){
        events[getEventsLength()] = evt;
    }

    private static void clearEventsQueue(){
        int a = 0;
        while(events[a] != null){
            events[a] = null;
            a++;
        }
    }

    private static int getEventsLength(){
        int a = 0;
        while(events[a] != null){
            a++;
        }
        return a;
    }

}
