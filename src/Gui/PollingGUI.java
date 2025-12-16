package src.Gui;
import javax.swing.*;

import src.HandleException.VoteGandaException;
import src.Main.Main;
import src.Model.PollingInterface;
import src.Model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PollingGUI extends JFrame {

    private Main mainApp;
    private User currentUser;
    private PollingInterface polling;

    private JRadioButton[] voteButtons;
    private JButton voteButton;
    private JButton showResultButton;
    private JButton backButton;

    private JTextArea hasilArea;

    public PollingGUI(Main app, User user, PollingInterface polling) {
        this.mainApp = app;
        this.currentUser = user;
        this.polling = polling;

        setTitle("Polling - " + currentUser.getUsername());
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initUI();
        initListener();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        //membuat pertanyaan
        JLabel questionLabel = new JLabel(
                "<html><b>" + polling.getQuestion() + "</b></html>",
                SwingConstants.CENTER);
        add(questionLabel, BorderLayout.NORTH);

        //pilihan
        JPanel votePanel = new JPanel();
        votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.Y_AXIS));

        Map<String, Integer> options = polling.getOptions();
        voteButtons = new JRadioButton[options.size()];
        ButtonGroup group = new ButtonGroup();

        int i = 0;
        for (String option : options.keySet()) {
            JRadioButton rb = new JRadioButton(option);
            group.add(rb);
            votePanel.add(rb);
            voteButtons[i++] = rb;
        }

        add(new JScrollPane(votePanel), BorderLayout.CENTER);

        //panel bawah
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        voteButton = new JButton("Submit Vote");
        showResultButton = new JButton("Lihat Hasil");
        backButton = new JButton("Logout)");

        buttonPanel.add(voteButton);
        buttonPanel.add(showResultButton);
        buttonPanel.add(backButton);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        hasilArea = new JTextArea(5, 30);
        hasilArea.setEditable(false);
        hasilArea.setText("Hasil polling akan muncul di sini...");
        bottomPanel.add(new JScrollPane(hasilArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        //cek jika user sudah vote
        if (currentUser.isHasVoted()) {
            voteButton.setEnabled(false);
            hasilArea.setText("Anda sudah melakukan voting.");
        }
    }

    private void initListener() {

        //submit vote
        voteButton.addActionListener(e -> {
            String selected = null;

            for (JRadioButton rb : voteButtons) {
                if (rb.isSelected()) {
                    selected = rb.getText();
                    break;
                }
            }

            if (selected == null) {
                JOptionPane.showMessageDialog(this,
                        "Pilih salah satu opsi!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                polling.vote(selected, currentUser);
                currentUser.setHasVoted(true);
                voteButton.setEnabled(false);

                JOptionPane.showMessageDialog(this,
                        "Vote berhasil!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (VoteGandaException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        //melihat hasil
        showResultButton.addActionListener(e -> {
            hasilArea.setText(polling.tampilkanHasil());
        });

        //tombol back logout
        backButton.addActionListener(e -> {
            mainApp.showLogin(); // balik ke login
            dispose();
        });
    }
}