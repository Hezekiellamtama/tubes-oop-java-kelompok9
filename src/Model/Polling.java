package src.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.HandleException.VoteGandaException;

import java.util.LinkedHashMap;

public abstract class Polling {
    
    private String question;
    //visibility diubah menjadi protected
    protected Map<String, Integer> options; 

    //Constructor Polling
    public Polling(String question, List<String> pilihanList) {
        this.question = question;
        this.options = new LinkedHashMap<>();
        for (String pilihan : pilihanList) {
            this.options.put(pilihan, 0); // Inisialisasi vote 0
        }
    }

    //Abstract methods wajib
    public abstract void vote(String selectedOption, User user) throws VoteGandaException;
    
    //Abstract Getters
    public abstract Map<String, Integer> getOptions(); 
    public abstract List<String> getPilihanList();

    // Getter Umum
    public String getQuestion() {
        return question;
    }
}