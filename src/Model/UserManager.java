package src.Model;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private Map<String, User> users = new HashMap<>(); //menyimpan user berdasarkan username

    // REGISTER VOTER
    public boolean registerUser(String username, String password, String role) {
        if (users.containsKey(username)) return false;
        users.put(username, new User(username, password, role)); //tambah user baru
        return true; //registrasi berhasil
    }
    
    public boolean registerUser(String username, String password) { 
        return registerUser(username, password, "VOTER"); //memanggil method utama dengan role default
    }

    public User loginUser(String username, String password) { //login user
        User user = users.get(username); //ambil user berdasarkan username
        if (user != null && user.getPassword().equals(password)) { //cek password
            return user; //login berhasil
        }
        return null; //login gagal
    }
}