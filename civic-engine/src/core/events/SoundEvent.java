// @author: Philipp Jean-Jacques

package core.events;

import utils.Sound;

public class SoundEvent extends Event {

    private Sound sound;

    public SoundEvent(Sound sound){
        this.sound = sound;
    }

    @Override
    public void execute(){
        sound.startPlay();
    }

}
