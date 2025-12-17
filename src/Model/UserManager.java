package src.Model;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private Map<String, User> users = new HashMap<>();

    // REGISTER VOTER
    public boolean registerUser(String username, String password, String role) {
        if (users.containsKey(username)) return false;
        users.put(username, new User(username, password, role));
        return true;
    }

    // supaya kode lama tetap jalan
    public boolean registerUser(String username, String password) {
        return registerUser(username, password, "VOTER");
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
