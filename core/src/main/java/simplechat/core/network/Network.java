package main.java.simplechat.core.network;

import com.sun.istack.internal.NotNull;
import main.java.simplechat.core.exceptions.ChatException;
import main.java.simplechat.core.interfaces.INetworkListener;
import org.jgroups.*;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;

import java.util.ArrayList;
import java.util.List;

public class Network implements Receiver, MembershipListener {
    private ArrayList<INetworkListener> listeners;
    private RpcDispatcher dispatcher;
    private JChannel channel;
    private View oldView;

    public Network() {
        listeners = new ArrayList<>();
    }

    public void start(String group, Object serverObj) throws ChatException {
        try {
            channel = new JChannel().setReceiver(this);
            channel.setDiscardOwnMessages(false);
            dispatcher = new RpcDispatcher(channel, serverObj);
            dispatcher.setMembershipListener(this);
            channel.connect(group);
        } catch (Exception e) {
            throw new ChatException("Could not connect to JGroups channel.", e);
        }
    }

    public void stop() {
        channel.close();
    }

    public void registerListener(INetworkListener listener) {
        listeners.add(listener);
    }

    @Override
    public void receive(Message message) {
        System.out.println("Got message: " + message.getObject());
    }

    @Override
    public void viewAccepted(View newView) {
        if (oldView == null)
            oldView = channel.getView();

        for (Address address : newView.getMembers())
            if (!oldView.containsMember(address))
                userJoined(address);

        for (Address address : oldView.getMembers())
            if (!newView.containsMember(address))
                userLeft(address);

        oldView = channel.getView();
    }

    public Address getMe() {
        return channel.getAddress();
    }

    public List<Address> getMembers() {
        return channel.getView().getMembers();
    }

    public void call(@NotNull Address dest, String method, Object arg1, Class type1) {
        try {
            dispatcher.callRemoteMethod(dest, method, new Object[]{arg1}, new Class[]{type1}, new RequestOptions());
        } catch (Exception e) {
            error("Error calling remote method.", e);
        }
    }

    private void userJoined(Address address){
        listeners.forEach(listener -> listener.userJoined(address));
    }

    private void userLeft(Address address){
        listeners.forEach(listener -> listener.userLeft(address));
    }

    private void error(String message, Exception e) {
        listeners.forEach(listener -> listener.error(message, e));
    }
}
