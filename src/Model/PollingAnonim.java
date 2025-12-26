package src.Model;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import src.HandleException.VoteGandaException;

public class PollingAnonim extends Polling implements PollingInterface {

    public PollingAnonim(String pertanyaan, List<String> pilihan) {
        super(pertanyaan, pilihan);
    }

    @Override
    public void vote(String selectedOption, User user) throws VoteGandaException {
        // Option tidak valid
        if (!options.containsKey(selectedOption)) {
            throw new IllegalArgumentException("Pilihan tidak valid.");
        }
        // Tambahkan suara
        options.put(selectedOption, options.get(selectedOption) + 1);
    }

    @Override
    public String tampilkanHasil() {
        StringBuilder sb = new StringBuilder();

        //hitung total suara
        int total = options.values() 
                           .stream()
                           .mapToInt(Integer::intValue)
                           .sum();

        sb.append("Total Suara: ").append(total).append("\n");

        //tampilkan hasil per pilihan
        for (Map.Entry<String, Integer> entry : options.entrySet()) {
            double persentase = total > 0
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