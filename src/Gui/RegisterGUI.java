package src.Gui;

import javax.swing.*;
import src.Main.*;
import src.Model.*;
import java.awt.*;

public class RegisterGUI extends JFrame {

    private UserManager userManager;
    private Main mainApp;

    private JTextField namaField;
    private JTextField idVoterField;
    private JTextField umurField;
    private JPasswordField passwordField;
    private JRadioButton lakiBtn, perempuanBtn;

    public RegisterGUI(UserManager manager, Main app) {
        this.userManager = manager;
        this.mainApp = app;

        setTitle("Register Voter");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        namaField = new JTextField();
        idVoterField = new JTextField();
        umurField = new JTextField();
        passwordField = new JPasswordField();

        lakiBtn = new JRadioButton("Laki-laki");
        perempuanBtn = new JRadioButton("Perempuan");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(lakiBtn);
        genderGroup.add(perempuanBtn);

        formPanel.add(new JLabel("Nama:"));
        formPanel.add(namaField);

        formPanel.add(new JLabel("ID Voter:"));
        formPanel.add(idVoterField);

        formPanel.add(new JLabel("Umur:"));
        formPanel.add(umurField);

        formPanel.add(new JLabel("Jenis Kelamin:"));
        JPanel genderPanel = new JPanel();
        genderPanel.add(lakiBtn);
        genderPanel.add(perempuanBtn);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back to Login");

        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // REGISTER VOTER
        registerBtn.addActionListener(e -> {
            String idVoter = idVoterField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (idVoter.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "ID Voter dan Password wajib diisi!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            boolean success = userManager.registerUser(idVoter, password, "VOTER");

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Registrasi berhasil, Silakan login.",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE
                );
                new LoginGUI(userManager, mainApp).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "ID Voter sudah terdaftar",
                        "Gagal",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        backBtn.addActionListener(e -> {
            new LoginGUI(userManager, mainApp).setVisible(true);
            dispose();
        });
    }
}
