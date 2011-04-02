// @author: Philipp Jean-Jacques

package core;

import core.actions.Action;
import core.actions.conditions.Condition;
import core.actions.events.Event;
import core.actions.events.EventListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Trigger implements EventListener {

    private Event[] events = new Event[0];
    private Action[] actions = new Action[0];
    private Condition[] conditions = new Condition[0];

    public Trigger(){
    }

    public Trigger(Event[] events, Action[] actions, Condition[] conditions){
        for(int i = 0; i < events.length; i++){
            addEvent(events[i]);
        }
        for(int i = 0; i < actions.length; i++){
            addAction(actions[i]);
        }
        for(int i = 0; i < conditions.length; i++){
            addCondition(conditions[i]);
        }
    }

    public Event[] getEvents(){
        return events;
    }

    public void addEvent(Event event){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(actions));
        list.add(event);
        events = (Event[])list.toArray();
        event.addEventListener(this);
    }

    public void removeEvent(Event event){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(actions));
        list.remove(event);
        events = (Event[])list.toArray();
        event.removeEventListener(this);
    }

    public Action[] getActions(){
        return actions;
    }

    public void addAction(Action action){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(actions));
        list.add(action);
        actions = (Action[])list.toArray();
    }

    public void removeAction(Action action){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(actions));
        list.remove(action);
        actions = (Action[])list.toArray();
    }

    public Condition[] getConditions(){
        return conditions;
    }

    public void addCondition(Condition condition){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(conditions));
        list.add(condition);
        conditions = (Condition[])list.toArray();
    }

    public void removeCondition(Condition condition){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(conditions));
        list.remove(condition);
        conditions = (Condition[])list.toArray();
    }

    public boolean checkConditions(Event evt){
        // BY DEFAULT ALL CONDITIONS ARE CHECKED WITH "AND" OPERATOR
        boolean alltrue = true;
        for(int i = 0; i < conditions.length; i++){
            if(conditions[i].check(evt) == false) alltrue = false;
        }
        return alltrue;
    }

    public void run(Event evt){
        if(checkConditions(evt) == true){
            for(int i = 0; i < actions.length; i++){
                //GameCore.actionHandler.addToActionQueue(actions[i]);
            }
        }
    }

    public void runIgnoreConditions(){
        for(int i = 0; i < actions.length; i++){
            //GameCore.actionHandler.addToActionQueue(actions[i]);
        }
    }

    // TRIGGER WILL RUN IF ONE EVENT IS TRIGGERED
    public void eventTriggered(Event evt) {
        for(int i = 0; i < events.length; i++){
            if(events[i].equals(evt)){
                run(evt);
            }
        }
    }

}