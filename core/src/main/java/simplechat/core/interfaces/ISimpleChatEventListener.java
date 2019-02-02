package main.java.simplechat.core.interfaces;

import main.java.simplechat.core.model.UserList;

public interface ISimpleChatEventListener {
    void started(IChatConnection chat, UserList users);
    void connectionError(ISimpleChatController chatController, String message);
}
