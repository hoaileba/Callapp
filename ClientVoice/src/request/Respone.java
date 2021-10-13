/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request;

import java.io.Serializable;

/**
 *
 * @author admin123
 */
public class Respone implements Serializable{
    private boolean accept ;
    private int port ;

    public Respone() {
    }

    public Respone(boolean accept, int port) {
        this.accept = accept;
        this.port = port;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
}
