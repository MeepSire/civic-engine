// @author: Philipp Jean-Jacques

package core.net.protocols;

import core.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatProtocol extends Protocol {

    private int[] registeredIDs = new int[999];
    private TCPPackage[] packages = new TCPPackage[registeredIDs.length];

    public ChatProtocol(){

        super("JavaChat/1.0");  // HEADER

        // INIT ARRAYS
        for(int i = 0; i < registeredIDs.length; i++){
            registeredIDs[i] = -2;
            packages[i] = null;
        }

    }

    // RETURNS AN ANSWER PACKAGE TO A CLIENT REQUEST
    @Override
    public TCPPackage processInput(TCPPackage pkg) throws WrongPackageTypeException {

        String[] msg = getMessages(pkg);

        int clientID = getClientID(pkg);

        if(clientID == -1){
            clientID = getUniqueID();
        }

        for(int a = 0; a < registeredIDs.length; a++){
            if(registeredIDs[a] != -2){
                for(int i = 0; i < msg.length; i++){
                    getPackageForID(registeredIDs[a]).addLine("MSG=" + msg[i]);
                }
            }
        }

        TCPPackage ret = getPackageForID(clientID);
        removePackageFromList(clientID);
        
        return ret;
        
    }

    private void removePackageFromList(int ID) throws WrongPackageTypeException{
        for(int i = 0; i < packages.length; i++){
            if(packages[i] != null){
                if(getClientID(packages[i]) == ID){
                    packages[i] = null;
                    break;
                }
            }
        }
    }

    private TCPPackage getPackageForID(int ID){
        int a = 0;
        while(packages[a] != null){
            try {
                if (getClientID(packages[a]) == ID) {
                    return packages[a];
                }
            } catch (WrongPackageTypeException ex) {
                Logger.getLogger(ChatProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
            a++;
        }
        return packages[a] = createPackage(ID);
    }

    private int getUniqueID(){
        int ID = -2;
        boolean newID = false;
        while(newID == false){
            newID = true;
            ID = (int)(Math.random()*99999);
            int i = 0;
            while(registeredIDs[i] != -2){
                if(registeredIDs[i] == ID){
                    newID = false;
                }
                i++;
            }
            if(newID == true) registeredIDs[i] = ID;
        }
        return ID;
    }

    @Override
    public boolean validatePackage(TCPPackage pkg){
        
        if(pkg.getLine(0).startsWith(header)){
            return true;
        } else return false;

    }

    // <editor-fold defaultstate="collapsed" desc="Package Building">
    @Override
    public TCPPackage getPackageFromStream(BufferedReader in) throws WrongPackageTypeException {

        TCPPackage pkg = new TCPPackage();

        try {
            String line = null;
            while ((line = in.readLine()) != null && !line.equals("") && !line.equals("null")) {
                pkg.addLine(line);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        validatePackage(pkg);

        return pkg;

    }

    public TCPPackage createPackage(int ID, String nick, String[] messages){

        TCPPackage pkg = new TCPPackage();

        pkg.addLine(header);
        pkg.addLine("ID=" + ID);
        pkg.addLine("NICK=" + nick);
        for(int i = 0; i < messages.length; i++){
            pkg.addLine("MSG=" + nick + ": " + messages[i]);
        }
        return pkg;

    }

    public TCPPackage createPackage(int ID, String nick){
        TCPPackage pkg = new TCPPackage();

        pkg.addLine(header);
        pkg.addLine("ID=" + ID);
        pkg.addLine("NICK=" + nick);
        return pkg;
    }

    public TCPPackage createPackage(int ID){
        TCPPackage pkg = new TCPPackage();

        pkg.addLine(header);
        pkg.addLine("ID=" + ID);
        return pkg;
    }

    public TCPPackage createPackage(int ID, String nick, String message){

        return createPackage(ID, nick, new String[]{ message });

    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Package Content">
    public String[] getMessages(TCPPackage pkg) throws WrongPackageTypeException {

        if(validatePackage(pkg) == true){
            
            String[] msg = new String[0];

            for(int i = 0; i < pkg.getNumberOfLines(); i++){
                if(pkg.getLine(i).startsWith("MSG=") /*&& !pkg.getLine(i).equals("MSG="+getNickName(pkg)+": ") */){
                    String[] tempList = msg;
                    msg = new String[msg.length+1];
                    for(int a = 0; a < tempList.length; a++){
                        msg[a] = tempList[a];
                    }
                    msg[msg.length-1] = pkg.getLine(i).replaceFirst("MSG=", "");
                }
            }

            return msg;

        }
        else{
            if(host != null)host.printLog("(getMessages) expected " + header + ", got " + pkg.getLine(0), TCPHost.MEDIUM);
            throw new WrongPackageTypeException("(getMessages) expected " + header + ", got " + pkg.getLine(0));
        }

    }

    public int getClientID(TCPPackage pkg) throws WrongPackageTypeException {

        if(validatePackage(pkg) == true){

            int ID = -1;

            for(int i = 0; i < pkg.getNumberOfLines(); i++){
                if(pkg.getLine(i).startsWith("ID=")){
                    ID = Integer.parseInt(pkg.getLine(i).replaceAll("ID=", ""));
                }
            }

            return ID;

        }

        else{
            throw new WrongPackageTypeException("(getClientID) expected " + header + ", got " + pkg.getLine(0));
        }

    }

    public String getNickName(TCPPackage pkg){

        for(int i = 0; i < pkg.getNumberOfLines(); i++){
            if(pkg.getLine(i).startsWith("NICK=")){
                return pkg.getLine(i).replaceFirst("NICK=", "");
            }
        }

        return null;

    }

    // </editor-fold>

}