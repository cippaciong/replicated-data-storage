package it.polimi.distsys.server;

import it.polimi.distsys.server.multicast.MulticastPublisher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessageHandler {

    private MulticastPublisher publisher = new MulticastPublisher("230.0.0.0", 4446);

    public Boolean isJoinAck(String message) {
        if ("JoinAck".equals(message)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public List<String> handleMessage(String message) {

        if (message.contains("write,") || message.contains("read,") || message.contains("join,")
            || message.contains("ack,") || message.contains("joinack,")) {

            System.out.println("here");

            String[] parts = message.split(",");
            String operation = parts[2].replaceAll("\\s","");

            switch (operation) {
                case "write":
                    System.out.println("Got a write");
                    publisher.publish("Got a write");
                    break;
                case "read":
                    System.out.println("Got a read");
                    publisher.publish("Got a, read");
                    break;
                case "join":
                    System.out.println("Got a join");
                    publisher.publish("Got a, join");
                    break;
                case "Ack":
                    System.out.println("Someone wants to join the group");
                    publisher.publish("JoinAck");
                    break;
                case "joinack":
                    System.out.println("Got a joinack");
                    publisher.publish("Got a, joinack");
                    break;
            }
            return Arrays.asList(parts);

        } else {
            System.out.println(message);
            return Collections.emptyList();
        }
    }
}
