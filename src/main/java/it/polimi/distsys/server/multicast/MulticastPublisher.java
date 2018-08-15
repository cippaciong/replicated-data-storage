package it.polimi.distsys.server.multicast;

import it.polimi.distsys.server.Server;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

public class MulticastPublisher {

    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

    private DatagramSocket socket;
    private InetAddress group;
    private int port;
    private byte[] buf;

    public MulticastPublisher(String address, int port) {
        try {
            this.group = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            LOGGER.severe("Unable to initialize publish publisher with the given address: " + address);
        }

        this.port = port;
    }


    public void publish(String message ) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            LOGGER.severe("Unable to instantiate datagram socket");
        }
        buf = message.getBytes();

        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, group, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            LOGGER.severe("Unable to sent publish packet");
        }
        socket.close();
    }
}
