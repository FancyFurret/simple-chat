package simplechat.core;

import java.util.ArrayList;
import java.util.Collection;

public class UserList {
    private ArrayList<User> users;

    UserList() {
        users = new ArrayList<>();
    }

    void addUser(User user) {
        users.add(user);
    }

    public int getAmount() {
        return users.size();
    }

    public Collection<User> getUsers() {
        return new ArrayList<>(users);
    }
}
