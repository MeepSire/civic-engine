// @author: Philipp Jean-Jacques

package core.net;

import core.net.protocols.Protocol;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TCPPackage {

    private ArrayList line = new ArrayList();

    public TCPPackage(){
    }

    public void addLine(String str){
        line.add(str);
    }

    public String getLine(int i){
        String str = (String)line.get(i);
        return str;
    }

    public boolean validarePackage(Protocol protocol){
        return protocol.validatePackage(this);
    }

    public int getNumberOfLines(){
        return line.size();
    }

    public void send(PrintWriter out){
        if(out != null){
            for(int i = 0; i < line.size(); i++){
                out.println(getLine(i));
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
            if(i != 0)str += "\n" + getLine(i);
            else str+= getLine(i);
        }
        return str;
    }

    

}