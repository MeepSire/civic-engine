// @author: Philipp Jean-Jacques

package core.net;

import java.net.*;
import java.io.*;

public class TCPHost extends Thread {
    
    private ServerSocket socket;
    private Socket[] clientSockets = new Socket[1000];

    private BufferedReader[] in = new BufferedReader[1000];
    private PrintWriter out;

    public boolean connected = false;

    public TCPHost(int port) throws IOException{
        socket = new ServerSocket(port);
    }
    
    public void acceptConnection() throws IOException{
        int a = getNumberOfClients();
        clientSockets[a] = socket.accept();
        in[a] = new BufferedReader(new InputStreamReader(clientSockets[a].getInputStream()));
        connected = true;
    }
    
    public String recieveMessage(int clientNumber) throws IOException{
        String str = in[clientNumber].readLine();
        sendMessage(str);
        return str;
    }

    public int getNumberOfClients(){
        int a = 0;
        while(clientSockets[a] != null){
            a++;
        }
        return a;
    }

    public void sendMessage(String msg) throws IOException{
        for(int i = 0; i < getNumberOfClients(); i++){
            out = new PrintWriter(clientSockets[i].getOutputStream(), true);
            out.println(msg);
        }
    }
    
}
