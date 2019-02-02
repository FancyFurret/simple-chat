package main.java.simplechat.core.handlers;

import main.java.simplechat.core.exceptions.ChatException;
import main.java.simplechat.core.model.Message;
import main.java.simplechat.core.interfaces.IChatConnection;
import main.java.simplechat.core.interfaces.IChatListener;

import java.util.ArrayList;

public class ChatHandler implements IChatConnection {

    private NetworkHandler network;
    private ArrayList<IChatListener> listeners;

    public ChatHandler() {
        network = new NetworkHandler();
        listeners = new ArrayList<>();
    }

    public void connect(String multicastIp, int port) throws ChatException {
        network.startMulticast(multicastIp, port);
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
        recvMessage(new Message(message));
    }

    private void error(String message) {
        listeners.forEach(listener -> listener.error(message));
    }

    private void recvMessage(Message message) {
        listeners.forEach(listener -> listener.recvMessage(message));
    }
}
