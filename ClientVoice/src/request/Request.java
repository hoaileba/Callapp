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
public class Request implements Serializable{
    private String username;
    private String type;
    private int localport;
    private String usernameCall;

    public Request() {
    }

    public Request(String username, String type, int localport, String usernameCall) {
        this.username = username;
        this.type = type;
        this.localport = localport;
        this.usernameCall = usernameCall;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLocalport() {
        return localport;
    }

    public void setLocalport(int localport) {
        this.localport = localport;
    }

    public String getUsernameCall() {
        return usernameCall;
    }

    public void setUsernameCall(String usernameCall) {
        this.usernameCall = usernameCall;
    }
    
    
}
