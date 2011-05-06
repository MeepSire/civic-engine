/**
* @author Bastian Hinterleitner
*/

// CiviC Engine

package utils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 *
 * @author Bastian Hinterleitner
 */
public abstract class SimpleFile {
    /**
     * downloads a file from an internet url to a destination file
     * @param url address of the file you want to download
     * @param dest path to the file you want to save it as
     * @return returns whether successful
     * @throws IOException java.io.BufferedInputStream and java.io.FileOutputStream
     */
    public static boolean download(String url, String dest) throws IOException{
        try{
            java.io.BufferedInputStream in = new java.io.BufferedInputStream(new
            java.net.URL(url).openStream());
            java.io.FileOutputStream fos = new java.io.FileOutputStream(dest);
            //java.io.BufferedOutputStream bout = new BufferedOutputStream(fos,4096);
            //byte data[] = new byte[4096];
            //while(in.read(data,0,4096)>=0)
            int oneChar =0;
            while((oneChar=in.read())>=0)
            {
                //bout.write(data);
                fos.write(oneChar);
            }
            //bout.close();
            in.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    /**
     *reads a textfile and returns the content as a String
     * @param url path to the textfile
     * @return String containing the content of the file
     */
    public static String read(String url){
        String result="";
        File file = new File(url);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
            BufferedReader d = new BufferedReader(new InputStreamReader(dis));

            // dis.available() returns 0 if the file does not have more lines.
            while (d.ready()) {

            // this statement reads the line from the file and print it to
            // the console.
            result += d.readLine()+"\n";
        }

        // dispose all the resources after using them.
        fis.close();
        bis.close();
        dis.close();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return result;
    }
    /**
     *saves a textfile
     * @param str the String the textfail will contain
     * @param url the Path to the file you want to save it to
     * @return returns whether successful
     */
    public static boolean save(String str, String url){
            try{
                FileWriter fstream = new FileWriter(url);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(str);
                out.close();
                return true;
            }
            catch(Exception e){
                return false;
            }
    }

    /**
     *Copies a file to a given location
     * @param sourceFile the File you want to copy
     * @param destFile the File you want it to be copied to
     * @throws IOException FileInputStream and FileOutputStream
     */
    public static void copy(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }
}
