package src.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import src.HandleException.VoteGandaException;
import java.util.LinkedHashMap;

public abstract class Polling {
    
    private String question; //menampung pertanyaan polling
    protected Map<String, Integer> options;  //menampung pilihan dan jumlah votenya

    //Constructor Polling
    public Polling(String question, List<String> pilihanList) {
        this.question = question;
        this.options = new LinkedHashMap<>(); //LinkedHashMap agar urutan pilihan tetap terjaga
        for (String pilihan : pilihanList) {  //inisialisasi pilihan dari daftar
            this.options.put(pilihan, 0); // Inisialisasi vote 0 untuk setiap pilihan
        }
    }

    //Abstract Method Vote 
    public abstract void vote(String selectedOption, User user) throws VoteGandaException;
    
    //Abstract Getters 
    public abstract Map<String, Integer> getOptions();
    public abstract List<String> getPilihanList();

    // Getter Umum
    public String getQuestion() {
        return question;
    }

    //method untuk mereset vote pada polling
    public void resetVotes() {
        //reset semua pilihan ke 0
        for (Map.Entry<String, Integer> entry : options.entrySet()) {
            options.put(entry.getKey(), 0);
        }
    }
}