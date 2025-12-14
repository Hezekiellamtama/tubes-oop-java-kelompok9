import java.util.Map;

public interface PollingInterface {
    
    //method vote sekarang menerima argumen User user
    void vote(String selectedOption, User user) throws VoteGandaException; // TAMBAHKAN User user
    
    String tampilkanHasil();
    
    //tambahkan method ini jika belum ada
    String getQuestion();
    Map<String, Integer> getOptions();
}