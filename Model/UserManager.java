package Model;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
      
    }

    public boolean registerUser(String username, String password) {
        if (findUser(username) == null) {
            User newUser = new User(username, password);
            users.add(newUser);
            return true;
        }
        return false;
    }

    public User loginUser(String username, String password) {
        User user = findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Method untuk menandai user sudah vote
    public void markUserVoted(User user) {
        user.setHasVoted(true); 
    }
}