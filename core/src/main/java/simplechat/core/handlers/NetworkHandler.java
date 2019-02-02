package main.java.simplechat.core.handlers;

import main.java.simplechat.core.exceptions.ChatException;

import java.io.IOException;
import java.net.MulticastSocket;

class NetworkHandler {
    private MulticastSocket multicastSocket;

    void startMulticast(String multicastIp, int port) throws ChatException {
        try {
            multicastSocket = new MulticastSocket(port);
        } catch (IOException e) {
            throw new ChatException("Could not connect to mulitcast socket!", e);
        }
    }

    public void stop() {

    }
}
