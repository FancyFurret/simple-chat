package main.java.simplechat.core.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sun.istack.internal.Nullable;
import main.java.simplechat.core.exceptions.ChatException;
import main.java.simplechat.core.interfaces.INetworkListener;

import java.util.ArrayList;
import java.util.Map;

/***
 * Handles messages regardless of transfer protocol. Messages are expected to use json in the following form:
 *
 * {
 *     "mode": "(send|recv)",
 *     "action": "[action_name]",
 *     "args": {
 *         // args here for recv commands
 *     }
 * }
 */
public abstract class Network {
    private ArrayList<INetworkListener> listeners;
    private Map<String, FunctionPair> functionMap;

    private Gson gson;

    Network(Map<String, FunctionPair> functionMap) {
        listeners = new ArrayList<>();
        this.functionMap = functionMap;

        gson = new Gson();
    }

    abstract void start(String ip, int port) throws ChatException;

    abstract void stop();

    abstract void sendMessage(String message);

    void recvMessage(String message) {
        try {
            JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
            String mode = jsonMessage.get("mode").getAsString();
            String action = jsonMessage.get("action").getAsString();

            if (!functionMap.containsKey(action)) {
                error("No matching function for specified action.", new Exception());
                return;
            }

            FunctionPair functionPair = functionMap.get(action);
            if (mode.equals("send")) {
                JsonObject args = new JsonObject();
                functionPair.getSend().run(args);
                sendMessage("recv", action, args);
            } else if (mode.equals("recv")) {
                JsonObject args = jsonMessage.getAsJsonObject("args");
                functionPair.getRecv().run(args);
            }
        } catch (JsonParseException e) {
            error("Invalid json received.", e);
        }
    }

    private void sendMessage(String mode, String action, @Nullable JsonObject args) {
        JsonObject json = new JsonObject();
        json.addProperty("mode", mode);
        json.addProperty("action", action);
        if (args != null)
            json.add("args", args);

        sendMessage(gson.toJson(json));
    }

    public void sendActionMessage(String action) {
        sendMessage("send", action, null);
    }

    void error(String message, Exception e) {
        listeners.forEach(listener -> listener.error(message, e));
    }

    public void registerListener(INetworkListener listener) {
        listeners.add(listener);
    }
}
