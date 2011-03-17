package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OOUtil {
   private final float version = 1.1f;
   public String toString()      { return "OOUtil v" + version; }
   
   public static int readInt(){
      return readInt("Bitte geben Sie eine ganze Zahl ein: ");
   }

   public static int readInt(String msg){
      try {
         if (msg != null) System.out.print(msg);
         BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
         String number = input.readLine();
         return Integer.parseInt(number);
      } catch (NumberFormatException e) {
         return readInt(msg);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return 0;
   }
   
   public static float readFloat() {
      return readFloat("Bitte geben Sie eine Fliesskommazahl ein: ");
   }

   public static float readFloat(String msg) {
      try {
         if (msg != null) System.out.print(msg);
         BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
         String number = input.readLine();
         return Float.parseFloat(number);
      } catch (NumberFormatException e) {
         return readFloat(msg);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return 0;
   }
  
   public static double readDouble() {
      return readDouble("Bitte geben Sie eine Fliesskommazahl ein: ");
   }

   public static double readDouble(String msg) {
      try {
         if (msg != null) System.out.print(msg);
         BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
         String number = input.readLine();
         return Double.parseDouble(number);
      } catch (NumberFormatException e) {
         return readDouble(msg);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return 0.0;
   }

   public static String readString() {
      return readString("Bitte geben Sie einen String ein: ");
   }

   public static String readString(String msg) {
      try {
         if (msg != null) System.out.print(msg);
         BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
         return input.readLine();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "";
   }
  
   public static char readChar() {
      return readChar("Bitte geben Sie einen Character ein: ");
   }

   public static char readChar(String msg) {
      if (msg != null) System.out.print(msg);
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      try {
         char c = (char)input.read();
         if(c!='\n' && c!='\r' && c!='\t') {
            return c;
         } else {
            return readChar(msg);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return 'a';
   }
}