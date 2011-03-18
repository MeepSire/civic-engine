// @author: Philipp Jean-Jacques

package core.net;

import core.net.protocols.ServerProtocol;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPHost implements Runnable {

    ServerSocket serverSocket;
    int port;

    // CLIENT ID's
    private int[] clientID = new int[99999];

    private TCPHost host;

    private TCPPackage[] recievedPackages = new TCPPackage[0];
    private TCPPackage[] sendablePackages = new TCPPackage[99999];

    public TCPHost(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.port = port;
        for(int i = 0; i < clientID.length; i++){
            clientID[i] = -1;
        }
        for(int i = 0; i < sendablePackages.length; i++){
            sendablePackages[i] = null;
        }
        new Thread(this).start();
        host = this;
        
        System.out.println("Server started");

    }

    public void sendMessageToClient(int clientID, String msg){
        TCPPackage sendPkg = null;

        for(int i = 0; i < sendablePackages.length; i++){
            if(sendablePackages[i] != null){
                if(sendablePackages[i].getClientID() == clientID){
                    sendPkg = sendablePackages[i];
                }
            }
        }

        if(sendPkg == null){
            sendPkg = new TCPPackage(clientID);
            int a = 0;
            while(sendablePackages[a] != null){
                a++;
            }
            sendablePackages[a] = sendPkg;
        }

        sendPkg.addMessage(msg);
    }

    public void sendMessageToClient(int clientID, TCPPackage pkg){
        String[] msg = pkg.getMessages();
        for(int i = 0; i < msg.length; i++){
            sendMessageToClient(clientID, msg[i]);
        }
    }

    private TCPPackage getPackageForID(int clientID){
        for(int i = 0; i < sendablePackages.length; i++){
            if(sendablePackages[i] != null){
                if(sendablePackages[i].getClientID() == clientID){
                    return sendablePackages[i];
                }
            }
        }
        return new TCPPackage(clientID);
    }

    private void sendPackageToClient(int clientID, PrintWriter out){
        for(int i = 0; i < sendablePackages.length; i++){
            if(sendablePackages[i] != null && sendablePackages[i].getClientID() == clientID){
                out.println(sendablePackages[i].getPackageString());
                sendablePackages[i] = null;
            }
        }
    }

    private void listen() throws IOException{
        System.out.println("listening on port " + port);
        new Thread(new connectionHandler(serverSocket.accept())).start();
        listen();
    }

    private void addPackage(TCPPackage pkg){
        TCPPackage[] tempList = this.recievedPackages;
        recievedPackages = new TCPPackage[recievedPackages.length+1];
        for(int i = 0; i < tempList.length; i++){
            recievedPackages[i] = tempList[i];
        }
        recievedPackages[recievedPackages.length-1] = pkg;
    }

    public TCPPackage getNextPackage(){
        if(recievedPackages.length > 0){
            TCPPackage ret = recievedPackages[0];
            TCPPackage[] tempList = recievedPackages;
            recievedPackages = new TCPPackage[recievedPackages.length-1];
            for(int i = 0; i < recievedPackages.length; i++){
                recievedPackages[i] = tempList[i+1];
            }
            return ret;
        }
        return null;
    }

    public void run() {
        try {
            listen();
        } catch (Exception e) {
        }
    }

    private class connectionHandler extends Thread{

        Socket socket;

        public connectionHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){

            BufferedReader in = null;

            System.out.println("recieving package from " + socket.getRemoteSocketAddress());

            // READ FROM SOCKET
            while(in == null){
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                } catch (Exception e) {
                }
            }

            String req = null;
            try {
                req = in.readLine();
            } catch (Exception e) {
            }

            System.out.println("returning package to " + socket.getRemoteSocketAddress());

            // WRITE BACK
            if(req != null){

                PrintWriter out = null;

                while(out == null){
                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                    } catch (Exception e) {
                    }
                }

                TCPPackage pkg = TCPPackage.getPackageFromString(req);
                

                ServerProtocol.handlePackage(pkg, host, out);

                host.addPackage(pkg);

                int a = 0;
                while(clientID[a] != -1){
                    host.sendMessageToClient(clientID[a], req);
                    a++;
                }

                host.sendPackageToClient(pkg.getClientID(), out);

            }

        }

    }

    public void removeClient(int ID){
        for(int i = 0; i < clientID.length; i++){
            if(ID == clientID[i]){
                int a = i;
                while(clientID[i+1] != -1){
                    clientID[i] = clientID[i+1];
                    a++;
                }
                clientID[a] = -1;
            }
        }
    }

    public int getUniqueID(){
        boolean isUnique = false;
        int ID = -1;
        while(!isUnique){
            ID = (int)(Math.random()*99999);
            isUnique = true;
            int a = 0;
            while(clientID[a] != -1){
                if(ID == clientID[a]){
                    isUnique = false;
                }
                a++;
            }
        }
        int a = 0;
        while(clientID[a] != -1){
            a++;
        }
        clientID[a] = ID;

        return ID;
    }

}
