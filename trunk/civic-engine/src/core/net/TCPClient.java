// @author: Philipp Jean-Jacques

package core.net;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    private String hostname;
    private int port;
    private TCPPackage recievedPackage = null;

    private int ID = -1;

    public TCPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        try {
            sendMessage("");
        } catch (UnknownHostException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public TCPPackage getRecievedPackage(){
        return recievedPackage;
    }

    public void sendMessage(String str) throws UnknownHostException, IOException{

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

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        TCPPackage pkg = new TCPPackage(ID);
        pkg.addMessage(str);
        
        out.println(pkg.getPackageString());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String read = null;

        while(read == null){
            try{
                read = in.readLine();
            }
            catch(Exception e){
            }
        }

        TCPPackage rPkg = TCPPackage.getPackageFromString(read);

        recievedPackage = rPkg;

        if(ID == -1) {
            ID = rPkg.getClientID();
            System.out.println(ID);
        }

        String[] msg = rPkg.getMessages();
        for(int i = 0; i < msg.length; i++){
            // if(msg[i] != null && !msg[i].equals(""))System.out.println(msg[i]);
        }
        // System.out.println();

        out.close();

        while(!socket.isClosed()){
            socket.close();
        }

    }

}