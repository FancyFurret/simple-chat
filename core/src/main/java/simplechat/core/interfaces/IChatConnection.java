package main.java.simplechat.core.interfaces;

public interface IChatConnection {
    void registerListener(IChatListener listener);
    void sendMessage(String message);
}
