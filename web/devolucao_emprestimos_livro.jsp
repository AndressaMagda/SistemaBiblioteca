<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Emprestimo"%>
<%@include file="templates/header.jsp" %>

<%
   List<ArrayList> emprestimos = null;
   
   if (request.getAttribute("emprestimos") != null) {
       emprestimos = (List<ArrayList>) request.getAttribute("emprestimos");
   }
   
   int id_livro = (Integer) request.getAttribute("id_livro");
   String titulo_livro = (String) request.getAttribute("titulo_livro");
   
   DecimalFormat df = new DecimalFormat("#,##0.00");
%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title text-center">
                <strong>Visualizar Empréstimos de Livro</strong>
            </h3>
        </div>
        <div class="panel-body">

            <div class="table-responsive">
                <form method="post" action="EmprestimoControler">
                    <input value="devolver_por_livro" name="acao" type="hidden">
                    <input value="<%= id_livro %>" name="id_livro" type="hidden">
                    
                    <%
                        Object s = request.getAttribute("sucesso");
                        
                        if (s != null) {
                            String sucesso = (String) s;

                            if (sucesso.equals("sim")) {
                                double multa_total = (Double) request.getAttribute("multatotal");
                                out.println("<div class='alert alert-success'>Devolução realizada com sucesso!<br><br>Multa Total: R$ " + df.format(multa_total) + "</div>");
                            }
                            else if (sucesso.equals("erroselecionar")) {
                                out.println("<div class='alert alert-danger'>Você precisa selecionar o Empréstimo a ser devolvido.</div>");
                            }
                            else {
                                out.println("<div class='alert alert-danger'>Ocorreu um problema, tente novamente.</div>");
                            }
                        }
                    %>
                    
                    <p style="margin-bottom: 20px; margin-top: 10px; font-size: 12pt; text-align: center;"><strong>Livro: </strong><%= titulo_livro %></p>
                    
                    <table id="tabEmprestimo" class="table table-striped table-bordered dataTable" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Cliente</th>
                                <th>Data de Empréstimo</th>
                                <th>Dias de Atraso</th>
                                <th>Multa</th>
                                <th>Devolver</th>
                            </tr>
                        </thead>

                        <tfoot>
                            <tr>
                                <th>Cliente</th>
                                <th>Data de Empréstimo</th>
                                <th>Dias de Atraso</th>
                                <th>Multa</th>
                                <th>Devolver</th>
                            </tr>
                        </tfoot>

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
                                    <td><input type="checkbox" name="devolver" value="<%= emprestimo.get(0) %>"></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                
                <div class="form-group" style="margin-top: 25px; text-align: right;">
                    <label style="margin-right: 15px;">Data de Devolução:</label>
                    <input type="text" name="data_devolucao" id="dataDevolucao" required>
                </div>
                
                <div class="col-lg-12" style="text-align: right; margin-top: 15px; margin-bottom: 10px;">
                    <button  class='btn btn-warning btn-large' onclick='window.history.go(-1)'><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>Voltar</button>
                    <button class="btn btn-primary" type="submit" style="margin-left: 30px;">Devolver</button>
                </div>
            </form>

        </div><!-- Fim panel-body -->
    </div><!-- Fim panel -->
    
<%@include file="templates/footer.jsp" %>

<script type="text/javascript">
    
    $(document).ready(function() {
         $('#tabEmprestimo').dataTable();
         
         $('#dataDevolucao').datepicker({
            format: "dd/mm/yyyy",
            language: "pt-BR",
            todayHighlight: true,
            endDate: "today",
            orientation: "bottom auto",
            autoclose: true
        });
    } );

</script>