// @author: Philipp Jean-Jacques

package core.actions.conditions;

import core.actions.events.Event;
import java.awt.Image;

public abstract class Condition {

    public Condition(){
    }

    public boolean check(Event evt){
        return true;
    }

}