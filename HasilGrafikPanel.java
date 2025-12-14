import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList; // <--- PERBAIKAN: Import ArrayList

public class HasilGrafikPanel extends JPanel {

    private Polling polling;
    
    public HasilGrafikPanel(Polling polling) {
        this.polling = polling;
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Map<String, Integer> voteData = polling.getOptions(); 
        if (voteData == null || voteData.isEmpty()) return;

        List<String> pilihanList = new ArrayList<>(voteData.keySet());
        List<Integer> voteCounts = new ArrayList<>(voteData.values());

        int totalVotes = voteCounts.stream().mapToInt(Integer::intValue).sum();

        // 1. Hitung Vote Maksimum (untuk skala tinggi grafik)
        int maxVotes = 0;
        for (int count : voteCounts) {
            if (count > maxVotes) {
                maxVotes = count;
            }
        }
        if (maxVotes == 0) maxVotes = 1;

        // 2. Set Dimensi dan Margin
        int width = getWidth();
        int height = getHeight();
        int barWidth = width / (pilihanList.size() * 2);
        int margin = 30;
        int graphHeight = height - margin * 2;
        int xBase = margin;
        int yBase = height - margin;

        g.drawLine(margin, yBase, width - margin, yBase); // Garis horizontal
        
        // 3. Gambar Batang dan Label
        for (int i = 0; i < pilihanList.size(); i++) {
            int voteCount = voteCounts.get(i);
            
            // Hitung tinggi batang
            int barHeight = (int) (((double) voteCount / maxVotes) * graphHeight);
            int x = xBase + i * barWidth * 2;
            int y = yBase - barHeight;

            // Gambar Batang
            g.setColor(new Color(i * 30 % 255, 100, 200));
            g.fillRect(x, y, barWidth, barHeight);

            // Tampilkan Nilai Vote di atas Batang
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(voteCount), x + barWidth / 2 - 5, y - 5);
            
            // Tampilkan Persentase di bawah Batang
            g.setColor(Color.BLACK);
            double persentase = (totalVotes > 0) ? ((double) voteCount / totalVotes) * 100 : 0;
            g.drawString(String.format("%.1f%%", persentase), x, yBase + 30);

            // Tampilkan Label Pilihan di paling bawah
            g.drawString(pilihanList.get(i), x, yBase + 15);
        }
    }
}