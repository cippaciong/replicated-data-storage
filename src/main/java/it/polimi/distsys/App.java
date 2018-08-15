package it.polimi.distsys;

import com.google.gson.Gson;
import io.reactivex.disposables.Disposable;
import it.polimi.distsys.server.Server;
import it.polimi.distsys.server.messages.Join;

public class App {


    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();

        Join join = new Join(server.getUuid());
        Gson gson = new Gson();
        String joinJson = gson.toJson(join);
        Join newJoin = gson.fromJson(joinJson, Join.class);
        System.out.println(newJoin.getAction());

        Disposable joinGroupAction = server.joinGroup();
        server.handleMessages();


        System.out.println("End of stream operations");
    }
}

