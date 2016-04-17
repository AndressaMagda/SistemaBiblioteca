package controle;

import dao.ClienteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cliente;


public class ClienteControler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try(PrintWriter out = response.getWriter()){
            String acao = request.getParameter("acao");
            String pagina = "";
            ClienteDAO cDAO = new ClienteDAO();

            if (acao != null && acao.equals("inserir")) {
                String nome = new String(request.getParameter("nome").getBytes("iso-8859-1"), "UTF-8");
                int matricula = Integer.parseInt(request.getParameter("matricula"));
                String tipo = request.getParameter("tipo");
                boolean inserido = false;
                String sucesso="";
                
                try {
                    if(!cDAO.isRegistro(matricula)){
                        Cliente c = new Cliente(0, matricula, nome, tipo);
                        inserido =  cDAO.incluir(c);
                        sucesso = "sim";
                    }
                    else{
                        inserido = false;
                        sucesso="não";
                        request.setAttribute("matricula", "Essa Matricula já existe. Por favor tente novamente com outra");
                        
                    }
                    request.setAttribute("sucesso", sucesso);

                    pagina = "inserir_cliente.jsp";
                    
                } catch (SQLException ex) {
                    System.err.println("Ocorreu um erro na Inserção do Cliente: " + ex.getMessage()); 
                }
            }
            else if(acao != null && acao.equals("visualizar")){
                List<Cliente> clientes =  cDAO.getAll();

                request.setAttribute("clientes", clientes);
                pagina = "visualizar_cliente.jsp";
            }
            else if(acao != null && acao.equals("editar")){
                int id = Integer.parseInt(request.getParameter("id"));
                Cliente c = cDAO.getById(id);
                request.setAttribute("cliente", c);
                pagina = "alterar_cliente.jsp";
            }
            else if(acao.equals("atualizar")){
                int id = Integer.parseInt(request.getParameter("id"));
                int matricula = Integer.parseInt(request.getParameter("matricula"));
                String nome = new String(request.getParameter("nome").getBytes("iso-8859-1"), "UTF-8");
                String tipo = request.getParameter("tipo");
                boolean alterado;
                String sucesso="";
                
                if(cDAO.atualiza_matricula(id, matricula)){
                    Cliente c = new Cliente(id, matricula, nome, tipo);
                    alterado =  cDAO.alterar(c);
                    sucesso = "sim";
                    pagina = "visualizar_cliente.jsp"; 
                }
                else{
                    alterado = false;
                    sucesso="não";
                    request.setAttribute("matricula", "Essa Matricula já existe. Por favor tente novamente com outra");
                    pagina = "ClienteControler?acao=editar&id="+id;
                }
                request.setAttribute("sucesso", sucesso);
                List<Cliente> clientes = cDAO.getAll();
                request.setAttribute("clientes", clientes);
            }
            else if(acao != null && acao.equals("excluir")){
                Cliente c = new Cliente();
                String id = request.getParameter("id");
                c.setId(Integer.parseInt(id));

                boolean removido = cDAO.remover(c);
                String mensagem = "Removido com sucesso!";
                if (!removido)
                    mensagem = "Houve um problema. Tente novamente";
                request.setAttribute("mensagem", mensagem);

                List<Cliente> clientes = cDAO.getAll();
                request.setAttribute("clientes", clientes);
                pagina = "visualizar_cliente.jsp";
            }
            RequestDispatcher rd = request.getRequestDispatcher(pagina);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
//        String acao = request.getParameter("acao");
//        String pagina = "";
//        ClienteDAO cDAO = new ClienteDAO();
//
//        if (acao != null && acao.equals("inserir")) {
//            String nome = request.getParameter("nome");
//            int matricula = Integer.parseInt(request.getParameter("matricula"));
//            String tipo = request.getParameter("tipo");
//
//            Cliente c = new Cliente(0, matricula, nome, tipo);
//            boolean inserido =  cDAO.incluir(c);
//
//            String sucesso = "sim";
//
//            if (!inserido) {
//                sucesso = "não";
//            }
//
//            request.setAttribute("sucesso", sucesso);
//
//            pagina = "inserir_cliente.jsp";
//            
//            RequestDispatcher saida = request.getRequestDispatcher(pagina);
//            saida.forward(request, response);
//        }
//        else if(acao != null && acao.equals("visualizar")){
//            List<Cliente> clientes =  cDAO.getAll();
//
//            request.setAttribute("clientes", clientes);
//            RequestDispatcher saida = request.getRequestDispatcher("visualizar_cliente.jsp");
//            saida.forward(request, response);
//
////            pagina = "visualizar_cliente.jsp";
//        }
//        else if(acao != null && acao.equals("editar")){
//            Cliente c = new Cliente();
////            int id = Integer.parseInt(request.getParameter("id"));
////            c = cDAO.getById(id);
////            request.setAttribute("cliente", c);
////            request.getRequestDispatcher("alterar_cliente.jsp").forward(request, response);
//                int id = Integer.parseInt(request.getParameter("id"));
//                c = cDAO.getById(id);
//                request.setAttribute("cliente", c);
//                pagina = "alterar_cliente.jsp";
//                RequestDispatcher rd = request.getRequestDispatcher(pagina);
//                rd.forward(request, response);
//            
////            response.sendRedirect("ClienteControler?acao=visualizar");
//
//        }
//        else if(acao != null && acao.equals("atualizar")){
//                int id = Integer.parseInt(request.getParameter("id"));
//                int matricula = Integer.parseInt(request.getParameter("matricula"));
//                String nome = request.getParameter("nome");
//                String tipo = request.getParameter("tipo");
//                
//                Cliente c = new Cliente(id, matricula, nome, tipo);
//                
//                boolean inserido =  cDAO.alterar(c);
//                String mensagem = "Seu produto foi atualizado com sucesso!";
//                if (!inserido)
//                    mensagem = "Houve um problema. Tente novamente";
//                request.setAttribute("mensagem", mensagem);
//                List<Cliente> clientes = cDAO.getAll();
//                request.setAttribute("clientes", clientes);
//                pagina = "visualizar_cliente.jsp";
//                RequestDispatcher rd = request.getRequestDispatcher(pagina);
//                rd.forward(request, response);
//            }
//            else if(acao != null && acao.equals("excluir")){
//                Cliente c = new Cliente();
//                String id = request.getParameter("id");
//                c.setId(Integer.parseInt(id));
//                
//                boolean removido = cDAO.remover(c);
//                String mensagem = "Removido com sucesso!";
//                if (!removido)
//                    mensagem = "Houve um problema. Tente novamente";
//                request.setAttribute("mensagem", mensagem);
//                
//                List<Cliente> clientes = cDAO.getAll();
//                request.setAttribute("clientes", clientes);
//                pagina = "visualizar_cliente.jsp";
//                //response.sendRedirect("ClienteControler?acao=visualizar");
//                RequestDispatcher rd = request.getRequestDispatcher(pagina);
//                rd.forward(request, response);
//            }

//        rd.forward(request, response);
                
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
//        ClienteDAO cDAO = new ClienteDAO();
//        String acao = request.getParameter("acao");
//        
//        //Capturando parametros da tela
//        String nome = request.getParameter("nome");
//        int matricula = Integer.parseInt(request.getParameter("matricula"));
//        String tipo = request.getParameter("tipo");
//
//        //Criando obj cliente e atribuindo valores da tela
//        Cliente c = new Cliente();
//        c.setMatricula(matricula);
//        c.setNome(nome);
//        c.setTipo(tipo);
//        
//        //Salvando no BD
//        if (acao != null && acao.equals("inserir")) {
//            boolean inserido =  cDAO.incluir(c);
//            String sucesso = "sim";
//
//            if (!inserido) {
//                sucesso = "não";
//            }
//
//            request.setAttribute("sucesso", sucesso);
//
//            RequestDispatcher saida = request.getRequestDispatcher("inserir_cliente.jsp");
//            saida.forward(request, response);
//        }
//        else if(acao != null && acao.equals("editar")){
//            String id = request.getParameter("id");
//            c.setId(Integer.parseInt(id));
//            
//            cDAO.alterar(c);
//            response.sendRedirect("ClienteControler?acao=visualizar");
//        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
