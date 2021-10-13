/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servervoice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;
import model.Client;
import request.Request;

/**
 *
 * @author admin123
 */
public class ServerThread extends Thread{
    
    public DatagramSocket server;
    public SourceDataLine audio_out;
    byte []buffer =  new byte[512];
    public ArrayList<Client> allClient;
    public ArrayList<Request> allRequest;

    public ServerThread() {
        allClient = new ArrayList<>();
        allRequest = new ArrayList<>();
    }
    
    
    @Override
    public void run(){
        try {
            while(ServerVoice.calling)
            {
                DatagramPacket incomming = new DatagramPacket(buffer, buffer.length);
                byte []buffer =  new byte[512];
                server.receive(incomming);
                buffer = incomming.getData();
                ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer);
                DataInputStream dataIn = new DataInputStream(byteIn);
                String request = dataIn.readUTF();
                String []part = request.split("_");
                System.out.println(request);
                System.out.println(allClient.size());
                if(part[0].equals("Access"))
                {
                    boolean exist = false;
                    for(int i = 0 ; i < allClient.size() ; i++)
                    {
                        if(allClient.get(i).getUsername().equals(part[2]))
                        {
                            allClient.get(i).setPort(Integer.parseInt(part[1]));
                            exist = true;
                            break;
                        }

                    }
                    if (exist == false){
                        Client c = new Client(part[2],Integer.parseInt(part[1]) );
                        allClient.add(c);
                    }

                }
                if(part[0].equals("Call"))
                {   
                    System.out.println("In");
                    String name = part[2];
                    int callport = -1;
                    for(Client c : allClient)
                    {
                        if(c.getUsername().equals(name))
                        {
                            callport = c.getPort();
                            break;
                        }
                    }
                    if(callport==-1)
                    {
                        String respone = "No";
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        DataOutputStream dataOut = new DataOutputStream(byteOut);
                        dataOut.writeUTF(respone);
                        dataOut.close();
                        InetAddress inet = incomming.getAddress();

                        byte[] bytes = byteOut.toByteArray();

                        DatagramPacket data = new DatagramPacket(bytes, bytes.length, inet,Integer.parseInt(part[1]) );
                        server.send(data);
                    }
                    else{
                        
                        String respone1 = "Yes_"+callport;
                        
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        DataOutputStream dataOut = new DataOutputStream(byteOut);
                        dataOut.writeUTF(respone1);
                        dataOut.close();
                        InetAddress inet = incomming.getAddress();

                        byte[] bytes = byteOut.toByteArray();
                        
                        DatagramPacket data = new DatagramPacket(bytes, bytes.length,inet, Integer.parseInt(part[1]) );
                        String respone = "CallFrom_"+Integer.parseInt(part[1]);
                        System.out.println(respone + callport);
                        byteOut = new ByteArrayOutputStream();
                        dataOut = new DataOutputStream(byteOut);
                        dataOut.writeUTF(respone);
                        dataOut.close();
                        inet = incomming.getAddress();
                        
                        bytes = byteOut.toByteArray();
                        DatagramPacket data_1 = new DatagramPacket(bytes, bytes.length, inet, callport);
                        server.send(data);
                        server.send(data_1);
                    }

                }


    //                try {
    //                    server.receive(incomming);
    //                    InetAddress inet = incomming.getAddress();
    //                    int client_port = incomming.getPort();
    ////                    System.out.println(inet);
    ////                    System.out.println(client_port);
    //                    buffer = incomming.getData();
    //                    DatagramPacket sendback = new DatagramPacket(buffer, buffer.length, inet, client_port);
    //                    server.send(sendback);
    ////                System.out.println(buffer);
    ////                audio_out.write(buffer, 0, buffer.length);
    //
    //                } catch (IOException ex) {
    //                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
    //                }
//                

            }
            audio_out.close();
            audio_out.drain();
            System.out.println("stop");
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
