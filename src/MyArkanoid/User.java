package MyArkanoid;

public class User {
    private String username;
    private String numAluno;
    private int pontos;
    private long timePlayed;


    public User() {
        this.username = "";
        this.numAluno = "";
        this.pontos = 0;
        this.timePlayed = 0;
    }

    public User(String username, String numAluno, int pontos, long timePlayed) {
        this.username = username;
        this.numAluno = numAluno;
        this.pontos = pontos;
        this.timePlayed = timePlayed;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumAluno() {
        return numAluno;
    }

    public void setNumAluno(String numAluno) {
        this.numAluno = numAluno;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public boolean validateUser() {
        this.getUsername().replaceAll(" ", "");
        this.getNumAluno().replaceAll(" ", "");

        try {
            int num = Integer.parseInt(this.getNumAluno());
        }
        catch (Exception es){
            new ArkanoidException("Número de aluno apenas pode conter valores númericos!").showError();
            return false;
        }


        if ((this.getNumAluno().isEmpty() || this.getNumAluno().isBlank() && (this.getUsername().isEmpty() || this.getUsername().isBlank()))) {
            new ArkanoidException("Nome e número de aluno é obrigatório!").showError();
            return false;
        }
        else if (this.getUsername().isEmpty() || this.getUsername().isBlank()) {
            new ArkanoidException("Nome é obrigatório!").showError();
            return false;
        }
        else if (this.getNumAluno().isEmpty() || this.getNumAluno().isBlank()) {
            new ArkanoidException("Número de aluno é obrigatório!").showError();
            return false;
        }

        return true;
    }

    public boolean validadeNumeroAluno() {
        this.getNumAluno().replaceAll(" ", "");

        if (this.getNumAluno().isEmpty() || this.getNumAluno().isBlank()) {
            new ArkanoidException("Número de aluno é obrigatório para eliminar!").showError();
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return this.username + "-" + this.numAluno + "-" + this.pontos + "-" + this.timePlayed;
    }
}
