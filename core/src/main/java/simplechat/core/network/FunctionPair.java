package main.java.simplechat.core.network;

import com.google.gson.JsonObject;
import main.java.simplechat.core.utils.Action;

public class FunctionPair {

    private Action<JsonObject> send;
    private Action<JsonObject> recv;

    public FunctionPair(Action<JsonObject> send, Action<JsonObject> recv) {
        this.send = send;
        this.recv = recv;
    }

    public Action<JsonObject> getSend() {
        return send;
    }

    public Action<JsonObject> getRecv() {
        return recv;
    }
}
