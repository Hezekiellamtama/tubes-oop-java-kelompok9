package src.Gui;

import javax.swing.*;
import src.HandleException.VoteGandaException;
import src.Main.*;
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
        // Header dengan pertanyaan dari admin
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel questionLabel = new JLabel(
            "<html><div style='text-align:center;'><b>PERTANYAAN:</b><br>" + 
            polling.getQuestion() + "</div></html>",
            SwingConstants.CENTER
        );
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(questionLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);

        // Panel pilihan jawaban
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

        // Panel bawah dengan tombol submit
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Panel tombol
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

        // Area informasi
        hasilArea = new JTextArea(4, 30);
        hasilArea.setEditable(false);
        hasilArea.setFont(new Font("Arial", Font.PLAIN, 11));
        hasilArea.setText("Silakan pilih salah satu jawaban di atas,\n" +
                         "kemudian klik 'KIRIM JAWABAN'.");
        
        bottomPanel.add(new JScrollPane(hasilArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // CEK: Gunakan Main untuk tracking, bukan User
        if (mainApp.hasUserVoted(currentUser.getUsername(), polling.getQuestion())) {
            voteButton.setEnabled(false);
            voteButton.setText("Sudah Dijawab");
            voteButton.setBackground(Color.GRAY);
            
            String userChoice = mainApp.getUserVote(currentUser.getUsername(), polling.getQuestion());
            hasilArea.setText("Anda sudah menjawab polling ini.\n" +
                            "Jawaban Anda: " + userChoice + "\n\n" +
                            polling.tampilkanHasil());
        }
    }

    private void initListener() {
        // Submit vote
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
                        "Pilih salah satu jawaban!",
                        "Perhatian",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Cek dulu apakah sudah vote (via Main)
                if (mainApp.hasUserVoted(currentUser.getUsername(), polling.getQuestion())) {
                    throw new VoteGandaException("Anda sudah menjawab polling ini!");
                }
                
                // Vote via polling
                polling.vote(selected, currentUser);
                
                // Simpan voting dan tracking di Main
                mainApp.saveUserVote(currentUser.getUsername(), polling.getQuestion(), selected);
                
                // Update UI
                voteButton.setEnabled(false);
                voteButton.setText("Sudah Dijawab");
                voteButton.setBackground(Color.GRAY);

                JOptionPane.showMessageDialog(this,
                        "JAWABAN TERKIRIM\n\n" +
                        "Pertanyaan: " + polling.getQuestion() + "\n" +
                        "Jawaban Anda: " + selected,
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Update hasil area
                hasilArea.setText("Terima kasih telah menjawab!\n" +
                                "Jawaban Anda: " + selected + "\n\n" +
                                polling.tampilkanHasil());

            } catch (VoteGandaException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Tombol kembali
        backButton.addActionListener(e -> {
            new UserPollingMenu(currentUser, mainApp).setVisible(true);
            dispose();
        });
    }
}