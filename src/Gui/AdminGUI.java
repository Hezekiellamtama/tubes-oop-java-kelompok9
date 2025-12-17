package src.Gui;

import javax.swing.*;
import src.Main.Main;
import src.Model.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminGUI extends JFrame {

    private User admin;
    private Main mainApp;

    public AdminGUI(User admin, Main mainApp) {
        this.admin = admin;
        this.mainApp = mainApp;

        setTitle("Admin Panel");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("PANEL ADMINISTRATOR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        
        JLabel infoLabel = new JLabel();
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(Color.GRAY);
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(infoLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // Panel tombol utama (Pertanyaan dan Hasil)
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Tombol 1: Buat Polling (Pertanyaan)
        JButton buatPollingBtn = new JButton("BUAT POLLING BARU");
        styleBigButton(buatPollingBtn, Color.BLUE);
        buatPollingBtn.addActionListener(e -> showBuatPollingForm());

        // Tombol 2: Hasil Voting
        JButton hasilVotingBtn = new JButton("LIHAT HASIL VOTING");
        styleBigButton(hasilVotingBtn, Color.GREEN.darker());
        hasilVotingBtn.addActionListener(e -> showHasilVoting());

        buttonPanel.add(buatPollingBtn);
        buttonPanel.add(hasilVotingBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // Status polling
        JPanel statusPanel = new JPanel();
        updateStatusLabel(statusPanel);
        add(statusPanel, BorderLayout.SOUTH);

        // Tombol logout
        JPanel logoutPanel = new JPanel();
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            mainApp.showLogin();
            dispose();
        });
        logoutPanel.add(logoutBtn);
        add(logoutPanel, BorderLayout.SOUTH);
    }

    private void styleBigButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(300, 60));
    }

    private void updateStatusLabel(JPanel panel) {
        panel.removeAll();
        int totalPolling = mainApp.getPollingList().size();
        
        JLabel statusLabel = new JLabel("Total Polling Aktif: " + totalPolling);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setForeground(totalPolling > 0 ? Color.GREEN : Color.RED);
        
        panel.add(statusLabel);
        panel.revalidate();
        panel.repaint();
    }

    private void showBuatPollingForm() {
        JDialog dialog = new JDialog(this, "Buat Polling Baru", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input pertanyaan
        formPanel.add(new JLabel("MASUKKAN PERTANYAAN:"));
        JTextArea pertanyaanArea = new JTextArea(3, 30);
        pertanyaanArea.setLineWrap(true);
        pertanyaanArea.setWrapStyleWord(true);
        pertanyaanArea.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(new JScrollPane(pertanyaanArea));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Input pilihan
        formPanel.add(new JLabel("PILIHAN JAWABAN (minimal 2):"));
        
        DefaultListModel<String> pilihanModel = new DefaultListModel<>();
        JList<String> pilihanList = new JList<>(pilihanModel);
        pilihanList.setVisibleRowCount(5);
        
        JScrollPane listScrollPane = new JScrollPane(pilihanList);
        listScrollPane.setPreferredSize(new Dimension(400, 120));
        formPanel.add(listScrollPane);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel untuk menambah pilihan
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        JTextField pilihanField = new JTextField();
        JButton tambahBtn = new JButton("Tambah");
        
        inputPanel.add(pilihanField, BorderLayout.CENTER);
        inputPanel.add(tambahBtn, BorderLayout.EAST);
        inputPanel.setMaximumSize(new Dimension(400, 30));
        
        formPanel.add(new JLabel("Tambah pilihan:"));
        formPanel.add(inputPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Tombol hapus pilihan
        JButton hapusBtn = new JButton("Hapus Pilihan Terpilih");
        hapusBtn.setMaximumSize(new Dimension(400, 30));
        formPanel.add(hapusBtn);

        dialog.add(formPanel, BorderLayout.CENTER);

        // Panel tombol aksi
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton simpanBtn = new JButton("SIMPAN POLLING");
        simpanBtn.setBackground(Color.BLUE);
        simpanBtn.setForeground(Color.WHITE);
        
        JButton batalBtn = new JButton("BATAL");
        batalBtn.setBackground(Color.RED);
        batalBtn.setForeground(Color.WHITE);
        
        actionPanel.add(simpanBtn);
        actionPanel.add(batalBtn);
        dialog.add(actionPanel, BorderLayout.SOUTH);

        // Action listeners
        tambahBtn.addActionListener(e -> {
            String pilihan = pilihanField.getText().trim();
            if (!pilihan.isEmpty()) {
                pilihanModel.addElement(pilihan);
                pilihanField.setText("");
                pilihanField.requestFocus();
            }
        });

        hapusBtn.addActionListener(e -> {
            int selectedIndex = pilihanList.getSelectedIndex();
            if (selectedIndex != -1) {
                pilihanModel.remove(selectedIndex);
            }
        });

        simpanBtn.addActionListener(e -> {
            String pertanyaan = pertanyaanArea.getText().trim();
            
            if (pertanyaan.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Pertanyaan harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (pilihanModel.size() < 2) {
                JOptionPane.showMessageDialog(dialog, "Minimal 2 pilihan jawaban!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Konversi ke List
            List<String> pilihanListData = new ArrayList<>();
            for (int i = 0; i < pilihanModel.size(); i++) {
                pilihanListData.add(pilihanModel.get(i));
            }
            
            // Buat polling baru
            PollingInterface pollingBaru = new PollingAnonim(pertanyaan, pilihanListData);
            mainApp.addPolling(pollingBaru);
            
            // Update status
            updateStatusLabel((JPanel) this.getContentPane().getComponent(2));
            
            JOptionPane.showMessageDialog(dialog, 
                "POLLING BERHASIL DIBUAT!\n\n" +
                "Pertanyaan: " + pertanyaan + "\n" +
                "Jumlah pilihan: " + pilihanListData.size() + "\n" +
                "Sekarang voter dapat menjawab polling ini.",
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            
            dialog.dispose();
        });

        batalBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void showHasilVoting() {
        JDialog dialog = new JDialog(this, "Hasil Voting", true);
        dialog.setSize(700, 550);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Tabbed pane untuk 2 jenis hasil
        JTabbedPane tabbedPane = new JTabbedPane();

        // TAB 1: Hasil per Polling
        JPanel pollingResultsPanel = new JPanel(new BorderLayout());
        pollingResultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea pollingResultsArea = new JTextArea();
        pollingResultsArea.setEditable(false);
        pollingResultsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        List<PollingInterface> pollingList = mainApp.getPollingList();
        
        StringBuilder sb1 = new StringBuilder();
        sb1.append("                                   \n");
        sb1.append("HASIL POLLING\n");
        sb1.append("                                    \n\n");
        
        if (pollingList.isEmpty()) {
            sb1.append("Belum ada polling yang dibuat.\n");
        } else {
            for (int i = 0; i < pollingList.size(); i++) {
                PollingInterface polling = pollingList.get(i);
                sb1.append("POLLING ").append(i + 1).append("\n");
                sb1.append("Pertanyaan: ").append(polling.getQuestion()).append("\n");
                sb1.append(polling.tampilkanHasil()).append("\n");
                sb1.append("                                    \n\n");
            }
        }
        
        pollingResultsArea.setText(sb1.toString());
        pollingResultsPanel.add(new JScrollPane(pollingResultsArea), BorderLayout.CENTER);
        tabbedPane.addTab(" Hasil Polling", pollingResultsPanel);

        // TAB 2: Hasil per User
        JPanel userResultsPanel = new JPanel(new BorderLayout());
        userResultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea userResultsArea = new JTextArea();
        userResultsArea.setEditable(false);
        userResultsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        Map<String, Map<String, String>> allVotes = mainApp.getAllUserVotes();
        
        StringBuilder sb2 = new StringBuilder();
        sb2.append("     \n");
        sb2.append("HASIL VOTING PER USER\n");
        sb2.append(" \n\n");
        
        if (allVotes.isEmpty()) {
            sb2.append("Belum ada user yang melakukan voting.\n");
        } else {
            sb2.append("Total user yang sudah vote: ").append(allVotes.size()).append("\n\n");
            
            int userCount = 1;
            for (Map.Entry<String, Map<String, String>> userEntry : allVotes.entrySet()) {
                String username = userEntry.getKey();
                Map<String, String> userVotes = userEntry.getValue();
                
                sb2.append("USER ").append(userCount).append(": ").append(username).append("\n");
                sb2.append("Total menjawab: ").append(userVotes.size()).append(" polling\n");
                
                for (Map.Entry<String, String> voteEntry : userVotes.entrySet()) {
                    sb2.append("  • ").append(voteEntry.getKey())
                       .append(" → ").append(voteEntry.getValue()).append("\n");
                }
                sb2.append(" \n\n");
                userCount++;
            }
        }
        
        userResultsArea.setText(sb2.toString());
        userResultsPanel.add(new JScrollPane(userResultsArea), BorderLayout.CENTER);
        tabbedPane.addTab(" Hasil per User", userResultsPanel);

        dialog.add(tabbedPane, BorderLayout.CENTER);

        // Tombol tutup
        JPanel closePanel = new JPanel();
        JButton closeBtn = new JButton("Tutup");
        closeBtn.addActionListener(e -> dialog.dispose());
        closePanel.add(closeBtn);
        dialog.add(closePanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}