package simplechat.core.interfaces;

import simplechat.core.Message;
import simplechat.core.User;

public interface IChatListener {
    void userJoined(User user);
    void userLeft(User user);
    void newMessage(Message message);
}
