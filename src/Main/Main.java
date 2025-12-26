package src.Main;

import javax.swing.SwingUtilities;
import src.Model.*;
import src.Gui.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    // Daftar semua polling
    private List<PollingInterface> pollingList;

    // Menyimpan voting user
    // username -> (pollingQuestion -> jawaban)
    private Map<String, Map<String, String>> userVotes;

    private UserManager userManager;

    public Main() {
        userManager = new UserManager();
        pollingList = new ArrayList<>();
        userVotes = new HashMap<>();
    }

    public void startApp() {
        showLogin();
    }

    // Arahkan user sesuai role
    public void startPolling(User user) {
        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            new AdminGUI(user, this).setVisible(true);
        } else {
            new UserPollingMenu(user, this).setVisible(true);
        }
    }

    // Tampilkan halaman login
    public void showLogin() {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI(userManager, this).setVisible(true);
        });
    }


    public List<PollingInterface> getPollingList() {
        return pollingList;
    }

    public void addPolling(PollingInterface polling) {
        pollingList.add(polling);
    }

    // Simpan vote user (1 polling = 1 vote)
    public void saveUserVote(String username, String pollQuestion, String choice) {
        userVotes.putIfAbsent(username, new HashMap<>());
        userVotes.get(username).put(pollQuestion, choice);
    }

    // Cek apakah user sudah vote polling tertentu
    public boolean hasUserVoted(String username, String pollQuestion) {
        return userVotes.containsKey(username)
                && userVotes.get(username).containsKey(pollQuestion);
    }

    // Ambil jawaban user untuk polling tertentu
    public String getUserVote(String username, String pollQuestion) {
        if (!userVotes.containsKey(username)) {
            return null;
        }
        return userVotes.get(username).get(pollQuestion);
    }

    // Ambil semua voting milik user
    public Map<String, String> getUserVotes(String username) {
        return userVotes.getOrDefault(username, new HashMap<>());
    }

    // Ambil semua voting seluruh user (untuk admin)
    public Map<String, Map<String, String>> getAllUserVotes() {
        return userVotes;
    }

    // Reset semua voting (untuk admin)
    public void resetAllVotes() {
        // 1. Reset semua voting user
        userVotes.clear();
        
        // 2. Reset voting di setiap polling
        for (PollingInterface polling : pollingList) {
            polling.resetVotes();  // Panggil method reset di setiap polling
        }
    }

    public static void main(String[] args) {
        new Main().startApp();
    }
}