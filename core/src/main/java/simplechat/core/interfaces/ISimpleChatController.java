package main.java.simplechat.core.interfaces;

public interface ISimpleChatController {
    void registerListener(ISimpleChatEventListener listener);
    void start();
    void stop();
}
