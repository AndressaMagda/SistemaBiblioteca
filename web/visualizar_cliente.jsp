<%@page import="java.util.List"%>
<%@page import="modelo.Cliente"%>
<%@include file="templates/header.jsp" %>

<%
   List<Cliente> clientes = null;
   if (request.getAttribute("clientes")!=null)
       clientes = (List<Cliente>) request.getAttribute("clientes");
%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title text-center">
                    <strong>Visualizar Clientes</strong>
            </h3>
        </div>
        <div class="panel-body">

            <div class="table-responsive">
                <table id="tabCliente" class="table table-striped table-bordered dataTable" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Indice</th>
                            <th>Matricula</th>
                            <th>Nome</th>
                            <th>Tipo</th>
                            <th>Ações</th>
                        </tr>
                    </thead>

                    <tfoot>
                        <tr>
                            <th>Indice</th>
                            <th>Matricula</th>
                            <th>Nome</th>
                            <th>Tipo</th>
                            <th>Ações</th>
                        </tr>
                    </tfoot>

                    <tbody>
                        <%for(int i=0; i<clientes.size(); i++){%>
                            <tr>
                                <td><%=(i+1) %></td>
                                <td><%=clientes.get(i).getMatricula() %></td>
                                <td><%=clientes.get(i).getNome() %></td>
                                <td><%=clientes.get(i).getTipo() %></td>
                                <td>
                                    <a href="ClienteControler?acao=editar&id=<%=clientes.get(i).getId() %>"  class='btn btn-sm btn-warning' role='button'><span class="glyphicon glyphicon-pencil"></span></a>
                                    <a onclick="remover(<%=clientes.get(i).getId() %>);" class="btn btn-sm btn-danger" role='button'><span class="glyphicon glyphicon-remove"></span></a>
                                </td>
                            </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>

            <div class="col-md-12">
                <button  class='btn btn-warning btn-large' onclick='window.history.go(-1)'><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>Voltar</button>
            </div>

        </div><!-- Fim panel-body -->
    </div><!-- Fim panel -->
    
<%@include file="templates/footer.jsp" %>

<script type="text/javascript">
    
    $(document).ready(function() {
         $('#tabCliente').dataTable();
    } );
    
    function remover(id){
        if (confirm("Você realmente deseja remover esse cliente?")) {
            window.location.href = "ClienteControler?acao=excluir&id="+id;
        } 
    }

</script>