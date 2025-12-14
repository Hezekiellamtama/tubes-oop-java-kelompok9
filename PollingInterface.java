import java.util.Map;

public interface PollingInterface {
    
    // UBAH BARIS INI:
    void vote(String selectedOption, User user) throws VoteGandaException; // TAMBAHKAN User user
    
    String tampilkanHasil();
    
    // Tambahkan method ini jika belum ada
    String getQuestion();
    Map<String, Integer> getOptions();
}