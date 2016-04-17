package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import modelo.Emprestimo;

public class EmprestimoDAO {
    private Conexao bd = new Conexao();
    private Connection con;
    
    public boolean emprestar(int id_cliente, int id_livro, int id_usuario, String data_emprestimo) {
        boolean resultado = false;

        String sql = "INSERT INTO emprestimo (id_cliente, id_livro, id_usuario, data_emprestimo, devolvido, multa) values (?, ?, ?, ?, ?, ?)";
        
        java.sql.Date dtValue = java.sql.Date.valueOf(data_emprestimo);
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, id_cliente);
            ps.setInt(2, id_livro);
            ps.setInt(3, id_usuario);
            ps.setDate(4, dtValue);
            ps.setBoolean(5, false);
            ps.setDouble(6, 0);
            
            ps.executeUpdate();
            ps.close();
            
            System.out.println("Emprestimo efetuado com Sucesso!!");
            
            resultado = true;
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return resultado;
    }
    
    public boolean devolver(int id_emprestimo, String data_devolucao, double multa) throws ParseException {
        boolean resultado = false;
        
        String sql = "UPDATE emprestimo SET data_devolucao = ?, devolvido = ?, multa = ? where id = ?";
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            java.sql.Date sqlDate = java.sql.Date.valueOf(data_devolucao);
            
            System.out.println("Data: " + sqlDate.toString());
            
            ps.setDate(1, sqlDate);
            ps.setBoolean(2, true);
            ps.setDouble(3, multa);
            ps.setInt(4, id_emprestimo);
            
            ps.execute();
            ps.close();
            
            System.out.println("Devolvido com Sucesso!!");
            
            resultado = true;
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return resultado;
    }
    
    public long diasAposEmprestimo(String data_emprestimo) throws ParseException {
        // Pega data atual e subtrai a data passada e retorna número de dias
        // dias = data_atual - data_emprestimo;
        Date data_atual = new Date();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date data_emp = format.parse(data_emprestimo);
        
        long diferenca = (( data_atual.getTime() - data_emp.getTime() ) / (24 * 60 * 60 * 1000) );
        
        System.out.println("Dias após Empréstimo: " + diferenca);
        
        return diferenca;
    }
    
    public List<Emprestimo> getAll(){
	String sql = "SELECT * FROM emprestimo";
        
	List<Emprestimo> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Emprestimo emprestimo = new Emprestimo();
                
                emprestimo.setId(resultados.getInt("id"));
                emprestimo.setId_cliente(resultados.getInt("id_cliente"));
                emprestimo.setId_livro(resultados.getInt("id_livro"));
                emprestimo.setId_usuario(resultados.getInt("id_usuario"));
                emprestimo.setData_emprestimo(resultados.getString("data_emprestimo"));
                emprestimo.setData_devolucao(resultados.getString("data_devolucao"));
                emprestimo.setDevolvido(resultados.getBoolean("devolvido"));
                emprestimo.setMulta(resultados.getDouble("multa"));
                lista.add(emprestimo);
            }
            
            System.out.println("Emprestimos encontrados com sucesso!");
	}  catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public Emprestimo getById(int id){
        Emprestimo emprestimo = null;
        
        String sql = "SELECT * FROM emprestimo WHERE id = ?";
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, id);

            //Retorno da consulta em ResultSet
            ResultSet resultado = preparador.executeQuery();

            //Se tem registro
            while(resultado.next()){
                //Instancia o objeto usuario
                emprestimo = new Emprestimo();
                emprestimo.setId(resultado.getInt("id"));
                emprestimo.setId_cliente(resultado.getInt("id_cliente"));
                emprestimo.setId_livro(resultado.getInt("id_livro"));
                emprestimo.setId_usuario(resultado.getInt("id_usuario"));
                emprestimo.setData_emprestimo(resultado.getString("data_emprestimo"));
                emprestimo.setData_devolucao(resultado.getString("data_devolucao"));
                emprestimo.setDevolvido(resultado.getBoolean("devolvido"));
                emprestimo.setMulta(resultado.getDouble("multa"));
            }
            
            System.out.println("Emprestimo encontrado com sucesso!");
        } catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return emprestimo;
    }
    
    public List<Emprestimo> getLivrosEmprestados() {
	String sql = "SELECT * FROM emprestimo WHERE devolvido = ?";
        
	List<Emprestimo> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            
            preparador.setBoolean(1, false);
            
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Emprestimo emprestimo = new Emprestimo();
                
                emprestimo.setId(resultados.getInt("id"));
                emprestimo.setId_cliente(resultados.getInt("id_cliente"));
                emprestimo.setId_livro(resultados.getInt("id_livro"));
                emprestimo.setId_usuario(resultados.getInt("id_usuario"));
                emprestimo.setData_emprestimo(resultados.getString("data_emprestimo"));
                emprestimo.setData_devolucao(resultados.getString("data_devolucao"));
                emprestimo.setDevolvido(resultados.getBoolean("devolvido"));
                emprestimo.setMulta(resultados.getDouble("multa"));
                lista.add(emprestimo);
            }
            
            System.out.println("Emprestimos encontrados com sucesso!");
	}  catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public List<ArrayList> getLivrosMaisEmprestados() {
	String sql = "SELECT id_livro, COUNT(*) AS emprestimos FROM emprestimo GROUP BY id_livro ORDER BY emprestimos DESC";
        
	List<ArrayList> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                ArrayList a = new ArrayList();
                
                a.add(0, resultados.getInt("id_livro"));
                a.add(1, resultados.getInt("emprestimos"));
                lista.add(a);
            }
            
            System.out.println("Emprestimos encontrados com sucesso!");
	}  catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public List<Emprestimo> getByCliente(int id_cliente, boolean devolvido) {
	String sql = "SELECT * FROM emprestimo WHERE id_cliente = ? AND devolvido = ?";
        
	List<Emprestimo> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            
            preparador.setInt(1, id_cliente);
            preparador.setBoolean(2, devolvido);
            
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Emprestimo emprestimo = new Emprestimo();
                
                emprestimo.setId(resultados.getInt("id"));
                emprestimo.setId_cliente(resultados.getInt("id_cliente"));
                emprestimo.setId_livro(resultados.getInt("id_livro"));
                emprestimo.setId_usuario(resultados.getInt("id_usuario"));
                emprestimo.setData_emprestimo(resultados.getString("data_emprestimo"));
                emprestimo.setData_devolucao(resultados.getString("data_devolucao"));
                emprestimo.setDevolvido(resultados.getBoolean("devolvido"));
                emprestimo.setMulta(resultados.getDouble("multa"));
                lista.add(emprestimo);
            }
            
            System.out.println("Emprestimos encontrados com sucesso!");
	}  catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public List<Emprestimo> getByLivro(int id_livro, boolean devolvido) {
	String sql = "SELECT * FROM emprestimo WHERE id_livro = ? AND devolvido = ?";
        
	List<Emprestimo> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            
            preparador.setInt(1, id_livro);
            preparador.setBoolean(2, devolvido);
            
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Emprestimo emprestimo = new Emprestimo();
                
                emprestimo.setId(resultados.getInt("id"));
                emprestimo.setId_cliente(resultados.getInt("id_cliente"));
                emprestimo.setId_livro(resultados.getInt("id_livro"));
                emprestimo.setId_usuario(resultados.getInt("id_usuario"));
                emprestimo.setData_emprestimo(resultados.getString("data_emprestimo"));
                emprestimo.setData_devolucao(resultados.getString("data_devolucao"));
                emprestimo.setDevolvido(resultados.getBoolean("devolvido"));
                emprestimo.setMulta(resultados.getDouble("multa"));
                lista.add(emprestimo);
            }
            
            System.out.println("Emprestimos encontrados com sucesso!");
	}  catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public List<Emprestimo> getAllByCliente(int id_cliente) {
	String sql = "SELECT * FROM emprestimo WHERE id_cliente = ? ORDER BY data_emprestimo";
        
	List<Emprestimo> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            
            preparador.setInt(1, id_cliente);
            
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Emprestimo emprestimo = new Emprestimo();
                
                emprestimo.setId(resultados.getInt("id"));
                emprestimo.setId_cliente(resultados.getInt("id_cliente"));
                emprestimo.setId_livro(resultados.getInt("id_livro"));
                emprestimo.setId_usuario(resultados.getInt("id_usuario"));
                emprestimo.setData_emprestimo(resultados.getString("data_emprestimo"));
                emprestimo.setData_devolucao(resultados.getString("data_devolucao"));
                emprestimo.setDevolvido(resultados.getBoolean("devolvido"));
                emprestimo.setMulta(resultados.getDouble("multa"));
                lista.add(emprestimo);
            }
            
            System.out.println("Emprestimos encontrados com sucesso!");
	}  catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public List<Emprestimo> getAllByLivro(int id_livro) {
	String sql = "SELECT * FROM emprestimo WHERE id_livro = ? ORDER BY data_emprestimo";
        
	List<Emprestimo> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            
            preparador.setInt(1, id_livro);
            
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Emprestimo emprestimo = new Emprestimo();
                
                emprestimo.setId(resultados.getInt("id"));
                emprestimo.setId_cliente(resultados.getInt("id_cliente"));
                emprestimo.setId_livro(resultados.getInt("id_livro"));
                emprestimo.setId_usuario(resultados.getInt("id_usuario"));
                emprestimo.setData_emprestimo(resultados.getString("data_emprestimo"));
                emprestimo.setData_devolucao(resultados.getString("data_devolucao"));
                emprestimo.setDevolvido(resultados.getBoolean("devolvido"));
                emprestimo.setMulta(resultados.getDouble("multa"));
                lista.add(emprestimo);
            }
            
            System.out.println("Emprestimos encontrados com sucesso!");
	}  catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
}
