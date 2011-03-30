// @author: Philipp Jean-Jacques

package core.exceptions;

public class NoSuchAnimationException extends Exception {

    public NoSuchAnimationException(String msg){
        super("no such animation: " + msg);
    }

}