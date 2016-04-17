package controle;

import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

public class autenticador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String semail = request.getParameter("login");
            String ssenha = request.getParameter("senha");

            Usuario user = new Usuario();
            user.setLogin(semail);
            user.setSenha(ssenha);

            UsuarioDAO userDAO = new UsuarioDAO();
            Usuario userAutenticado = userDAO.autenticacao(user);

            //Se a autenticação tiver sido feita
            if(userAutenticado != null){
                HttpSession sessao = request.getSession();
                sessao.setAttribute("userAutenticado", userAutenticado);
                //sessao.setMaxInactiveInterval(30000); //Tempo de sessao

                request.getRequestDispatcher("home.jsp").forward(request, response);
            }
            else{
                request.setAttribute("msg", "Falha ao logar. Por favor, verifique seus dados e tente novamente.");
                request.getRequestDispatcher("login_error.jsp").forward(request, response);
//                response.sendRedirect("login_error.jsp");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        if(sessao != null){
            sessao.invalidate();
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
