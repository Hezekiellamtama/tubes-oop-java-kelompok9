public class User {
    private String username;
    private String password;
    //field status voting
    private boolean hasVoted = false; 

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    // PERBAIKAN Poin 4: Getter dan Setter status voting
    public boolean getHasVoted() {
        return hasVoted;
    }
    
    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}