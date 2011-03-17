package core.net;

import java.io.IOException;
import utils.*;

public class NetworkTestChat {

    public static void main(String[] args) throws IOException {

        char readChar = 'x';
        while(readChar != 'h' && readChar != 'c'){
            readChar = OOUtil.readChar("Host (h) or Client (c) ?\n");
        }

        if(readChar == 'h'){
            TCPHost host = new TCPHost(OOUtil.readInt("Port: "));

            System.out.println("Waiting for Connections...");

            while(host.connected == false){
                try{
                    host.acceptConnection();
                }
                catch(Exception e){
                }
            }

            System.out.println("Connection established!");

            String nickName = OOUtil.readString("Nickname: ");

            while(true){
                host.sendMessage(nickName + ": " + OOUtil.readString());
                host.recieveMessage();
            }

        }
        else{
            TCPClient client = new TCPClient(OOUtil.readString("Hostname: "), OOUtil.readInt("Port: "));

            System.out.println("Connection established!");

            String nickName = OOUtil.readString("Nickname: ");

            while(true){
                client.sendMessage(nickName + ": " + OOUtil.readString());
                client.recieveMessage();
            }
        }

    }

}
