package dao;

import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Conexao bd = new Conexao();
    private Connection con;
    
    public void cadastro(Usuario user){
        
        try {
          con = bd.abrirConexao();
          System.out.println("Conexão obtida com sucesso.");
          String sql = "INSERT INTO USUARIO (nome, login, senha) values (?, ?, ?)";
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, user.getNome());
                ps.setString(2, user.getLogin());
                ps.setString(3, user.getSenha());

                ps.executeUpdate();
                ps.close();

                System.out.println("Cadastrado com Sucesso!!");
            } catch (SQLException ex) {
                System.err.println("Erro - " + ex.getMessage());
            }
        }
        catch (Exception e) {
          System.out.println("Problemas ao tentar conectar com o banco de dados: " + e);
        }
        
        
    }
    
    public void alterar(Usuario user){
        
        String sql = "UPDATE USUARIO SET NOME=?, LOGIN=?, SENHA=? where id = ?";
        try {
            con = bd.abrirConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getNome());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getSenha());
            ps.setInt(4, user.getId());
            
            ps.execute();
            ps.close();
            
            System.out.println("Alterado com Sucesso!!");
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
    }
    
    public void remover(Usuario user){
        String sql = "DELETE FROM USUARIO WHERE id = ?";
        try {
            con = bd.abrirConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            
            ps.execute();
            ps.close();
            
            System.out.println("Removido com Sucesso!!");
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
    }
    
    public List<Usuario> buscarTodos(Usuario usuario){
	String sql = "SELECT * FROM USUARIO";
	List<Usuario> lista = new ArrayList<>();
	try{
            con = bd.abrirConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            while(resultados.next()){
                Usuario user = new Usuario();
                user.setId(resultados.getInt("id"));
                user.setNome(resultados.getString("nome"));
                user.setLogin(resultados.getString("login"));
                user.setSenha(resultados.getString("senha"));
                lista.add(user);
            }
	}catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        return lista;
    }
    
    public Usuario buscarporId(Integer id){
        Usuario userRetorno = null;
        String sql = "SELECT * FROM USUARIO WHERE id=?";
        try{
            con = bd.abrirConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, id);

            //Retorno da consulta em ResultSet
            ResultSet resultado = preparador.executeQuery();

            //Se tem registro
            while(resultado.next()){
                //Instancia o objeto usuario
                userRetorno = new Usuario();
                userRetorno.setId(resultado.getInt("id"));
                userRetorno.setNome(resultado.getString("nome"));
                userRetorno.setLogin(resultado.getString("login"));
                userRetorno.setSenha(resultado.getString("senha"));
            }
            System.out.println("Encontrado com sucesso!");
        }catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        return userRetorno;

    }
    
    public Usuario autenticacao(Usuario user){
        Usuario userRetorno = null;
        String sql = "SELECT * FROM USUARIO WHERE login=? and senha=?";
        try{
            con=bd.abrirConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setString(1, user.getLogin());
            preparador.setString(2, user.getSenha());

            //Retorno da consulta em ResultSet
            ResultSet resultado = preparador.executeQuery();

            //Se tem registro
            if(resultado.next()){
                //Instancia o objeto usuario
                userRetorno = new Usuario();
                userRetorno.setId(resultado.getInt("id"));
                userRetorno.setNome(resultado.getString("nome"));
                userRetorno.setLogin(resultado.getString("login"));
                userRetorno.setSenha(resultado.getString("senha"));
            }
            System.out.println("Usuário encontrado com sucesso!");
        }catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        return userRetorno;

    }
    
}
