package main.java.simplechat.core.interfaces;

import main.java.simplechat.core.Message;
import main.java.simplechat.core.User;

public interface IChatListener {
    void userJoined(User user);
    void userLeft(User user);
    void newMessage(Message message);
}
