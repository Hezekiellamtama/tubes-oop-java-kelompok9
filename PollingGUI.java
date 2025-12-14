import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PollingGUI extends JFrame {

    private Main mainApp;
    private User currentUser;
    // PERBAIKAN: Gunakan PollingInterface agar bisa mengakses tampilkanHasil()
    private PollingInterface polling; 
    
    // Komponen GUI
    private JRadioButton[] voteButtons;
    private JButton voteButton;
    private JButton showResultButton;
    private JTextArea hasilArea;
    private JPanel votePanel;
    private JScrollPane scrollPane;
    private JLabel questionLabel;
    
    // Konstruktor
    public PollingGUI(Main app, User user, PollingInterface p) {
        this.mainApp = app;
        this.currentUser = user;
        this.polling = p; 
        
        setTitle("Sistem Polling - Selamat Datang, " + currentUser.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLayout(new BorderLayout(10, 10));
        
        setupComponents();
        addListeners();
        
        setLocationRelativeTo(null);
    }
    
    private void setupComponents() {
        // --- Header dan Pertanyaan ---
        questionLabel = new JLabel("<html><b>" + polling.getQuestion() + "</b></html>", SwingConstants.CENTER);
        add(questionLabel, BorderLayout.NORTH);
        
        // --- Panel Pilihan Jawaban (Tengah) ---
        votePanel = new JPanel();
        votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.Y_AXIS));
        
        Map<String, Integer> options = polling.getOptions();
        voteButtons = new JRadioButton[options.size()];
        ButtonGroup group = new ButtonGroup();
        
        int i = 0;
        for (String option : options.keySet()) {
            JRadioButton button = new JRadioButton(option);
            group.add(button);
            votePanel.add(button);
            voteButtons[i] = button;
            i++;
        }
        
        scrollPane = new JScrollPane(votePanel);
        add(scrollPane, BorderLayout.CENTER);
        
        // --- Panel Bawah (Tombol dan Hasil) ---
        JPanel southPanel = new JPanel(new BorderLayout());
        
        // Tombol
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        voteButton = new JButton("Submit Pilihan");
        showResultButton = new JButton("Lihat Hasil Polling");
        
        buttonRow.add(voteButton);
        buttonRow.add(showResultButton);
        southPanel.add(buttonRow, BorderLayout.NORTH);
        
        // Area Hasil
        hasilArea = new JTextArea("Hasil Polling Akan Ditampilkan di Sini.");
        hasilArea.setEditable(false);
        hasilArea.setRows(5);
        southPanel.add(new JScrollPane(hasilArea), BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        // Listener Tombol VOTE
        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = null;
                for (JRadioButton button : voteButtons) {
                    if (button.isSelected()) {
                        selectedOption = button.getText();
                        break;
                    }
                }
                
                if (selectedOption != null) {
                    try {
                        // Memanggil method vote() dari PollingInterface
                        polling.vote(selectedOption, currentUser);
                        JOptionPane.showMessageDialog(PollingGUI.this, "Terima kasih! Suara Anda berhasil dicatat.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        voteButton.setEnabled(false); // Disable setelah vote pertama
                        currentUser.setHasVoted(true);
                    } catch (VoteGandaException ex) {
                        JOptionPane.showMessageDialog(PollingGUI.this, ex.getMessage(), "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PollingGUI.this, "Harap pilih salah satu opsi sebelum submit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Listener Tombol LIHAT HASIL
        showResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // PERBAIKAN: Karena 'polling' sekarang bertipe PollingInterface, method ini bisa diakses
                String hasilText = polling.tampilkanHasil();
                hasilArea.setText(hasilText);
            }
        });
    }

    // Method main tidak diperlukan di sini
}