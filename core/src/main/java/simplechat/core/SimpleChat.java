package main.java.simplechat.core;

import main.java.simplechat.core.interfaces.IChatListener;
import main.java.simplechat.core.interfaces.ISimpleChatEventListener;

import java.util.ArrayList;

public class SimpleChat {
    private ArrayList<ISimpleChatEventListener> eventListeners;
    private ArrayList<IChatListener> chatListeners;

    public SimpleChat() {
        eventListeners = new ArrayList<>();
        chatListeners = new ArrayList<>();
    }

    public void registerEventListener(ISimpleChatEventListener listener) {
        eventListeners.add(listener);
    }

    public void registerChatListener(IChatListener listener) {
        chatListeners.add(listener);
    }

    public void start() {
        // TODO: Make this actually do stuff
        new Thread(() -> {
            try {
                // Loading...
                Thread.sleep(1500);
                started();

                // Send message
                Thread.sleep(1000);
                newMessage(new Message("You got mail!"));

                // Send another message
                Thread.sleep(1000);
                newMessage(new Message("You got more mail!"));

                // Send a bunch more messages
                Thread.sleep(1000);
                for (int i = 0; i < 10; i++)
                    newMessage(new Message("This is message " + i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void started() {
        UserList users = new UserList();
        users.addUser(new User("Bob"));
        eventListeners.forEach(listener -> listener.started(users));
    }

    private void newMessage(Message message) {
        chatListeners.forEach(listener -> listener.newMessage(message));
    }
}
