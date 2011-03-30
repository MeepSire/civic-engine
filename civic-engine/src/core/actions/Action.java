// @author: Philipp Jean-Jacques

package core.actions;

import core.Actor;
import core.actions.conditions.Condition;
import core.actions.events.Event;
import core.actions.events.EventListener;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Action {

    public Action(){
    }

    public void execute(){
    }

    @Override
    public String toString(){
        return "Generic Action";
    }

}