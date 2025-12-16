package src.Model;

public class User {
    private String username;
    private String password;
    private boolean hasVoted;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.hasVoted = false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}