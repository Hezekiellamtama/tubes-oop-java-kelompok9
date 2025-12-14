import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterGUI extends JFrame {

    // Dependensi (Class yang Dibutuhkan)
    private UserManager userManager;
    private Main mainApp; // Digunakan untuk memanggil startPolling setelah login sukses
    
    // Komponen Swing
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;
    
    private boolean isLoginMode = true; // State awal: Login

    // Konstruktor
    public LoginRegisterGUI(UserManager manager, Main app) {
        this.userManager = manager;
        this.mainApp = app;
        
        setTitle("Login atau Registrasi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLayout(new BorderLayout(10, 10)); // Layout utama
        
        setupComponents();
        addListeners();
        
        setLocationRelativeTo(null); // Posisikan di tengah layar
    }
    
    private void setupComponents() {
        // Panel Utama untuk Input (Grid Layout)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        statusLabel = new JLabel("Masukkan kredensial:");
        
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(statusLabel);
        
        add(inputPanel, BorderLayout.CENTER);
        
        // Panel untuk Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        loginButton = new JButton("Login");
        registerButton = new JButton("Switch ke Registrasi");
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        updateButtonText(true);
    }
    
    private void updateButtonText(boolean loginMode) {
        if (loginMode) {
            loginButton.setText("Login");
            registerButton.setText("Switch ke Registrasi");
            setTitle("Login");
        } else {
            loginButton.setText("Daftar Sekarang");
            registerButton.setText("Switch ke Login");
            setTitle("Registrasi User Baru");
        }
        this.isLoginMode = loginMode;
    }

    private void addListeners() {
        
        // Listener untuk tombol Login/Daftar
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    statusLabel.setText("Username/Password tidak boleh kosong!");
                    return;
                }
                
                if (isLoginMode) {
                    // Logika Login
                    User user = userManager.loginUser(username, password);
                    if (user != null) {
                        JOptionPane.showMessageDialog(LoginRegisterGUI.this, "Login Berhasil! Selamat datang, " + user.getUsername(), "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Tutup jendela login
                        mainApp.startPolling(user); // Panggil method di Main
                    } else {
                        statusLabel.setText("Login Gagal. Cek kredensial Anda.");
                    }
                } else {
                    // Logika Registrasi
                    boolean success = userManager.registerUser(username, password);
                    if (success) {
                        JOptionPane.showMessageDialog(LoginRegisterGUI.this, "Registrasi Berhasil! Silakan Login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        updateButtonText(true); // Kembali ke mode Login
                        usernameField.setText("");
                        passwordField.setText("");
                        statusLabel.setText("Registrasi Berhasil. Silakan Login:");
                    } else {
                        statusLabel.setText("Registrasi Gagal. Username sudah digunakan.");
                    }
                }
            }
        });
        
        // Listener untuk tombol Switch
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Balik state
                updateButtonText(!isLoginMode);
                statusLabel.setText(isLoginMode ? "Masukkan kredensial Anda:" : "Buat akun baru:");
                usernameField.setText("");
                passwordField.setText("");
            }
        });
    }

    // Catatan: Tidak perlu method main di sini, karena sudah ada di Main.java
}