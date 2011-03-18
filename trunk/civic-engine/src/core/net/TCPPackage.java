// @author: Philipp Jean-Jacques

package core.net;

public class TCPPackage {

    public static final char seperator = 678;
    public static final char idMarker = 679;


    private int clientID;
    private String[] messages = new String[9999];

    public TCPPackage(int clientID){
        this.clientID = clientID;

        for(int i = 0; i < messages.length; i++){
            messages[i] = null;
        }
    }

    public void addMessage(String msg){
        if(msg != null && !msg.equals("")){
            int a = 0;
            while(messages[a] != null && a < messages.length){
                a++;
            }
            messages[a] = msg;
        }
    }

    public String[] getMessages(){
        return messages;
    }

    public int getClientID(){
        return clientID;
    }

    public String getPackageString(){
        String str = "";
        str += clientID + "" + idMarker;
        int a = 0;
        while(messages[a] != null){
            str += messages[a] + "" + seperator;
            a++;
        }
        return str;
    }

    public static TCPPackage getPackageFromString(String pkgString){

        String str = "";

        int pos = 0;
        while(pkgString.charAt(pos) != idMarker){
            str += pkgString.charAt(pos);
            pos++;
            if(pos > pkgString.length() - 1)break;
        }

        TCPPackage pkg = new TCPPackage(Integer.parseInt(str));

        while(pos <= pkgString.length()){
            str = "";
            while(pkgString.charAt(pos) != seperator && pkgString.charAt(pos) != idMarker){
                str += pkgString.charAt(pos);
                pos++;
                if(pos > pkgString.length() - 1)break;
            }
            if(pkgString.charAt(pos) != idMarker)pkg.addMessage(str);
            pos++;
            if(pos > pkgString.length() - 1)break;
        }

        return pkg;

    }

}
