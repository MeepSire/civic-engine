// @author: Philipp Jean-Jacques

package core;

public class Key {

    // constants (not appropriate)
    public static final Key LEFT = new Key(32);
    public static final Key RIGHT = new Key(33);
    public static final Key UP = new Key(34);
    public static final Key DOWN = new Key(35);

    private int keyCode;

    public Key(int UnicodeKeyCode){
        this.keyCode = UnicodeKeyCode;
    }

    public int getKeyCode(){
        return keyCode;
    }

}