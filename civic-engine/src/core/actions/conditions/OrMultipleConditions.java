// @author: Philipp Jean-Jacques

package core.actions.conditions;

import core.actions.events.Event;

public class OrMultipleConditions extends Condition {

    private Condition[] conditions;

    public OrMultipleConditions(Condition[] conditions){
        this.conditions = conditions;
    }

    @Override
    public boolean check(Event evt){
        boolean onetrue = false;
        for(int i = 0; i < conditions.length; i++){
            if(conditions[i].check(evt) == true){
                onetrue = true;;
            }
        }
        return onetrue;
    }

}