package src.Model;

public class User {

    private String username;
    private String password;
    private String role; 
    private boolean hasVoted; //menandai apakah user sudah voting atau belum

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.hasVoted = false; //default belum voting
    }

    public User(String username, String password) { //default role VOTER
        this(username, password, "VOTER");
    }

    public String getUsername() { //mengambil username
        return username;
    }

    public String getPassword() { //mengambil password
        return password;
    }

    public String getRole() { //mengambil role
        return role;
    }

    public boolean isHasVoted() { //cek status voting
        return hasVoted; //mengembalikan status apakah user sudah voting atau belum
    }

    public void setHasVoted(boolean hasVoted) { //set status voting
        this.hasVoted = hasVoted; //mengatur status apakah user sudah voting atau belum
    }
}