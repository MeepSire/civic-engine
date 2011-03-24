// @author: Philipp Jean-Jacques

package core.net;

import core.net.protocols.Protocol;
import java.io.PrintWriter;

public class TCPPackage {

    private String[] line = new String[99999];

    public TCPPackage(){
        for(int a = 0; a < line.length; a++){
            line[a] = "";
        }
    }

    public void addLine(String str){
        line[getNumberOfLines()] = str;
    }

    public String getLine(int i){
        if(i >= 0 && i < line.length){
            return line[i];
        }
        else{
            return null;
        }
    }

    public boolean validarePackage(Protocol protocol){
        return protocol.validatePackage(this);
    }

    public int getNumberOfLines(){
        int a = 0;
        while(line[a] != null && !line[a].equals("")){
            a++;
        }
        return a;
    }

    public void send(PrintWriter out){
        if(out != null){
            for(int i = 0; i < getNumberOfLines(); i++){
                out.println(line[i]);
            }
            out.println("");
        }
    }

    public boolean validate(Protocol protocol){
        return protocol.validatePackage(this);
    }

    @Override
    public String toString(){
        String str = "";
        for(int i = 0; i < getNumberOfLines(); i++){
            if(i != 0)str += "\n" + line[i];
            else str+= line[i];
        }
        return str;
    }

    

}