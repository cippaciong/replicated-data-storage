/*package it.polimi.distsys.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.distsys.server.messages.Join;
import it.polimi.distsys.server.multicast.MulticastPublisher;
import it.polimi.distsys.server.multicast.Node;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MessageHandler {

    private MulticastPublisher publisher = new MulticastPublisher("230.0.0.0", 4446);

    public List<String> handleMessage(String message, List<Node> nodes) {

        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        String action = jsonObject.get("action").getAsString();

        switch (action) {
            case "join":
                handleJoin(message, nodes);
                break;
            case "write":
                System.out.println("Got a write");
                publisher.publish("Got a write");
                break;
            case "read":
                System.out.println("Got a read");
                publisher.publish("Got a, read");
                break;
            case "ack":
                System.out.println("Someone wants to join the group");
                publisher.publish("JoinAck");
                break;
            case "joinAck":
                System.out.println("Got a joinack");
                publisher.publish("Got a, joinack");
                break;
        }
//        return Arrays.asList(parts);
        return Arrays.asList("");


//        if (message.contains("write,") || message.contains("read,") || message.contains("join,")
//            || message.contains("ack,") || message.contains("joinack,")) {
//
//            System.out.println("here");
//
//            String[] parts = message.split(",");
//            String operation = parts[2].replaceAll("\\s","");
//            SWITCHONE
//        } else {
//            System.out.println(message);
//            return Collections.emptyList();
//        }
    }

    public Boolean isJoinAck(String message) {
        if ("JoinAck".equals(message)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void handleJoin(String message, List<Node> nodes) {
        Gson gson = new Gson();
        Join join = gson.fromJson(message, Join.class);
        Node node = new Node(join.getIPAddress(), join.getPort(), UUID.fromString(join.getSenderID()));
    }
}*/
