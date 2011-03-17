/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Basti
 */
public class xml {

    public Part[] part= new Part[9999];
    private int parts = 0;
    public String url,name;

    public xml(String name){
        this.name = name;
    }
    
    public static String find(String url, String[] node){
        try {
            File file = new File(url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            int[]id=new int[5];
            NodeList nodeLst = doc.getElementsByTagName(node[0]);
            for(int i=1;i<node.length;i++){
                Node fstNode = nodeLst.item(0);
                Element fstElmnt = (Element) fstNode;
                nodeLst = fstElmnt.getElementsByTagName(node[i]);
            }
            Element fstNmElmnt = (Element) nodeLst.item(0);
            NodeList fstNm = fstNmElmnt.getChildNodes();
            return ((Node) fstNm.item(0)).getNodeValue();
        }catch(Exception e){
            return null;
        }
    }

    public boolean save(String url){
        try{
            File file = new File(url);
            //String here ->
            String str ="<?xml version=\"1.0\" encoding=\"iso-8859-15\" ?>\n<"+name+">\n";
            for(int i=0;i<parts;i++){
                str+=part[i].save();
            }
            str +="</"+name+">";
            //String here <-
            utils.file.save(str,url);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public Part addPart(String name){
        part[parts]=new Part(name,1);
        Part p = part[parts];
        parts++;
        return p;
    }

    public Part addPart(String name, String info){
        part[parts]=new Part(name,info,1);
        Part p = part[parts];
        parts++;
        return p;
    }

    public boolean remPart(int i){
        try{
            for(int j=i;j<parts;j++){
                part[j]=part[j+1];
            }
            parts--;
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    
    public String[] getParts(){
        String[] str = new String[parts];
        for(int i=0;i<str.length;i++){
            str[i]=part[i].name;
        }
        return str;
    }
}
