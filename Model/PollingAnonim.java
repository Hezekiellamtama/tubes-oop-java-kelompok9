package Model;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class PollingAnonim extends Polling implements PollingInterface {

    // Konstruktor
    public PollingAnonim(String pertanyaan, List<String> pilihan) {
        super(pertanyaan, pilihan);
    }

    @Override
    public void vote(String selectedOption, User user) throws VoteGandaException {
        // Cek apakah user sudah pernah vote
        if (user.getHasVoted()) {
            throw new VoteGandaException("Anda sudah menggunakan hak pilih Anda.");
        }

        // Tambah suara jika opsi valid
        if (options.containsKey(selectedOption)) {
            options.put(selectedOption, options.get(selectedOption) + 1);
        }
    }

    @Override
    public String tampilkanHasil() {
        StringBuilder sb = new StringBuilder();
        sb.append("------ HASIL POLLING ANONIM ------\n");

        int total = options.values().stream().mapToInt(Integer::intValue).sum();
        sb.append("Total Suara: ").append(total).append("\n");

        for (Map.Entry<String, Integer> entry : options.entrySet()) {
            double persentase = (total > 0)
                    ? ((double) entry.getValue() / total) * 100
                    : 0;

            sb.append(entry.getKey())
              .append(": ")
              .append(entry.getValue())
              .append(" suara (")
              .append(String.format("%.2f", persentase))
              .append("%)\n");
        }
        return sb.toString();
    }

    @Override
    public Map<String, Integer> getOptions() {
        return this.options;
    }

    @Override
    public List<String> getPilihanList() {
        return new ArrayList<>(this.options.keySet());
    }
}
