/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NetworkChatTestSwing.java
 *
 * Created on 17.03.2011, 04:48:25
 */

package core.net;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.OOUtil;

/**
 *
 * @author Philipp
 */
public class NetworkChatTestSwing extends javax.swing.JFrame {

    public static TCPHost host;
    public static TCPClient client;
    public static String nickName;
    public static boolean isHost;
    public static String chatText="";

    public NetworkChatTestSwing() {
        initComponents();
        new Reciever();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        textField = new javax.swing.JTextField();
        SendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textArea.setColumns(20);
        textArea.setEditable(false);
        textArea.setRows(5);
        ScrollPane.setViewportView(textArea);

        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldActionPerformed(evt);
            }
        });
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldKeyPressed(evt);
            }
        });

        SendButton.setText("Send");
        SendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(ScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(textField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(SendButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(ScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 285, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, SendButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, textField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldActionPerformed

    private void textFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyPressed
        if(evt.getKeyCode() == 10){
            SendButtonActionPerformed(null);
        }
    }//GEN-LAST:event_textFieldKeyPressed

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed
        if(isHost){
            chatText += nickName + ": " + textField.getText() + "\n";
            textArea.setText(chatText);
            try {
                host.sendMessage(nickName + ": " + textField.getText());
            } catch (IOException ex) {
                Logger.getLogger(NetworkChatTestSwing.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            textField.setText("");
        }
        else{
            try {
                client.sendMessage(nickName + ": " + textField.getText());
            } catch (IOException ex) {
                Logger.getLogger(NetworkChatTestSwing.class.getName()).log(Level.SEVERE, null, ex);
            }
            textField.setText("");
        }
        System.out.println(chatText);
    }//GEN-LAST:event_SendButtonActionPerformed

    public static void main(String args[]) throws IOException {
        
        char readChar = 'x';
        while(readChar != 'h' && readChar != 'c'){
            readChar = OOUtil.readChar("Host (h) or Client (c) ?\n");
        }

        if(readChar == 'h'){
            host = new TCPHost(OOUtil.readInt("Port: "));

            System.out.println("Waiting for Connections...");

            while(host.connected == false){
                try{
                    host.acceptConnection();
                }
                catch(Exception e){
                }
            }

            isHost = true;
            
        }
        else{

            client = new TCPClient(OOUtil.readString("Hostname: "), OOUtil.readInt("Port: "));       

            isHost = false;

        }
        
        System.out.println("Connection established!");
        nickName = OOUtil.readString("Nickname: ");

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NetworkChatTestSwing().setVisible(true);
            }
        });
        
    }
    
    private class Reciever implements Runnable {
        
        public Reciever(){
            new Thread(this).start();
        }
        
        public void run() {
            while(true){
                try {
                    if(NetworkChatTestSwing.isHost){
                        String str = null;
                        for(int i = 0; i < host.getNumberOfClients(); i++){
                            str = host.recieveMessage(i);
                            if(str != null){
                                NetworkChatTestSwing.chatText += str + "\n";
                            }
                            str = null;
                        }
                        textArea.setText(NetworkChatTestSwing.chatText);
                        System.out.println(NetworkChatTestSwing.chatText);
                    }
                    else{
                        String str = client.recieveMessage();
                        if(str != null){
                            NetworkChatTestSwing.chatText += str + "\n";
                        }
                        textArea.setText(NetworkChatTestSwing.chatText);
                        System.out.println(NetworkChatTestSwing.chatText);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NetworkChatTestSwing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JScrollPane ScrollPane;
    public javax.swing.JButton SendButton;
    public javax.swing.JTextArea textArea;
    public javax.swing.JTextField textField;
    // End of variables declaration//GEN-END:variables

}