import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public abstract class Polling {
    
    private String question;
    protected Map<String, Integer> options; 

    //konstructor Polling
    public Polling(String question, List<String> pilihanList) {
        this.question = question;
        this.options = new LinkedHashMap<>();
        for (String pilihan : pilihanList) {
            this.options.put(pilihan, 0); // Inisialisasi vote 0
        }
    }

    //abstract methods wajib
    public abstract void vote(String selectedOption, User user) throws VoteGandaException;
    
    //abstract Getters
    public abstract Map<String, Integer> getOptions(); 
    public abstract List<String> getPilihanList();

    //getter Umum
    public String getQuestion() {
        return question;
    }
}