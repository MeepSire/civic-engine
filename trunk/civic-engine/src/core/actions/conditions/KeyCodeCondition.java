// @author: Philipp Jean-Jacques

package core.actions.conditions;

import core.actions.events.Event;
import core.actions.events.KeyPressedEvent;

public class KeyCodeCondition extends Condition {

    private int keyCode;

    public KeyCodeCondition(int keyCode){
        this.keyCode = keyCode;
    }

    @Override
    public boolean check(Event evt){

        try{
            KeyPressedEvent event = (KeyPressedEvent)evt;
            if(event.getKey().getKeyCode() == keyCode) return true;
        }
        catch(Exception e){
        }

        return false;

    }

}
