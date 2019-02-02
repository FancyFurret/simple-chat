package main.java.simplechat.core.interfaces;

import main.java.simplechat.core.model.Message;
import main.java.simplechat.core.model.User;

public interface IChatListener {
    void userJoined(User user);
    void userLeft(User user);
    void recvMessage(Message message);
    void error(String message);
}
