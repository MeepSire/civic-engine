// @author: Philipp Jean-Jacques

package core;

import core.actions.Action;
import core.actions.conditions.Condition;
import core.actions.events.Event;
import core.actions.events.EventListener;
import java.util.ArrayList;

public class Trigger implements EventListener {

    private ArrayList events = new ArrayList();
    private ArrayList actions = new ArrayList();
    private ArrayList conditions = new ArrayList();

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
        Event[] ret = new Event[events.size()];
        for(int i = 0; i < events.size(); i++){
            ret[i] = (Event)events.get(i);
        }
        return ret;
    }

    public void addEvent(Event event){
        events.add(event);
        event.addEventListener(this);
    }

    public void removeEvent(Event event){
        events.remove(event);
        event.removeEventListener(this);
    }

    public Action[] getActions(){
        Action[] ret = new Action[actions.size()];
        for(int i = 0; i < actions.size(); i++){
            ret[i] = (Action)actions.get(i);
        }
        return ret;
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public void removeAction(Action action){
        actions.remove(action);
    }

    public Condition[] getConditions(){
        Condition[] ret = new Condition[conditions.size()];
        for(int i = 0; i < conditions.size(); i++){
            ret[i] = (Condition)conditions.get(i);
        }
        return ret;
    }

    public void addCondition(Condition condition){
        conditions.add(condition);
    }

    public void removeCondition(Condition condition){
        conditions.remove(condition);
    }

    public boolean checkConditions(Event evt){
        // BY DEFAULT ALL CONDITIONS ARE CHECKED WITH "AND" OPERATOR
        boolean alltrue = true;
        Condition[] conditions = this.getConditions();
        for(int i = 0; i < conditions.length; i++){
            if(conditions[i].check(evt) == false) alltrue = false;
        }
        return alltrue;
    }

    public void run(Event evt){
        if(checkConditions(evt) == true){
            for(int i = 0; i < actions.size(); i++){
                GameCore.gamecore.getActionHandler().addToActionQueue((Action)actions.get(i));
            }
        }
    }

    public void runIgnoreConditions(){
        for(int i = 0; i < actions.size(); i++){
            GameCore.gamecore.getActionHandler().addToActionQueue((Action)actions.get(i));
        }
    }

    // TRIGGER WILL RUN IF ONE EVENT IS TRIGGERED
    public void eventTriggered(Event evt) {
        run(evt);
    }

}