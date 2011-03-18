// @author: Philipp Jean-Jacques

package core.net;

import java.io.IOException;
import utils.OOUtil;

public class ChatClient {

    String text = "";

    public static void main(String[] args) throws IOException{

        TCPClient[] client = new TCPClient[]{
            new TCPClient("127.0.0.1", 1337)
        };

        String nick = OOUtil.readString("Nick: ");

        while(true){
            client[0].sendMessage(nick + ": " + OOUtil.readString("Me : "));

            TCPPackage pkg = client[0].getRecievedPackage();
            String[] msg = pkg.getMessages();

            for(int i = 0; i < msg.length; i++){

                if(msg[i] != null && !msg[i].equals("") && !msg[i].startsWith(nick)){
                    System.out.println(msg[i]);
                }

            }
        }

    }

}
