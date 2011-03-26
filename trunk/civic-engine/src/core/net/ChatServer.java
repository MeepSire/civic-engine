// @author: Philipp Jean-Jacques

package core.net;

import core.net.protocols.ChatProtocol;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.OOUtil;

public class ChatServer extends Thread {

    private TCPHost host;
    private int port;
    private static int verbosity = TCPHost.IMPORTANT;

    public ChatServer(int port) throws IOException{
        this.port = port;
        new Thread(this).start();
        while(true){
            String cmd = OOUtil.readString("");
            if(cmd.equals("quit") || cmd.equals("exit") || cmd.equals("stop")){
                host.stop();
                break;
            }else{
                host.printLog("unknown command", TCPHost.MEDIUM);
            }
        }
    }

    public static void main(String[] args) throws IOException{

        int port = 0;

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("-p")){
                port = Integer.parseInt(args[i+1]);
            }
            if(args[i].equals("-v")){
                verbosity = Integer.parseInt(args[i+1]);
            }
        }

        if(port < 1024){
            port = 1337;
        }

        new ChatServer(port);
    }

    @Override
    public void run(){
        try {
            host = new TCPHost(port, new ChatProtocol());
            host.setLogVerbosity(verbosity);
        } catch (Exception ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
