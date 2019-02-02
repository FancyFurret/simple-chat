package main.java.simplechat.core.model;

import java.util.ArrayList;
import java.util.Collection;

public class UserList {
    private ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public int getAmount() {
        return users.size();
    }

    public Collection<User> getUsers() {
        return new ArrayList<>(users);
    }
}
