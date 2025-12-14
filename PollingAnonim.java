import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class PollingAnonim extends Polling implements PollingInterface { 
    // PERBAIKAN: Nama class tidak menyertakan .java
    
    // PERBAIKAN Poin 3: Constructor yang menerima argumen
    public PollingAnonim(String pertanyaan, List<String> pilihan) {
        super(pertanyaan, pilihan); // Memanggil constructor Polling (Parent)
    }

    @Override
    public void vote(String selectedOption, User user) throws VoteGandaException {
        // PERBAIKAN: Method vote sekarang menerima argumen User user
        if (user.getHasVoted()) { // PERBAIKAN: Method getHasVoted() sudah ditambahkan di User.java
            throw new VoteGandaException("Anda sudah menggunakan hak pilih Anda.");
        }

        // PERBAIKAN Poin 5: options sudah dideklarasikan protected di Polling.java
        if (options.containsKey(selectedOption)) { 
            options.put(selectedOption, options.get(selectedOption) + 1);
        }
    }
    
    @Override
    public String tampilkanHasil() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== HASIL POLLING ANONIM =====\n");
        int total = options.values().stream().mapToInt(Integer::intValue).sum();
        sb.append("Total Suara: ").append(total).append("\n");

        for (Map.Entry<String, Integer> entry : options.entrySet()) {
            double persentase = (total > 0) ? ((double) entry.getValue() / total) * 100 : 0;
            sb.append(entry.getKey())
              .append(": ")
              .append(entry.getValue())
              .append(" suara (")
              .append(String.format("%.2f", persentase))
              .append("%)\n");
        }
        return sb.toString();
    }
    
    // PERBAIKAN Poin 2: Getter yang dibutuhkan HasilGrafikPanel
    @Override
    public Map<String, Integer> getOptions() {
        return this.options; 
    }

    @Override
    public List<String> getPilihanList() {
        return new ArrayList<>(this.options.keySet()); 
    }
}