package src.Model;

public class User {

    private String username;
    private String password;
    private String role; // ADMIN / VOTER
    private boolean hasVoted;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.hasVoted = false;
    }

    // constructor lama (biar tidak error di kode lain)
    public User(String username, String password) {
        this(username, password, "VOTER");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}
