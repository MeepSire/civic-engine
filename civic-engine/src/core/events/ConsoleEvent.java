// @author: Philipp Jean-Jacques

package core.events;

public class ConsoleEvent extends Event {

    private String output;

    public ConsoleEvent(String output){
        this.output = output;
    }

    @Override
    public void execute(){
        System.out.println(output);
    }

}
