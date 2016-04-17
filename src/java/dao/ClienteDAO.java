package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;

public class ClienteDAO {
    
    private Conexao bd = new Conexao();
    private Connection con;
    
    public boolean incluir(Cliente cliente) {
        boolean resultado = false;
        
        String sql = "INSERT INTO cliente (nome, matricula, tipo, ativo) values (?, ?, ?, ?)";
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, cliente.getNome());
            ps.setInt(2, cliente.getMatricula());
            ps.setString(3, cliente.getTipo());
            ps.setBoolean(4, true);
            
            ps.executeUpdate();
            ps.close();
            
            System.out.println("Cliente cadastrado com Sucesso!!");
            
            resultado = true;
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return resultado;
    }
    
    public boolean alterar(Cliente cliente){
        boolean resultado = false;
        
        String sql = "UPDATE cliente SET matricula = ?, nome = ?, tipo = ?, ativo = ? where id = ?";
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, cliente.getMatricula());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getTipo());
            ps.setBoolean(4, true);
            ps.setInt(5, cliente.getId());
            
            ps.execute();
            ps.close();
            
//            System.out.println("Cliente alterado com Sucesso!!");
            
            resultado = true;
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return resultado;
        
    }
    
    public boolean remover(Cliente cliente){
        boolean resultado = false;
        
        //String sql = "DELETE FROM cliente WHERE id = ?";
        String sql = "UPDATE cliente SET ativo=false WHERE id = ?";
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, cliente.getId());
            
            ps.execute();
            ps.close();
            
//            System.out.println("Cliente removido com Sucesso!!");
            
            resultado = true;
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return resultado;
    }
    
    public List<Cliente> getAll(){
	String sql = "SELECT * FROM cliente WHERE ativo=true ORDER BY nome";
        
	List<Cliente> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Cliente cliente = new Cliente();
                
                cliente.setId(resultados.getInt("id"));
                cliente.setNome(resultados.getString("nome"));
                cliente.setMatricula(resultados.getInt("matricula"));
                cliente.setTipo(resultados.getString("tipo"));
                lista.add(cliente);
            }
            
            System.out.println("Clientes encontrados com sucesso!");
	} catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
    public Cliente getById(int id){
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id = ? and ativo = ?";
        
        try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, id);
            preparador.setBoolean(2, true);

            //Retorno da consulta em ResultSet
            ResultSet resultado = preparador.executeQuery();

            //Se tem registro
            while(resultado.next()){
                //Instancia o objeto usuario
                cliente = new Cliente();
                cliente.setId(resultado.getInt("id"));
                cliente.setMatricula(resultado.getInt("matricula"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setTipo(resultado.getString("tipo"));
            }
            
//            System.out.println("Cliente encontrado com sucesso!");
        } catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return cliente;
    }
    
    public List<Cliente> getClienteComEmprestimo() {
        String sql = "SELECT DISTINCT cliente.id AS id, cliente.nome AS nome FROM emprestimo LEFT JOIN cliente ON emprestimo.id_cliente = cliente.id WHERE emprestimo.devolvido = 'f' AND cliente.ativo = 't' ORDER BY cliente.nome";
        
	List<Cliente> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Cliente cliente = new Cliente();
                
                cliente.setId(resultados.getInt("id"));
                cliente.setNome(resultados.getString("nome"));
                lista.add(cliente);
            }
            
//            System.out.println("Clientes encontrados com sucesso!");
	} catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
    }
    
     public List<Cliente> getClientesSemAtraso(){
         
        String sql = "SELECT DISTINCT * FROM cliente WHERE ativo = 't' ORDER BY nome";
        
	List<Cliente> lista = new ArrayList<>();
        
	try {
            con = bd.abrirConexao();
            
            PreparedStatement preparador = con.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            
            while(resultados.next()){
                Cliente cliente = new Cliente();
                
                try {
                    if(!verificaAtraso(resultados.getInt("id"))){ //Se o Cliente não tiver atrasos
                        cliente.setId(resultados.getInt("id"));
                        cliente.setNome(resultados.getString("nome"));
                        cliente.setTipo(resultados.getString("tipo"));
                        cliente.setMatricula(resultados.getInt("matricula"));
                        lista.add(cliente);
                    }
                } catch (ParseException ex) {
                    ex.getMessage();
                }
            }
            
//            System.out.println("Clientes sem multa encontrados com sucesso!");
	} catch(SQLException e){
            System.err.println("Erro - " + e.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
        return lista;
     }
     
    public boolean verificaAtraso(int id_cliente) throws ParseException {
        String sql = "SELECT * FROM emprestimo WHERE id_cliente = ? AND devolvido = 'f'";

        try {
            con = bd.abrirConexao();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, id_cliente);
            
            ResultSet resultados = ps.executeQuery();
            String data_emprestimo="";
            
            while(resultados.next()){
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                data_emprestimo = formato.format(resultados.getDate("data_emprestimo"));
                
                // Verificação de emprestimos com atraso
                if (diasAposEmprestimo(data_emprestimo) > 7) {
                    System.out.println("Cliente com Atraso!!");
                    return true;
                }
            }
            
        } catch (SQLException ex) {
            System.err.println("Erro - " + ex.getMessage());
        }
        finally{
            bd.fecharConexao();
        }
        
//        System.out.println("Cliente sem atrasos.");
        return false;
    }
    
    public long diasAposEmprestimo(String data_emprestimo) throws ParseException {
        // Pega data atual e subtrai a data passada e retorna número de dias
        // dias = data_atual - data_emprestimo;
        Date data_atual = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date data_emp = format.parse(data_emprestimo);

        long diferenca = (( data_atual.getTime() - data_emp.getTime() ) / (24 * 60 * 60 * 1000) );

//        System.out.println("Dias após Empréstimo: " + diferenca);
        
        return diferenca;
    }
    
    
    public boolean isRegistro(int matricula) throws SQLException{  
        ResultSet resultados = null;
        try 
        { 
            con = bd.abrirConexao();
            String sql = "SELECT * FROM cliente WHERE matricula = ? LIMIT 1";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, matricula);

            resultados = ps.executeQuery();
            
            if(resultados.next()){  
                return true;  //A matricula já existe
            }  
        } 
        catch(Exception erro) 
        { 
            System.out.println("Ocorreu um erro no metodo isRegistro: " + erro.getMessage()); 
        }
        finally{
            bd.fecharConexao();
        }

        return false;
    }
    
    public boolean atualiza_matricula(int id, int matricula_nova){
        ResultSet resultados = null;
        int  matricula_cliente = 0;
        
        try 
        { 
            con = bd.abrirConexao();
            String sql = "SELECT matricula FROM cliente WHERE id = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            
            resultados = ps.executeQuery();
            
            if(resultados.next()){  
                matricula_cliente = resultados.getInt("matricula"); //Matricula do cliente sem alteração
            }
            
            if(matricula_cliente==matricula_nova) return true; //Ele pode querer mudar os demais campos e deixar a matricula com está
            else
                return !this.isRegistro(matricula_nova); //retorna true se já existir usuario com essa matricula
        } 
        catch(Exception erro) 
        { 
            System.out.println("Ocorreu um erro no metodo Cliente.conCliente(): " + erro.getMessage()); 
        }
        finally{
            bd.fecharConexao();
        }

        return false;
    }
    
    
}
