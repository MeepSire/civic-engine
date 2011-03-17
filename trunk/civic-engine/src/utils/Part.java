/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author Basti
 */
class Part {

    public Part[] part= new Part[9999];
    private int parts = 0;
    public String name;
    public String info="";
    private int deep;

    Part(String name,int d){
        deep=d;
        this.name = name;
    }

    Part(String name, String info, int d){
        deep=d;
        this.name = name;
        this.info = info;
    }
    
    public Part addPart(String name){
        part[parts]=new Part(name,deep+1);
        Part p = part[parts];
        parts++;
        return p;
    }

    public Part addPart(String name, String info){
        part[parts]=new Part(name,info,deep+1);
        Part p = part[parts];
        parts++;
        return p;
    }

    public boolean remPart(int i){
        try{
            for(int j=i;j<parts;j++){
                part[j]=part[j+1];
            }
            return true;
        }
        catch(Exception e){
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

    public String save(){
        String str ="";
        for(int v=0;v<deep;v++){str+="    ";}
        str +="<"+name+">";
        if (parts==0){
            str += info;
        }
        else{
            str +="\n";
            for(int i=0;i<parts;i++){
                str+=part[i].save();
            }
            for(int v=0;v<deep;v++){str+="    ";}
        }
        str +="</"+name+">\n";
        return str;
    }
}
