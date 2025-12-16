package src.Model;
import java.util.Map;

import src.HandleException.VoteGandaException;

public interface PollingInterface {
    

    void vote(String selectedOption, User user) throws VoteGandaException; // TAMBAHKAN User user
    
    String tampilkanHasil();
    String getQuestion();
    Map<String, Integer> getOptions();
}