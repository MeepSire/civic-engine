// @author: Philipp Jean-Jacques

package core.sprite;

class NoSuchAnimationException extends Exception {

    public NoSuchAnimationException(String msg){
        super("no such animation: " + msg);
    }

}