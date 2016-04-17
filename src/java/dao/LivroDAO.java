package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Livro;

public class LivroDAO {
    private Conexao bd = new Conexao();
    private Connection con;
    
    public boolean incluir(Livro livro) {
        boolean resultado = false;
        
        try {
            con = bd.abrirConexao();
            
            String sql = "INSERT INTO livro (titulo, autor, editora, quantidade) values (?, ?, ?, ?)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getEditora());
            ps.setInt(4, livro.getQuantidade());
            
            ps.executeUpdate();
            ps.close();
            
//            System.out.println("Livro cadastrado com Sucesso!!");
            
            resultado = true;
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return resultado;
    }
    
        public boolean alterar(Livro livro){
        boolean resultado = false;
        
        String sql = "UPDATE livro SET titulo = ?, autor = ?, editora = ?, quantidade = ? where id = ?";
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getEditora());
            ps.setInt(4, livro.getQuantidade());
            ps.setInt(5, livro.getId());
            
            ps.execute();
            ps.close();
            
//            System.out.println("Livro alterado com Sucesso!!");
            
            resultado = true;
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return resultado;
    }
    
    public boolean remover(Livro livro){
        boolean resultado = false;
        
        String sql = "UPDATE livro SET quantidade = 0 where id = ?";
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, livro.getId());
            
            ps.execute();
            ps.close();
            
//            System.out.println("Livro removido com Sucesso!!");
            
            resultado = true;
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return resultado;
    }
    
    public List<Livro> getAll(){
	String sql = "SELECT * FROM livro ORDER BY titulo";
        
	List<Livro> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Livro livro = new Livro();
                
                livro.setId(resultados.getInt("id"));
                livro.setTitulo(resultados.getString("titulo"));
                livro.setAutor(resultados.getString("autor"));
                livro.setEditora(resultados.getString("editora"));
                livro.setQuantidade(resultados.getInt("quantidade"));
                lista.add(livro);
            }
            
//            System.out.println("Livros encontrados com sucesso!");
	} catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public Livro getById(int id){
        Livro livro = null;
        
        String sql = "SELECT * FROM livro WHERE id = ?";
        
        try {
            con = bd.abrirConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, id);

            //Retorno da consulta em ResultSet
            ResultSet resultado = preparador.executeQuery();

            //Se tem registro
            while(resultado.next()){
                //Instancia o objeto usuario
                livro = new Livro();
                
                livro.setId(resultado.getInt("id"));
                livro.setTitulo(resultado.getString("titulo"));
                livro.setAutor(resultado.getString("autor"));
                livro.setEditora(resultado.getString("editora"));
                livro.setQuantidade(resultado.getInt("quantidade"));
            }
            
//            System.out.println("Livro encontrado com sucesso!");
        } catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return livro;
    }
    
    public List<Livro> getDisponiveis(){
	String sql = "SELECT * FROM livro LEFT JOIN (SELECT id_livro, COUNT(*) AS emprestados FROM emprestimo GROUP BY id_livro) AS emp ON emp.id_livro = livro.id ORDER BY titulo";
        
	List<Livro> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            
            int disponivel;
            
            while(resultados.next()){
                disponivel = (resultados.getInt("quantidade") - resultados.getInt("emprestados"));
                
                if (disponivel > 0) {
                    Livro livro = new Livro();

                    livro.setId(resultados.getInt("id"));
                    livro.setTitulo(resultados.getString("titulo"));
                    livro.setAutor(resultados.getString("autor"));
                    livro.setEditora(resultados.getString("editora"));
                    livro.setQuantidade(disponivel);
                    lista.add(livro);
                }
            }
            
            System.out.println("Livros encontrados com sucesso!");
	} catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public List<Livro> getLivrosEmprestados() {
        String sql = "SELECT DISTINCT livro.id AS id, livro.titulo AS titulo FROM emprestimo LEFT JOIN livro ON emprestimo.id_livro = livro.id WHERE emprestimo.devolvido = 'f' ORDER BY livro.titulo";
        
	List<Livro> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Livro livro = new Livro();
                
                livro.setId(resultados.getInt("id"));
                livro.setTitulo(resultados.getString("titulo"));
                lista.add(livro);
            }
            
//            System.out.println("Livros encontrados com sucesso!");
	} catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
}
