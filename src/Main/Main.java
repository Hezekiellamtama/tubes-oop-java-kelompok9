package src.Main;
import javax.swing.*;

import src.Model.*;
import src.Gui.*;
import src.HandleException.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private PollingInterface pollingKita;
    private UserManager userManager;

    public Main() {
        userManager = new UserManager();

        //membuat data polling
        String pertanyaan = "Bahasa Pemrogman Paling Favorite";
        List<String> pilihan = new ArrayList<>();
        pilihan.add("Java");
        pilihan.add("C++");
        pilihan.add("Python");

        pollingKita = new PollingAnonim(pertanyaan, pilihan);
    }

    //menampilkan login
    public void startApp() {
        showLogin();
    }

    //dipanggil setelah login sukses
    public void startPolling(User user) {
        SwingUtilities.invokeLater(() -> {
            new PollingGUI(this, user, pollingKita).setVisible(true);
        });
    }

    //untuk tombol back
    public void showLogin() {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI(userManager, this).setVisible(true);
        });
    }

    public static void main(String[] args) {
        new Main().startApp();
    }
}