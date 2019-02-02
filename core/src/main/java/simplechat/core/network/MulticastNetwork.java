package main.java.simplechat.core.network;

import main.java.simplechat.core.exceptions.ChatException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;

// TODO: make this reliable
public class MulticastNetwork extends Network {
    private int port;
    private InetAddress group;
    private MulticastSocket socket;
    private byte[] buffer = new byte[1024];

    public MulticastNetwork(Map<String, FunctionPair> functionMap) {
        super(functionMap);
    }

    @Override
    public void start(String ip, int port) throws ChatException {
        try {
            this.port = port;
            group = InetAddress.getByName(ip);
            socket = new MulticastSocket(port);
            socket.setLoopbackMode(false);
            socket.joinGroup(group);
            new Thread(this::loop).start();
        } catch (IOException e) {
            throw new ChatException("Could not start Multicast.", e);
        }
    }

    @Override
    public void stop() {
        try {
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) {
            error("Could not stop Multicast socket", e);
        }
    }

    @Override
    void sendMessage(String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
        } catch (IOException e) {
            error("Could not send Multicast message.", e);
        }
    }

    private void loop() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                if (!packet.getAddress().equals(InetAddress.getLocalHost()))
                    recvMessage(new String(packet.getData(), 0, packet.getLength()));
            }
        } catch (IOException e) {
            error("Multicast error.", e);
        }
    }
}
