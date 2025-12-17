package src.Gui;

import javax.swing.*;
import src.Main.Main;
import src.Model.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class UserPollingMenu extends JFrame {

    private User user;
    private Main mainApp;

    public UserPollingMenu(User user, Main mainApp) {
        this.user = user;
        this.mainApp = mainApp;

        setTitle("Menu Polling - " + user.getUsername());
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Selamat Datang " + user.getUsername(),
                SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Panel daftar polling
        JPanel pollingPanel = new JPanel();
        pollingPanel.setLayout(new BoxLayout(pollingPanel, BoxLayout.Y_AXIS));
        pollingPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        List<PollingInterface> pollingList = mainApp.getPollingList();

        for (PollingInterface polling : pollingList) {

            JPanel pollItemPanel = new JPanel(new BorderLayout(10, 0));
            pollItemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            pollItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

            JLabel pollTitle = new JLabel("<html><b>" + polling.getQuestion() + "</b></html>");

            boolean sudahVote = mainApp.hasUserVoted(
                    user.getUsername(),
                    polling.getQuestion()
            );

            String statusText = sudahVote ? "Sudah Dijawab" : "○ Belum Dijawab";
            JLabel statusLabel = new JLabel(statusText);
            statusLabel.setForeground(sudahVote ? new Color(0, 150, 0) : Color.RED);

            JButton actionButton;
            if (sudahVote) {
                actionButton = new JButton("Lihat Hasil");
                actionButton.addActionListener(e -> showPollingResults(polling));
            } else {
                actionButton = new JButton("Jawab Polling");
                actionButton.addActionListener(e -> openPolling(polling));
            }

            // Panel kiri (judul + status)
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.add(pollTitle);
            leftPanel.add(Box.createVerticalStrut(4));
            leftPanel.add(statusLabel);

            // Panel kanan (tombol di tengah)
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            rightPanel.setPreferredSize(new Dimension(130, 50));
            rightPanel.add(actionButton);

            pollItemPanel.add(leftPanel, BorderLayout.CENTER);
            pollItemPanel.add(rightPanel, BorderLayout.EAST);

            pollingPanel.add(pollItemPanel);
            pollingPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        JScrollPane scrollPane = new JScrollPane(pollingPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Panel tombol bawah
        JPanel buttonPanel = new JPanel();
        JButton lihatSemuaHasilBtn = new JButton("Lihat Semua Hasil Saya");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(lihatSemuaHasilBtn);
        buttonPanel.add(logoutBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action
        lihatSemuaHasilBtn.addActionListener(e -> showAllMyResults());
        logoutBtn.addActionListener(e -> {
            mainApp.showLogin();
            dispose();
        });
    }

    private void openPolling(PollingInterface polling) {
        new PollingGUI(mainApp, user, polling).setVisible(true);
        dispose();
    }

    private void showPollingResults(PollingInterface polling) {
        JTextArea textArea = new JTextArea(polling.tampilkanHasil());
        textArea.setEditable(false);

        String userChoice = mainApp.getUserVote(
                user.getUsername(),
                polling.getQuestion()
        );

        if (userChoice != null) {
            textArea.append("\n\nJawaban Anda: " + userChoice);
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Hasil Polling",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showAllMyResults() {
        Map<String, String> myVotes = mainApp.getUserVotes(user.getUsername());

        if (myVotes.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Anda belum menjawab polling apapun.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("HASIL VOTING ANDA\n\n");

        for (Map.Entry<String, String> entry : myVotes.entrySet()) {
            sb.append("Polling : ").append(entry.getKey()).append("\n");
            sb.append("Jawaban: ").append(entry.getValue()).append("\n\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Semua Jawaban Anda",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
