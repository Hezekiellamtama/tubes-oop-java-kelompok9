package src.Gui;

import javax.swing.*;
import src.Main.*;
import src.Model.*;
import java.awt.*;

public class RegisterGUI extends JFrame {

    private UserManager userManager;
    private Main mainApp;

    private JTextField namaField; //nama voter
    private JTextField idVoterField; //ID voter
    private JTextField umurField; //umur voter
    private JPasswordField passwordField; //password voter
    private JRadioButton lakiBtn, perempuanBtn; //jenis kelamin

    //konstruktur RegisterGUI
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

    //form registrasi voter
    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        //inisialisasi komponen form
        namaField = new JTextField();
        idVoterField = new JTextField();
        umurField = new JTextField();
        passwordField = new JPasswordField();

        //radio button jenis kelamin
        lakiBtn = new JRadioButton("Laki-laki");
        perempuanBtn = new JRadioButton("Perempuan");

        //radio button group agar hanya satu yang bisa dipilih
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(lakiBtn);
        genderGroup.add(perempuanBtn);

        //tambah komponen ke panel form
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

        //panel tombol register & back
        JPanel buttonPanel = new JPanel();
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back to Login");

        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        //event tombol register
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

            boolean success = userManager.registerUser(idVoter, password, "VOTER"); //proses registrasi voter

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

        //event tombol back ke login
        backBtn.addActionListener(e -> {
            new LoginGUI(userManager, mainApp).setVisible(true);
            dispose(); //tutup RegisterGUI
        });
    }
}
