// @author: Philipp Jean-Jacques

package core;

import core.interfaces.Actable;
import java.util.ArrayList;

public abstract class Actor implements Actable {
      
    private boolean deleteme =false;

    protected float x;
    protected float y;
    private int id;

    private static ArrayList idList = new ArrayList();

    public void delete(){
        deleteme = true;
    }

    public boolean isDeleteable(){
        return deleteme;
    }

    public Actor(float x, float y){
        this.x = x;
        this.y = y;
        id = getNewID();
    }

    public int getID(){
        return id;
    }

    private int getNewID(){
        boolean newid = false;
        int id = 0;
        while(newid == false){
            id = (int)(Math.random()*99999);
            newid = true;
            for(int i = 0; i < idList.size(); i++){
                if(new Integer(id).equals((Integer)idList.get(i))){
                    newid = false;
                }
            }
        }
        idList.add(new Integer(id));
        return id;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

}