// @author: Philipp Jean-Jacques

package core.actions;

import java.util.ArrayList;
import java.util.Arrays;

public class ActionHandler {

    private Action[] actions = new Action[0];

    public ActionHandler(){
    }

    public void executeActions(){
        for(int i = 0; i < actions.length; i++){
            actions[i].execute();
        }
        clearActionQueue();
    }

    public void addToActionQueue(Action action){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(actions));
        list.add(action);
        actions = (Action[])list.toArray();
    }

    private void clearActionQueue(){
        actions = new Action[0];
    }

    public Action[] getActionQueue(){
        return actions;
    }

}