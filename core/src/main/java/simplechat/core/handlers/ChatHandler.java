package main.java.simplechat.core.handlers;

import main.java.simplechat.core.exceptions.ChatException;
import main.java.simplechat.core.interfaces.IChatConnection;
import main.java.simplechat.core.interfaces.IChatListener;
import main.java.simplechat.core.interfaces.INetworkListener;
import main.java.simplechat.core.model.Message;
import main.java.simplechat.core.model.User;
import main.java.simplechat.core.model.UserList;
import main.java.simplechat.core.network.Network;
import org.jgroups.Address;

import java.util.ArrayList;
import java.util.Collection;

public class ChatHandler implements IChatConnection, INetworkListener {

    private User me;
    private UserList users;
    private Network network;
    private ArrayList<IChatListener> listeners;

    public ChatHandler() {
        users = new UserList();
        network = new Network();
        listeners = new ArrayList<>();
    }

    public void connect(String group) throws ChatException {
        network.registerListener(this);
        network.start(group, this);

        me = new User(network.getMe().toString());
        for (Address addr : network.getMembers())
            users.addUser(new User(addr.toString()));
    }

    public void stop() {
        network.stop();
    }

    @Override
    public void registerListener(IChatListener listener) {
        listeners.add(listener);
    }

    @Override
    public void sendMessage(String message) {
        for (Address addr : network.getMembers()) {
            network.call(addr, "recvMessage", new Message(me, message), Message.class);
        }
    }

    @Override
    public Collection<User> getUsers() {
        return users.getUsers();
    }

    @Override
    public void userJoined(Address address) {
        listeners.forEach(listener -> listener.userJoined(new User(address.toString())));
    }

    @Override
    public void userLeft(Address address) {
        listeners.forEach(listener -> listener.userLeft(new User(address.toString())));
    }

    @Override
    public void error(String message, Exception e) {
        e.printStackTrace();
        error(message);
    }

    private void error(String message) {
        listeners.forEach(listener -> listener.error(message));
    }

    private void recvMessage(Message message) {
        listeners.forEach(listener -> listener.recvMessage(message));
    }
}
