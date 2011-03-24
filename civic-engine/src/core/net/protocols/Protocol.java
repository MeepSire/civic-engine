// @author: Philipp Jean-Jacques

package core.net.protocols;

import core.net.*;
import java.io.BufferedReader;

public abstract class Protocol {

    public final String header;

    protected TCPHost host = null;

    public Protocol(String header){
        this.header = header;
    }

    public TCPPackage processInput(TCPPackage pkg) throws WrongPackageTypeException {
        return pkg;
    }

    public void processReply(TCPPackage pkg) throws WrongPackageTypeException{

    }

    public TCPPackage getPackageFromStream(BufferedReader in) throws WrongPackageTypeException{
        return new TCPPackage();
    }

    public void setHost(TCPHost host){
        this.host = host;
    }

    public boolean validatePackage(TCPPackage pkg){
        return true;
    }

}