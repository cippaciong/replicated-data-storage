package it.polimi.distsys.server.messages;

import java.util.Set;

public class JoinAck {
    private String action;
    private String senderID;
    private String recipienID;
    private Set<String> knownIDs;

    public JoinAck(String senderID, String recipienID, Set<String> knownIDs) {
        this.action = "joinAck";
        this.senderID = senderID;
        this.recipienID = recipienID;
        this.knownIDs = knownIDs;
    }

    public String getAction() {
        return action;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getRecipienID() {
        return recipienID;
    }

    public Set<String> getKnownIDs() {
        return knownIDs;
    }
}
