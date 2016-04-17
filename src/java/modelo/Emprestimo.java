package modelo;

public class Emprestimo {
    
    private int id;
    private int id_cliente;
    private int id_livro;
    private int id_usuario;
    private String data_emprestimo;
    private String data_devolucao;
    private boolean devolvido;
    private double multa;
    
    public Emprestimo() {
        
    }
    
    public Emprestimo(int id, int id_cliente, int id_livro, int id_usuario, String data_emprestimo, String data_devolucao, boolean devolvido, double multa) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_livro = id_livro;
        this.id_usuario = id_usuario;
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao = data_devolucao;
        this.devolvido = devolvido;
        this.multa = multa;
    }

    public int getId() {
        return id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getId_livro() {
        return id_livro;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getData_emprestimo() {
        return data_emprestimo;
    }

    public String getData_devolucao() {
        return data_devolucao;
    }

    public boolean isDevolvido() {
        return devolvido;
    }
    
    public double getMulta() {
        return multa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setData_emprestimo(String data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    public void setData_devolucao(String data_devolucao) {
        this.data_devolucao = data_devolucao;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }
    
    public void setMulta(double multa) {
        this.multa = multa;
    }
}
