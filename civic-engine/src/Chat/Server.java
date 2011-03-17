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

class Server {
    public static String nickName;
    public static boolean connection=true;
    public static ServerSocket srvr = null;

    public Server() throws IOException{
      nickName = OOUtil.readString("(host)Nick: ");
      System.out.println("Server started");
      srvr = new ServerSocket(1234);
      new Recieve();
      new Send();
      while(connection){
      }
      srvr.close();
    }

   public static void main(String args[]) throws IOException{
      new Server();
   }
   
   public static void send(String str){
       try {
             Socket skt = srvr.accept();
             //System.out.print("Server has connected!\n");
             PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
             out.print(nickName+": "+str);
             out.close();
             skt.close();
          }
          catch(Exception e) {
             System.out.print("Whoops! It didn't work!\n");
          }
   }
   private class Send extends Thread{
       public Send(){
           this.start();
       }
       public void run(){
           while(true){

             String str = OOUtil.readString("Message: ");
             send(str);
                try {
                    this.sleep(1L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
       }
   }
    private class Recieve extends Thread{
        public Recieve(){
            this.start();
        }
        @Override
        public void run(){
            while(true){
            try{
             Socket skt = srvr.accept();
             BufferedReader in = new BufferedReader(new
             InputStreamReader(skt.getInputStream()));

            while (!in.ready()) {}
             String str = in.readLine();
            System.out.println(str); // Read one line and output it
            send(str);

            System.out.print("\n");
            in.close();
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