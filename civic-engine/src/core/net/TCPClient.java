// @author: Philipp Jean-Jacques

package core.net;

import java.net.*;
import java.io.*;

public class TCPClient {

    private Socket socket;

    private PrintWriter out;
    private BufferedReader in;

    public TCPClient(String host, int port) throws UnknownHostException, IOException{

        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    public String recieveMessage() throws IOException{
        return in.readLine();
    }

    public void sendMessage(String msg) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println(msg);
    }

}
