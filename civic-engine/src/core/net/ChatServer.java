// @author: Philipp Jean-Jacques

package core.net;

import java.io.IOException;

public class ChatServer {

    private TCPHost host;

    public ChatServer(int port) throws IOException{
        host = new TCPHost(port);
    }

    public static void main(String[] args) throws IOException{

        new ChatServer(1337);

    }

}
