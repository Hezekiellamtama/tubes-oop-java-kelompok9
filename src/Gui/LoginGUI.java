package src.Gui;
import javax.swing.*;

import src.Main.*;
import src.Model.*;
import src.HandleException.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    //mengelola data user
    private UserManager userManager;
    private Main mainApp; //login ke polling

    private JTextField voterIdField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    //konstruktor loginGUI
    public LoginGUI(UserManager manager, Main app) {
        this.userManager = manager;
        this.mainApp = app;

        setTitle("Login");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initUI();
    }

    //method untuk tampilan login
    private void initUI() {
        JPanel center = new JPanel(new GridLayout(2, 2, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        
        center.add(new JLabel("ID Voter:"));
        voterIdField = new JTextField();
        center.add(voterIdField);

        center.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        center.add(passwordField);

        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        bottom.add(loginBtn);
        bottom.add(registerBtn);

        add(bottom, BorderLayout.SOUTH);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        //aksi tombol login 
        loginBtn.addActionListener(e -> {
            String idVoter = voterIdField.getText().trim(); // nama lokal boleh
            String password = new String(passwordField.getPassword());

            User user = userManager.loginUser(idVoter, password);
            if (user != null) {
                mainApp.startPolling(user);
                dispose();
            } else {
                
                statusLabel.setText("Login gagal, ID Voter tidak terdaftar");
            }
        });

        // aksi register ketika diklik
        registerBtn.addActionListener(e -> {
            RegisterGUI reg = new RegisterGUI(userManager, mainApp);
            reg.setVisible(true);
            dispose();
        });
    }
}
