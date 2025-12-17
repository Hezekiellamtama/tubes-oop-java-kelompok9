package src.Gui;

import javax.swing.*;
import src.Main.*;
import src.Model.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    private UserManager userManager;
    private Main mainApp;

    private JTextField idField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JLabel statusLabel;

    public LoginGUI(UserManager manager, Main app) {
        this.userManager = manager;
        this.mainApp = app;

        setTitle("Login Sistem Polling");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initUI();
    }

    private void initUI() {
        JPanel center = new JPanel(new GridLayout(4, 2, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        center.add(new JLabel("ID User:"));
        idField = new JTextField();
        center.add(idField);

        center.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        center.add(passwordField);

        center.add(new JLabel("Role:"));
        roleCombo = new JComboBox<>(new String[]{"Voter", "Admin"});
        center.add(roleCombo);

        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        bottom.add(loginBtn);
        bottom.add(registerBtn);
        add(bottom, BorderLayout.SOUTH);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        loginBtn.addActionListener(e -> handleLogin());

        registerBtn.addActionListener(e -> {
            new RegisterGUI(userManager, mainApp).setVisible(true);
            dispose();
        });
    }

    private void handleLogin() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleCombo.getSelectedItem(); 

        //LOGIN ADMIN
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

        //LOGIN VOTER
        User voter = userManager.loginUser(id, password);
        if (voter != null) {
            mainApp.startPolling(voter);
            dispose();
        } else {
            statusLabel.setText("Voter belum terdaftar!");
        }
    }
}
