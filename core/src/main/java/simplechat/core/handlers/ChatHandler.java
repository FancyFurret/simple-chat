package main.java.simplechat.core.handlers;

import main.java.simplechat.core.exceptions.ChatException;
import main.java.simplechat.core.interfaces.IChatConnection;
import main.java.simplechat.core.interfaces.IChatListener;
import main.java.simplechat.core.interfaces.INetworkListener;
import main.java.simplechat.core.model.Message;
import main.java.simplechat.core.network.FunctionPair;
import main.java.simplechat.core.network.MulticastNetwork;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatHandler implements IChatConnection, INetworkListener {

    private MulticastNetwork network;
    private ArrayList<IChatListener> listeners;

    public ChatHandler() {
        network = new MulticastNetwork(new HashMap<String, FunctionPair>() {{
            put("get_ip", new FunctionPair(
                    (args) -> {
                        try {
                            args.addProperty("ip", InetAddress.getLocalHost().getHostAddress());
                        } catch (UnknownHostException e) {
                            error("Could not get ip.", e);
                        }
                    },
                    (args) -> System.out.println("Found friend at: " + args.get("ip").getAsString())));
        }});

        listeners = new ArrayList<>();
    }

    public void connect(String multicastIp, int port) throws ChatException {
        network.registerListener(this);
        network.start(multicastIp, port);
        network.sendActionMessage("get_ip");

        // TODO loop here, waiting for messages?
    }

    public void stop() {
        network.stop();
    }

    @Override
    public void registerListener(IChatListener listener) {
        listeners.add(listener);
    }

    @Override
    public void sendMessage(String message) {
    }

    @Override
    public void error(String message, Exception e) {
        e.printStackTrace();
        error(message);
    }

    private void error(String message) {
        listeners.forEach(listener -> listener.error(message));
    }

    private void recvMessage(Message message) {
        listeners.forEach(listener -> listener.recvMessage(message));
    }
}
