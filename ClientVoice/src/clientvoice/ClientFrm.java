/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientvoice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author admin123
 */
public class ClientFrm extends javax.swing.JFrame {

    /**
     * Creates new form ClientFrm
     */
    public int port_server  = 2021;
    public String add_server = "127.0.0.1";
    public static AudioFormat getAudioFormat(){
        float sampleRate = 8000.0F;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean bigEndian = false;
        boolean signed = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    };
    TargetDataLine audio_in ;
    SourceDataLine audio_out;
    DatagramSocket socket;
    ClientThread cr ;
    Thread audio ;
    
    boolean isStop = true;
    boolean startClick = false;
//    int runningPort = 1111;
//    String name = "LeBaHoai", targetName = "hoaileba" ;
    int runningPort = 1112;
    String name = "hoaileba", targetName = "LeBaHoai";
    Thread thread ;
    public ClientFrm() {
        try {
            initComponents();
//            String name = "hoaileba" ;
            jLabel1.setText(name+ "_" + runningPort);
//            byte [] sendData = new byte[512];
//            System.out.println(sendData.length);
//            DatagramPacket data = new DatagramPacket(sendData, sendData.length, inet, port_server);
            socket = new DatagramSocket(runningPort);
            
            int localport = socket.getLocalPort();
            System.out.println(localport);
            String request = "Access_"+localport+"_" + name;
            sendStringRequest(request,port_server);

            System.out.println(socket.getLocalPort());
        } catch (SocketException ex) {
            Logger.getLogger(ClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        strBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        strBtn.setText("Start");
        strBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strBtnActionPerformed(evt);
            }
        });

        jButton2.setText("Stop");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        jButton1.setText("Accept");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(strBtn)
                            .addComponent(jButton2)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(strBtn)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(26, 26, 26)
                        .addComponent(jButton1)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void strBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_strBtnActionPerformed
        int callPort = -1;
        int port = port_server;
        if (socket.isClosed()){
            try {
                socket = new DatagramSocket(runningPort);
            } catch (SocketException ex) {
                Logger.getLogger(ClientFrm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        startClick = true;
        System.out.println(port_server);
        init_audio(port_server);
    }//GEN-LAST:event_strBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ClientVoice.calling = false;
        cr.stop();
        audio_in.stop();
//        cr.client.close();
        cr.audio_in.stop();
        strBtn.setEnabled(true);
        jButton2.setEnabled(false);
        port_server = 2021;
        startClick = false;
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (socket.isClosed()){
            try {
                socket = new DatagramSocket(runningPort);
            } catch (SocketException ex) {
                Logger.getLogger(ClientFrm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String respone = receiveStringRequest();
        String []p = respone.split("_");
        int callPort = Integer.parseInt(p[1]);
        System.out.println("acc"+callPort);
        String request = "Accept_LeBaHoai";
        sendStringRequest(request, callPort);
        init_audio(callPort);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrm().setVisible(true);
            }
        });
    }
    public void init_audio(int callPort){
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info infor_send = new DataLine.Info(TargetDataLine.class, format);
            if(!AudioSystem.isLineSupported(infor_send))
            {
                System.out.println("Not suppport");
                System.exit(0);
            }
            DataLine.Info infor_receive = new DataLine.Info(SourceDataLine.class, format);
            if(!AudioSystem.isLineSupported(infor_receive))
            {
                System.out.println("Not suppport");
                System.exit(0);
            }
            audio_in = (TargetDataLine) AudioSystem.getLine(infor_send);
            audio_in.open(format);
            audio_in.start();
            
            audio_out = (SourceDataLine) AudioSystem.getLine(infor_receive);
            audio_out.open(format);
            audio_out.start();
            cr = new ClientThread();
            InetAddress inet = InetAddress.getByName(add_server);
            System.out.println(inet);
            cr.audio_in = audio_in;
            cr.audio_out = audio_out;
            cr.client = socket;
            cr.server_ip = inet;
            cr.server_port = callPort; 
            cr.runningPort = runningPort;
            cr.targetCallName = targetName;
            cr.startCall = startClick;
            ClientVoice.calling = true;
           
            cr.start();
            strBtn.setEnabled(false);
            jButton2.setEnabled(true);
            
            
        } catch (LineUnavailableException ex) {
            Logger.getLogger(ClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendStringRequest(String request,int port){
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);
//            dataOut.writeInt(localport);
            dataOut.writeUTF(request);
            dataOut.close();

            byte[] bytes = byteOut.toByteArray();
            InetAddress inet = InetAddress.getByName(add_server);
            DatagramPacket data = new DatagramPacket(bytes, bytes.length, inet, port);
            socket.send(data);
        } catch (IOException ex) {
            Logger.getLogger(ClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String receiveStringRequest(){
        String respone = null;
        try {
            byte [] buffer = new byte[512];
            DatagramPacket incomming = new DatagramPacket(buffer, buffer.length);
            socket.receive(incomming);
            buffer = incomming.getData();
            ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer);
            DataInputStream dataIn = new DataInputStream(byteIn);
            respone = dataIn.readUTF();
            System.out.println(respone);
        } catch (IOException ex) {
            Logger.getLogger(ClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respone;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton strBtn;
    // End of variables declaration//GEN-END:variables

    private int getPort() {
        int callPort = -1;
        int port = port_server;
        while(ClientVoice.calling == false)
        {
            //                String respone = "";
            String name = jTextField1.getText();
            String request = "Call_"+ runningPort + "_"+name;
            
            sendStringRequest(request,port);
            String respone = receiveStringRequest();
            if(respone != null  )
            {
                String []p = respone.split("_");
                jTextArea1.append(respone);
                if(p[0].equals("Yes"))
                {
                    
                    callPort = Integer.parseInt(p[1]);
                    port = callPort;
//                    break;
                }
                if(p[0].equals("Accept"))
                {
                    
//                    callPort = Integer.parseInt(p[1]);
//                    port = callPort;
                    break;
                }
            }
        }
        return callPort;
        
    }
}
