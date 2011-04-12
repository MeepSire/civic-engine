// @author: Philipp Jean-Jacques

package core.actions;

import java.util.ArrayList;
import java.util.Arrays;

public class ActionHandler {

    private ArrayList actions = new ArrayList();

    public ActionHandler(){
    }

    public void executeActions(){
        for(int i = 0; i < actions.size(); i++){
            Action action = (Action)actions.get(i);
            if(action != null)action.execute();
            actions.remove(i);
        }
    }

    public void addToActionQueue(Action action){
        actions.add(action);
       
    }

    public Action[] getActionQueue(){
        Action[] actionArray = new Action[actions.size()];
        for(int i = 0; i < actions.size(); i++){
            actionArray[i] = (Action)actions.get(i);
        }
        return actionArray;
    }

}