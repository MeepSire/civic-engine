// @author: Philipp Jean-Jacques

package core.net;

import java.net.*;
import java.io.*;

public class TCPHost extends Thread {
    
    private ServerSocket socket;
    private Socket clientSocket = null;

    private BufferedReader in;
    private PrintWriter out;

    public boolean connected = false;

    public TCPHost(int port) throws IOException{
        socket = new ServerSocket(port);
    }
    
    public void acceptConnection() throws IOException{
        clientSocket = socket.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        connected = true;
    }
    
    public String recieveMessage() throws IOException{
        return in.readLine();
    }
    
    public void sendMessage(String msg) throws IOException{
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(msg);
    }
    
}
