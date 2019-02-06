package main.java.simplechat.core.model;

import java.io.Serializable;

public class Message implements Serializable {
    private User sender;
    private String contents;

    public Message(User sender, String contents) {
        this.sender = sender;
        this.contents = contents;
    }

    public User getSender() {
        return sender;
    }

    public String getContents() {
        return contents;
    }
}
