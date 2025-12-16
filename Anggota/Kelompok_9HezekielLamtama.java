package Anggota;
class Poll {
    String title;
    int totalVotes; 

    public Poll(String title) {
        this.title = title;
        this.totalVotes = 0;
    }

    // KODE BARU UNTUK COMMIT 3/3
    public void vote() {
        this.totalVotes++;
        System.out.println("Suara berhasil ditambahkan ke: " + title);
    }
}

class HezekielLogic {
    public static void main(String[] args) {
        // Output sesuai permintaan tugas
        System.out.println("Saya anggota Hezekiel NIM 12345678"); 
    }
}