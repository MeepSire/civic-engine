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
public class EasyXml {

    public Part[] part= new Part[9999];
    protected int parts = 0;
    public String url,name;

    public EasyXml(String name){
        this.name = name;
    }

    public static String[] find(File file, String[] node){
        if(file.exists()){
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
                Node firstNode = doc.getChildNodes().item(0);
                NodeList nodeLst = doc.getElementsByTagName(node[node.length-1]);

                Element element;
                NodeList scnLst;

                if(node.length>1){
                
                    String[] str;
                    String[] firstStr=new String[9999];
                    int strLength =0;

                    for(int i=0;i<nodeLst.getLength();i++){
                        Node newNode=nodeLst.item(i);
                        boolean entspricht = false;
                        for(int j=node.length-2;j>=0;j--){
                            while((!newNode.getParentNode().equals(firstNode))&&(!newNode.getParentNode().getNodeName().equals(node[j]))){
                                newNode = newNode.getParentNode();
                            }
                        }
                        if(!newNode.getParentNode().getNodeName().equals(firstNode.getNodeName()))entspricht=true;
                        if (entspricht){
                            int j=0;
                            while(j<nodeLst.item(i).getChildNodes().getLength()){
                                if(!nodeLst.item(i).getChildNodes().item(j).getNodeName().equals("#text")){
                                    firstStr[strLength]=nodeLst.item(i).getChildNodes().item(j).getNodeName();
                                    strLength++;
                                }else{
                                    if(nodeLst.item(i).getChildNodes().getLength()==1){
                                        firstStr[strLength]=nodeLst.item(i).getChildNodes().item(j).getTextContent();
                                        strLength++;j++;
                                    }
                                }
                                j++;
                            }
                        }
                    }
                    str = new String[strLength];
                    for(int i=0;i<str.length;i++){
                        str[i]=firstStr[i];
                    }
                    return str;
                }else{
                    String[] firstStr = new String[9999];
                    int lng = nodeLst.getLength();
                    int strLength=0;
                    for(int i=0;i<lng;i++){
                        int j=0;
                            while(j<nodeLst.item(i).getChildNodes().getLength()){
                                if(!nodeLst.item(i).getChildNodes().item(j).getNodeName().equals("#text")){
                                    firstStr[strLength]=nodeLst.item(i).getChildNodes().item(j).getNodeName();
                                    strLength++;
                                }else{
                                    if(nodeLst.item(i).getChildNodes().getLength()==1){
                                        firstStr[strLength]=nodeLst.item(i).getChildNodes().item(j).getTextContent();
                                        strLength++;j++;
                                    }
                                }
                                j++;
                            }
                    }
                    String[] str = new String[strLength];
                    for(int i=0;i<str.length;i++){
                        str[i]=firstStr[i];
                    }
                    return str;
                }

            }catch(Exception e){
                return null;
            }
        }else{
            return new String[]{};
        }
    }

    public boolean save(String url){
        try{
            //String here ->
            String str ="<?xml version=\"1.0\" encoding=\"iso-8859-15\" ?>\n<"+name+">\n";
            for(int i=0;i<parts;i++){
                str+=part[i].save();
            }
            str +="</"+name+">";
            //String here <-
            utils.TextFile.save(str,url);
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
