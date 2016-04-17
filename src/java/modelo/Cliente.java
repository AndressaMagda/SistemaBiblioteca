package modelo;

public class Cliente {
    private int id;
    private int matricula;
    private String nome;
    private String tipo;

    public Cliente() {
    }
    
    public Cliente(int id, int matricula, String nome, String tipo) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
