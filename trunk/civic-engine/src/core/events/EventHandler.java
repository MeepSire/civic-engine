// @author: Philipp Jean-Jacques

package core.events;

public class EventHandler {

    private static Event[] events = new Event[99999];

    public static void handleEvents(){
        for(int i = 0; i < getEventsLength(); i++){
            events[i].execute();
            System.out.println("executed event: " + events[i].toString());
        }
        clearEventsQueue();
    }

    public static void addToEventQueue(Event evt){
        events[getEventsLength() - 1] = evt;
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
        return a + 1;
    }

}
