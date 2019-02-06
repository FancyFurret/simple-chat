package main.java.simplechat.core;

import main.java.simplechat.core.exceptions.ChatException;
import main.java.simplechat.core.handlers.ChatHandler;
import main.java.simplechat.core.interfaces.ISimpleChatController;
import main.java.simplechat.core.interfaces.ISimpleChatEventListener;
import main.java.simplechat.core.model.User;
import main.java.simplechat.core.model.UserList;

import java.util.ArrayList;

public class SimpleChat implements ISimpleChatController {

    private final String GROUP = "com.eightbitforest.simplechat.jgroup";

    private ChatHandler chatHandler;
    private ArrayList<ISimpleChatEventListener> listeners;

    private SimpleChat() {
        listeners = new ArrayList<>();
    }

    public static ISimpleChatController newController() {
        return new SimpleChat();
    }

    @Override
    public void registerListener(ISimpleChatEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void start() {
        new Thread(() -> {
            try {
                chatHandler = new ChatHandler();
                chatHandler.connect(GROUP);
                started();
            } catch (ChatException e) {
                connectionError(e.printMessage());
            }
        }).start();
    }

    @Override
    public void stop() {
        chatHandler.stop();
    }

    private void started() {
        listeners.forEach(listener -> listener.started(chatHandler));
    }

    private void connectionError(String message) {
        listeners.forEach(listener -> listener.connectionError(this, message));
    }
}
