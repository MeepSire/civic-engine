// @author: Philipp Jean-Jacques

package core.events;

public class SpeedTestEvent extends Event {

    private long creationTime;

    public SpeedTestEvent(){
        creationTime = System.currentTimeMillis();
    }

    @Override
    public void execute(){
        System.out.println("Time to execute Event: " + (System.currentTimeMillis() - creationTime) + "ms");
    }

}
