// @author: Philipp Jean-Jacques

package core.net;

import core.net.protocols.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPHost implements Runnable {

    // -- VARIABLES
    // SERVER
    private final ServerSocket serverSocket;
    private final int port;
    private final TCPHost host;
    private final Protocol protocol;

    // LOG
    private int logVerbosity = 0;
    public static final int LOW = 0;
    public static final int MEDIUM = 1;
    public static final int IMPORTANT = 2;
    public static final int NOLOG = 3;


    public TCPHost(int port, Protocol protocol) throws IOException {

        this.host = this;
        this.port = port;
        this.protocol = protocol;
        this.protocol.setHost(this);

        serverSocket = new ServerSocket(port);

        new Thread(this).start();

        printLog("server started", IMPORTANT);
        printLog("listening on port " + port, IMPORTANT);
        printLog("handling incoming packages with " + protocol.header, IMPORTANT);

    }

    public void setLogVerbosity(int level){
        logVerbosity = level;
    }

    private boolean running = true;

    private void listen() throws IOException{
        new Thread(new connectionHandler(serverSocket.accept())).start();
        if(running)listen();
    }

    public void stop() throws IOException{
        running = false;
        printLog("server shutting down", IMPORTANT);
        serverSocket.close();
        System.exit(0);
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

            // READ FROM SOCKET
            while(in == null){
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                } catch (Exception e) {
                }
            }

            TCPPackage pkg = null;

            try {
                pkg = protocol.getPackageFromStream(in);
            } catch (WrongPackageTypeException ex) {
                printLog(ex.getMessage(), IMPORTANT);
            }

            printLog("exchanging packages with " + (socket.getRemoteSocketAddress() + "").replaceAll("(/)|(:[0-9]+)", ""), LOW);

            // WRITE BACK
            if(pkg != null){

                PrintWriter out = null;

                while(out == null){
                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                    } catch (Exception e) {
                    }
                }
                
                try {
                    TCPPackage retPkg = protocol.processInput(pkg);
                    retPkg.send(out);
                } catch (WrongPackageTypeException ex) {
                    printLog(ex.getMessage(), IMPORTANT);
                }

                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(TCPHost.class.getName()).log(Level.SEVERE, null, ex);
                }

                out.close();

            }

        }

    }

    public void printLog(String msg, int level){
        if(level >= logVerbosity){
            String out = getTimeString() + " - " + msg;
            if(logVerbosity < IMPORTANT){
                System.out.println(out);
            }
            else{
                System.err.println(out);
            }
        }
    }

    private String getTimeString(){
        Date date = new Date(System.currentTimeMillis());
        String str = "";
        if(date.getHours() < 10){
            str += "0" + date.getHours() + ":";
        }else{
            str += date.getHours() + ":";
        }
        if(date.getMinutes() < 10){
            str += "0" + date.getMinutes() + ":";
        }else{
            str += date.getMinutes() + ":";
        }
        if(date.getSeconds() < 10){
            str += "0" + date.getSeconds();
        }else{
            str += date.getSeconds();
        }
        return str;
    }

}
