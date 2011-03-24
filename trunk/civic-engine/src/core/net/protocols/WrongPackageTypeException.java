// @author: Philipp Jean-Jacques

package core.net.protocols;

public class WrongPackageTypeException extends Exception {

    public WrongPackageTypeException(String msg){
        super("Wrong Package Type: " + msg);
    }

}
