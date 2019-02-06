package main.java.simplechat.core.interfaces;

import main.java.simplechat.core.model.User;

import java.util.Collection;

public interface IChatConnection {
    void registerListener(IChatListener listener);

    void sendMessage(String message);

    Collection<User> getUsers();
}
