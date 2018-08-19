package it.polimi.distsys;

import io.reactivex.disposables.Disposable;
import it.polimi.distsys.server.Server;

public class App {


    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();

        server.joinGroup();
        server.run();

        System.out.println("End of stream operations");
    }
}

