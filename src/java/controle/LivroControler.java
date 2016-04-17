package controle;

import dao.LivroDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Livro;

public class LivroControler extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try(PrintWriter out = response.getWriter()){
            String acao = request.getParameter("acao");
            String pagina = "";
            LivroDAO lDAO = new LivroDAO();
            
//            System.out.println("Acao: " + acao);

            if (acao != null && acao.equals("inserir")) {
                String titulo = new String(request.getParameter("titulo").getBytes("iso-8859-1"), "UTF-8");
                String autor = new String(request.getParameter("autor").getBytes("iso-8859-1"), "UTF-8");
                String editora = new String(request.getParameter("editora").getBytes("iso-8859-1"), "UTF-8");
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                Livro l = new Livro(0, titulo, autor, editora, quantidade);
                boolean inserido =  lDAO.incluir(l);

                String sucesso = "sim";

                if (!inserido) {
                    sucesso = "não";
                }

                request.setAttribute("sucesso", sucesso);

                pagina = "inserir_livro.jsp";

            }
            else if(acao != null && acao.equals("visualizar")){
                List<Livro> livros =  lDAO.getAll();

                request.setAttribute("livros", livros);
                pagina = "visualizar_livros.jsp";
            }
            else if(acao != null && acao.equals("editar")){
                int id = Integer.parseInt(request.getParameter("id"));
                Livro l = lDAO.getById(id);
                request.setAttribute("livro", l);
                pagina = "alterar_livro.jsp";
            }
            else if(acao != null && acao.equals("atualizar")){
                int id = Integer.parseInt(request.getParameter("id"));
                String titulo = new String(request.getParameter("titulo").getBytes("iso-8859-1"), "UTF-8");
                String autor = new String(request.getParameter("autor").getBytes("iso-8859-1"), "UTF-8");
                String editora = new String(request.getParameter("editora").getBytes("iso-8859-1"), "UTF-8");
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                        
//                System.out.println("Id: " + id);
//                System.err.println("Titulo: " + titulo);
//                System.err.println("autor: " + autor);
//                System.err.println("editora: " + editora);
//                System.err.println("editora: " + quantidade);

                Livro l = new Livro(id, titulo, autor, editora, quantidade);

                boolean alterado =  lDAO.alterar(l);
                String mensagem = "Atualizado com sucesso!";
                if (!alterado)
                    mensagem = "Houve um problema. Tente novamente";
                request.setAttribute("mensagem", mensagem);
                List<Livro> livros = lDAO.getAll();
                request.setAttribute("livros", livros);
                pagina = "visualizar_livros.jsp";
            }
            else if(acao != null && acao.equals("excluir")){
                Livro l = new Livro();
                String id = request.getParameter("id");
                l.setId(Integer.parseInt(id));

                boolean removido = lDAO.remover(l);
                String mensagem = "Removido com sucesso!";
                if (!removido)
                    mensagem = "Houve um problema. Tente novamente";
                request.setAttribute("mensagem", mensagem);

                List<Livro> livros = lDAO.getAll();
                request.setAttribute("livros", livros);
                pagina = "visualizar_livros.jsp";
            }
            RequestDispatcher rd = request.getRequestDispatcher(pagina);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
