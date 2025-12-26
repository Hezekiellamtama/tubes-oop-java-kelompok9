package src.Gui;
// Halaman utama untuk user memilih dan mengirimkan jawaban polling

import javax.swing.*;
import src.HandleException.VoteGandaException;
import src.Main.Main;
import src.Model.*;
import java.awt.*;
import java.util.Map;

public class PollingGUI extends JFrame {

    private Main mainApp;
    private User currentUser;
    private PollingInterface polling;

    private JRadioButton[] voteButtons;
    private JButton voteButton;
    private JButton backButton;
    private JTextArea hasilArea;

    public PollingGUI(Main app, User user, PollingInterface polling) {
        this.mainApp = app;
        this.currentUser = user;
        this.polling = polling;

        setTitle("Jawab Polling: " + polling.getQuestion());
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initUI();
        initListener();

        setVisible(true);
    }

    private void initUI() {
        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel questionLabel = new JLabel(
            "<html><div style='text-align:center;'><b>PERTANYAAN:</b><br>"
            + polling.getQuestion() + "</div></html>",
            SwingConstants.CENTER
        );
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(questionLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // PANEL PILIHAN VOTING
        JPanel votePanel = new JPanel();
        votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.Y_AXIS));
        votePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        Map<String, Integer> options = polling.getOptions();
        voteButtons = new JRadioButton[options.size()];
        ButtonGroup group = new ButtonGroup();

        int i = 0;
        for (String option : options.keySet()) {
            JRadioButton rb = new JRadioButton(option);
            rb.setFont(new Font("Arial", Font.PLAIN, 12));
            rb.setBackground(Color.WHITE);
            group.add(rb);
            votePanel.add(rb);
            votePanel.add(Box.createRigidArea(new Dimension(0, 5)));
            voteButtons[i++] = rb;
        }

        add(new JScrollPane(votePanel), BorderLayout.CENTER);

        // PANEL BAWAH
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        voteButton = new JButton("KIRIM JAWABAN");
        voteButton.setBackground(Color.GREEN);
        voteButton.setForeground(Color.WHITE);
        voteButton.setFont(new Font("Arial", Font.BOLD, 14));

        backButton = new JButton("Kembali");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);

        buttonPanel.add(voteButton);
        buttonPanel.add(backButton);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        hasilArea = new JTextArea(4, 30);
        hasilArea.setEditable(false);
        hasilArea.setFont(new Font("Arial", Font.PLAIN, 11));

        bottomPanel.add(new JScrollPane(hasilArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // CEK JIKA USER SUDAH VOTING
        if (mainApp.hasUserVoted(currentUser.getUsername(), polling.getQuestion())) {
            String userChoice = mainApp.getUserVote(
                    currentUser.getUsername(),
                    polling.getQuestion()
            );

            hasilArea.setText(
                "Anda sudah menjawab polling ini.\n" +
                "Jawaban Anda: " + userChoice + "\n\n" +
                polling.tampilkanHasil()
            );
        } else {
            hasilArea.setText(
                "Silakan pilih salah satu jawaban di atas,\n" +
                "kemudian klik 'KIRIM JAWABAN'."
            );
        }
    }

    private void initListener() {

        // AKSI TOMBOL VOTE
        voteButton.addActionListener(e -> {
            String selected = null;

            for (JRadioButton rb : voteButtons) {
                if (rb.isSelected()) {
                    selected = rb.getText();
                    break;
                }
            }

            if (selected == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Pilih salah satu jawaban!",
                        "Perhatian",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            try {
                // CEK VOTE GANDA
                if (mainApp.hasUserVoted(
                        currentUser.getUsername(),
                        polling.getQuestion())) {
                    throw new VoteGandaException(null);
                }

                // PROSES VOTING
                polling.vote(selected, currentUser);
                mainApp.saveUserVote(
                        currentUser.getUsername(),
                        polling.getQuestion(),
                        selected
                );

                JOptionPane.showMessageDialog(
                        this,
                        "JAWABAN TERKIRIM\n\n" +
                        "Pertanyaan: " + polling.getQuestion() + "\n" +
                        "Jawaban Anda: " + selected,
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE
                );

                hasilArea.setText(
                    "Terima kasih telah menjawab!\n" +
                    "Jawaban Anda: " + selected + "\n\n" +
                    polling.tampilkanHasil()
                );

            } catch (VoteGandaException ex) {
                // PESAN DIAMBIL LANGSUNG DARI HANDLE EXCEPTION
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        // TOMBOL KEMBALI
        backButton.addActionListener(e -> {
            new UserPollingMenu(currentUser, mainApp).setVisible(true);
            dispose();
        });
    }
}
