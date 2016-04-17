/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.ClienteDAO;
import dao.EmprestimoDAO;
import dao.LivroDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Cliente;
import modelo.Emprestimo;
import modelo.Livro;
import modelo.Usuario;

/**
 *
 * @author Andressa
 */
public class EmprestimoControler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String acao = request.getParameter("acao");
            String pagina = "";
            
            EmprestimoDAO eDAO = new EmprestimoDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            LivroDAO livroDAO = new LivroDAO();
            
            System.out.println("Ação: " + acao);
            
            if (acao != null) {
                if (acao.equals("devolucao_selecionar_cliente")) {
                    List<Cliente> clientes = clienteDAO.getClienteComEmprestimo();
                    
                    request.setAttribute("clientes", clientes);
                    pagina = "devolucao_selecionar_cliente.jsp";
                }
                else if (acao.equals("devolucao_selecionar_livro")) {
                    List<Livro> livros = livroDAO.getLivrosEmprestados();
                    
                    request.setAttribute("livros", livros);
                    pagina = "devolucao_selecionar_livro.jsp";
                }
                else if (acao.equals("relatorio_selecionar_cliente")) {
                    List<Cliente> clientes = clienteDAO.getAll();
                    
                    request.setAttribute("clientes", clientes);
                    pagina = "relatorio_selecionar_cliente.jsp";
                }
                else if (acao.equals("relatorio_selecionar_livro")) {
                    List<Livro> livros = livroDAO.getAll();
                    
                    request.setAttribute("livros", livros);
                    pagina = "relatorio_selecionar_livro.jsp";
                }
                else if (acao.equals("devolucao_emprestimos_cliente")) {
                    int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
                    
                    System.out.println("Cliente id: " + id_cliente);
                    
                    List<Emprestimo> emprestimos = eDAO.getByCliente(id_cliente, false);
                    List<ArrayList> emp = new ArrayList<>();
                    Livro livro;
                    long dias;
                    Cliente cliente = clienteDAO.getById(id_cliente);
                    double taxa_multa = 0.50;
                    double multa = 0;
                    long atraso = 0;
                    
                    if (cliente.getTipo() == "professor") { taxa_multa = 0.80; }
                    
                    for (Emprestimo e: emprestimos) {
                        livro = livroDAO.getById(e.getId_livro());
                        dias = eDAO.diasAposEmprestimo(e.getData_emprestimo());
                        multa = 0;
                        atraso = 0;
                        
                        if (dias > 7) {
                            atraso = dias - 7;
                            multa = taxa_multa * atraso;
                        }
                        
                        ArrayList a = new ArrayList();
                        a.add(0, e.getId());
                        a.add(1, livro.getTitulo());
                        a.add(2, e.getData_emprestimo());
                        a.add(3, atraso == 0 ? "-" : atraso);
                        a.add(4, multa == 0 ? "-" : multa);
                        
                        emp.add(a);
                    }
                    
                    request.setAttribute("emprestimos", emp);
                    request.setAttribute("id_cliente", id_cliente);
                    request.setAttribute("nome_cliente", cliente.getNome());
                    pagina = "devolucao_emprestimos_cliente.jsp";
                }
                else if (acao.equals("devolucao_emprestimos_livro")) {
                    int id_livro = Integer.parseInt(request.getParameter("id_livro"));
                    
                    System.out.println("Livro id: " + id_livro);
                    
                    List<Emprestimo> emprestimos = eDAO.getByLivro(id_livro, false);
                    List<ArrayList> emp = new ArrayList<>();
                    Livro livro = livroDAO.getById(id_livro);
                    long dias;
                    Cliente cliente;
                    double taxa_multa;
                    double multa = 0;
                    long atraso = 0;
                    
                    for (Emprestimo e: emprestimos) {
                        cliente = clienteDAO.getById(e.getId_cliente());
                        dias = eDAO.diasAposEmprestimo(e.getData_emprestimo());
                        multa = 0;
                        atraso = 0;
                        taxa_multa = 0.50;
                        
                        if (cliente.getTipo() == "professor") { taxa_multa = 0.80; }
                        
                        if (dias > 7) {
                            atraso = dias - 7;
                            multa = taxa_multa * atraso;
                        }
                        
                        ArrayList a = new ArrayList();
                        a.add(0, e.getId());
                        a.add(1, cliente.getNome());
                        a.add(2, e.getData_emprestimo());
                        a.add(3, atraso == 0 ? "-" : atraso);
                        a.add(4, multa == 0 ? "-" : multa);
                        
                        emp.add(a);
                    }
                    
                    request.setAttribute("emprestimos", emp);
                    request.setAttribute("id_livro", id_livro);
                    request.setAttribute("titulo_livro", livro.getTitulo());
                    pagina = "devolucao_emprestimos_livro.jsp";
                }
                else if (acao.equals("devolver_por_cliente")) {
                    int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
                    String[] devolver = request.getParameterValues("devolver");
                    String data_devolucao = request.getParameter("data_devolucao");
                    
                    String[] splitEmprestimo = data_devolucao.split("/");
                    data_devolucao = splitEmprestimo[2] + "-" + splitEmprestimo[1] + "-" + splitEmprestimo[0];
                    
                    String sucesso = "nao";
                    double taxa_multa = 0.50;
                    double multa = 0;
                    Livro livro;
                    long dias;
                    long atraso = 0;
                    Cliente cliente = clienteDAO.getById(id_cliente);
                    Emprestimo emprestimo = null;
                    double multa_total = 0;
                    
                    if (cliente.getTipo() == "professor") { taxa_multa = 0.80; }
                    
                    if (devolver != null) {
                        for (int i = 0; i < devolver.length; i++) {
                            System.out.println("Devolver: " + devolver[i]);

                            //Calculando multa
                            emprestimo = eDAO.getById(Integer.parseInt(devolver[i]));
                            dias = eDAO.diasAposEmprestimo(emprestimo.getData_emprestimo());
                            multa = 0;
                            atraso = 0;

                            if (dias > 7) {
                                atraso = dias - 7;
                                multa = taxa_multa * atraso;
                            }

                            System.out.println("Multa: " + multa);
                            multa_total += multa;

                            // Devolve o livro
                            eDAO.devolver(Integer.parseInt(devolver[i]), data_devolucao, multa);
                        }

                        sucesso = "sim";
                    }
                    else {
                        sucesso = "erroselecionar";
                    }
                    
                    List<Emprestimo> emprestimos = eDAO.getByCliente(id_cliente, false);
                    List<ArrayList> emp = new ArrayList<>();
                    
                    for (Emprestimo e: emprestimos) {
                        livro = livroDAO.getById(e.getId_livro());
                        dias = eDAO.diasAposEmprestimo(e.getData_emprestimo());
                        multa = 0;
                        atraso = 0;
                        
                        if (dias > 7) {
                            atraso = dias - 7;
                            multa = taxa_multa * atraso;
                        }
                        
                        ArrayList a = new ArrayList();
                        a.add(0, e.getId());
                        a.add(1, livro.getTitulo());
                        a.add(2, e.getData_emprestimo());
                        a.add(3, atraso == 0 ? "-" : atraso);
                        a.add(4, multa == 0 ? "-" : multa);
                        
                        emp.add(a);
                    }
                    
                    request.setAttribute("sucesso", sucesso);
                    request.setAttribute("multatotal", multa_total);
                    request.setAttribute("emprestimos", emp);
                    request.setAttribute("id_cliente", id_cliente);
                    request.setAttribute("nome_cliente", cliente.getNome());
                    pagina = "devolucao_emprestimos_cliente.jsp";
                }
                else if (acao.equals("devolver_por_livro")) {
                    int id_livro = Integer.parseInt(request.getParameter("id_livro"));
                    String[] devolver = request.getParameterValues("devolver");
                    String data_devolucao = request.getParameter("data_devolucao");
                    
                    String[] splitEmprestimo = data_devolucao.split("/");
                    data_devolucao = splitEmprestimo[2] + "-" + splitEmprestimo[1] + "-" + splitEmprestimo[0];
                    
                    String sucesso = "nao";
                    double taxa_multa;
                    double multa = 0;
                    Livro livro = livroDAO.getById(id_livro);
                    long dias;
                    long atraso = 0;
                    Cliente cliente;
                    Emprestimo emprestimo = null;
                    double multa_total = 0;
                    
                    if (devolver != null) {
                        for (int i = 0; i < devolver.length; i++) {
                            System.out.println("Devolver: " + devolver[i]);

                            //Calculando multa
                            emprestimo = eDAO.getById(Integer.parseInt(devolver[i]));
                            cliente = clienteDAO.getById(emprestimo.getId_cliente());
                            dias = eDAO.diasAposEmprestimo(emprestimo.getData_emprestimo());
                            multa = 0;
                            atraso = 0;
                            taxa_multa = 0.50;

                            if (cliente.getTipo() == "professor") { taxa_multa = 0.80; }

                            if (dias > 7) {
                                atraso = dias - 7;
                                multa = taxa_multa * atraso;
                            }

                            System.out.println("Multa: " + multa);
                            multa_total += multa;

                            // Devolve o livro
                            eDAO.devolver(Integer.parseInt(devolver[i]), data_devolucao, multa);
                        }

                        sucesso = "sim";
                    }
                    else {
                        sucesso = "erroselecionar";
                    }
                    
                    List<Emprestimo> emprestimos = eDAO.getByLivro(id_livro, false);
                    List<ArrayList> emp = new ArrayList<>();
                    
                    for (Emprestimo e: emprestimos) {
                        cliente = clienteDAO.getById(e.getId_cliente());
                        dias = eDAO.diasAposEmprestimo(e.getData_emprestimo());
                        multa = 0;
                        atraso = 0;
                        taxa_multa = 0.50;
                        
                        if (cliente.getTipo() == "professor") { taxa_multa = 0.80; }
                        
                        if (dias > 7) {
                            atraso = dias - 7;
                            multa = taxa_multa * atraso;
                        }
                        
                        ArrayList a = new ArrayList();
                        a.add(0, e.getId());
                        a.add(1, cliente.getNome());
                        a.add(2, e.getData_emprestimo());
                        a.add(3, atraso == 0 ? "-" : atraso);
                        a.add(4, multa == 0 ? "-" : multa);
                        
                        emp.add(a);
                    }
                    
                    request.setAttribute("sucesso", sucesso);
                    request.setAttribute("multatotal", multa_total);
                    request.setAttribute("emprestimos", emp);
                    request.setAttribute("id_livro", id_livro);
                    request.setAttribute("titulo_livro", livro.getTitulo());
                    pagina = "devolucao_emprestimos_livro.jsp";
                }
                else if (acao.equals("relatorio_emprestimos_cliente")) {
                    int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
                    
                    System.out.println("Cliente id: " + id_cliente);
                    
                    List<Emprestimo> emprestimos = eDAO.getAllByCliente(id_cliente);
                    List<ArrayList> emp = new ArrayList<>();
                    Livro livro;
                    Cliente cliente = clienteDAO.getById(id_cliente);
                    double totalgasto = 0;
                    
                    for (Emprestimo e: emprestimos) {
                        livro = livroDAO.getById(e.getId_livro());
                        
                        String[] splitEmprestimo = e.getData_emprestimo().split("-");
                        String data_emprestimo = splitEmprestimo[2] + "/" + splitEmprestimo[1] + "/" + splitEmprestimo[0];
                        
                        String data_devolucao = "-";
                        
                        if (e.getData_devolucao() != null) {
                            String[] splitDevolucao = e.getData_devolucao().split("-");
                            data_devolucao = splitDevolucao[2] + "/" + splitDevolucao[1] + "/" + splitDevolucao[0];
                        }
                        
                        ArrayList a = new ArrayList();
                        a.add(0, e.getId());
                        a.add(1, livro.getTitulo());
                        a.add(2, data_emprestimo);
                        a.add(3, data_devolucao);
                        a.add(4, e.getMulta() == 0 ? "-" : e.getMulta());
                        
                        totalgasto += e.getMulta();
                        
                        emp.add(a);
                    }
                    
                    request.setAttribute("totalgasto", totalgasto);
                    request.setAttribute("emprestimos", emp);
                    request.setAttribute("id_cliente", id_cliente);
                    request.setAttribute("nome_cliente", cliente.getNome());
                    pagina = "relatorio_emprestimos_cliente.jsp";
                }
                else if (acao.equals("relatorio_emprestimos_livro")) {
                    int id_livro = Integer.parseInt(request.getParameter("id_livro"));
                    
                    System.out.println("Livro id: " + id_livro);
                    
                    List<Emprestimo> emprestimos = eDAO.getAllByLivro(id_livro);
                    List<ArrayList> emp = new ArrayList<>();
                    Livro livro = livroDAO.getById(id_livro);
                    Cliente cliente;
                    double totalgasto = 0;
                    
                    for (Emprestimo e: emprestimos) {
                        cliente = clienteDAO.getById(e.getId_cliente());
                        
                        String[] splitEmprestimo = e.getData_emprestimo().split("-");
                        String data_emprestimo = splitEmprestimo[2] + "/" + splitEmprestimo[1] + "/" + splitEmprestimo[0];
                        
                        String data_devolucao = "-";
                        
                        if (e.getData_devolucao() != null) {
                            String[] splitDevolucao = e.getData_devolucao().split("-");
                            data_devolucao = splitDevolucao[2] + "/" + splitDevolucao[1] + "/" + splitDevolucao[0];
                        }
                        
                        ArrayList a = new ArrayList();
                        a.add(0, e.getId());
                        a.add(1, cliente.getNome());
                        a.add(2, data_emprestimo);
                        a.add(3, data_devolucao);
                        a.add(4, e.getMulta() == 0 ? "-" : e.getMulta());
                        
                        totalgasto += e.getMulta();
                        
                        emp.add(a);
                    }
                    
                    request.setAttribute("totalgasto", totalgasto);
                    request.setAttribute("emprestimos", emp);
                    request.setAttribute("id_livro", id_livro);
                    request.setAttribute("titulo_livro", livro.getTitulo());
                    pagina = "relatorio_emprestimos_livro.jsp";
                }
                else if (acao.equals("relatorio_livros_emprestados")) {
                    List<Emprestimo> emprestimos = eDAO.getLivrosEmprestados();
                    List<ArrayList> emp = new ArrayList<>();
                    Livro livro;
                    Cliente cliente;
                    double totalgasto = 0;
                    
                    for (Emprestimo e: emprestimos) {
                        livro = livroDAO.getById(e.getId_livro());
                        cliente = clienteDAO.getById(e.getId_cliente());
                        
                        String[] splitEmprestimo = e.getData_emprestimo().split("-");
                        String data_emprestimo = splitEmprestimo[2] + "/" + splitEmprestimo[1] + "/" + splitEmprestimo[0];
                        
                        String data_devolucao = "-";
                        
                        if (e.getData_devolucao() != null) {
                            String[] splitDevolucao = e.getData_devolucao().split("-");
                            data_devolucao = splitDevolucao[2] + "/" + splitDevolucao[1] + "/" + splitDevolucao[0];
                        }
                        
                        ArrayList a = new ArrayList();
                        a.add(0, e.getId());
                        a.add(1, livro.getTitulo());
                        a.add(2, cliente.getNome());
                        a.add(3, data_emprestimo);
                        
                        emp.add(a);
                    }
                    
                    request.setAttribute("emprestimos", emp);
                    pagina = "relatorio_livros_emprestados.jsp";
                }
                else if (acao.equals("relatorio_livros_mais_emprestados")) {
                    List<ArrayList> emprestimos = eDAO.getLivrosMaisEmprestados();
                    List<ArrayList> emp = new ArrayList<>();
                    Livro livro;
                    
                    for (ArrayList e: emprestimos) {
                        livro = livroDAO.getById((int) e.get(0));
                        
                        ArrayList a = new ArrayList();
                        a.add(0, livro.getTitulo());
                        a.add(1, livro.getAutor());
                        a.add(2, e.get(1));
                        
                        emp.add(a);
                    }
                    
                    request.setAttribute("emprestimos", emp);
                    pagina = "relatorio_livros_mais_emprestados.jsp";
                }
                else if (acao.equals("emprestimo")) { //Tela de solicitação de emprestimo
                    List<Cliente> clientesSemAtraso = clienteDAO.getClientesSemAtraso(); //Lista com os clientes sem entregas em atraso
                    
//                    for(int i=0; i<clientesSemAtraso.size(); i++){
//                        System.out.println("clientesSemAtraso: " + clientesSemAtraso.get(i).getNome() + " Tipo: "+ clientesSemAtraso.get(i).getTipo());
//                    }
                    List<Livro> livros = livroDAO.getDisponiveis();
//                    for(int i=0; i<livros.size(); i++){
//                        System.out.println("livros: " + livros.get(i).getTitulo()+ " ID: "+ livros.get(i).getId());
//                    }
                    
                    request.setAttribute("clientesSemAtraso", clientesSemAtraso);
                    request.setAttribute("livros", livros);
                    
                    pagina = "emprestimo.jsp";
                }
                else if(acao.equals("emprestar")){ //Efetua (insere) o emprestimo
                    String[] livrosID =  request.getParameterValues("livrosCadastrados[]");
                    String clienteID = request.getParameter("clientesAptos");
                    System.out.println("Pegando o ID Cliente: " + clienteID);
                    
                    String dataEmprestimo = this.formata_Data(request.getParameter("dataEmprestimo"));
                    System.out.println("DATA em String: " + dataEmprestimo);
                    
                    HttpSession sessao = request.getSession();
                    Usuario user = (Usuario)sessao.getAttribute("userAutenticado");
                    
                    System.out.println("ID Usuario: " + user.getId());
                    
                    Emprestimo e = new Emprestimo();
                    boolean emprestar = true;
                    
                    for(int i=0; i<livrosID.length; i++){
                        emprestar =  eDAO.emprestar(Integer.parseInt(clienteID), Integer.parseInt(livrosID[i]), user.getId(), dataEmprestimo);
                    }
                    
                    if(emprestar) request.setAttribute("sucesso", "sim");
                    else
                        request.setAttribute("sucesso", "não");
                    
                    List<Cliente> clientesSemAtraso = clienteDAO.getClientesSemAtraso(); //Lista com os clientes sem entregas em atraso
                    List<Livro> livros = livroDAO.getAll();
                    request.setAttribute("clientesSemAtraso", clientesSemAtraso);
                    request.setAttribute("livros", livros);
                    
                    pagina = "emprestimo.jsp";
                }
            }
            
            RequestDispatcher rd = request.getRequestDispatcher(pagina);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(EmprestimoControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(EmprestimoControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public java.sql.Date converte_String_to_Date(String dataString){
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");  
      try {  
          java.sql.Date data = new java.sql.Date(fmt.parse(dataString).getTime());
          System.out.println(data);
      } catch (ParseException ex) {
          ex.getMessage();
      }
        return null;
    }
    
    public String formata_Data(String date){ //Converte para o formato yyyy-MM-dd
        date = date.replace("/", "-");
        String retorno = date.substring(6);
        retorno+=date.substring(2,5);
        retorno +="-"+date.substring(0,2);
        
        return retorno;
    }

}
