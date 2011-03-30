// @author: Philipp Jean-Jacques

package core.actions.conditions;

import core.actions.events.Event;

public class AndMultipleConditions extends Condition {

    private Condition[] conditions;

    public AndMultipleConditions(Condition[] conditions){
        this.conditions = conditions;
    }

    @Override
    public boolean check(Event evt){
        boolean alltrue = true;
        for(int i = 0; i < conditions.length; i++){
            if(conditions[i].check(evt) == false){
                alltrue = false;
            }
        }
        return alltrue;
    }

}
