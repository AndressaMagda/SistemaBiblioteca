package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    private Connection c = null;

    public Connection abrirConexao() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/biblioteca",
                    "postgres","12345678");
        } catch (ClassNotFoundException ex) {
            System.err.println("Falta o driver!");
        } 
        finally{
            return c;
        }
    }

    public void fecharConexao() {
        try {
            c.close();
        } catch (SQLException sql) {
            System.err.println(sql.getMessage());
        }
    }
}
