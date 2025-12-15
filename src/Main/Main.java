package Main;
import javax.swing.*;


import Gui.*;
import Model.*;

import java.util.ArrayList;
import java.util.List;
public class Main {

    private PollingInterface pollingKita; 
    private UserManager userManager;

    public Main() {
        this.userManager = new UserManager();
        
        //data Polling
        String pertanyaanPolling = "Bahasa Pemrograman Favoritmu?";
        List<String> pilihanPolling = new ArrayList<>();
        pilihanPolling.add("Java");
        pilihanPolling.add("C++");
        pilihanPolling.add("Python");
        
        // PollingAnonim dibuat sekali
        this.pollingKita = new PollingAnonim(pertanyaanPolling, pilihanPolling);
    }
    
    //membuka LoginGUI 
    public void startApp() {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI(this.userManager, this).setVisible(true);
        });
    }

    //method dipanggil setelah login sukses 
    public void startPolling(User userYangLogin) {
        Main app = this; 
        SwingUtilities.invokeLater(() -> {
            PollingGUI frame = new PollingGUI(app, userYangLogin, pollingKita);
            frame.setVisible(true);
        });
    }

    // method untuk logout 
    public void logout(JFrame currentFrame) {
        SwingUtilities.invokeLater(() -> {
            currentFrame.dispose(); // tutup polling GUI
            startApp();             // kembali ke login
        });
    }
    public UserManager getUserManager (){
        return userManager;
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.startApp();
    }
}
