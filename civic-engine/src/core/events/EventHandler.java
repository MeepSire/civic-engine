// @author: Philipp Jean-Jacques

package core.events;

public class EventHandler {

    private Event[] events = new Event[99999];

    public EventHandler(){
    }

    public void handleEvents(){
        for(int i = 0; i < getEventsLength(); i++){
            events[i].execute();
        }
        clearEventsQueue();
    }

    public void addToEventQueue(Event evt){
        System.out.println("added event to queue");
        events[getEventsLength()] = evt;
    }

    private void clearEventsQueue(){
        int a = 0;
        while(events[a] != null){
            events[a] = null;
            a++;
        }
    }

    private int getEventsLength(){
        int a = 0;
        while(events[a] != null){
            a++;
        }
        return a;
    }

}
