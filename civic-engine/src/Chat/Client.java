/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Chat;

/**
 *
 * @author Basti
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.OOUtil;

class Client {
    public static String nickName;
    public Client(){
        nickName = OOUtil.readString("(client)Nick: ");
       new Send();
       while(true){
        try {
             Socket skt = new Socket("localhost", 1234);
             BufferedReader in = new BufferedReader(new
                InputStreamReader(skt.getInputStream()));

            while (!in.ready()) {}
            System.out.println(in.readLine()); // Read one line and output it

            System.out.print("\n");
            in.close();
        }
        catch(Exception e) {
             System.out.print("Whoops! It didn't work!\n");
        }
      }
    }
   public static void main(String args[]) {
       new Client();
   }
   private class Send extends Thread{
       public Send(){
            this.start();
        }
        @Override
       public void run(){
            while(true){
        try {
             Socket skt = new Socket("localhost", 1234);
             PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
             String str = OOUtil.readString("Message: ");
             out.print(nickName+": "+str);
             out.close();
             skt.close();
            }
        catch(Exception e){}
        /*try {
                    this.sleep(1L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }*/
       }

       }
   }
}