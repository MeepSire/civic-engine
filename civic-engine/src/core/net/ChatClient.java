// @author: Philipp Jean-Jacques

package core.net;

import java.io.IOException;
import java.net.UnknownHostException;

public class ChatClient  {

    String text = "";
    public static String nick;
    public static TCPClient client;

    public static void connect(String ip, int port){
        client = new TCPClient(ip, port);
    }

    public static String send(String str) throws UnknownHostException, IOException{
        client.sendMessage(str);
        TCPPackage pkg = client.getRecievedPackage();
            String[] msg = pkg.getMessages();
            String str2="";
            for(int i = 0; i < msg.length; i++){

                if(msg[i] != null && !msg[i].equals("")){
                     str2 += msg[i]+"\n";
                }

            }
            return str2;
    }

    public static String update() throws UnknownHostException, IOException{
        return send("");
    }

    public static void main(String[] args) throws IOException{

        new Connect().setVisible(true);

        while(true){
                        
        }

    }

}