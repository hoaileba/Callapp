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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author admin123
 */
public class ClientThread extends Thread{
    public TargetDataLine audio_in = null;
    public SourceDataLine audio_out;
    public DatagramSocket client ;
    byte byte_buff[] = new byte[512];
    byte []buffer =  new byte[512];
    public InetAddress server_ip;
    public int server_port ;
    public String targetCallName ;
    public int runningPort;
    public boolean startCall = false;
        public void sendStringRequest(String request,int port){
      
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);
//            dataOut.writeInt(localport);
            dataOut.writeUTF(request);
            dataOut.close();

            byte[] bytes = byteOut.toByteArray();
            InetAddress inet = InetAddress.getByName("127.0.0.1");
            System.out.println(inet);
            System.out.println(port);
            DatagramPacket data = new DatagramPacket(bytes, bytes.length, inet, port);
            client.send(data);
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public String receiveStringRequest(){
        String respone = null;
        try {
            byte [] buffer = new byte[512];
            DatagramPacket incomming = new DatagramPacket(buffer, buffer.length);
            client.receive(incomming);
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
    @Override
    public void run(){
        int i = 0;
//        client.send()
        
        while(startCall){
            String request = "Call_"+ runningPort + "_"+targetCallName;
            
            sendStringRequest(request,server_port);
            String respone = receiveStringRequest();
            if(respone != null  )
            {
                String []p = respone.split("_");
                if(p[0].equals("Yes"))
                {
                    
                    server_port = Integer.parseInt(p[1]);
//                    break;
                }
                if(p[0].equals("Accept"))
                {
                    ClientVoice.calling = true;
                    break;
                }
            }
//            break;
        }
        while(ClientVoice.calling){
            try {
                audio_in.read(byte_buff, 0 , byte_buff.length);
                DatagramPacket data = new DatagramPacket(byte_buff, byte_buff.length, server_ip, server_port);
                i+=1;
                System.out.println("send "+ server_port);
                client.send(data);
                
                DatagramPacket incomming = new DatagramPacket(buffer, buffer.length);
                client.receive(incomming);
                buffer = incomming.getData();
                
                
                System.out.println(buffer)
                        ;
                audio_out.write(buffer, 0, buffer.length);
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        audio_out.close();
        audio_out.drain();
        System.out.println("stop");
        
        
    }
}
