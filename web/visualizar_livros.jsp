<%@page import="java.util.List"%>
<%@page import="modelo.Livro"%>
<%@include file="templates/header.jsp" %>

<%
   List<Livro> livros = null;
   if (request.getAttribute("livros")!=null)
       livros = (List<Livro>) request.getAttribute("livros");
%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title text-center">
                    <strong>Visualizar Livros</strong>
            </h3>
        </div>
        <div class="panel-body">

            <div class="table-responsive">
                <table id="tabLivros" class="table table-hover table-striped table-bordered dataTable" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Indice</th>
                            <th>Titulo</th>
                            <th>Autor</th>
                            <th>Editora</th>
                            <th>Quantidade</th>
                            <th>Ações</th>
                        </tr>
                    </thead>

                    <tfoot>
                        <tr>
                            <th>Indice</th>
                            <th>Titulo</th>
                            <th>Autor</th>
                            <th>Editora</th>
                            <th>Quantidade</th>
                            <th>Ações</th>
                        </tr>
                    </tfoot>

                    <tbody>
                        <%for(int i=0; i<livros.size(); i++){%>
                            <tr>
                                <td><%=(i+1) %></td>
                                <td><%=livros.get(i).getTitulo() %></td>
                                <td><%=livros.get(i).getAutor() %></td>
                                <td><%=livros.get(i).getEditora() %></td>
                                <td><%=livros.get(i).getQuantidade() %></td>
                                <td>
                                    <a href="LivroControler?acao=editar&id=<%=livros.get(i).getId() %>"  class='btn btn-sm btn-warning' role='button'><span class="glyphicon glyphicon-pencil"></span></a>
                                    <a onclick="remover(<%=livros.get(i).getId() %>);" class="btn btn-sm btn-danger" role='button'><span class="glyphicon glyphicon-remove"></span></a>
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
         $('#tabLivros').dataTable();
    } );
    
    function remover(id){
        if (confirm("Você realmente deseja remover esse livro?")) {
            window.location.href = "LivroControler?acao=excluir&id="+id;
        } 
    }

</script>