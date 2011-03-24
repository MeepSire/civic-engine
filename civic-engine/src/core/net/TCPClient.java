// @author: Philipp Jean-Jacques

package core.net;

import core.net.protocols.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    private String hostname;
    private int port;
    private TCPPackage recievedPackage = null;

    private Protocol protocol;

    public TCPClient(String hostname, int port, Protocol protocol) {
        this.hostname = hostname;
        this.port = port;
        this.protocol = protocol;
    }

    public Protocol getProtocol(){
        return protocol;
    }

    public TCPPackage getRecievedPackage(){
        return recievedPackage;
    }

    public void sendPackage(TCPPackage pkg) throws UnknownHostException, IOException{

        // CONNECT SOCKET
        Socket socket = null;

        while(socket == null){
            try{
                socket = new Socket(hostname, port);
            }
            catch(Exception e){
            }
        }

        while(!socket.isConnected()){
            socket.close();
            socket = new Socket(hostname, port);
        }

        // WRITE TO SOCKET
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        pkg.send(out);

        BufferedReader in = null;

        // READ FROM SOCKET
        while(in == null){
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            } catch (Exception e) {
            }
        }

        TCPPackage rPkg = null;

        try {
            rPkg = protocol.getPackageFromStream(in);
        } catch (WrongPackageTypeException ex) {
            System.out.println(ex.getMessage());
        }

        recievedPackage = rPkg;

        out.close();
        
        while(!socket.isClosed()){
            socket.close();
        }

    }

}