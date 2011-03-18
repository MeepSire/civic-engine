// @author: Philipp Jean-Jacques

package core.net.protocols;

import core.net.TCPPackage;

public class ChatProtocol {

    public static String[] getMessages(TCPPackage pkg){
        String[] msg = pkg.getMessages();

        String[] chatMsg = new String[0];

        for(int i = 0; i < msg.length; i++){
            if(msg[i].startsWith("CMSG")){
                String[] tempStr = chatMsg;
                chatMsg = new String[chatMsg.length + 1];
                for(int a = 0; a < tempStr.length; a++){
                    chatMsg[a] = tempStr[a];
                }
                chatMsg[chatMsg.length-1] = msg[i].replaceFirst("CMSG", "");
            }
        }

        return chatMsg;
    }

}
