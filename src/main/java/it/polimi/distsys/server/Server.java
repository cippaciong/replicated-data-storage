package it.polimi.distsys.server;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.polimi.distsys.server.multicast.MulticastPublisher;
import it.polimi.distsys.server.multicast.MulticastReceiver;

import java.util.UUID;
import java.util.logging.Logger;

public class Server {

    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

    private UUID uuid = UUID.randomUUID();

    private MulticastPublisher multicastPublisher = new MulticastPublisher("230.0.0.0", 4446);
    private MulticastReceiver multicastReceiver = new MulticastReceiver();

    private Integer groupMembers = 0;

    public Disposable joinGroup() {
        MessageHandler messageHandler = new MessageHandler();


        multicastPublisher.publish("join," + uuid.toString());

        return multicastReceiver.getData()
                .subscribeOn(Schedulers.io())
                .filter(message -> messageHandler.isJoinAck(message))
                //.map(message -> messageHandler.handleMessage(message))
                .subscribe(element -> {
                    this.groupMembers += 1;
                    System.out.println(String.format("There are %d members in the group", this.groupMembers));
                });

    }

    public void handleMessages() {
        MessageHandler messageHandler = new MessageHandler();
        multicastReceiver.getData()
                .map(message -> messageHandler.handleMessage(message))
                .subscribe(element -> System.out.println(element + " - " + uuid.toString()));

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



}
