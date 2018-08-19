package it.polimi.distsys.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.distsys.server.messages.Join;
import it.polimi.distsys.server.messages.JoinAck;
import it.polimi.distsys.server.multicast.MulticastPublisher;
import it.polimi.distsys.server.multicast.MulticastReceiver;
import it.polimi.distsys.server.multicast.Node;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class Server {

    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

    private UUID uuid = UUID.randomUUID();
    private String IPAddress;
    private String multicastAddress = "230.0.0.0";
    private int port = 4446;
    private HashMap<String, Node> nodes = new HashMap<>();
    private MulticastPublisher multicastPublisher = new MulticastPublisher(multicastAddress, port);
    private MulticastReceiver multicastReceiver = new MulticastReceiver();
//    private MessageHandler messageHandler = new MessageHandler();
    private Gson gson = new Gson();

    public Server() {
        {
            try {
                this.IPAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                this.IPAddress = "Unknown IP Address";
                LOGGER.warning("Unable to detect the server's IP Address");
            }
        }

    }

    public void joinGroup() {
        Join join = new Join(uuid, IPAddress);
        Gson gson = new Gson();
        String joinJson = gson.toJson(join);

        System.out.println("Sending Join request");
        System.out.println(joinJson);
        multicastPublisher.publish(joinJson);

/*        return multicastReceiver.getData()
                .subscribeOn(Schedulers.io())
                .filter(message -> messageHandler.isJoinAck(message))
                .map(message -> messageHandler.handleMessage(message))
                .subscribe(element -> {
                    System.out.println(String.format("There are %d members in the group", nodes.size()));
                });*/

    }

    public void run() {
        System.out.println("Starting message processing... " + uuid.toString());
        multicastReceiver.getData()
//                .subscribeOn(Schedulers.io())
                .map(message -> handleMessage(message))
                .subscribe(element -> System.out.println(element + " - " + uuid.toString()));

    }

    private String handleMessage(String message) {

        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        String action = jsonObject.get("action").getAsString();
        System.out.println("Server " + uuid.toString() + "received action: " + action);

        switch (action) {
            case "join":
                handleJoin(message);
                break;
            case "joinAck":
                handleJoinAck(message);
                break;
            case "write":
                System.out.println("Got a write");
                multicastPublisher.publish("Got a write");
                break;
            case "read":
                System.out.println("Got a read");
                multicastPublisher.publish("Got a, read");
                break;
            case "ack":
                System.out.println("Someone wants to join the group");
                multicastPublisher.publish("JoinAck");
                break;
        }
//        return Arrays.asList(parts);
        return message;
    }

    public void wirite(Integer dataId, Integer value) {

    }

    // Data to be replicated are INTEGER numbers,
    // Each identified by a unique id (integer?).
    // N servers keep a copy of the data shared by clients, offering two primitives:

    public Integer read(Integer dataId) {
        return 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Boolean isJoinAck(String message) {
        if ("JoinAck".equals(message)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void handleJoin(String message) {
        Join join = gson.fromJson(message, Join.class);
        Node node = new Node(UUID.fromString(join.getSenderID()), "confirmed");
        nodes.put(join.getSenderID(), node);

        System.out.println("Received Join request from " + join.getSenderID());

        sendJoinAck(join.getSenderID());

    }

    private void sendJoinAck(String recipientID) {
        JoinAck joinAck = new JoinAck(uuid.toString(), recipientID, nodes.keySet());
        String jsonJoinAck = gson.toJson(joinAck);
        System.out.println("Sending Join Ack to " + recipientID);
        multicastPublisher.publish(jsonJoinAck);
    }

    private void handleJoinAck(String message) {
        JoinAck joinAck = gson.fromJson(message, JoinAck.class);
        String recipientID = joinAck.getRecipienID();

        System.out.println("Received Join Ack from " + joinAck.getSenderID());

        //check if the message was sent to you
        if (recipientID.equals(uuid.toString())) {
            String senderID = joinAck.getSenderID();

            //check if the sender is in the node list
            if (nodes.containsKey(senderID)) {

                // yes -> change its status to 'confirmed'
                Node sender = nodes.get(senderID);
                if (!"confirmed".equals(sender.getStatus())) {
                    sender.setStatus("confirmed");
                    nodes.replace(senderID, sender);
                } else {
                // no -> add it with status 'confirmed'
                    Node node = new Node(UUID.fromString(senderID), "confirmed");
                    nodes.put(senderID, node);
                }
            }

            //for each node in known ids:
                // if the node is not yet in the list
                // add it with status 'pending'
            for(String nodeID : joinAck.getKnownIDs()) {

                if (!nodes.containsKey(senderID)) {
                    Node node = new Node(UUID.fromString(senderID), "pending");
                    nodes.put(nodeID, node);
                }
            }


        }

        System.out.println("Finished handling the Join Ack, current nodes are " + nodes.toString());

    }

}
