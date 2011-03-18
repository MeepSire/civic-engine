// @author: Philipp Jean-Jacques

package core.net.protocols;

import core.net.*;
import java.io.PrintWriter;

public class ServerProtocol {

    public static void handlePackage(TCPPackage pkg, TCPHost host, PrintWriter out){
        
        if(pkg.getClientID() == -1){
            out.println(new TCPPackage(host.getUniqueID()).getPackageString());
            System.out.println("New Client connected");
        }

        String[] msg = pkg.getMessages();

        for(int i = 0; i < msg.length; i++){
            if(msg.equals("QUIT")){
                host.removeClient(pkg.getClientID());
            }
        }
        
    }

}
