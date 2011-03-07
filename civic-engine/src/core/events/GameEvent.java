// @author: Philipp Jean-Jacques

package core.events;

import core.GameCore;

public class GameEvent extends Event {

    private int number;

    public GameEvent(int stateNumber){
        this.number = stateNumber;
    }

    @Override
    public void execute(){
        GameCore.gameCore.setState(number);
    }

}
