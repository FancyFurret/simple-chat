package main.java.simplechat.core.interfaces;

import org.jgroups.Address;

public interface INetworkListener {
    void userJoined(Address address);

    void userLeft(Address address);

    void error(String message, Exception e);
}
