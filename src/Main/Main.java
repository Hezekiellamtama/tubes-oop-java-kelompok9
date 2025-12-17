package src.Main;

import javax.swing.*;
import src.Model.*;
import src.Gui.*;
import src.HandleException.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private List<PollingInterface> pollingList;
    private Map<String, Map<String, String>> userVotes; // username -> (pollTitle -> choice)
    private UserManager userManager;

    public Main() {
        userManager = new UserManager();
        pollingList = new ArrayList<>();
        userVotes = new HashMap<>();
    }

    public void startApp() {
        showLogin();
    }

    public void startPolling(User user) {
        if (user.getRole().equals("ADMIN")) {
            // Admin masuk ke dashboard admin
            new AdminGUI(user, this).setVisible(true);
        } else {
            // Voter masuk ke menu pemilihan polling
            new UserPollingMenu(user, this).setVisible(true);
        }
    }

    public void showLogin() {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI(userManager, this).setVisible(true);
        });
    }

    // Getter dan setter
    public List<PollingInterface> getPollingList() {
        return pollingList;
    }

    public void addPolling(PollingInterface polling) {
        pollingList.add(polling);
    }

    // Method untuk menyimpan hasil voting user
    public void saveUserVote(String username, String pollTitle, String choice) {
        if (!userVotes.containsKey(username)) {
            userVotes.put(username, new HashMap<>());
        }
        userVotes.get(username).put(pollTitle, choice);
    }

    // Method untuk mendapatkan hasil voting semua user
    public Map<String, Map<String, String>> getAllUserVotes() {
        return userVotes;
    }

    // Method untuk mendapatkan voting user tertentu untuk polling tertentu
    public String getUserVote(String username, String pollTitle) {
        if (userVotes.containsKey(username)) {
            return userVotes.get(username).get(pollTitle);
        }
        return null;
    }

    // Method untuk mendapatkan semua voting user tertentu
    public Map<String, String> getUserVotes(String username) {
        return userVotes.getOrDefault(username, new HashMap<>());
    }

    // Method untuk mengecek apakah user sudah vote di polling tertentu
    public boolean hasUserVoted(String username, String pollTitle) {
        return getUserVote(username, pollTitle) != null;
    }

    public static void main(String[] args) {
        new Main().startApp();
    }
}
