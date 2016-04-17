<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Livro"%>
<%@include file="templates/header.jsp" %>

<%
   List<ArrayList> emprestimos = null;
   
   if (request.getAttribute("emprestimos") != null) {
       emprestimos = (List<ArrayList>) request.getAttribute("emprestimos");
   }
%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title text-center">
                <strong>Relatório - Livros Mais Emprestados</strong>
            </h3>
        </div>
        <div class="panel-body">
            
            <% if (emprestimos.size() == 0) { %>
                <div class='alert alert-warning'>Nenhum livro está atualmente emprestado.</div>
            <%
                }
                else {
            %>
                <div class="table-responsive">
                    <table id="tabLivros" class="table table-striped table-bordered table-hover" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Título do Livro</th>
                                <th>Autor</th>
                                <th>Empréstimos</th>
                            </tr>
                        </thead>

                        <tbody>
                            <% for (ArrayList emprestimo: emprestimos) { %>
                                <tr>
                                    <td><%= emprestimo.get(0) %></td>
                                    <td><%= emprestimo.get(1) %></td>
                                    <td><%= emprestimo.get(2) %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } %>
            
            <div class="col-md-12">
                <button  class='btn btn-warning btn-large' onclick='window.history.go(-1)'><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>Voltar</button>
            </div>

        </div><!-- Fim panel-body -->
    </div><!-- Fim panel -->
    
<%@include file="templates/footer.jsp" %>