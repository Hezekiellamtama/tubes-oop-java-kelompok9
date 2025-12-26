package src.Gui;

import javax.swing.*;
import src.Main.*;
import src.Model.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    //mengelola data user (registrasi & login voter)
    private UserManager userManager; //mengelola data voter,login,registrasi 
    private Main mainApp; //memulai aplikasi utama polling setelah login

    private JTextField idField; //input ID user
    private JPasswordField passwordField; //input password user
    private JComboBox<String> roleCombo; //pilih role: Voter/Admin
    private JLabel statusLabel; //menampilkan status login

    //konstruktur loginGUI
    public LoginGUI(UserManager manager, Main app) {
        this.userManager = manager;
        this.mainApp = app;

        setTitle("Login Sistem Polling");
        setSize(420, 260); //jendela tampilan 420 ke samping
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));  //jarak komponen

        initUI(); //inisialisasi tampilan UI
    }

    private void initUI() { //panel input login
        JPanel center = new JPanel(new GridLayout(4, 2, 10, 10)); //grid 4 baris 2 kolom
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        center.add(new JLabel("ID User:")); //input ID user
        idField = new JTextField();
        center.add(idField);

        center.add(new JLabel("Password:")); //input password user
        passwordField = new JPasswordField();
        center.add(passwordField);

        center.add(new JLabel("Role:")); //pilih role user
        roleCombo = new JComboBox<>(new String[]{"Voter", "Admin"});
        center.add(roleCombo);

        add(center, BorderLayout.CENTER);

        //panel tombol login & register
        JPanel bottom = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        bottom.add(loginBtn);
        bottom.add(registerBtn);
        add(bottom, BorderLayout.SOUTH);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        loginBtn.addActionListener(e -> handleLogin());

        // tombol register → pindah ke RegisterGUI
        registerBtn.addActionListener(e -> {
            new RegisterGUI(userManager, mainApp).setVisible(true); //menampilkan halaman registrasi
            dispose(); //tutup halaman login
        });
    }

    private void handleLogin() { //proses login user
        String id = idField.getText().trim(); //menghapus spasi di awal & akhir
        String password = new String(passwordField.getPassword());
        String role = (String) roleCombo.getSelectedItem(); 

        //login admin
        if (role.equals("Admin")) {
            if (id.equals("ADM01") && password.equals("admin123")) {

                User admin = new User("ADM01", "admin123", "Admin");
                dispose(); // tutup login

                new AdminGUI(admin, mainApp).setVisible(true);

            } else {
                statusLabel.setText("Login admin gagal!");
            }
            return;
        }

        //login voter
        User voter = userManager.loginUser(id, password);
        if (voter != null) {
            mainApp.startPolling(voter);
            dispose();
        } else {
            statusLabel.setText("Voter belum terdaftar!");
        }
    }
}
