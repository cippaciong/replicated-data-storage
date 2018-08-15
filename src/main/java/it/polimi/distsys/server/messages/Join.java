package it.polimi.distsys.server.messages;

import java.util.UUID;

public class Join {
    private String action;
    private String sender;

    public Join(UUID sender) {
        this.action = "join";
        this.sender = sender.toString();
    }

    public String getAction() {
        return action;
    }

    public String getSender() {
        return sender;
    }
}
