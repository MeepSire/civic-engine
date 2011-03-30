// @author: Philipp Jean-Jacques

package core.net.protocols;

import core.net.*;
import java.io.PrintWriter;

public class HyperTextTransferProtocol extends Protocol {

    public HyperTextTransferProtocol(){
        super("HTTP/1.1");
    }

    @Override
    public TCPPackage processInput(TCPPackage tcp){
        return tcp;
    }

}