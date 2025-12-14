import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PollingGUI extends JFrame {

    private Main app;
    private User user;
    private PollingInterface polling;

    private JButton voteBtn;
    private JButton hasilBtn;
    private JButton backBtn;
    private JRadioButton[] radios;
    private JTextArea hasilArea;

    public PollingGUI(Main app, User user, PollingInterface polling) {
        this.app = app;
        this.user = user;
        this.polling = polling;

        setTitle("Polling - " + user.getUsername());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //membuat header
        JLabel questionLabel = new JLabel(polling.getQuestion(), SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        //pilihan
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        ButtonGroup group = new ButtonGroup();
        Map<String, Integer> options = polling.getOptions();

        radios = new JRadioButton[options.size()];
        int i = 0;
        for (String option : options.keySet()) {
            radios[i] = new JRadioButton(option);
            group.add(radios[i]);
            centerPanel.add(radios[i]);
            i++;
        }

        add(centerPanel, BorderLayout.CENTER);

        // tampilan panel bawah
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        voteBtn = new JButton("Vote");
        hasilBtn = new JButton("Lihat Hasil");
        backBtn = new JButton("Back");

        buttonPanel.add(voteBtn);
        buttonPanel.add(hasilBtn);
        buttonPanel.add(backBtn);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        hasilArea = new JTextArea(5, 30);
        hasilArea.setEditable(false);
        bottomPanel.add(new JScrollPane(hasilArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        //menonaktifkan tombol vote jika sudah voting
        if (user.getHasVoted()) {
            voteBtn.setEnabled(false);
        }

        //aksi tombol vote
        voteBtn.addActionListener(e -> {
            String selectedOption = null;

            for (JRadioButton r : radios) {
                if (r.isSelected()) {
                    selectedOption = r.getText();
                    break;
                }
            }

            if (selectedOption == null) {
                JOptionPane.showMessageDialog(this,
                        "Silakan pilih salah satu opsi!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                polling.vote(selectedOption, user);
                app.getUserManager().markUserVoted(user);
                voteBtn.setEnabled(false);

                JOptionPane.showMessageDialog(this,
                        "Terima kasih! Vote Anda berhasil.",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        hasilBtn.addActionListener(e ->
                hasilArea.setText(polling.tampilkanHasil())
        );

        backBtn.addActionListener(e -> app.logout(this));

        setLocationRelativeTo(null);
    }
}
