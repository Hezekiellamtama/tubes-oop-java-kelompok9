package src.HandleException;
public class VoteGandaException extends Exception {
// Jika user sudah memilih akan muncul tulisan   
    public VoteGandaException(String pesan) {
        super("Anda Sudah Menggunakan Hak Vote Anda! ");
    }
}