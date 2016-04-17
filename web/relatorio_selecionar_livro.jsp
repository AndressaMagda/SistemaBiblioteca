<%@page import="java.util.List"%>
<%@page import="modelo.Livro"%>
<%@include file="templates/header.jsp" %>

<%
   List<Livro> livros = null;
   
   if (request.getAttribute("livros") != null) {
       livros = (List<Livro>) request.getAttribute("livros");
   }
%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title text-center">
                <strong>Relatório de Cliente</strong>
            </h3>
        </div>
        <div class="panel-body">

            <form method="post" action="EmprestimoControler">
                <input value="relatorio_emprestimos_livro" name="acao" type="hidden">
                
                <div class="form-group">
                    <label for="select" class="col-lg-1 col-lg-offset-4 control-label" style="margin-top: 5px;">Cliente</label>
                    <div class="col-lg-5">
                        <select class="form-control" name="id_livro" id="livro" required>
                            <% for (Livro livro: livros) { %>
                                <option value="<%= livro.getId() %>"><%= livro.getTitulo()%></option>
                            <% } %>
                        </select>
                    </div>
                </div>
                
                <div class="col-lg-8 col-lg-offset-2" style="text-align: center; margin-top: 35px;">
                    <button class="btn btn-primary" type="submit">Gerar Relatório</button>
                </div>
            </form>

        </div><!-- Fim panel-body -->
    </div><!-- Fim panel -->
    
<%@include file="templates/footer.jsp" %>

<script type="text/javascript">
    $(document).ready(function() {
        
        $('#livro').multiselect({
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true,
            buttonWidth: '200px',
        });
        
    });
</script>