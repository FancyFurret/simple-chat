package main.java.simplechat.core.interfaces;

public interface ISimpleChatEventListener {
    void started(IChatConnection chat);
    void connectionError(ISimpleChatController chatController, String message);
}
