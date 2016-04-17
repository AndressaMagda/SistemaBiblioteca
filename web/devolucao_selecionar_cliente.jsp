<%@page import="java.util.List"%>
<%@page import="modelo.Cliente"%>
<%@include file="templates/header.jsp" %>

<%
   List<Cliente> clientes = null;
   
   if (request.getAttribute("clientes") != null) {
       clientes = (List<Cliente>) request.getAttribute("clientes");
   }
%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title text-center">
                <strong>Devolução por Cliente</strong>
            </h3>
        </div>
        <div class="panel-body">

            <form method="post" action="EmprestimoControler">
                <input value="devolucao_emprestimos_cliente" name="acao" type="hidden">
                
                <div class="form-group">
                    <label for="select" class="col-lg-1 col-lg-offset-4 control-label" style="margin-top: 5px;">Cliente</label>
                    <div class="col-lg-2">
                        <select class="form-control" name="id_cliente" id="cliente" required>
                            <% for (Cliente cliente: clientes) { %>
                                <option value="<%= cliente.getId() %>"><%= cliente.getNome() %></option>
                            <% } %>
                        </select>
                    </div>
                </div>
                
                <div class="col-lg-8 col-lg-offset-2" style="text-align: center; margin-top: 35px;">
                    <button class="btn btn-primary" type="submit">Buscar Empréstimos</button>
                </div>
            </form>

        </div><!-- Fim panel-body -->
    </div><!-- Fim panel -->
    
<%@include file="templates/footer.jsp" %>

<script type="text/javascript">
    $(document).ready(function() {
        
        $('#cliente').multiselect({
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true,
            buttonWidth: '200px',
        });
        
    });
</script>