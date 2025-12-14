import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
    // PERBAIKAN: Deklarasi PollingInterface
    private PollingInterface pollingKita; 
    private UserManager userManager;

    public Main() {
        this.userManager = new UserManager();
        
        // Data Polling
        String pertanyaanPolling = "Siapa yang pantas menjadi Ketua Kelas?";
        List<String> pilihanPolling = new ArrayList<>();
        pilihanPolling.add("Budi Santoso");
        pilihanPolling.add("Cinta Laura");
        pilihanPolling.add("Andi Wijaya");
        
        // PERBAIKAN: Constructor PollingAnonim sudah menerima argumen
        this.pollingKita = new PollingAnonim(pertanyaanPolling, pilihanPolling);
    }
    
    public void startApp() {
        // Tampilkan jendela Login/Register
        SwingUtilities.invokeLater(() -> {
            LoginRegisterGUI loginFrame = new LoginRegisterGUI(this.userManager, this);
            loginFrame.setVisible(true);
        });
    }

    // Method yang dipanggil setelah sukses login
    public void startPolling(User userYangLogin) {
        SwingUtilities.invokeLater(() -> {
            // PERBAIKAN: Gunakan 'this' (Main) sebagai argumen pertama jika PollingGUI membutuhkan Main
            // PollingGUI frame = new PollingGUI(this.pollingKita, userYangLogin, userManager); 
            // ASUMSI PollingGUI butuh (Main app, User user, PollingInterface p)
            PollingGUI frame = new PollingGUI(this, userYangLogin, this.pollingKita); 
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.startApp();
    }
}