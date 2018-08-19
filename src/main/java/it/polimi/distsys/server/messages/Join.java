package it.polimi.distsys.server.messages;

import java.util.UUID;

public class Join {
    private String action;
    private String senderID;
    private String IPAddress;
    private int port;

    public Join(UUID senderID, String IPAddress ) {
        this.action = "join";
        this.senderID = senderID.toString();
        this.IPAddress = IPAddress;
    }

    public String getAction() {
        return action;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public int getPort() {
        return port;
    }
}
