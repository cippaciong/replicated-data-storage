package it.polimi.distsys.server.multicast;

import io.reactivex.Observable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {

    private MulticastSocket socket = null;
    private int port = 4446;
    private String address = "230.0.0.0";
    private byte[] buf = new byte[256];

    private Observable<String> observable =
            Observable.create( subscriber -> {
                try {
                    socket = new MulticastSocket(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Join a publish group
                InetAddress group = null;
                try {
                    group = InetAddress.getByName(address);
                    socket.joinGroup(group);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Enter the receive loop
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        subscriber.onError(e);
                    }
                    String received = new String(
                            packet.getData(), 0, packet.getLength());
                    if ("end".equals(received)) {
                        subscriber.onComplete();
                        break;
                    }
                    else {
                        subscriber.onNext(received);
                    }
                }
                try {
                    socket.leaveGroup(group);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket.close();
            });

    public Observable<String> getData() {
        return this.observable;
    }
}
