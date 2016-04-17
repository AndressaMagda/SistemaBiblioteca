<%@page import="java.text.DecimalFormat"%>
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
   
   int id_cliente = (Integer) request.getAttribute("id_cliente");
   String nome_cliente = (String) request.getAttribute("nome_cliente");
   double totalgasto = (Double) request.getAttribute("totalgasto");
   
   DecimalFormat df = new DecimalFormat("#,##0.00");
%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title text-center">
                <strong>Relatório - Empréstimos de Cliente</strong>
            </h3>
        </div>
        <div class="panel-body">

            <div class="table-responsive">
                <p style="margin-bottom: 20px; margin-top: 10px;"><strong>Cliente: </strong><%= nome_cliente %></p>
                
                <table id="tabLivros" class="table table-striped table-bordered table-hover" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Título do Livro</th>
                            <th>Data de Empréstimo</th>
                            <th>Data de Devolução</th>
                            <th>Multa</th>
                        </tr>
                    </thead>

                    <tbody>
                        <% for (ArrayList emprestimo: emprestimos) { %>
                            <tr>
                                <td><%= emprestimo.get(1) %></td>
                                <td><%= emprestimo.get(2) %></td>
                                <td><%= emprestimo.get(3) %></td>
                                <%
                                    String valor = "-";
                                    if (emprestimo.get(4) != "-") { valor = "R$ " + df.format(emprestimo.get(4)); }
                                %>
                                <td><%= valor %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            
            <p style="margin-bottom: 30px; padding-right: 15px; text-align: right;"><strong>Total Gasto: </strong>R$ <%= df.format(totalgasto) %></p>

            <div class="col-md-12">
                <button  class='btn btn-warning btn-large' onclick='window.history.go(-1)'><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>Voltar</button>
            </div>

        </div><!-- Fim panel-body -->
    </div><!-- Fim panel -->
    
<%@include file="templates/footer.jsp" %>