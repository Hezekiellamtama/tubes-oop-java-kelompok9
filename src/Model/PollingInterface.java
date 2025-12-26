package src.Model;

import src.HandleException.VoteGandaException;
import java.util.List;
import java.util.Map;

public interface PollingInterface {
    String getQuestion(); //menampung pertanyaan polling
    void vote(String choice, User user) throws VoteGandaException; 
    String tampilkanHasil(); //menampilkan hasil polling
    Map<String, Integer> getOptions(); 
    List<String> getPilihanList();
    void resetVotes();  //method untuk mereset vote pada polling
}