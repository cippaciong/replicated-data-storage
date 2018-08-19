package it.polimi.distsys.server.multicast;

import java.util.UUID;

public class Node {
    private UUID id;
    private String status;

    public Node(UUID id, String status) {
        this.id = id;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
